package me.emperio.pulsecore.objects;

import me.emperio.pulsecore.PulseCore;
import me.emperio.pulsecore.util.EmpUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataManager {

    private PulseCore plugin;

    public DataManager(PulseCore plugin){
        this.plugin = plugin;
    }

    public FileConfiguration cfg;
    public File cfgfile;

    public void initialize(){

        cfgfile = new File(plugin.getDataFolder(), "data.yml");

        if(!cfgfile.exists()){
            try{
                cfgfile.createNewFile();
                plugin.getServer().getConsoleSender().sendMessage("[PulseCore] data.yml does not exist, creating new one...");
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[PulseCore] data.yml successfully created");
            }
            catch (IOException e){
                plugin.getServer().getConsoleSender().sendMessage("[PulseCore] "+ ChatColor.RED + "data.yml creation failed, refer to stacktrace below for more info");
                e.printStackTrace();
            }
        }

        cfg = YamlConfiguration.loadConfiguration(cfgfile);
        cfg.set("spawners.0,60,0,world,0,0", "EMERALDSPLIT3");
        cfg.set("spawners.17,60,17,world,1,1", "EMERALDSPLIT3");
        cfg.set("spawners.33,60,33,world,2,2", "EMERALDSPLIT3");
        saveData();

    }

    public FileConfiguration getData(){
        return cfg;
    }

    public void saveData(){

        try{
            cfg.save(cfgfile);
        }
        catch (IOException e){
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "An error has occurred while trying to save the data.yml file, refer to the stacktrace below");
            e.printStackTrace();
        }

    }

    public void reload(){
        cfg = YamlConfiguration.loadConfiguration(cfgfile);
    }

}
