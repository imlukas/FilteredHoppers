package me.imlukas.filteredhoppers;

import lombok.Getter;
import me.imlukas.filteredhoppers.commands.GiveHopperItemCommand;
import me.imlukas.filteredhoppers.data.HopperStorage;
import me.imlukas.filteredhoppers.customitem.HopperItem;
import me.imlukas.filteredhoppers.data.HopperData;
import me.imlukas.filteredhoppers.gui.HopperFilterGui;
import me.imlukas.filteredhoppers.listeners.InventoryClickListener;
import me.imlukas.filteredhoppers.listeners.InventoryCloseListener;
import me.imlukas.filteredhoppers.listeners.PlayerInteractListener;
import me.imlukas.filteredhoppers.utils.GuiUtil;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class FilteredHoppers extends JavaPlugin {
    private HopperData hopperData;
    private HopperStorage hopperStorage;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        HopperItem.init(this);
        hopperStorage = new HopperStorage(this, "hoppers.yml");
        hopperData = new HopperData(this);
        registerCommands();
        registerListeners();
    }

    private void registerCommands(){
        getCommand("hopperitem").setExecutor(new GiveHopperItemCommand());

    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
