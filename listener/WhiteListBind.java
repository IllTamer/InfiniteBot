package me.illtamer.infinitebot.listener;

import fr.xephi.authme.api.v3.AuthMeApi;
import me.illtamer.infinitebot.InfiniteBot;
import me.illtamer.infinitebot.bot.Bot;
import me.illtamer.infinitebot.date.ReDate;
import me.illtamer.infinitebot.event.group.GroupMessageEvent;
import me.illtamer.infinitebot.utils.Config;
import me.illtamer.infinitebot.utils.Drivers;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitTask;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 白名单
 * @author IllTamer
 */
public class WhiteListBind implements Listener
{
    /** 短期内总连接次数 */
    private static int joins;
    /** 上次有玩家加入的时间 */
    private static long last;
    /** 可疑地址 连接次数 */
    private static HashMap<String, Integer> attackers = new HashMap<>();
    /** 玩家uuid 时间+验证码 */
    private static LinkedHashMap<String, ReDate> blacks = new LinkedHashMap<>();


    public WhiteListBind()
    {
        if (InfiniteBot.getMainFile().getConfig().getBoolean("Options.WhiteList.Enable"))
        {
            Drivers.sendMessage("&7验证码白名单 &2√",false);
        }
    }


    /**
     * 登录事件
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(AsyncPlayerPreLoginEvent event)
    {//uuid:qq
        if (!InfiniteBot.getMainFile().getConfig().getBoolean("Options.WhiteList.Enable")) { return; }

        if (InfiniteBot.getMainFile().getConfig().getBoolean("Options.WhiteList.AuthMe"))
        {
            if (AuthMeApi.getInstance().isRegistered(event.getName())) { return; }
        }
        FileConfiguration bind = InfiniteBot.getBindFile().getConfig();
        String uuid = event.getUniqueId().toString();
        if (//未绑定
                bind.getConfigurationSection("") == null
                || !bind.getConfigurationSection("")
                        .getKeys(false).contains(event.getUniqueId().toString())
            )
        {
            if (blacks.size() != 0 && blacks.get(uuid) != null)
            {//缓存中未注册
                ReDate date = blacks.get(uuid);
                kickUnregisteredPlayer(event,date);
                return;
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(new Date(System.currentTimeMillis() + 8 * 60000L));
            String code = getNonce_str();
            ReDate date = new ReDate(time,code);

            blacks.put(uuid, date);
            kickUnregisteredPlayer(event, date);
            try {
                    Bukkit.getScheduler().runTaskLater(InfiniteBot.getInstance(), () ->
                    {//每八分钟移除验证码
                        blacks.remove(uuid);
                    }, 9600);
            } catch (Exception ignored) { }
        }
    }


    /**
     * 组验证码事件
     */
    @EventHandler
    public void onCode(GroupMessageEvent event)
    {
        if (!InfiniteBot.getMainFile().getConfig().getBoolean("Options.WhiteList.Enable")) { return; }
        if (event.getGroupID() != InfiniteBot.getMainFile().getConfig().getLong("Options.Group")) { return; }
        Bukkit.getScheduler().runTaskAsynchronously(InfiniteBot.getInstance(),() ->
        {
            String code = isMyCode(event.getMsg());
            if (code == null) { return; }

            if (Bot.getAPI().getPlayerId(event.getSenderID()) != null)
            {
                event.sendMessage("您已绑定了角色，请勿重复操作！");
                return;
            }

            for (Map.Entry<String, ReDate> black : blacks.entrySet())
            {
                ReDate date = black.getValue();
                String uuid = black.getKey();

                if (!date.getCode().equals(code)) { continue; }
                Bot.getAPI().setBind(UUID.fromString(uuid),event.getSenderID());
                event.sendMessage(InfiniteBot.getMainFile().getConfig().getString("Options.WhiteList.Success"));

                Config bind = InfiniteBot.getBindFile();
                bind.getConfig().set(uuid,event.getSenderID());
                bind.save();

                try {
                    blacks.remove(uuid);
                } catch (Exception ignore) { }

                return;
            }
            event.sendMessage("验证码错误，请检查！");
        });
    }


    /**
     * 防压测
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDefence(AsyncPlayerPreLoginEvent event)
    {
        if (!InfiniteBot.getMainFile().getConfig().getBoolean("Options.WhiteList.Defence")) { return; }
        if (System.currentTimeMillis() - last > 30000) { last = System.currentTimeMillis(); return; }
        last = System.currentTimeMillis();
        if (++joins <= 20) { return; }
        //释放
        Bukkit.getScheduler().runTaskLater(InfiniteBot.getInstance(), () ->
            {
                if (joins > 0) { joins--; }
            }, 60 * 20L);

        String address = event.getAddress().getHostAddress();
        int now = 1;
        if (!attackers.containsKey(address))
        { attackers.put(address,now); }
        else
        {
            now = attackers.get(address) + 1;
            attackers.put(address,now);
        }
        if (now < 5) { return; }
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,"§c§l检测非法连接，已将您踢出服务器！");
        attackers.put(address,++now);
        Drivers.sendMessage("&e&l已拦截攻击%num%次！".replace("%num%",String.valueOf(now)),true);
    }


    private void kickUnregisteredPlayer(AsyncPlayerPreLoginEvent event, ReDate date)
    {//踢出玩家
        StringBuilder builder = new StringBuilder();
        for (String temp : InfiniteBot.getMainFile().getConfig().getStringList("Options.WhiteList.Messages"))
        {//踢出信息
            builder.append(temp.replace("%code%", date.getCode())
                    .replace("%time%", date.getTime()).replace("%player%", event.getName())).append("\n");
        }
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, builder.toString().replace("&", "§"));
    }

    private static final String SYMBOLS = "0123456789";
    private static final Random RANDOM = new SecureRandom();
    private static String getNonce_str()
    {//返回验证码
        char[] nonceChars = new char[4];

        for (int index = 0; index < nonceChars.length; ++index)
        { nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length())); }

        return new String(nonceChars);
    }

    private static String isMyCode(String str)
    {//判断是否可能为验证码
        try {
            long code = Long.parseLong(str);
            if (code > 9999) { return null; }
            char[] codes = str.toCharArray();
            if (codes.length == 4) { return str; }
        } catch (Exception e) {
            return null;
        }
        return str;
    }
}
