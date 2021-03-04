package me.illtamer.infinitebot.event.person;

import me.illtamer.infinitebot.event.MessageReceiveEvent;

/**
 * 好友消息事件
 * @author IllTamer
 */
public class FriendMessageEvent extends MessageReceiveEvent
{
    private final Long senderID;
    private final net.mamoe.mirai.event.events.FriendMessageEvent event;

    public FriendMessageEvent(net.mamoe.mirai.event.events.FriendMessageEvent event)
    {
        super(event);
        this.senderID = event.getSender().getId();
        this.event = event;
    }

    public Long getSenderID() {
        return senderID;
    }

    @Override
    public net.mamoe.mirai.event.events.FriendMessageEvent getEvent() {
        return this.event;
    }
}
