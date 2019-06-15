package me.emperio.pulsecore.events;

import me.emperio.pulsecore.PulseCore;
import me.emperio.pulsecore.objects.*;
import me.emperio.pulsecore.util.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerPlacesBlock implements Listener {

    PulseCore plugin;
    ISpawnerManager ism = new ISpawnerManager();

    public PlayerPlacesBlock(PulseCore instance){
        this.plugin = instance;
    }

    @EventHandler
    public void placedBlock(BlockPlaceEvent e){

        if(!e.getItemInHand().hasItemMeta()){
            return;
        }

        String raw = e.getItemInHand().getItemMeta().getDisplayName();
        String displayName = raw.replaceAll("§", "&");

        LangManager lm = plugin.getLangManager();

        if(displayName.equals(ISpawnerManager.name)){
            ISpawner spawner = ISpawner.fromItemStack(e.getItemInHand());

            PPlayer player = new PPlayer(e.getPlayer());
            if(ism.getISpawnerAtChunk(e.getBlockPlaced().getLocation().getChunk()) != null){
                e.setCancelled(true);
                player.sendMessage(lm.get("already-spawner-in-chunk"));
                return;
            }
            ism.registerISpawner(spawner, e.getBlockPlaced().getLocation());

            String placed = plugin.getConfig().getString("lang.placed-spawner").replace("{tier}", "" + spawner.getTier())
                    .replace("{type}", "" + spawner.getType().name());

            player.sendMessage(placed);
            Location loc = e.getBlockPlaced().getLocation();

            loc.setX(loc.getX() + 0.5);
            loc.setZ(loc.getZ() + 0.5);
            loc.setY(loc.getY() + 1);

            new Hologram(loc, ChatColor.AQUA + "" + ChatColor.BOLD + spawner.getType() + ChatColor.GOLD + ChatColor.BOLD + " » "  + spawner.getTier()).spawn();



//            new BukkitRunnable() {
//
//                @Override
//                public void run() {
//                    WrappedSpawner ws = new WrappedSpawner(e.getBlock().getLocation());
//
//                    ws.spawnItem();
//
//                }
//
//            }.runTaskLater(this.plugin, 60);



        }






    }

}
