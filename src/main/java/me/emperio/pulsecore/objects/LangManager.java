package me.emperio.pulsecore.objects;

import me.emperio.pulsecore.PulseCore;

public class LangManager {
    PulseCore plugin;
    public LangManager(PulseCore instance){
        this.plugin = instance;
    }

    public String get(String query){
        return plugin.getConfig().getString("lang." + query);
    }

}
