package me.emperio.pulsecore.events;

import me.emperio.pulsecore.objects.ISpawner;
import me.emperio.pulsecore.objects.ISpawnerManager;
import me.emperio.pulsecore.objects.PPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractsWithSpawner implements Listener {

    @EventHandler
    public void playerInteracts(PlayerInteractEvent e){

        if(e.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }

        if(e.getClickedBlock().getType() != Material.BEACON){
            return;
        }

        ISpawnerManager ism = new ISpawnerManager();
        ISpawner spawner = ism.getISpawnerAtLocation(e.getClickedBlock().getLocation());

        if(spawner == null){
            return;
        }
        e.setCancelled(true);

        PPlayer player = new PPlayer(e.getPlayer());
        player.sendMessage("");
        player.sendMessage(ISpawnerManager.name + " &9&lINFO");
        player.sendMessage(" » &7Tier: &9" + spawner.getTier());
        player.sendMessage(" » &7Type: &9" + spawner.getType());
        player.sendMessage(" ");

    }

}
