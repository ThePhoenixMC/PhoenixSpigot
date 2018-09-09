package com.lss233.phoenix.spigot.utils.spigot;

import com.lss233.phoenix.world.Location;
import org.bukkit.Bukkit;

import static com.lss233.phoenix.spigot.SpigotMain.getTransformer;

/**
 *
 */
public interface LocationTransform {
    default Location toPhoenix(org.bukkit.Location location){
        return new Location(getTransformer().toPhoenix(location.getWorld()),
                location.getX(),
                location.getY(),
                location.getZ());
    }

    default org.bukkit.Location toSpigot(Location location) {
        return new org.bukkit.Location(Bukkit.getWorld(location.getWorld().getUniqueId()),
                location.getX(),
                location.getY(),
                location.getZ());
    }
}
