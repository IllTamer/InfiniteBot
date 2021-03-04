package me.illtamer.infinitebot.utils;

import me.illtamer.infinitebot.InfiniteBot;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;


/**
 * 配置文件处理
 * @author IllTamer
 */
public class Config
{
    private String name;
    private Plugin instance;
    private File file;
    private FileConfiguration config;


    public Config(String name, Plugin instance)
    {//输入的name为"xx.yml"
        this.name = name;
        this.instance = instance;
        this.config = load();
    }


    public void save()
    {//已包含reload
        try {
            config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.config = load();
    }


    private FileConfiguration load()
    {
        File file = new File(instance.getDataFolder(),this.name);
        if (!file.exists())
        { instance.saveResource(this.name,false); }

        YamlConfiguration yaml = new YamlConfiguration();
        try {//如果load失败 file还是能用的
            yaml.load(file);
            this.file = file;
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }

        return yaml;
    }

    public void reload() {
        this.config = load();
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
