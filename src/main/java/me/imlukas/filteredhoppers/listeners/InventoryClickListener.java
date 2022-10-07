package me.imlukas.filteredhoppers.listeners;

import me.imlukas.filteredhoppers.FilteredHoppers;
import me.imlukas.filteredhoppers.data.HopperData;
import me.imlukas.filteredhoppers.data.HopperFilterMode;
import me.imlukas.filteredhoppers.gui.HopperFilterGui;
import me.imlukas.filteredhoppers.utils.GuiUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryClickListener implements Listener {

    private final HopperData hopperData;
    private final List<Integer> borderPositions;
    private final List<Integer> itemPositions = new ArrayList<>();

    public InventoryClickListener(FilteredHoppers main) {
        hopperData = main.getHopperData();
        borderPositions = GuiUtil.obtainBorders(4);
        borderPositions.replaceAll(integer -> integer + 18);
    }
    @EventHandler
    private void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof HopperFilterGui){
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) {
            return;
        }
        if (!(clickedInventory.getHolder() instanceof HopperFilterGui hopperFilterGui)) {
            return;
        }
        event.setCancelled(true);
        UUID hopperUUID = hopperFilterGui.getHopperUUID();
        if (event.getSlot() == 10){
            if (clickedInventory.getItem(10).getType().equals(hopperFilterGui.getWhiteListItem().getType())){
                clickedInventory.setItem(10, hopperFilterGui.getBlackListItem());
                hopperData.setMode(HopperFilterMode.BLACKLIST, hopperUUID);
                return;
            }
            clickedInventory.setItem(10, hopperFilterGui.getWhiteListItem());
            hopperData.setMode(HopperFilterMode.WHITELIST, hopperUUID);
            return;
        }

        if (borderPositions.contains(event.getSlot())) {
            event.setCancelled(true);
            return;
        }

        ItemStack itemToAdd = event.getCursor();
        if (itemToAdd == null) {
            return;
        }
        if (hopperData.getFilteredItems(hopperUUID).contains(itemToAdd.getType().toString())){
            return;
        }

        ItemStack itemCloned = itemToAdd.clone();
        itemCloned.setAmount(1);

        hopperData.addFilterItem(itemCloned, hopperUUID);
        clickedInventory.setItem(event.getSlot(), itemCloned);

    }
}
