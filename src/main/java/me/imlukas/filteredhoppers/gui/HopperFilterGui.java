package me.imlukas.filteredhoppers.gui;

import lombok.Getter;
import me.imlukas.filteredhoppers.FilteredHoppers;
import me.imlukas.filteredhoppers.data.HopperData;
import me.imlukas.filteredhoppers.utils.GuiUtil;
import me.imlukas.filteredhoppers.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HopperFilterGui implements InventoryHolder {
    private final Inventory hopperFilterGui;
    private final UUID blockUUID;
    @Getter
    private final ItemStack borderItem, whiteListItem, blackListItem;

    public HopperFilterGui(FilteredHoppers main, UUID hopperUUID) {
        HopperData hopperData = main.getHopperData();
        FileConfiguration config = main.getConfig();
        blockUUID = hopperUUID;
        borderItem = new ItemStack(Material.valueOf(config.getString("hopper-inventory.border-item")));
        whiteListItem = new ItemStack(Material.valueOf(config.getString("hopper-inventory.whitelist-item")));
        blackListItem = new ItemStack(Material.valueOf(config.getString("hopper-inventory.blacklist-item")));

        hopperFilterGui = Bukkit.createInventory(this, 54, TextUtil.setColor(config.getString("hopper-inventory.title")));
        if (config.getBoolean("hopper-inventory.border")) {
            GuiUtil.fillBorder(hopperFilterGui, borderItem, 6);
        }
        for (int i = 18; i < 27; i++) {
            hopperFilterGui.setItem(i, borderItem);
        }

        if (hopperData.getMode(hopperUUID).equalsIgnoreCase("whitelist")) {
            hopperFilterGui.setItem(10, whiteListItem);
        } else {
            hopperFilterGui.setItem(10, blackListItem);
        }
        int count = 27;
        for (String item : hopperData.getFilteredItems(hopperUUID)) {
            Material itemMaterial = Material.valueOf(item);
            hopperFilterGui.setItem(count, new ItemStack(itemMaterial));
            count++;
        }

    }

    public UUID getHopperUUID() {
        return blockUUID;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return hopperFilterGui;
    }
}
