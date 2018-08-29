package com.lss233.phoenix.spigot.utils.spigot.utils;


import com.lss233.phoenix.math.Vector;

/**
 *
 */
public interface VelocityTransform {
    default Vector toPhoenix(org.bukkit.util.Vector velocity) {
        return new Vector(velocity.getX(), velocity.getY(), velocity.getZ());
    }

    default org.bukkit.util.Vector toSpigot(Vector vector) {
        return new org.bukkit.util.Vector(vector.getX(), vector.getY(), vector.getZ());
    }
}
