package me.imlukas.filteredhoppers.listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.imlukas.filteredhoppers.FilteredHoppers;
import me.imlukas.filteredhoppers.data.HopperData;
import me.imlukas.filteredhoppers.gui.HopperFilterGui;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerInteractListener implements Listener {
    private final FilteredHoppers main;
    private final HopperData hopperData;

    public PlayerInteractListener(FilteredHoppers main){
        this.main = main;
        hopperData = main.getHopperData();
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        if(clickedBlock == null){
            return;
        }
        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }
        Material blockType = event.getClickedBlock().getType();
        if (!blockType.equals(Material.HOPPER)) {
            return;
        }
        NBTItem nbtItem = new NBTItem(item);
        if (!nbtItem.hasKey("isHopperItem")) {
            return;
        }
        hopperData.setUUID(clickedBlock);
        HopperFilterGui hopperFilterGui = new HopperFilterGui(main, hopperData.getUUID(clickedBlock));
        player.openInventory(hopperFilterGui.getInventory());

    }


}
