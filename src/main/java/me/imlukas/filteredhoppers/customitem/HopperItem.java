package me.imlukas.filteredhoppers.customitem;

import de.tr7zw.nbtapi.NBTItem;
import me.imlukas.filteredhoppers.FilteredHoppers;
import me.imlukas.filteredhoppers.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HopperItem {
    private static ItemStack ITEM;

    public static void init(FilteredHoppers main) {
        if(ITEM != null)
            return;

        FileConfiguration config = main.getConfig();
        String itemMaterial = config.getString("hopper-item.material");
        String itemName = config.getString("hopper-item.name");
        List<String> itemLore = config.getStringList("hopper-item.lore");
        boolean itemGlow = config.getBoolean("hopper-item.glow");

        ITEM = new ItemBuilder(Material.valueOf(itemMaterial))
                .name(itemName)
                .lore(itemLore)
                .glowing(itemGlow)
                .build();

        NBTItem nbtItem = new NBTItem(ITEM);
        nbtItem.setBoolean("isHopperItem", true);
        nbtItem.applyNBT(ITEM);
    }


    public static ItemStack get() {
        return ITEM;
    }
}
