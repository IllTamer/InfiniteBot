package me.illtamer.infinitebot.bot;

import me.illtamer.infinitebot.InfiniteBot;

import me.illtamer.infinitebot.api.InfiniteBotAPI;
import me.illtamer.infinitebot.event.group.GroupMessageEvent;
import me.illtamer.infinitebot.event.InfiniteEvent;
import me.illtamer.infinitebot.event.MessageReceiveEvent;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.utils.BotConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;

/**
 * @author IllTmaer
 */
public class Bot
{
    private static net.mamoe.mirai.Bot bot = null;
    private static InfiniteBotAPI infiniteBotAPI = null;

    public static void initAll()
    {
        FileConfiguration main = InfiniteBot.getMainFile().getConfig();
        if (bot != null)
        {
            bot.close(new Throwable());
        }

        long account = main.getLong("Init.Login.Account");
        String password = main.getString("Init.Login.Password");

        String protocol = main.getString("Init.Protocol");

        BotConfiguration botConfig = new BotConfiguration() {};

        //登录协议
        botConfig.setProtocol(BotConfiguration.MiraiProtocol.valueOf(protocol));
        //存储设备信息到.json
        botConfig.fileBasedDeviceInfo("InfiniteBotInfo.json");

        if (!main.getBoolean("Init.Log"))
        {//不输出log
            botConfig.noBotLog();
            botConfig.noNetworkLog();
        }

        Bukkit.getScheduler().runTaskAsynchronously(InfiniteBot.getInstance(),() ->
        {
            bot = BotFactory.INSTANCE.newBot(account, password, botConfig);
            infiniteBotAPI = new InfiniteBotAPI(bot);
            bot.login();

            bot.getEventChannel().subscribeAlways(net.mamoe.mirai.event.Event.class, (event) ->
            {
                InfiniteEvent infiniteEvent = new InfiniteEvent(event);
                callEvent(infiniteEvent);
            });
            bot.getEventChannel().subscribeAlways(MessageEvent.class, (event) ->
            {
                MessageReceiveEvent messageReceiveEvent = new MessageReceiveEvent(event);
                callEvent(messageReceiveEvent);
            });

            bot.getEventChannel().subscribeAlways(net.mamoe.mirai.event.events.GroupMessageEvent.class, (event) ->
            {
                GroupMessageEvent groupMessageEvent = new GroupMessageEvent(event);
                callEvent(groupMessageEvent);
            });

            bot.getEventChannel().subscribeAlways(GroupTempMessageEvent.class, (event) ->
            {
                me.illtamer.infinitebot.event.group.GroupTempMessageEvent groupTempMessageEvent = new me.illtamer.infinitebot.event.group.GroupTempMessageEvent(event);
                callEvent(groupTempMessageEvent);
            });

            bot.getEventChannel().subscribeAlways(FriendMessageEvent.class, (event) ->
            {
                me.illtamer.infinitebot.event.person.FriendMessageEvent friendMessageEvent = new me.illtamer.infinitebot.event.person.FriendMessageEvent(event);
                callEvent(friendMessageEvent);
            });

            bot.getEventChannel().subscribeAlways(MessageRecallEvent.class, (event) ->
            {
                me.illtamer.infinitebot.event.MessageRecallEvent messageRecallEvent = new me.illtamer.infinitebot.event.MessageRecallEvent(event);
                callEvent(messageRecallEvent);
            });
        });
    }


    private static void callEvent(Event event)
    {//call事件
        if (InfiniteBot.getInstance().getConfig().getBoolean("Init.Async"))
        {//异步
            Bukkit.getScheduler().runTask(InfiniteBot.getInstance(), () ->
                    Bukkit.getPluginManager().callEvent(event));
            return;
        }//非法同步
        Bukkit.getPluginManager().callEvent(event);
    }

    public static InfiniteBotAPI getAPI() {
        return infiniteBotAPI;
    }
}
