package me.emperio.pulsecore.commands;

import me.emperio.pulsecore.PulseCore;
import me.emperio.pulsecore.objects.ISpawnerManager;
import me.emperio.pulsecore.objects.LangManager;
import me.emperio.pulsecore.objects.PPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ISpawnerCommand implements CommandExecutor {

    PulseCore plugin;

    public ISpawnerCommand(PulseCore instance){
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        PPlayer player = new PPlayer((Player) sender);
        ISpawnerManager isManager = new ISpawnerManager();
        LangManager lm = plugin.getLangManager();

        if(!player.getPlayer().hasPermission("ispawner-give")){
            player.sendMessage("&cYou do not have access to that command");
            return true;
        }

        if(args.length == 0){
            player.sendMessage(lm.get("no-subcommand-ispawner"));
            return true;
        }
        if(args[0].equals("give")){

            if(args.length != 4){
                player.sendMessage(lm.get("insufficient-args-ispawner-give"));
                return true;
            }
            String playerName = args[1];
            String type = args[2];
            String tier = args[3];

            Player receiver = Bukkit.getPlayerExact(playerName);

            if(receiver == null){
                player.sendMessage("&cPlayer is not valid");
                return true;
            }

            Material mat = Material.getMaterial(type);
            if(mat == null){
                player.sendMessage(lm.get("invalid-material-ispawner"));
                return true;
            }

            int tierInt= 0;

            try{
                tierInt = Integer.parseInt(tier);
            }
            catch (NumberFormatException e){
                player.sendMessage("&cThe &7tier&c argument is not a valid number!");
                return true;
            }

            if(tierInt > 3 || tierInt < 1){
                player.sendMessage(lm.get("invalid-tier-ispawner"));
                return true;
            }

            player.getPlayer().getInventory().addItem(isManager.getSpawner(type, tierInt).getItemStack());

        }
        else{
            player.sendMessage(lm.get("invalid-subcommand-ispawner"));
        }

        return true;
    }
}
