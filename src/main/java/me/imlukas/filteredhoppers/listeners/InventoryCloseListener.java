package me.imlukas.filteredhoppers.listeners;

import me.imlukas.filteredhoppers.FilteredHoppers;
import me.imlukas.filteredhoppers.data.HopperData;
import me.imlukas.filteredhoppers.gui.HopperFilterGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryCloseListener implements Listener {

    private final HopperData hopperData;

    public InventoryCloseListener(FilteredHoppers main){
        hopperData = main.getHopperData();
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event){
        Inventory closedInventory = event.getInventory();
        if (!(closedInventory.getHolder() instanceof HopperFilterGui hopperFilterGui)){
            return;
        }
        hopperData.updateFilterItems(hopperFilterGui.getHopperUUID());

    }
}
