package com.lss233.phoenix.spigot.utils.spigot.utils;

import java.util.Vector;

/**
 *
 */
public interface VelocityTransform {
    default Vector toPhoenix(org.bukkit.util.Vector velocity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
