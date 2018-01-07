package com.lss233.phoenix.spigot;

import com.lss233.phoenix.Phoenix;
import com.lss233.phoenix.entity.living.Player;
import com.lss233.phoenix.world.World;
import com.lss233.phoenix.channel.MessageListener;
import com.lss233.phoenix.command.Command;
import com.lss233.phoenix.command.PhoenixCommand;
import com.lss233.phoenix.logging.Logger;
import com.lss233.phoenix.module.Module;
import com.lss233.phoenix.spigot.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lss233.phoenix.spigot.SpigotUtils.toPhoenix;

/**
 * .
 */
public class SpigotMain extends JavaPlugin {
    private static SpigotMain instance;

    static SpigotMain getInstance() {
        return instance;
    }

    @Override
    public void onEnable(){
        instance = this;
        SpigotServer server = new SpigotServer();
        Phoenix.setServer(server);
        initSpigotSide();
    }

    private void initSpigotSide() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    private class SpigotServer implements Phoenix.Server{

        public String getName() {
            return Bukkit.getName();
        }

        public String getVersion() {
            return Bukkit.getVersion();
        }

        public String getIp() {
            return Bukkit.getIp();
        }

        public String getServerName() {
            return Bukkit.getServerName();
        }

        public String getServerId() {
            return Bukkit.getServerId();
        }

        public int getMaxPlayers() {
            return Bukkit.getMaxPlayers();
        }

        public int getViewDistance() {
            return Bukkit.getViewDistance();
        }

        public boolean hasAllowNether() {
            return Bukkit.getAllowNether();
        }

        public boolean hasWhitelist() {
            return Bukkit.hasWhitelist();
        }

        public boolean hasGenerateStructures() {
            return Bukkit.getGenerateStructures();
        }

        public List<World> getWorlds() {
            List<World> worlds = new ArrayList<>();
            Bukkit.getWorlds().forEach((world)-> worlds.add(toPhoenix(world)));
            return worlds;
        }

        public List<Player> getOnlinePlayers() {
            List<Player> players = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach((player)-> players.add(toPhoenix(player)));
            return players;
        }

        public File getPhoenixDataDir() {
            return instance.getDataFolder();
        }

        public Logger getLogger() {
            return new Logger() {
                @Override
                public void info(String msg) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + msg);
                }

                @Override
                public void warn(String msg) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + msg);
                }

                @Override
                public void debug(String msg) {
                    if(Phoenix.getDebugMode()){
                        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + msg);
                    }

                }
            };
        }

        @Override
        public Interface getInterface() {
            return new Interface() {

                @Override
                public void loadModules() {
                    File moduleDir = new File(getDataFolder(), "modules");
                    if (!moduleDir.exists()) {
                        moduleDir.mkdirs();
                        return;
                    }

                    for (File file : moduleDir.listFiles()) {
                        if (!file.getName().endsWith(".jar"))
                            continue;
                        try {
                            Phoenix.getModuleManager().loadModule(file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void registerCommand(Command command) {
                    String b_label = command.getClass().getAnnotation(PhoenixCommand.class).label(),
                            description = command.getClass().getAnnotation(PhoenixCommand.class).description(),
                            permission = command.getClass().getAnnotation(PhoenixCommand.class).permission();
                    String[] aliases = command.getClass().getAnnotation(PhoenixCommand.class).alias();
                    BukkitCommand proxy = new BukkitCommand(b_label) {
                        @Override
                        public boolean execute(CommandSender commandSender, String label, String[] args) {
                            return Phoenix.getCommandManager().handleCommand(toPhoenix(commandSender), label, args);
                        }
                    };
                    proxy.setAliases(Arrays.asList(aliases));
                    proxy.setDescription(description);
                    proxy.setPermission(permission);

                    try {
                        Method commandMap = getServer().getClass().getMethod("getCommandMap", (Class<?>[]) null);
                        Object cmdMap = commandMap.invoke(getServer(), (Object[]) null);
                        Method register = cmdMap.getClass().getMethod("register", String.class, org.bukkit.command.Command.class);
                        register.invoke(cmdMap, proxy.getName(), proxy);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public MessageChannelManager getMessageChannelManager() {
                    return new MessageChannelManager() {
                        @Override
                        public void registerOutgoingPluginChannel(Module module, String channelName) {
                            getServer().getMessenger().registerOutgoingPluginChannel(instance, channelName);
                        }

                        @Override
                        public void registerIncomingPluginChannel(Module module, String channelName, MessageListener listener) {
                            getServer().getMessenger().registerIncomingPluginChannel(instance, channelName, toPhoenix(listener));
                        }
                    };
                }

            };
        }
    }
}
