package me.illtamer.infinitebot.utils;

import me.illtamer.infinitebot.InfiniteBot;
import me.illtamer.infinitebot.utils.drivers.Replace;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * 工具包
 * @author IllTamer
 */
public class Drivers
{
    public static void sendMessage(String str, boolean prefix)
    {
        if (prefix)
        { Bukkit.getConsoleSender().sendMessage(Replace.s(InfiniteBot.prefix + str)); }
        else { Bukkit.getConsoleSender().sendMessage(Replace.s(" &e|-&f" + str)); }

    }


    public static void sendMessage(String str, Player player)
    {
        player.sendMessage(Replace.s(InfiniteBot.prefix + str));
    }


    public static void sendMessage(List<String> strList, Player player)
    {
        StringBuilder builder = new StringBuilder();
        if (strList.size() != 1) {
            for (int i = 0; i < strList.size(); i ++)
            {
                if (i != (strList.size() - 1))
                { builder.append(strList.get(i)).append("\n"); }
                else { builder.append(strList.get(i)); }
            }
        } else { builder.append(strList.get(0)); }
        player.sendMessage(Replace.s(InfiniteBot.prefix + builder.toString()));
    }
}
