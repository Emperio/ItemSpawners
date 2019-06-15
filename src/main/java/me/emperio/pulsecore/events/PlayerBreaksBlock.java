package me.emperio.pulsecore.events;

import me.emperio.pulsecore.PulseCore;
import me.emperio.pulsecore.objects.ISpawner;
import me.emperio.pulsecore.objects.ISpawnerManager;
import me.emperio.pulsecore.objects.PPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class PlayerBreaksBlock implements Listener {

    private PulseCore plugin;
    public PlayerBreaksBlock(PulseCore instance){

        this.plugin = instance;

    }

    @EventHandler
    public void breaksBlock(BlockBreakEvent e){

        ISpawnerManager ism = new ISpawnerManager();
        Location blockLoc = e.getBlock().getLocation();

        ISpawner spawner = ism.getISpawnerAtLocation(blockLoc);

        if(spawner == null){
            return;
        }

        PPlayer player = new PPlayer(e.getPlayer());
        e.getBlock().setType(Material.AIR);
        blockLoc.getWorld().dropItemNaturally(blockLoc, spawner.getItemStack());
        ism.invalidateISpawnerAtLocation(blockLoc);

        String broken = plugin.getConfig().getString("lang.mined-spawner").replace("{tier}", "" + spawner.getTier())
                .replace("{type}", "" + spawner.getType().name());



        player.sendMessage(broken);
        Location loc = blockLoc.clone();
        loc.setY(loc.getY() + 1);

        ArrayList<Entity> armorStand = new ArrayList<>();

        for(Entity each : e.getBlock().getWorld().getNearbyEntities(loc, 1.0, 1.0, 1.0)){
            if (each.getType() == EntityType.ARMOR_STAND){
                armorStand.add(each);
            }
        }

        ArmorStand stand = (ArmorStand) armorStand.get(0);
        stand.remove();



    }

}
