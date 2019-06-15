package me.emperio.pulsecore.util;

import me.emperio.pulsecore.PulseCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

public class EmpUtil {



    public String getColored(String message){
        String messageToSend =  ChatColor.translateAlternateColorCodes('&', message);
        return messageToSend;

    }

    public Location getLocationFromString(String string){

        int x, y, z;
        String worldRaw;

        String[] parsedArray = string.split(",");

        x = Integer.parseInt(parsedArray[0]);
        y = Integer.parseInt(parsedArray[1]);
        z = Integer.parseInt(parsedArray[2]);

        worldRaw = parsedArray[3];

        World world = Bukkit.getServer().getWorld(worldRaw);

        Location location = new Location(world, (double) x, (double) y, (double) z);

        return location;

    }

}
