package me.imlukas.filteredhoppers.data;

import me.imlukas.filteredhoppers.utils.storage.YMLBase;
import org.bukkit.plugin.java.JavaPlugin;

public class HopperStorage extends YMLBase {
    public HopperStorage(JavaPlugin plugin, String name) {
        super(plugin, "hoppers.yml");
    }
}
