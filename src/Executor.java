package com.stan.plugin.mention;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Exec extends JavaPlugin {

    // Config file stuff
    private File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        // Create config
        createCustomConfig();

        // Create new instance of Commands class and point to executor
        getCommand("ping").setExecutor(new PingToggleCMD());

        // Create new instance of Events class and point to executor
        getServer().getPluginManager().registerEvents(new ChatEventPing(), this);
        getServer().getPluginManager().registerEvents(new ChatEventStaff(), this);
        getServer().getPluginManager().registerEvents(new ChatEventDiscord(), this);
        getServer().getPluginManager().registerEvents(new ChatEventJoin(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }


    // Config stuff once again! Actually create it this time...
    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "config.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        customConfig= new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
