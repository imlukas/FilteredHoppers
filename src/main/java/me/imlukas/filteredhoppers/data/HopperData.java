package me.imlukas.filteredhoppers.data;

import de.tr7zw.nbtapi.NBTBlock;
import de.tr7zw.nbtapi.NBTCompound;
import me.imlukas.filteredhoppers.FilteredHoppers;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HopperData {

    private final FileConfiguration hopperStorageConfig;
    private final HopperStorage hopperStorage;
    private final HashMap<UUID, List<String>> filteredItems = new HashMap<>();
    public HopperData(FilteredHoppers main){
        hopperStorage = main.getHopperStorage();
        hopperStorageConfig = hopperStorage.getConfiguration();
        for (String key : hopperStorageConfig.getConfigurationSection("hoppers").getKeys(false)){
            filteredItems.put(UUID.fromString(key), hopperStorageConfig.getStringList(key + "items"));
        }
    }
    public void addHopperToConfig(UUID blockUUID){
        hopperStorageConfig.createSection("hoppers." + blockUUID);
        hopperStorageConfig.createSection("hoppers." + blockUUID + ".mode");
        hopperStorageConfig.set("hoppers." + blockUUID + ".mode", "whitelist");
        hopperStorageConfig.createSection("hoppers." + blockUUID + ".items");
        hopperStorage.save();
        filteredItems.put(blockUUID, Collections.emptyList());

    }

    public void setUUID(Block block){
        NBTCompound nbtCompound = new NBTBlock(block).getData();
        if (nbtCompound.hasKey("hopperUUID")){
            return;
        }
        nbtCompound.setUUID("hopperUUID", UUID.randomUUID());
        addHopperToConfig(getUUID(block));
    }

    public void setMode(HopperFilterMode mode, UUID blockUUID) {
        hopperStorageConfig.set("hoppers." + blockUUID + ".mode", mode.toString());
        hopperStorage.save();
    }

    public UUID getUUID(Block block){
        NBTBlock nbtBlock = new NBTBlock(block);
        return nbtBlock.getData().getUUID("hopperUUID");
    }

    public List<String> getFilteredItems(Block block){
        return getFilteredItems(getUUID(block));
    }
    public List<String> getFilteredItems(UUID blockUUID){
        if (filteredItems.get(blockUUID) == null){
            return Collections.emptyList();
        }
        return filteredItems.get(blockUUID);
    }

    public String getMode(UUID blockUUID){
        if (hopperStorageConfig.getString("hoppers." + blockUUID + ".mode") == null){
            return "whitelist";
        }
        return hopperStorageConfig.getString("hoppers." + blockUUID + ".mode");

    }
    public void addFilterItem(ItemStack item, Block block){
        addFilterItem(item, getUUID(block));
    }
    public void addFilterItem(ItemStack item, UUID blockID){
        List<String> hopperItems = getFilteredItems(blockID);
        if (hopperItems.contains(item.getType().toString())){
            return;
        }
        System.out.println(hopperItems);
        hopperItems.add(item.getType().toString());
        filteredItems.put(blockID, hopperItems);
    }
    public void updateFilterItems(UUID blockUUID){
        List<String> filteredItemsStored = hopperStorageConfig.getStringList("hoppers." + blockUUID + ".items");
        System.out.println(filteredItemsStored);
        System.out.println(getFilteredItems(blockUUID));
        for (String item : getFilteredItems(blockUUID)){
            if (filteredItemsStored.contains(item)){
                continue;
            }
            filteredItemsStored.add(item);


        }
        System.out.println("after" + filteredItemsStored);
        hopperStorageConfig.set("hoppers." + blockUUID + ".items", filteredItemsStored);
        hopperStorage.save();
    }


}
