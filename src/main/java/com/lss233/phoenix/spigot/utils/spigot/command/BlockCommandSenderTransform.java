package com.lss233.phoenix.spigot.utils.spigot.command;

import com.lss233.phoenix.block.Block;
import com.lss233.phoenix.command.BlockCommandSender;

import static com.lss233.phoenix.spigot.SpigotMain.getTransformer;

/**
 *
 */
public interface BlockCommandSenderTransform {
    /**
     * Convert a Bukkit BlockCommandSender instance to a Phoenix BlockCommandSender instance.
     * @param blockCommandSender The Bukkit BlockCommandSender instance,
     * @return The Phoenix BlockCommandSender instance.
     */
    default BlockCommandSender toPhoenix(org.bukkit.command.BlockCommandSender blockCommandSender){
        return new BlockCommandSender() {
            @Override
            public boolean hasPermission(String s) {
                return blockCommandSender.hasPermission(s);
            }

            @Override
            public Block getBlock() {
                return getTransformer().toPhoenix(blockCommandSender.getBlock());
            }

            @Override
            public void sendMessage(String message) {
                blockCommandSender.sendMessage(message);
            }

            @Override
            public void sendMessage(String[] message) {
                blockCommandSender.sendMessage(message);
            }
        };
    }
}
