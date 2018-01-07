package com.lss233.phoenix.spigot;

import com.lss233.phoenix.channel.MessageListener;
import com.lss233.phoenix.entity.Entity;
import com.lss233.phoenix.entity.EntityTypes;
import com.lss233.phoenix.module.Module;
import com.lss233.phoenix.utils.Identifiable;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.*;

import static com.lss233.phoenix.spigot.PhoenixUtils.toSpigot;

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
        Entity _super = toPhoenix((org.bukkit.entity.Entity)BPlayer);
        com.lss233.phoenix.entity.living.Player PPlayer;

            PPlayer = new com.lss233.phoenix.entity.living.Player() {

                @Override
                public EntityTypes getType() {
                    return EntityTypes.valueOf(BPlayer.getType().toString());
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
                    return BPlayer.getHealth();
                }

                @Override
                public void setHealth(double health) {
                    BPlayer.setHealth(health);
                }

                @Override
                public double getMaxHealth() {
                    return BPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
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

                @Override
                public boolean equals(Object object) {
                    return _super.equals(object);
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
                public void sendMessage(String message) {
                    BPlayer.sendMessage(message);
                }

                @Override
                public void sendMessage(String[] message) {
                    BPlayer.sendMessage(message);
                }

            };
        return PPlayer;
    }

    public static Vector toPhoenix(org.bukkit.util.Vector velocity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Convert a Bukkit Entity instance to a Phoenix Entity instance.
     * @param BEntity The Bukkit Entity instance.
     * @return The Phoenix Entity instance.
     */
    public static Entity toPhoenix(org.bukkit.entity.Entity BEntity){
        return new Entity() {
            @Override
            public EntityTypes getType() {
                return EntityTypes.valueOf(BEntity.getType().toString());
            }

            @Override
            public boolean hasPassenger(Entity entity) {
                return BEntity.getPassengers().contains(toSpigot(entity));
            }

            @Override
            public List<Entity> getPassengers() {
                List<Entity> list = new ArrayList<>();
                for (org.bukkit.entity.Entity entity : BEntity.getPassengers()) {
                    list.add(toPhoenix(entity));
                }
                return list;
            }

            @Override
            public boolean addPassenger(Entity entity) {
                return BEntity.addPassenger(toSpigot(entity));
            }

            @Override
            public void clearPassengers() {
                BEntity.getPassengers().forEach(BEntity::removePassenger);
            }

            @Override
            public void removePassenger(Entity entity) {
                BEntity.removePassenger(toSpigot(entity));
            }

            @Override
            public Optional<Entity> getVehicle() {
                return Optional.of(toPhoenix(BEntity.getVehicle()));
            }

            @Override
            public boolean setVehicle(Entity entity) {
                return entity.addPassenger(toPhoenix(BEntity));
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
                return toPhoenix(BEntity.getVelocity());
            }

            @Override
            public boolean gravity() {
                return BEntity.hasGravity();
            }

            @Override
            public boolean teleport(com.lss233.phoenix.world.Location location) {
                return BEntity.teleport(toSpigot(location));
            }

            @Override
            public boolean teleport(Entity entity) {
                return BEntity.teleport(toSpigot(entity));
            }

            @Override
            public UUID getUniqueId() {
                return BEntity.getUniqueId();
            }

            @Override
            public com.lss233.phoenix.world.Location getLocation() {
                return toPhoenix(BEntity.getLocation());
            }

            @Override
            public com.lss233.phoenix.world.World getWorld() {
                return toPhoenix(BEntity.getWorld());
            }

            @Override
            public int hashCode(){
                return Objects.hashCode(this.getUniqueId());
            }

            @Override
            public boolean equals(Object object) {
                if (object instanceof Identifiable) {
                    Identifiable that = (Identifiable)object;
                    return Objects.equals(this.getUniqueId(), that.getUniqueId());
                }
                return false;
            }
        };
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
