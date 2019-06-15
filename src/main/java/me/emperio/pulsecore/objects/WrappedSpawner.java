package me.emperio.pulsecore.objects;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import java.util.concurrent.ThreadLocalRandom;

public class WrappedSpawner {

    private ISpawnerManager ism = new ISpawnerManager();

    private ISpawner spawner;

    private Location loc;

    public WrappedSpawner(Location location){

        this.loc = location;
        this.spawner = ism.getISpawnerAtLocation(location);

    }

    public WrappedSpawner(Location location, ISpawner spawner){
        this.loc = location;
        this.spawner = spawner;
    }



    public void spawnItem(){

        if(spawner == null){
            Bukkit.broadcastMessage("nulledxd");
        }

        ItemStack item = new ItemStack(spawner.getType());
        int min = -3;
        int max = 3;

        double xnum = loc.getX() + ThreadLocalRandom.current().nextInt(min, max + 1);
        double znum = loc.getZ() + ThreadLocalRandom.current().nextInt(min, max + 1);

        Location newloc = loc.clone();
        newloc.setX(xnum);
        newloc.setZ(znum);


        loc.getWorld().dropItemNaturally(newloc, item);
        loc.setY(loc.getY() + 1);
        loc.setX(loc.getX() + 0.5);
        loc.setZ(loc.getZ() + 0.5);
        loc.getWorld().playSound(loc, Sound.FIREWORK_LARGE_BLAST2, 100, 1);

        loc.getWorld().spigot().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 0, 0, 0.0F, 0, 0.0F, 0.1F, 5, 5);
        loc.getWorld().spigot().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 0, 0, 0.0F, 0, 0.0F, 0.3F, 5, 7);


    }

    public ISpawner getSpawner() {
        return spawner;
    }

    public Location getLocation() {
        return loc;
    }
}
