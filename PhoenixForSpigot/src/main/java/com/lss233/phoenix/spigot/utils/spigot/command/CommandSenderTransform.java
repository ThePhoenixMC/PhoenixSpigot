package com.lss233.phoenix.spigot.utils.spigot.command;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static com.lss233.phoenix.spigot.SpigotMain.getTransformer;

/**
 *
 */
public interface CommandSenderTransform extends BlockCommandSenderTransform, ConsoleCommandTransform{
    /**
     * Convert a Bukkit CommandSender instance to a Phoenix CommandSender instance.
     * @param commandSender The Bukkit CommandSender instance,
     * @return The Phoenix CommandSender instance.
     */
    default com.lss233.phoenix.command.CommandSender toPhoenix(CommandSender commandSender) {
        com.lss233.phoenix.command.CommandSender PSender;
        if (commandSender instanceof BlockCommandSender) {
            return toPhoenix((BlockCommandSender)commandSender);
        } else if (commandSender instanceof Player) {
            return getTransformer().toPhoenix((Player) commandSender);
        } else if (commandSender instanceof ConsoleCommandSender) {
            return toPhoenix((ConsoleCommandSender) commandSender);
        } else {
            throw new UnsupportedOperationException("Operation not supported.");
        }
    }
}
