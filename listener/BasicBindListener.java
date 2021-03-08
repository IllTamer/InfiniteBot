package me.illtamer.infinitebot.listener;

import me.illtamer.infinitebot.bot.Bot;
import me.illtamer.infinitebot.event.group.GroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;

/**
 * 基本绑定事件监听
 * @author IllTamer
 */
public class BasicBindListener implements Listener
{
    /** 申请绑定的QQ 玩家UUID */
    private static HashMap<Long, UUID> request = new HashMap<>();

    /**
     * 群内确认绑定
     */
    @EventHandler
    public void onRequestBind(GroupMessageEvent event)
    {
        if (Bot.getAPI().getPlayerId(event.getSenderID()) != null)
        {
            event.sendMessage("你已绑定角色: " + Bukkit.getOfflinePlayer(
                    Bot.getAPI().getPlayerId(event.getSenderID())).getName());
            return;
        }
        if (request.containsKey(event.getSenderID()))
        {
            if ("确认绑定".equals(event.getMsg()))
            {
                Bot.getAPI().setBind(request.get(event.getSenderID()),event.getSenderID());
                event.sendMessage("绑定成功！");
                try {
                    request.remove(event.getSenderID());
                } catch (Exception ignore) {}
            }
        }
    }


    public static HashMap<Long, UUID> getRequest() {
        return request;
    }
}
