package com.lss233.phoenix.spigot;

import com.lss233.phoenix.channel.MessageListener;
import com.lss233.phoenix.entity.Entity;
import com.lss233.phoenix.entity.EntityTypes;
import com.lss233.phoenix.entity.Vehicle;
import com.lss233.phoenix.module.Module;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.*;

/**
 * .Spigot classes to Phoenix classes utils.
 */
public class SpigotUtils {
    /**
     * Convert a Bukkit World instance to a Phoenix World instance.
     * @param BWorld The Bukkit World instance.
     * @return The Phoenix World instance.
     */
    public static com.lss233.phoenix.world.World toPhoenix(World BWorld) {
        com.lss233.phoenix.world.World PWorld;
        PWorld = new com.lss233.phoenix.world.World() {
            @Override
            public String getName() {
                return BWorld.getName();
            }

            @Override
            public List<com.lss233.phoenix.entity.living.Player> getPlayers() {
                List<com.lss233.phoenix.entity.living.Player> players = new ArrayList<>();
                for (Player player : BWorld.getPlayers())
                    players.add(toPhoenix(player));

                return players;
            }

            @Override
            public UUID getUniqueId() {
                return BWorld.getUID();
            }

            @Override
            public boolean equals(Object object) {
                if (object instanceof com.lss233.phoenix.entity.living.Player) {
                    com.lss233.phoenix.world.World that = (com.lss233.phoenix.world.World) object;
                    return Objects.equals(this.getUniqueId(), that.getUniqueId());
                }
                return false;
            }

            @Override
            public int hashCode() {
                return Objects.hash(this.getName(), this.getUniqueId());
            }
        };
        return PWorld;
    }

    /**
     * Convert a Bukkit player instance to a Phoenix player instance.
     * @param BPlayer The Bukkit player instance.
     * @return The Phoenix player instance.
     */
    public static com.lss233.phoenix.entity.living.Player toPhoenix(Player BPlayer) {
        com.lss233.phoenix.entity.living.Player PPlayer;

            PPlayer = new com.lss233.phoenix.entity.living.Player() {

                @Override
                public EntityTypes getType() {
                    return null;
                }

                @Override
                public boolean hasPassenger(Entity entity) {
                    return false;
                }

                @Override
                public List<Entity> getPassengers() {
                    return null;
                }

                @Override
                public boolean addPassenger(Entity entity) {
                    return false;
                }

                @Override
                public void clearPassengers() {

                }

                @Override
                public void removePassenger(Entity entity) {

                }

                @Override
                public Optional<Vehicle> getVehicle() {
                    return Optional.empty();
                }

                @Override
                public boolean setVehicle(Entity entity) {
                    return false;
                }

                @Override
                public Entity getBaseVehicle() {
                    return null;
                }

                @Override
                public Vector getVelocity() {
                    return null;
                }

                @Override
                public boolean gravity() {
                    return false;
                }

                @Override
                public boolean teleport(com.lss233.phoenix.world.Location location) {
                    return false;
                }

                @Override
                public boolean teleport(Entity entity) {
                    return false;
                }

                @Override
                public double getHealth() {
                    return 0;
                }

                @Override
                public void setHealth(double health) {

                }

                @Override
                public double getMaxHealth() {
                    return 0;
                }

                @Override
                public com.lss233.phoenix.world.Location getLocation() {
                    return toPhoenix(BPlayer.getLocation());
                }

                @Override
                public com.lss233.phoenix.world.World getWorld() {
                    return null;
                }

                @Override
                public void sendPluginMessage(Module source, String channel, byte[] messaeg) {
                    BPlayer.sendPluginMessage(SpigotMain.getInstance(), channel, messaeg);
                }

                @Override
                public String getName() {
                    return BPlayer.getName();
                }

                @Override
                public UUID getUniqueId() {
                    return BPlayer.getUniqueId();
                }

                @Override
                public void sendMessage(String message) {
                    BPlayer.sendMessage(message);
                }

                @Override
                public void sendMessage(String[] message) {
                    BPlayer.sendMessage(message);
                }

                @Override
                public boolean equals(Object object) {
                    if (object instanceof com.lss233.phoenix.entity.living.Player) {
                        com.lss233.phoenix.entity.living.Player that = (com.lss233.phoenix.entity.living.Player) object;
                        return this.getName().equals(that.getName()) && Objects.equals(this.getUniqueId(), that.getUniqueId());
                    }
                    return false;
                }

                @Override
                public int hashCode() {
                    return Objects.hash(this.getName(), this.getUniqueId());
                }
            };
        /*    playerMap.put(BPlayer, PPlayer);
        }*/
        return PPlayer;
    }


    /**
     * Convert a Bukkit CommandSender instance to a Phoenix CommandSender instance.
     * @param BSender The Bukkit CommandSender instance,
     * @return The Phoenix CommandSender instance.
     */
    public static com.lss233.phoenix.command.CommandSender toPhoenix(CommandSender BSender) {
        com.lss233.phoenix.command.CommandSender PSender;
            if (BSender instanceof BlockCommandSender) {
                PSender = new com.lss233.phoenix.command.BlockCommandSender() {

                    @Override
                    public com.lss233.phoenix.block.Block getBlock() {
                        return toPhoenix(((BlockCommandSender) BSender).getBlock());
                    }

                    @Override
                    public void sendMessage(String message) {
                        BSender.sendMessage(message);
                    }

                    @Override
                    public void sendMessage(String[] message) {
                        BSender.sendMessage(message);
                    }
                };
            } else if (BSender instanceof Player) {
                PSender = toPhoenix((Player) BSender);
            } else if (BSender instanceof ConsoleCommandSender) {

                PSender = new com.lss233.phoenix.command.ConsoleCommandSender() {

                    @Override
                    public void sendMessage(String message) {
                        BSender.sendMessage(message);
                    }

                    @Override
                    public void sendMessage(String[] message) {
                        BSender.sendMessage(message);
                    }

                };
            } else {
                throw new UnsupportedOperationException("Operation not supported.");
            }
        return PSender;
    }

    /**
     * Convert a Bukkit Block instance to a Phoenix Block instance.
     * @param block The Bukkit Block instance.
     * @return The Phoenix Block instance.
     */
    public static com.lss233.phoenix.block.Block toPhoenix(Block block) {
        throw new UnsupportedOperationException("Operation not supported.");
    }

    /**
     * Convert a Bukkit MessageListener instance to a Phoenix PluginMessageListener instance.
     * @param listener The Bukkit MessageListener instance.
     * @return The Phoenix PluginMessageListener instance.
     */
    public static PluginMessageListener toPhoenix(MessageListener listener) {
        return (s, player, bytes) -> listener.onPluginMessageReceived(s, toPhoenix(player), bytes);
    }

    /**
     * Convert a Bukkit Location instance to a Phoenix Location instance.
     * @param location The Bukkit Location instance.
     * @return The Phoenix Location instance.
     */
    public static com.lss233.phoenix.world.Location toPhoenix(Location location) {
        com.lss233.phoenix.world.Location PLocation;
        PLocation = new com.lss233.phoenix.world.Location(toPhoenix(location.getWorld()), location.getX(), location.getY(), location.getZ());
        return PLocation;
    }
}
