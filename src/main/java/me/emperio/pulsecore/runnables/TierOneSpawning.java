package me.emperio.pulsecore.runnables;

import me.emperio.pulsecore.PulseCore;
import me.emperio.pulsecore.objects.ISpawner;
import me.emperio.pulsecore.objects.WrappedSpawner;
import me.emperio.pulsecore.util.EmpUtil;
import org.bukkit.scheduler.BukkitRunnable;

public class TierOneSpawning extends BukkitRunnable {

    PulseCore plugin;
    EmpUtil api = new EmpUtil();

    public TierOneSpawning(PulseCore instance){
        this.plugin = instance;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void run() {

        for(String key : plugin.getData().getConfigurationSection("spawners").getKeys(false)){

            ISpawner spawner = ISpawner.fromString(key);
            WrappedSpawner wSpawner = new WrappedSpawner(api.getLocationFromString(key));

            if(spawner.getTier() == 2){
                wSpawner.spawnItem();
            }

        }

    }
}
