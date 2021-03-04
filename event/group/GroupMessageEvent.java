package me.illtamer.infinitebot.event.group;

import me.illtamer.infinitebot.event.MessageReceiveEvent;
import net.mamoe.mirai.message.data.Message;

/**
 * 群聊消息事件
 * @author IllTamer
 */
public class GroupMessageEvent extends MessageReceiveEvent
{
    private final Long senderID;
    private final Long groupID;
    private final net.mamoe.mirai.event.events.GroupMessageEvent event;

    public GroupMessageEvent(net.mamoe.mirai.event.events.GroupMessageEvent event) {
        super(event);
        this.senderID = event.getSender().getId();
        this.groupID = event.getGroup().getId();
        this.event = event;
    }

    /**
     * 向群聊中的消息发送者发送信息
     */
    public void sendPersonMessage(String msg) {
        this.event.getSender(/*返回消息事件主体.*/).sendMessage(msg);
    }

    public void sendPersonMessage(Message message) {
        this.event.getSender().sendMessage(message);
    }

    public Long getSenderID() {
        return senderID;
    }

    public Long getGroupID() {
        return groupID;
    }

    @Override
    public net.mamoe.mirai.event.events.GroupMessageEvent getEvent() {
        return event;
    }
}
