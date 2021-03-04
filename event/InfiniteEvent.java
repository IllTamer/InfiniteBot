package me.illtamer.infinitebot.event;

import net.mamoe.mirai.event.Event;

import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 桥梁事件
 * 绑定Mirai与Bukkit事件
 * @author IllTamer
 */
public class InfiniteEvent extends org.bukkit.event.Event
{
    private static final HandlerList HANDLERS = new HandlerList();
    private Event event;


    public InfiniteEvent(Event event)
    {
        super(true);
        this.event = event;
    }


    public Event getEvent()
    {//获得原Mirai事件
        return event;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
