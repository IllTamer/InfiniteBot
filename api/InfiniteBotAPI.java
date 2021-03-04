package me.illtamer.infinitebot.api;

import me.illtamer.infinitebot.InfiniteBot;
import me.illtamer.infinitebot.utils.Config;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.Message;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

/**
 * API部分
 * @author IllTamer
 */
public class InfiniteBotAPI
{
    private Bot bot;

    public InfiniteBotAPI(Bot bot)
    {
        this.bot = bot;
    }

    public boolean hasFriend(long friendID)
    {//是否有好友
        try {
            return bot.getFriend(friendID).getId() == friendID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean hasGroup(long groupID)
    {//是否在群中
        try {
            return bot.getGroup(groupID).getId() == groupID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void sendFriendMessage(long friendID, String message)
    {
        Bukkit.getScheduler().runTaskAsynchronously(InfiniteBot.getInstance(), () ->
        {
            try {
                bot.getFriend(friendID).sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void sendFriendMessage(long friendID, Message message)
    {
        Bukkit.getScheduler().runTaskAsynchronously(InfiniteBot.getInstance(), () ->
        {
            try {
                bot.getFriend(friendID).sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void sendGroupMessage(long groupID, String message)
    {
        Bukkit.getScheduler().runTaskAsynchronously(InfiniteBot.getInstance(), () ->
        {
            try {
                bot.getGroup(groupID).sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void sendGroupMessage(long groupID, Message message)
    {
        Bukkit.getScheduler().runTaskAsynchronously(InfiniteBot.getInstance(), () ->
        {
            try {
                bot.getGroup(groupID).sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void sendTempMessage(long groupID ,long memberID, String message)
    {//发送群成员临时会话信息
        Bukkit.getScheduler().runTaskAsynchronously(InfiniteBot.getInstance(), () ->
        {
            try {
                bot.getGroup(groupID).get(memberID).sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void sendTempMessage(long groupID ,long memberID, Message message)
    {//发送群成员临时会话信息
        Bukkit.getScheduler().runTaskAsynchronously(InfiniteBot.getInstance(), () ->
        {
            try {
                bot.getGroup(groupID).get(memberID).sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void setBind(UUID uuid, long userID)
    {//绑定 uuid:qq
        Config config = InfiniteBot.getBindFile();
        config.getConfig().set(String.valueOf(uuid),userID);
        config.save();
    }


    public void unBind(UUID uuid)
    {//解绑 uuid:qq
        Config config = InfiniteBot.getBindFile();
        config.getConfig().set(String.valueOf(uuid),null);
        config.save();
    }


    public long getUserId(UUID uuid)
    {//根据uuid获取Player
        return InfiniteBot.getBindFile().getConfig().getLong(uuid.toString());
    }


    public UUID getPlayerId(long userId)
    {//根据qq获取uuid
        UUID uuid = null;
        FileConfiguration bind = InfiniteBot.getBindFile().getConfig();

        for (String key : bind.getConfigurationSection("").getKeys(false))
        {
            if (bind.getLong(key) == userId)
            { uuid = UUID.fromString(key); break; }
        }
        return uuid;
    }


    public void changeUserTagCard(long userId, long groupId,String newTag)
    {
        Bukkit.getScheduler().runTaskAsynchronously(InfiniteBot.getInstance(),() ->
        {
            try {
                bot.getGroup(groupId).get(userId).setNameCard(newTag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public Bot getBot() {
        return bot;
    }
}
