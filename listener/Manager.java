package me.illtamer.infinitebot.listener;

import me.illtamer.infinitebot.InfiniteBot;
import me.illtamer.infinitebot.bot.Bot;
import me.illtamer.infinitebot.event.InfiniteEvent;
import me.illtamer.infinitebot.event.MessageReceiveEvent;
import me.illtamer.infinitebot.event.group.GroupMessageEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.*;

/**
 * 群管理功能
 * @author IllTamer
 */
public class Manager implements Listener
{
    private static final List<String> ALREADY = new ArrayList<>();

    /**
     * 功能切换
     */
    @EventHandler
    public void changeEvent(MessageReceiveEvent event)
    {
        FileConfiguration main = InfiniteBot.getMainFile().getConfig();
        if (!main.getBoolean("Options.Manager.BasicCommand")) { return; }
        if (!event.getMsg().startsWith("开启") || !event.getMsg().startsWith("关闭")) { return; }
        if (!main.getLongList("Admins").contains(event.getEvent().getSender().getId()))
        { event.sendMessage(main.getString("Messages.NoPermission")); return; }
        //除重
        if (ALREADY.contains(event.getMsg()))
        { ALREADY.remove(event.getMsg()); return; }
        ALREADY.add(event.getMsg());

        if (event.getMsg().startsWith("开启"))
        {
            String key = event.getMsg().substring("开启".length());
            switch (key)
            {
                case "防撤回":
                    main.set("Options.PreventRecall.Enable",true);
                    InfiniteBot.getMainFile().save();
                    event.sendMessage("设置已更新");
                    break;
                default:
                    return;
            }
        }
        else if (event.getMsg().startsWith("关闭"))
        {
            String key = event.getMsg().substring("关闭".length());
            switch (key)
            {
                case "防撤回":
                    main.set("Options.PreventRecall.Enable",false);
                    InfiniteBot.getMainFile().save();
                    event.sendMessage("设置已更新");
                    break;
                default:
                    return;
            }
        }
    }


    /**
     * 基础回复
     */
    @EventHandler
    public void basicReply(GroupMessageEvent event)
    {
        FileConfiguration main = InfiniteBot.getMainFile().getConfig();
        if (!main.getBoolean("Options.Manager.BasicCommand")) { return; }
        if ("今日新玩家".equals(event.getMsg()))
        {
            Bukkit.getScheduler().runTaskAsynchronously(InfiniteBot.getInstance(),() ->
            {
                int now = 0;
                for (OfflinePlayer player : Bukkit.getOfflinePlayers())
                {
                    if (equals(new Date(System.currentTimeMillis()),new Date(player.getFirstPlayed())))
                    {
                        now += 1;
                    }
                }
                event.sendMessage(main.getString("Messages.NewPlayer").replace("%num%",String.valueOf(now)));
            });
        }
        else if ("强制下线".equals(event.getMsg()))
        {
            UUID uuid = Bot.getAPI().getPlayerId(event.getSenderID());
            if (uuid == null)
            { event.sendMessage("您未绑定玩家！"); return; }

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if (!Bukkit.getOnlinePlayers().contains(offlinePlayer.getPlayer()))
            { event.sendMessage("您绑定的角色并未在线！"); return; }

            Bukkit.getScheduler().runTask(InfiniteBot.getInstance(),() ->
                    offlinePlayer.getPlayer().kickPlayer("§c§l您已被强制下线！"));
            event.sendMessage(main.getString("Messages.KickBind").replace("%name%",offlinePlayer.getName()));
        }
    }



    /**
     * 自动同意入群
     */
    @EventHandler
    public void autoAcceptJoin(InfiniteEvent e)
    {
        if (!InfiniteBot.getMainFile().getConfig().getBoolean("Options.Manager.AutoAcceptJoin")) { return; }
        if (e.getEvent() instanceof MemberJoinRequestEvent)
        {
            ((MemberJoinRequestEvent) e.getEvent()).accept();
        }
    }


    /**
     * 自动同意好友申请
     */
    @EventHandler
    public void autoAcceptFriend(InfiniteEvent e)
    {
        if (!InfiniteBot.getMainFile().getConfig().getBoolean("Options.Manager.AutoAcceptFriend")) { return; }
        if (e.getEvent() instanceof NewFriendRequestEvent)
        {
            ((NewFriendRequestEvent) e.getEvent()).accept();
        }
    }


    private static boolean equals(Date date1, Date date2)
    {
        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date1);
            Calendar c2 = Calendar.getInstance();
            c1.setTime(date2);
            return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
        } catch (Exception e) {
            throw new IllegalArgumentException("数据处理出错，请联系作者！(QQ: 765743073)");
        }
    }
}
