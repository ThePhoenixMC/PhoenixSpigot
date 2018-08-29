package com.lss233.phoenix.spigot.utils.spigot;

import com.lss233.phoenix.entity.Entity;
import com.lss233.phoenix.entity.EntityType;
import com.lss233.phoenix.math.Vector;
import com.lss233.phoenix.world.Chunk;
import com.lss233.phoenix.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.lss233.phoenix.spigot.SpigotMain.getTransformer;

/**
 *
 */
public interface ChunkTransform {
    /**
     * Convert a Bukkit Chunk instance to a Phoenix Chunk instance.
     *
     * @param chunk The Bukkit Chunk instance.
     * @return The Phoenix Chunk instance.
     */
    default Chunk toPhoenix(org.bukkit.Chunk chunk) {
        return new Chunk() {
            @Override
            public World getWorld() {
                return getTransformer().toPhoenix(chunk.getWorld());
            }

            @Override
            public int getX() {
                return chunk.getX();
            }

            @Override
            public int getZ() {
                return chunk.getZ();
            }

            @Override
            public boolean isLoaded() {
                return chunk.isLoaded();
            }

            @Override
            public boolean load(boolean b) {
                return chunk.load(b);
            }

            @Override
            public boolean unload() {
                return chunk.unload();
            }

            @Override
            public Optional<Entity> createEntity(EntityType entityType, Vector vector) {
                vector.setX(16 * getX() + vector.getX());
                vector.setZ(16 * getZ() + vector.getZ());
                return getWorld().createEntity(entityType, vector);
            }

            @Override
            public List<Entity> getNearbyEntities(Vector vector, double v) {
                vector.setX(16 * getX() + vector.getX());
                vector.setZ(16 * getZ() + vector.getZ());
                return getWorld().getNearbyEntities(vector, v);
            }

            @Override
            public List<Entity> getEntities() {
                return Arrays.stream(chunk.getEntities())
                        .map(entity -> getTransformer().toPhoenix(entity))
                        .collect(Collectors.toList());
            }

            @Override
            public Optional<Entity> getEntity(UUID uuid) {
                return Arrays.stream(chunk.getEntities())
                        .map(entity -> getTransformer().toPhoenix(entity))
                        .filter(entity -> entity.getUniqueId().equals(uuid))
                        .findAny();
            }
        };
    }

    default org.bukkit.Chunk toSpigot(Chunk chunk) {
        return getTransformer().toSpigot(chunk.getWorld()).getChunkAt(chunk.getX(), chunk.getZ());
    }
}
