package com.system32.cropsdupefix;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat {
    public static String prefix = ChatColor.translateAlternateColorCodes('&', "&b&lCropsDupeFix »&f");
    public static void sendConsoleMessage(String message){
        Bukkit.getConsoleSender().sendMessage(prefix + colorMessage(message));
    }
    public static void sendPlayerMessage(Player player, String message){
        player.sendMessage(colorMessage(message));
    }
    public static String colorMessage(String message){
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher match = pattern.matcher(message);
        while(match.find()){
            String color = message.substring(match.start(), match.end());
            message = message.replace(color, ChatColor.of(color)+"");
            match = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
