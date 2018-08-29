package com.lss233.phoenix.spigot.utils.spigot.entity;

import com.lss233.phoenix.entity.Entity;
import com.lss233.phoenix.entity.EntityType;
import com.lss233.phoenix.math.Vector;
import com.lss233.phoenix.utils.Identifiable;
import org.bukkit.Bukkit;

import java.util.*;

import static com.lss233.phoenix.spigot.SpigotMain.getTransformer;

/**
 *
 */
public interface EntityTransform {
    /**
     * Convert a Bukkit Entity instance to a Phoenix Entity instance.
     * @param entity The Bukkit Entity instance.
     * @return The Phoenix Entity instance.
     */
    default Entity toPhoenix(org.bukkit.entity.Entity entity){
        return new Entity() {
            @Override
            public EntityType getType() {
                return EntityType.valueOf(entity.getType().toString());
            }

            @Override
            public boolean hasPassenger(Entity target) {
                return entity.getPassengers().contains(toSpigot(target));
            }

            @Override
            public List<Entity> getPassengers() {
                List<Entity> list = new ArrayList<>();
                for (org.bukkit.entity.Entity entity : entity.getPassengers()) {
                    list.add(toPhoenix(entity));
                }
                return list;
            }

            @Override
            public boolean addPassenger(Entity passenger) {
                return entity.addPassenger(toSpigot(passenger));
            }

            @Override
            public void clearPassengers() {
                entity.getPassengers().forEach(entity::removePassenger);
            }

            @Override
            public void removePassenger(Entity passenger) {
                entity.removePassenger(toSpigot(passenger));
            }

            @Override
            public Optional<Entity> getVehicle() {
                return Optional.of(toPhoenix(entity.getVehicle()));
            }

            @Override
            public boolean setVehicle(Entity vehicle) {
                return entity.addPassenger(toSpigot(vehicle));
            }

            @Override
            public Entity getBaseVehicle() {
                if(this.getVehicle().isPresent())
                    return this.getVehicle().get().getBaseVehicle();
                else
                    return this;
            }

            @Override
            public Vector getVelocity() {
                return getTransformer().toPhoenix(entity.getVelocity());
            }

            @Override
            public boolean gravity() {
                return entity.hasGravity();
            }

            @Override
            public boolean teleport(com.lss233.phoenix.world.Location location) {
                return entity.teleport(getTransformer().toSpigot(location));
            }

            @Override
            public boolean teleport(Entity target) {
                return entity.teleport(toSpigot(target));
            }

            @Override
            public UUID getUniqueId() {
                return entity.getUniqueId();
            }

            @Override
            public com.lss233.phoenix.world.Location getLocation() {
                return getTransformer().toPhoenix(entity.getLocation());
            }

            @Override
            public com.lss233.phoenix.world.World getWorld() {
                return getTransformer().toPhoenix(entity.getWorld());
            }

            @Override
            public int hashCode(){
                return Objects.hashCode(this.getUniqueId());
            }

            @Override
            public boolean equals(Object object) {
                if (object instanceof Identifiable) {
                    Identifiable that = (Identifiable)object;
                    return equals(that);
                }
                return false;
            }
        };
    }
    /**
     * Covert a Phoenix Entity instance to a Spigot instance
     * @param entity The Phoenix Entity
     * @return The Spigot Entity
     */
    default org.bukkit.entity.Entity toSpigot(Entity entity) {
        return Bukkit.getEntity(entity.getUniqueId());
    }
}
