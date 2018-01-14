package com.lss233.phoenix.spigot.utils.spigot.entity;

import com.lss233.phoenix.entity.Entity;
import com.lss233.phoenix.entity.EntityTypes;
import com.lss233.phoenix.entity.living.Player;
import com.lss233.phoenix.module.Module;
import com.lss233.phoenix.spigot.SpigotMain;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Vector;

import static com.lss233.phoenix.spigot.SpigotMain.getTransformer;

/**
 *
 */
public interface PlayerTransform {
    /**
     * Convert a Bukkit player instance to a Phoenix player instance.
     * @param player The Bukkit player instance.
     * @return The Phoenix player instance.
     */
    default Player toPhoenix(org.bukkit.entity.Player player){
        Entity _super = getTransformer().toPhoenix((org.bukkit.entity.Entity) player);
        return new Player() {

            @Override
            public EntityTypes getType() {
                return EntityTypes.valueOf(player.getType().toString());
            }

            @Override
            public boolean hasPassenger(Entity entity) {
                return _super.hasPassenger(entity);
            }

            @Override
            public List<Entity> getPassengers() {
                return _super.getPassengers();
            }

            @Override
            public boolean addPassenger(Entity entity) {
                return _super.addPassenger(entity);
            }

            @Override
            public void clearPassengers() {
                _super.clearPassengers();
            }

            @Override
            public void removePassenger(Entity entity) {
                _super.removePassenger(entity);
            }

            @Override
            public Optional<Entity> getVehicle() {
                return _super.getVehicle();
            }

            @Override
            public boolean setVehicle(Entity entity) {
                return _super.setVehicle(entity);
            }

            @Override
            public Entity getBaseVehicle() {
                return _super.getBaseVehicle();
            }

            @Override
            public Vector getVelocity() {
                return _super.getVelocity();
            }

            @Override
            public boolean gravity() {
                return _super.gravity();
            }

            @Override
            public boolean teleport(com.lss233.phoenix.world.Location location) {
                return _super.teleport(location);
            }

            @Override
            public boolean teleport(Entity entity) {
                return _super.teleport(entity);
            }

            @Override
            public double getHealth() {
                return player.getHealth();
            }

            @Override
            public void setHealth(double health) {
                player.setHealth(health);
            }

            @Override
            public double getMaxHealth() {
                return player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
            }

            @Override
            public com.lss233.phoenix.world.Location getLocation() {
                return _super.getLocation();
            }

            @Override
            public com.lss233.phoenix.world.World getWorld() {
                return _super.getWorld();
            }

            @Override
            public UUID getUniqueId() {
                return _super.getUniqueId();
            }

            @Override
            public int hashCode() {
                return _super.hashCode();
            }

            @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
            @Override
            public boolean equals(Object object) {
                return _super.equals(object);
            }

            @Override
            public void sendPluginMessage(Module source, String channel, byte[] message) {
                player.sendPluginMessage(SpigotMain.getInstance(), channel, message);
            }


            public String getName() {
                return player.getName();
            }

            @Override
            public void sendMessage(String message) {
                player.sendMessage(message);
            }

            @Override
            public void sendMessage(String[] message) {
                player.sendMessage(message);
            }

        };
    }
    default org.bukkit.entity.Player toSpigot(Player player){
        return Bukkit.getPlayer(player.getUniqueId());
    }
}
