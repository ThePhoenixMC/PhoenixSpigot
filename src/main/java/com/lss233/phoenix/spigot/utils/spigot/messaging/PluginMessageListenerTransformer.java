package com.lss233.phoenix.spigot.utils.spigot.messaging;

import com.lss233.phoenix.channel.MessageListener;
import org.bukkit.plugin.messaging.PluginMessageListener;

import static com.lss233.phoenix.spigot.SpigotMain.getTransformer;

/**
 *
 */
public interface PluginMessageListenerTransformer {
    /**
     * Convert a Bukkit MessageListener instance to a Phoenix PluginMessageListener instance.
     * @param listener The Bukkit MessageListener instance.
     * @return The Phoenix PluginMessageListener instance.
     */
    default PluginMessageListener toPhoenix(MessageListener listener) {
        return (s, player, bytes) -> listener.onPluginMessageReceived(s, getTransformer().toPhoenix(player), bytes);
    }
}
