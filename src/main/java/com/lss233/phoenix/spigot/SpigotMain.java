package com.lss233.phoenix.spigot;

import com.lss233.phoenix.Phoenix;
import com.lss233.phoenix.channel.MessageListener;
import com.lss233.phoenix.command.Command;
import com.lss233.phoenix.command.CommandResult;
import com.lss233.phoenix.command.PhoenixCommand;
import com.lss233.phoenix.data.key.Keys;
import com.lss233.phoenix.entity.living.Player;
import com.lss233.phoenix.item.enchantment.Enchantment;
import com.lss233.phoenix.item.inventory.Inventory;
import com.lss233.phoenix.item.inventory.InventoryType;
import com.lss233.phoenix.item.inventory.ItemStack;
import com.lss233.phoenix.item.inventory.property.InventoryDimension;
import com.lss233.phoenix.item.inventory.property.InventoryTitle;
import com.lss233.phoenix.logging.Logger;
import com.lss233.phoenix.module.Module;
import com.lss233.phoenix.spigot.listener.EntityListener;
import com.lss233.phoenix.spigot.listener.PlayerListener;
import com.lss233.phoenix.spigot.utils.Transform;
import com.lss233.phoenix.spigot.utils.TransformUtil;
import com.lss233.phoenix.spigot.utils.spigot.enchantment.EnchantmentWrapper;
import com.lss233.phoenix.text.Text;
import com.lss233.phoenix.world.World;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * .
 */
public class SpigotMain extends JavaPlugin {
    private static SpigotMain instance;
    private static Transform transformer = new TransformUtil();

    public static SpigotMain getInstance() {
        return instance;
    }
    public static Transform getTransformer() { return  transformer; }

    @Override
    public void onEnable(){
        instance = this;
        SpigotServer server = new SpigotServer();
        Phoenix.setServer(server);
        initSpigotSide();
    }

    private void initSpigotSide() {
        if (!getDataFolder().exists()) {
            if(!getDataFolder().mkdirs()){
                Phoenix.getLogger("Phoenix").warn("Failed to create data folder, Phoenix Framework wont working.");
            }
        }
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
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
            Bukkit.getWorlds().forEach((world)-> worlds.add(getTransformer().toPhoenix(world)));
            return worlds;
        }

        public List<Player> getOnlinePlayers() {
            List<Player> players = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach((player)-> players.add(getTransformer().toPhoenix(player)));
            return players;
        }

        public File getPhoenixDataDir() {
            return instance.getDataFolder();
        }

        public Logger getLogger() {
            return new Logger() {
                @Override
                public void info(Object msg) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + String.valueOf(msg));
                }

                @Override
                public void warn(Object msg) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + String.valueOf(msg));
                }

                @Override
                public void severe(Object msg) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + String.valueOf(msg));
                }

                @Override
                public void debug(Object msg) {
                    if(Phoenix.getDebugMode()){
                        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + String.valueOf(msg));
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
                        if(!moduleDir.mkdirs()){
                            Phoenix.getLogger("Phoenix").warn("Failed to create module folder, Phoenix Framework wont working.");
                        }
                        return;
                    }

                    for (File file : Objects.requireNonNull(moduleDir.listFiles())) {
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
                            CommandResult result = Phoenix.getCommandManager().handleCommand(getTransformer().toPhoenix(commandSender), label, args);
                            return result.getReason().equals(CommandResult.Reason.NONE);
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
                            getServer().getMessenger().registerIncomingPluginChannel(instance, channelName, getTransformer().toPhoenix(listener));
                        }
                    };
                }

                @Override
                public Inventory registerInventory(Inventory.Builder builder) {
                    org.bukkit.inventory.Inventory inventory;

                    Integer size = ((InventoryDimension) builder.getProperties().get(InventoryDimension.PROPERTY_NAME)).getColumns() * ((InventoryDimension) builder.getProperties().get(InventoryDimension.PROPERTY_NAME)).getRows();
                    String title = ((InventoryTitle) builder.getProperties().get(InventoryTitle.PROPERTY_NAME)).getText().toString();

                    if (builder.getType().equals(InventoryType.CHEST) || builder.getType().equals(InventoryType.DOUBLE_CHEST))
                        inventory = Bukkit.createInventory(null, size, title);
                    else
                        inventory = Bukkit.createInventory(null, getTransformer().toSpigot(builder.getType()), title);

                    return getTransformer().toPhoenix(inventory);
                }

                @Override
                public ItemStack registerItemStack(ItemStack.Builder builder) {
                    org.bukkit.inventory.ItemStack itemStack = new org.bukkit.inventory.ItemStack(getTransformer().toSpigot(builder.getItemType()), builder.getQuantity());
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    builder.get(Keys.ITEM_LORE).ifPresent(lore -> itemMeta.setLore(lore.stream().map(Text::toString).collect(Collectors.toList())));

                    builder.get(Keys.ITEM_DURABILITY).ifPresent(v -> itemStack.setDurability(v.shortValue()));
                    builder.get(Keys.ITEM_ENCHANTMENTS).ifPresent(v -> v.forEach(enchantment -> {
                        EnchantmentWrapper enchantmentWrapper = getTransformer().toSpigot(enchantment);
                        itemStack.addEnchantment(enchantmentWrapper.getEnchantment(), enchantmentWrapper.getLevel());
                    }));
                    itemStack.setItemMeta(itemMeta);
                    return getTransformer().toPhoenix(itemStack);
                }

                @Override
                public Enchantment registerEnchantment(Enchantment.Builder builder) {
                    return getTransformer().toPhoenix(EnchantmentWrapper.of(org.bukkit.enchantments.Enchantment.getByName(builder.getType().getName()), builder.getLevel()));
                }

            };
        }
    }
}
