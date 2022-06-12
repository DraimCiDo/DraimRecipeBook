package net.draimcido.draimrecipebook;

import net.draimcido.draimrecipebook.Commands.MainCommands;
import net.draimcido.draimrecipebook.Commands.TabComplete;
import net.draimcido.draimrecipebook.Config.MessageConfig;
import net.draimcido.draimrecipebook.Events.HotbarEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;

public final class Main extends JavaPlugin {

    public static FileConfiguration config;

    public static NamespacedKey buttonKey;
    public static NamespacedKey mainPage;

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public static int getConfigInt(String path) {
        return config.getInt(path);
    }

    public static List<Integer> getConfigIntList(String path) {
        return config.getIntegerList(path);
    }

    public static Boolean getConfigBoolean(String path) {
        return config.getBoolean(path);
    }

    public static List<String> getConfigStringList(String path) {
        return config.getStringList(path);
    }

    public static Set<String> getConfigKeys(String path, boolean deep) {
        return config.getConfigurationSection(path).getKeys(deep);
    }

    public static String getConfigString(String path) {
        return config.getString(path);
    }


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new HotbarEvent(), this);
        initCommands();
        instance = this;
        this.saveDefaultConfig();
        config = getConfig();
        MessageConfig.getMessage().setUp();
    }

    @Override
    public void onDisable() {
        this.getLogger().info("The plugin has been disabled.");
    }

    public void reloadConfiguration() {
        reloadConfig();
        config = getConfig();
    }

    public void initCommands() {
        getCommand("draimrecipebook").setExecutor(new MainCommands(this));
        getCommand("draimrecipebook").setTabCompleter(new TabComplete());
    }
}
