package me.imlukas.filteredhoppers.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");


    public static String setColor(String message) {
        String[] split = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
        int minorVer = Integer.parseInt(split[1]);

        if (minorVer >= 16) {
            Matcher matcher = pattern.matcher(message);

            while (matcher.find()) {
                String color = message.substring(matcher.start(), matcher.end());
                message = message.replace(color, ChatColor.of(color) + "");
                matcher = pattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
