package com.lss233.phoenix.spigot.utils.spigot.block;

import com.lss233.phoenix.block.Block;

/**
 *
 */
public interface BlockTransform {
    /**
     * Convert a Bukkit Block instance to a Phoenix Block instance.
     * @param block The Bukkit Block instance.
     * @return The Phoenix Block instance.
     */
    default Block toPhoenix(org.bukkit.block.Block  block) {
        throw new UnsupportedOperationException("Operation not supported.");
    }
}
