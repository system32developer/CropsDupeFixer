package com.system32.cropsdupefix;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public final class CropsDupeFix extends JavaPlugin {
    public HashMap<String, Integer> dupeFixCountdown = new HashMap<>();

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new CropDupeListener(this), this);
        registerConfig();
        Chat.sendConsoleMessage(" "+ this.getConfig().getString("Messages.Load"));
    }

    @Override
    public void onDisable() {
        Chat.sendConsoleMessage(this.getConfig().getString("Messages.Unload"));
    }
    public void registerConfig() {
        File conf = new File(getDataFolder(), "config.yml");
        if (!conf.exists()) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }
}
