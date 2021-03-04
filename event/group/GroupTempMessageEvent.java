package me.illtamer.infinitebot.event.group;

import me.illtamer.infinitebot.event.MessageReceiveEvent;

/**
 * 群临时会话事件
 * @author IllTamer
 */
public class GroupTempMessageEvent extends MessageReceiveEvent
{
    private final Long senderID;
    private final net.mamoe.mirai.event.events.GroupTempMessageEvent event;


    public GroupTempMessageEvent(net.mamoe.mirai.event.events.GroupTempMessageEvent event)
    {
        super(event);
        this.event = event;
        this.senderID = event.getSender().getId();
    }

    public Long getSenderID() {
        return senderID;
    }

    @Override
    public net.mamoe.mirai.event.events.GroupTempMessageEvent getEvent() {
        return this.event;
    }
}
