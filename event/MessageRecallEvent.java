package me.illtamer.infinitebot.event;

import me.illtamer.infinitebot.InfiniteBot;

/**
 * 消息撤回事件
 * @author IllTamer
 */
public class MessageRecallEvent extends InfiniteEvent
{
    private final int time;
    private final long senderID;
    private final net.mamoe.mirai.event.events.MessageRecallEvent event;

    public MessageRecallEvent(net.mamoe.mirai.event.events.MessageRecallEvent event)
    {
        super(event);
        this.event = event;
        this.time = event.getMessageTime();
        this.senderID = event.getAuthor().getId();
    }

    /**
     * 因消息源不确定性 请获取InfiniteBotAPI中方法发出信息
     */

    public boolean isBot() {
        return senderID == InfiniteBot.getMainFile().getConfig().getLong("Init.Login.Account");
    }

    public int getTime() {
        return this.time;
    }

    public long getSenderID() {
        return senderID;
    }

    @Override
    public net.mamoe.mirai.event.events.MessageRecallEvent getEvent() {
        return this.event;
    }
}
