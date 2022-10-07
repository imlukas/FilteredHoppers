package me.imlukas.filteredhoppers.commands;

import me.imlukas.filteredhoppers.FilteredHoppers;
import me.imlukas.filteredhoppers.customitem.HopperItem;
import org.bukkit.Bukkit;
import org.bukkit.block.Hopper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GiveHopperItemCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null) {
                target.getInventory().addItem(HopperItem.get());
                return true;
            }
            sender.sendMessage("[FilteredHoppers] Player " + args[1] + " Not Found!");
            return true;
        }

        player.getInventory().addItem(HopperItem.get());
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
