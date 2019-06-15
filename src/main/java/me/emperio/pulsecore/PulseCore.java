package me.emperio.pulsecore;

import me.emperio.pulsecore.commands.ISpawnerCommand;
import me.emperio.pulsecore.events.PlayerBreaksBlock;
import me.emperio.pulsecore.events.PlayerInteractsWithSpawner;
import me.emperio.pulsecore.events.PlayerPlacesBlock;
import me.emperio.pulsecore.objects.*;
import me.emperio.pulsecore.runnables.TierOneSpawning;
import me.emperio.pulsecore.util.EmpUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
@SuppressWarnings("Duplicates")
public final class PulseCore extends JavaPlugin {

    private final String version = "v1.0";
    private final DataManager dataManager = new DataManager(this);
    private LangManager lm;
    EmpUtil api = new EmpUtil();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        log("&b------------------------");
        log("");
        log("&b!&fPulse&bPvP Core!");
        log("&f" + version + ", &1By Emperio");
        log("");
        log("&b------------------------");

        registerSettings();
        lm = new LangManager(this);

        this.getCommand("ispawner").setExecutor(new ISpawnerCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerPlacesBlock(this), this);
        getServer().getPluginManager().registerEvents(new PlayerBreaksBlock(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractsWithSpawner(), this);
        initializeDatabase();

        BukkitScheduler scheduler = getServer().getScheduler();


        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(String key : getData().getConfigurationSection("spawners").getKeys(false)){

                    ISpawner spawner = ISpawner.fromString(getData().getString("spawners." + key ));
                    WrappedSpawner wSpawner = new WrappedSpawner(api.getLocationFromString(key), spawner);

                    if(spawner.getTier() == 1){
                        wSpawner.spawnItem();
                    }

                }
            }
        }, 60L, (long) Settings.tierOneSeconds * 20);

        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(String key : getData().getConfigurationSection("spawners").getKeys(false)){

                    ISpawner spawner = ISpawner.fromString(getData().getString("spawners." + key ));
                    WrappedSpawner wSpawner = new WrappedSpawner(api.getLocationFromString(key), spawner);

                    if(spawner.getTier() == 2){
                        wSpawner.spawnItem();
                    }

                }
            }
        }, 60L, (long) Settings.tierTwoSeconds * 20);

        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(String key : getData().getConfigurationSection("spawners").getKeys(false)){

                    ISpawner spawner = ISpawner.fromString(getData().getString("spawners." + key ));

                    WrappedSpawner wSpawner = new WrappedSpawner(api.getLocationFromString(key), spawner);


                    if(spawner.getTier() == 3){
                        wSpawner.spawnItem();
                    }

                }
            }
        }, 60L, (long) Settings.tierThreeSeconds * 20);

        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for(String key : getData().getConfigurationSection("spawners").getKeys(false)){

                    ISpawner spawner = ISpawner.fromString(getData().getString("spawners." + key ));

                    WrappedSpawner wSpawner = new WrappedSpawner(api.getLocationFromString(key), spawner);
                    wSpawner.getLocation().setX(wSpawner.getLocation().getX() + 0.5);
                    wSpawner.getLocation().setZ(wSpawner.getLocation().getZ() + 0.5);
                    wSpawner.getLocation().setY(wSpawner.getLocation().getY() + 1);
                    wSpawner.getLocation().getWorld().
                            spigot().playEffect(wSpawner.getLocation(), Effect.LARGE_SMOKE, 0, 0, 0.0F, 0.0F, 0.0F, 0.2F, 20, 3);

                }
            }
        }, 60L, (long) 10 * 20);



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void log(String message){
        String messageToSend = ChatColor.translateAlternateColorCodes('&', message);
        getServer().getConsoleSender().sendMessage(messageToSend);

    }

    public LangManager getLangManager() {
        return lm;
    }

    public void initializeDatabase(){
        dataManager.initialize();
        dataManager.saveData();
        dataManager.reload();

    }


    public void saveData(){
        dataManager.saveData();
    }

    public FileConfiguration getData(){
        return dataManager.getData();
    }

    public void registerSettings(){

        ISpawnerManager.name = getConfig().getString("lang.item-spawner-title");


    }


}
