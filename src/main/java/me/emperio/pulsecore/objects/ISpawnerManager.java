package me.emperio.pulsecore.objects;

import me.emperio.pulsecore.PulseCore;
import me.emperio.pulsecore.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

public class ISpawnerManager {

    private PulseCore plugin = PulseCore.getPlugin(PulseCore.class);

    public ISpawner getSpawner(String type, int tier){

        return new ISpawner(tier,type);

    }

    public static int getSpawnFrequencyFromTier(int tier){
        int seconds = 0;
        switch (tier){

            case 1:
                seconds = Settings.tierOneSeconds;
                break;
            case 2:
                seconds = Settings.tierTwoSeconds;
                break;
            case 3:
                seconds = Settings.tierThreeSeconds;
                break;
            default:
                seconds = -1;
                break;


        }

        return seconds;
    }

    public static String name = "&b&lITEM SPAWNER";

    public void registerISpawner(ISpawner spawner, Location loc){

        String locationKey = "" + (int) loc.getX() + "," + (int) loc.getY() + "," + (int) loc.getZ() + "," + loc.getWorld().getName()
                + "," + loc.getChunk().getX() + "," + loc.getChunk().getZ();
        String serializedSpawner = spawner.getType() + "SPLIT" + spawner.getTier();

        plugin.getData().set("spawners." + locationKey, serializedSpawner);
        plugin.saveData();

    }

    public ISpawner getISpawnerAtChunk(Chunk chunk){

        for(String key : plugin.getData().getConfigurationSection("spawners").getKeys(false)){

            String x =  key.split(",")[4];
            String z =  key.split(",")[5];

            String testKey = x + "," + z;
            String chunkKey = chunk.getX() + "," + chunk.getZ();

            if(testKey.equals(chunkKey)){
                String type = plugin.getData().getString("spawners." + key).split("SPLIT")[0];
                int tier = Integer.parseInt(plugin.getData().getString("spawners." + key).split("SPLIT")[1]);

                return new ISpawner(tier, type);
            }

        }

        return null;

    }

    public ISpawner getISpawnerAtLocation(Location loc){

        String locVal = "" + (int) loc.getX() + "," + (int) loc.getY() + "," + (int) loc.getZ() + "," + loc.getWorld().getName()
                + "," + loc.getChunk().getX() + "," + loc.getChunk().getZ();

        if(plugin.getData().getConfigurationSection("spawners").getKeys(false).contains(locVal)){

           return ISpawner.fromString(plugin.getData().getString("spawners." + locVal));


        }

        return null;

    }

    public void invalidateISpawnerAtLocation(Location loc){
        String locVal = "" + (int) loc.getX() + "," + (int) loc.getY() + "," + (int) loc.getZ() + "," + loc.getWorld().getName()
                + "," + loc.getChunk().getX() + "," + loc.getChunk().getZ();
        plugin.getData().set("spawners." + locVal, null);
        plugin.saveData();

    }



}
