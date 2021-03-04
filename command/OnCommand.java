package me.illtamer.infinitebot.command;

import me.illtamer.infinitebot.InfiniteBot;
import me.illtamer.infinitebot.bot.Bot;
import me.illtamer.infinitebot.listener.CustomCommand;
import me.illtamer.infinitebot.listener.InternetChat;
import me.illtamer.infinitebot.listener.PreMessageReplace;
import me.illtamer.infinitebot.utils.Config;
import me.illtamer.infinitebot.utils.Drivers;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 游戏内命令
 * - 自动刷新功能
 * @author IllTamer
 */
public class OnCommand implements TabExecutor
{
    public OnCommand()
    {
        autoReload();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length == 0) { Drivers.sendMessage("请输入ib reload重载插件",true); return true; }

        if ("reload".equalsIgnoreCase(args[0]) && sender.hasPermission("InfiniteBot.reload"))
        {
            Drivers.sendMessage("&l插件重载中",true);
            InfiniteBot.getMainFile().reload();
            InfiniteBot.getBindFile().reload();
            CustomCommand.storeMessages();
            InternetChat.initConnect();
            PreMessageReplace.initImageOptions();
            Bot.initAll();
            Drivers.sendMessage("&a&l重载成功！",true);
            return true;
        }
        else if ("test".equalsIgnoreCase(args[0]))
        {
            return true;
        }

        if (sender instanceof Player)
        {
            if ("message".equalsIgnoreCase(args[0]))
            {
                Player player = (Player) sender;
                Config connect = InfiniteBot.getConnectFile();
                if ("close".equalsIgnoreCase(args[1]))
                {
                    if (connect == null)
                    {
                        Drivers.sendMessage("&c消息互通功能未被开启/初始化，该指令暂不可用！",player);
                        return true;
                    }
                    connect.getConfig().set(player.getUniqueId().toString(),true);
                    connect.save();
                    Drivers.sendMessage("&a您已关闭群消息接收",player);
                    return true;
                }
                else if ("open".equalsIgnoreCase(args[1]))
                {
                    if (connect == null)
                    {
                        Drivers.sendMessage("&c消息互通功能未被开启/初始化，该指令暂不可用！",player);
                        return true;
                    }
                    connect.getConfig().set(player.getUniqueId().toString(),false);
                    connect.save();
                    Drivers.sendMessage("&a您已开启群消息接收",player);
                    return true;
                }
                else if ("send".equalsIgnoreCase(args[1]))
                {//ib message send <msg>
                    if (!checkPermission(player,"InfiniteBot.sendGroupMessage"))
                    { return true; }
                    if (args.length <= 2)
                    {
                        Drivers.sendMessage("&c消息不能为空",player);
                        return true;
                    }
                    InternetChat.sendGroupMessage(player,args[2]);
                    Drivers.sendMessage("&a发送成功！",player);
                }
                else if ("list".equalsIgnoreCase(args[1]))
                {
                    if (!checkPermission(player,"InfiniteBot.sendGroupMessage"))
                    { return true; }
                    if (args.length != 2)
                    {
                        Drivers.sendMessage("&4指令格式错误！",true);
                    }
                    List<String> images = new ArrayList<>();
                    PreMessageReplace.getImages().forEach((key, value) -> images.add(key));
                    Drivers.sendMessage(images,player);
                }
            }
        } else { Drivers.sendMessage("&c此命令只允许玩家使用！",true); }

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabs;
        if (args.length == 1)
        {
            tabs = new ArrayList<>(Arrays.asList("reload", "test", "message"));
            tabs.removeIf(s -> !s.startsWith(args[0].toLowerCase()));
            return tabs;
        }
        else if (args.length >= 2 && "message".equalsIgnoreCase(args[0]))
        {
            if (sender.hasPermission("InfiniteBot.sendGroupMessage") && "send".equalsIgnoreCase(args[1]))
            {
                tabs = new ArrayList<>(Collections.singletonList("<messages>"));
                tabs.removeIf(s -> !s.startsWith(args[1].toLowerCase()));
                return tabs;
            }
            tabs = new ArrayList<>(Arrays.asList("open", "close"));
            if (sender.hasPermission("InfiniteBot.sendGroupMessage"))
            { tabs.add("send"); tabs.add("list"); }
            tabs.removeIf(s -> !s.startsWith(args[1].toLowerCase()));
            return tabs;
        }
        return null;
    }

    private void autoReload()
    {
        FileConfiguration main = InfiniteBot.getMainFile().getConfig();
        if (!main.getBoolean("Options.AutoReload.Enable")) { return; }
        long time = main.getLong("Options.AutoReload.Period");
        if (time <= 0)
        {
            Drivers.sendMessage("&c刷新时间配置错误(必须大于等于一分钟)！自动刷新功能注销，待下次服务器启动时启用！",false);
            return;
        }
        Drivers.sendMessage("&7自动重载 &2√",false);
        Bukkit.getScheduler().runTaskTimer(InfiniteBot.getInstance(),() ->
                Bukkit.dispatchCommand(InfiniteBot.getInstance().getServer().getConsoleSender(),"InfiniteBot reload"),time * 60 * 20L,time * 60 * 20L);
    }


    private static boolean checkPermission(Player player, String permission)
    {
        if (!player.hasPermission(permission))
        {
            FileConfiguration main = InfiniteBot.getMainFile().getConfig();
            Drivers.sendMessage("&c" + main.getString("Messages.NoPermission"),player);
            return false;
        }
        return true;
    }
}
