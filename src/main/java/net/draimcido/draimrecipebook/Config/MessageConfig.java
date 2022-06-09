package net.draimcido.draimrecipebook.Config;

import net.draimcido.draimrecipebook.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class MessageConfig {

    private static MessageConfig mc;
    private File f;
    private FileConfiguration fc;

    public void setUp() {
        if (this.f == null) {
            this.f = new File(Main.getInstance().getDataFolder(), "config.yml");
        }
        this.fc = YamlConfiguration.loadConfiguration(this.f);
        if (!this.f.exists()) {
            try (final InputStream in = Main.getInstance().getResource("config.yml")) {
                Files.copy(in, this.f.toPath());
                this.fc = YamlConfiguration.loadConfiguration(this.f);
                Bukkit.getServer().getConsoleSender().sendMessage("[DraimRecipeBook] The localization file has been created successfully.");
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage("[DraimRecipeBook] An error occurred while creating the localization file.");
            }
        }
    }

    public void reloadConfig() {
        this.f = new File(Main.getInstance().getDataFolder(), "config.yml");
        this.fc = YamlConfiguration.loadConfiguration(this.f);
    }

    public FileConfiguration getConfig() {
        if (this.fc == null) {
            this.reloadConfig();
        }
        return this.fc;
    }

    public void saveConfig() {
        try {
            this.getConfig().save(this.f);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("[DraimRecipeBook] Error in saving localization file: " + this.f);
        }
    }

    public static MessageConfig getMessage() {
        return MessageConfig.mc;
    }

    static {
        MessageConfig.mc = new MessageConfig();
    }
}
