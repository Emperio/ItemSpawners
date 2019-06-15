package me.emperio.pulsecore.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class Hologram {

    private Location location;
    private String title;

    private EmpUtil api;

    public Hologram(Location loc, String titl){
        this.location = loc;
        this.title = titl;
    }

    public void spawn(){

        ArmorStand hologram = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        hologram.setVisible(false);
        hologram.setCustomNameVisible(true);
        hologram.setCustomName(title);
        hologram.setGravity(true);
        hologram.setMarker(true);




    }


}
