package com.lss233.phoenix.spigot;

import com.lss233.phoenix.channel.MessageListener;
import com.lss233.phoenix.module.Module;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * .
 */
public class SpigotUtils {
    /*
    private static Map<Player, com.lss233.phoenix.Player> playerMap = new HashMap<>();
    private static Map<World, com.lss233.phoenix.World> worldMap = new HashMap<>();
    private static Map<CommandSender, com.lss233.phoenix.command.CommandSender> commandSenderMap = new HashMap<>();
    */

    public static com.lss233.phoenix.World translateWorld(World BWorld) {
        com.lss233.phoenix.World PWorld;
        PWorld = new com.lss233.phoenix.World() {
            @Override
            public String getName() {
                return BWorld.getName();
            }

            @Override
            public List<com.lss233.phoenix.Player> getPlayers() {
                List<com.lss233.phoenix.Player> players = new ArrayList<>();
                for (Player player : BWorld.getPlayers())
                    players.add(translatePlayer(player));

                return players;
            }

            @Override
            public UUID getUUID() {
                return BWorld.getUID();
            }

            @Override
            public boolean equals(Object object) {
                if (object instanceof com.lss233.phoenix.Player) {
                    com.lss233.phoenix.World that = (com.lss233.phoenix.World) object;
                    return Objects.equals(this.getUUID(), that.getUUID());
                }
                return false;
            }

            @Override
            public int hashCode() {
                return Objects.hash(this.getName(), this.getUUID());
            }
        };
        return PWorld;
    }

    public static com.lss233.phoenix.Player translatePlayer(Player BPlayer) {
        // Needs improve.
        com.lss233.phoenix.Player PPlayer;

        /*if (playerMap.containsKey(BPlayer))
            PPlayer = playerMap.get(BPlayer);
        else {*/
            PPlayer = new com.lss233.phoenix.Player() {

                @Override
                public com.lss233.phoenix.Location getLocation() {
                    return translateLocation(BPlayer.getLocation());
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
                    if (object instanceof com.lss233.phoenix.Player) {
                        com.lss233.phoenix.Player that = (com.lss233.phoenix.Player) object;
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


    public static com.lss233.phoenix.command.CommandSender translateCommandSender(CommandSender BSender) {
        com.lss233.phoenix.command.CommandSender PSender;
        /*if (commandSenderMap.containsKey(BSender))
            PSender = commandSenderMap.get(BSender);
        else {*/
            if (BSender instanceof BlockCommandSender) {
                PSender = new com.lss233.phoenix.command.BlockCommandSender() {

                    @Override
                    public com.lss233.phoenix.block.Block getBlock() {
                        return translateBlock(((BlockCommandSender) BSender).getBlock());
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
                PSender = translatePlayer((Player) BSender);
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
            /*commandSenderMap.put(BSender, PSender);
        }*/
        return PSender;
    }

    public static com.lss233.phoenix.block.Block translateBlock(Block block) {
        throw new UnsupportedOperationException("Operation not supported.");
    }


    public static PluginMessageListener translatePluginMessageListener(MessageListener listener) {
        return (s, player, bytes) -> listener.onPluginMessageReceived(s, translatePlayer(player), bytes);
    }

    public static com.lss233.phoenix.Location translateLocation(Location to) {
        com.lss233.phoenix.Location PLocation;
        PLocation = new com.lss233.phoenix.Location(translateWorld(to.getWorld()), to.getX(), to.getY(), to.getZ());
        return PLocation;
    }
}
