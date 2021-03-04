package me.illtamer.infinitebot.listener;

import me.illtamer.infinitebot.InfiniteBot;
import me.illtamer.infinitebot.event.person.FriendMessageEvent;
import me.illtamer.infinitebot.event.group.GroupMessageEvent;
import me.illtamer.infinitebot.event.MessageReceiveEvent;
import me.illtamer.infinitebot.utils.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

/**
 * 管理员向控制台输出命令
 * @author IllTamer
 */
public class AdminSendCommand implements Listener
{
    /**
     * 筛选命令
     */
    @EventHandler
    public void onSendCommand(MessageReceiveEvent e)
    {
        if (e instanceof GroupMessageEvent)
        {
            GroupMessageEvent event = (GroupMessageEvent) e;
            if (!isGroup(event.getGroupID())) { return; }
            response(event);
        }
        else if (e instanceof FriendMessageEvent)
        {
            FriendMessageEvent event = (FriendMessageEvent) e;
            response(event);
        }
    }


    private static void response(MessageReceiveEvent event)
    {
        FileConfiguration main = InfiniteBot.getMainFile().getConfig();

        String label = InfiniteBot.getMainFile().getConfig().getString("Options.Label") + " ";
        if (" ".equals(label) || !event.getMsg().startsWith(label)) { return; }

        if (!isAdmin(event.getEvent().getSender().getId()))
        {
            event.sendMessage(main.getString("Messages.NoPermission"));
            return;
        }

        event.sendMessage("命令已发送");

        Bukkit.getScheduler().runTaskAsynchronously(InfiniteBot.getInstance(),() ->
        {
            String command = event.getMsg().substring(label.length());
            MessageSender sender = new MessageSender(InfiniteBot.getInstance().getServer(),event);
            //事件主体在Async运行 在此该Bukkit方法要在主线程使用
            Bukkit.getScheduler().runTask(InfiniteBot.getInstance(), () -> Bukkit.dispatchCommand(sender,command));
            Bukkit.getConsoleSender().sendMessage((InfiniteBot.getMainFile().getConfig().getString("Messages.SendCommand")
                    .replace("%nick%",event.getEvent().getSender().getNick())
                    .replace("%qq%",String.valueOf(event.getEvent().getSender().getId()))
                    .replace("%command%",command)
                    .replace("&","§")));
        });
    }

    private static boolean isGroup(long group)
    {
        long configGroup = InfiniteBot.getMainFile().getConfig().getLong("Options.Group");
        return configGroup == group;
    }

    private static boolean isAdmin(long sender)
    {
        List<Long> admins = InfiniteBot.getMainFile().getConfig().getLongList("Admins");

        if (admins.size() == 0) { return false; }
        return admins.contains(sender);
    }
}
