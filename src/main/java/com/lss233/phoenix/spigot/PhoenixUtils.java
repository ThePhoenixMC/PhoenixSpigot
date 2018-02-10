package com.lss233.phoenix.spigot;

import com.lss233.phoenix.entity.Entity;
import com.lss233.phoenix.world.Location;

import static org.bukkit.Bukkit.*;

/**
 *
 */
public class PhoenixUtils {
    /**
     * Covert a Phoenix Entity instance to a Spigot instance
     * @param entity The Phoenix Entity
     * @return The Spigot Entity
     */
    public static org.bukkit.entity.Entity toSpigot(Entity entity) {
        return getEntity(entity.getUniqueId());
    }

    /**
     * Covert a Phoenix Location instance to a Spigot instance
     * @param location The Phoenix Location
     * @return The Spigot Location
     */
    public static org.bukkit.Location toSpigot(Location location) {
        return new org.bukkit.Location(getWorld(location.getWorld().getUniqueId()), location.getX(), location.getY(), location.getZ());
    }
}
