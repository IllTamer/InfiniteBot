package me.illtamer.infinitebot;

import me.illtamer.infinitebot.bot.Bot;
import me.illtamer.infinitebot.command.OnCommand;
import me.illtamer.infinitebot.listener.*;
import me.illtamer.infinitebot.utils.Config;
import me.illtamer.infinitebot.utils.Drivers;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 本插件引用了Mirai-2.3.2版本
 * @author IllTamer
 */
public class InfiniteBot extends JavaPlugin
{
    private static InfiniteBot instance;
    private static Config main,bind,connect;
    public static String prefix = "§7§l<§e§l§nInfiniteBot§7§l> §8§l-> §f";

    @Override
    public void onEnable()
    {
        instance = this;
        main = new Config("main.yml",this);
        bind = new Config("bind.yml",this);
        Bot.initAll();
        log();
        Drivers.sendMessage("插件加载中",true);
        getServer().getPluginManager().registerEvents(new AdminSendCommand(),this);
        getServer().getPluginManager().registerEvents(new GroupPreventRecall(),this);
        getServer().getPluginManager().registerEvents(new WhiteListBind(),this);
        getServer().getPluginManager().registerEvents(new CustomCommand(),this);
        getServer().getPluginManager().registerEvents(new InternetChat(),this);
        getServer().getPluginManager().registerEvents(new Manager(),this);
        getServer().getPluginManager().registerEvents(new PreMessageReplace(),this);
        OnCommand onCommand = new OnCommand();
        getCommand("InfiniteBot").setExecutor(onCommand);
        getCommand("InfiniteBot").setTabCompleter(onCommand);
        Drivers.sendMessage("§a成功加载！",true);
    }


    @Override
    public void onDisable()
    {
        HandlerList.unregisterAll(this);
        Drivers.sendMessage("§a成功卸载！",false);
        instance = null;
    }

    private static void log()
    {
        Bukkit.getConsoleSender().sendMessage(
                ".___        _____.__       .__  __        __________        __   \n" +
                "|   | _____/ ____\\__| ____ |__|/  |_  ____\\______   \\ _____/  |_ \n" +
                "|   |/    \\   __\\|  |/    \\|  \\   __\\/ __ \\|    |  _//  _ \\   __\\\n" +
                "|   |   |  \\  |  |  |   |  \\  ||  | \\  ___/|    |   (  <_> )  |  \n" +
                "|___|___|  /__|  |__|___|  /__||__|  \\___  >______  /\\____/|__|  \n" +
                "         \\/              \\/              \\/       \\/             ");
    }

    public static void setConnectFile(Config connect) {
        InfiniteBot.connect = connect;
    }

    public static InfiniteBot getInstance() {
        return instance;
    }

    public static Config getMainFile() {
        return main;
    }

    public static Config getBindFile() {
        return bind;
    }

    public static Config getConnectFile() {
        return connect;
    }
}
