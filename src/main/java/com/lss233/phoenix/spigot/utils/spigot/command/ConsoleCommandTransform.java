package com.lss233.phoenix.spigot.utils.spigot.command;

import com.lss233.phoenix.command.ConsoleCommandSender;

/**
 *
 */
public interface ConsoleCommandTransform {
    /**
     * Convert a Bukkit ConsoleCommandSender instance to a Phoenix ConsoleCommandSender instance.
     * @param consoleCommandSender The Bukkit ConsoleCommandSender instance,
     * @return The Phoenix ConsoleCommandSender instance.
     */
    default ConsoleCommandSender toPhoenix(org.bukkit.command.ConsoleCommandSender consoleCommandSender){
        return new com.lss233.phoenix.command.ConsoleCommandSender() {

            @Override
            public void sendMessage(String message) {
                consoleCommandSender.sendMessage(message);
            }

            @Override
            public void sendMessage(String[] message) {
                consoleCommandSender.sendMessage(message);
            }

        };
    }
}
