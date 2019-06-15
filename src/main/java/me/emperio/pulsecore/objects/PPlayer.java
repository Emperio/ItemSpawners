package me.emperio.pulsecore.objects;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PPlayer {

    private Player player;

    public PPlayer(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void sendMessage(String message){
        String messageToSend = ChatColor.translateAlternateColorCodes('&', message);
        player.sendMessage(messageToSend);
    }
}
