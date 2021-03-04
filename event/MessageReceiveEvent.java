package me.illtamer.infinitebot.event;

import me.illtamer.infinitebot.api.InfiniteBotAPI;
import me.illtamer.infinitebot.bot.Bot;
import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.Message;

import java.util.List;

/**
 * 消息接收部分
 * @author IllTamer
 */
public class MessageReceiveEvent extends InfiniteEvent
{
    private  int time;
    private  String msg;
    private  Message message;
    private  MessageEvent event;

    public MessageReceiveEvent(MessageEvent event)
    {
        super(event);
        this.event = event;
        this.time = event.getTime();
        this.message = event.getMessage();
        this.msg = event.getMessage().contentToString();
    }

    public int getTime() {
        return this.time;
    }

    public String getMsg() {
        return this.msg;
    }

    public Message getMessage() {
        return this.message;
    }

    public void sendMessage(String msg) {
        this.event.getSubject(/*返回消息事件主体.*/).sendMessage(msg);
    }

    public void sendMessage(Message message) {
        this.event.getSubject().sendMessage(message);
    }

    public void sendMessage(List<String> msgs) {
        StringBuilder builder = new StringBuilder();
        if (msgs.size() != 1) {
            for (int i = 0; i < msgs.size(); i ++)
            {
                if (i != (msgs.size() - 1))
                { builder.append(msgs.get(i)).append("\n"); }
                else { builder.append(msgs.get(i)); }
            }
        } else { builder.append(msgs.get(0)); }
        this.event.getSubject(/*返回消息事件主体.*/).sendMessage(builder.toString());
    }

    @Override
    public MessageEvent getEvent() {
        return this.event;
    }

    public void recall(){
        Mirai.getInstance().recallMessage(Bot.getAPI().getBot(), getEvent().getSource());
    }
}
