package com.system32.cropsdupefix;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class CropDupeListener implements Listener {
    CropsDupeFix plugin;
    public CropDupeListener(CropsDupeFix plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onCropPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType().toString().contains("BED")) {
            Block block = event.getBlock();
            Location blockLocation = block.getLocation();
            blockLocation.setY(blockLocation.getY() - 1);
            List<Material> crops = Arrays.asList(Material.WHEAT, Material.CARROTS, Material.POTATOES, Material.BEETROOTS, Material.NETHER_WART, Material.WHEAT_SEEDS);
            if(crops.contains(blockLocation.getBlock().getType())){
                event.setCancelled(true);
                HashMap<String, Integer> countdown = plugin.dupeFixCountdown;
                String player = event.getPlayer().getName();
                if(!countdown.containsKey(player)){
                    countdown.put(player, 1);
                }else{
                    if(countdown.get(player) >= 3){
                        forceCommand(Chat.colorMessage(plugin.getConfig().getString("WhenTryingToDupe.Command").replace("{PLAYER}", player).replace("{X}", String.valueOf(blockLocation.getX())).replace("{Y}", String.valueOf(blockLocation.getY())).replace("{Z}", String.valueOf(blockLocation.getZ()))));
                        countdown.remove(player);
                    }else{
                        event.getPlayer().sendMessage(Chat.colorMessage(plugin.getConfig().getString("WhenTryingToDupe.Message").replace("{COUNTDOWN}", countdown.get(player)+"/"+plugin.getConfig().getString("WhenTryingToDupe.MaxWarnings")).replace("{PREFIX}", Chat.prefix)));
                        if (plugin.getConfig().getBoolean("WhenTryingToDupe.StrikeLightning")){
                            Bukkit.getWorld(event.getPlayer().getWorld().getName()).strikeLightning(event.getPlayer().getLocation());
                        }
                        countdown.put(player, countdown.get(player) + 1);
                    }

                }
            }
        }
    }
    public static void forceCommand(String command){
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
