package me.emperio.pulsecore.objects;

import me.emperio.pulsecore.util.EmpUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ISpawner {

    private int tier;
    private Material type;
    private ItemStack item;

    EmpUtil api = new EmpUtil();

    public ISpawner(int tier, String type){
        this.tier = tier;
        this.type = Material.getMaterial(type);
    }

    public ItemStack getItemStack(){

        ItemStack item = new ItemStack(Material.BEACON);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(api.getColored("&bType » &9" + type.toString()));
        lore.add(api.getColored("&bTier » &9" + tier));
        lore.add(api.getColored("&7&oSpawns a &a&o" + type.toString() + "&7&o every&a&o " + ISpawnerManager.getSpawnFrequencyFromTier(tier)
        + "&7&o seconds"));
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setDisplayName(api.getColored(ISpawnerManager.name));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;


    }

    public static ISpawner fromItemStack(ItemStack item){

        String typeRaw = ChatColor.stripColor(item.getItemMeta().getLore().get(0));
        String tierRaw = ChatColor.stripColor(item.getItemMeta().getLore().get(1));

        String type = typeRaw.split(" » ")[1];
        int tier = Integer.parseInt(tierRaw.split(" » ")[1]);

        return new ISpawner(tier, type);

    }



    public int getTier() {
        return tier;
    }

    public Material getType() {
        return type;
    }

    public static ISpawner fromString(String serializedString){

        String type = serializedString.split("SPLIT")[0];
        int tier = Integer.parseInt(serializedString.split("SPLIT")[1]);
        return new ISpawner(tier, type);

    }
}
