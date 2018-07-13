package com.lss233.phoenix.spigot.utils;

import com.lss233.phoenix.spigot.utils.spigot.*;
import com.lss233.phoenix.spigot.utils.spigot.block.BlockTransform;
import com.lss233.phoenix.spigot.utils.spigot.command.CommandSenderTransform;
import com.lss233.phoenix.spigot.utils.spigot.enchantment.EnchantmentTransform;
import com.lss233.phoenix.spigot.utils.spigot.entity.EntityTransform;
import com.lss233.phoenix.spigot.utils.spigot.entity.PlayerTransform;
import com.lss233.phoenix.spigot.utils.spigot.inventory.InventoryTransform;
import com.lss233.phoenix.spigot.utils.spigot.inventory.InventoryTypeTransform;
import com.lss233.phoenix.spigot.utils.spigot.inventory.ItemStackTransform;
import com.lss233.phoenix.spigot.utils.spigot.messaging.PluginMessageListenerTransformer;
import com.lss233.phoenix.spigot.utils.spigot.utils.VelocityTransform;

/**
 *
 */
public interface Transform extends
        /* Root */
        WorldBorderTransform,
        WorldTransform,
        ChunkTransform,
        LocationTransform,
        MaterialTransform,
        /* enchantment */
        EnchantmentTransform,
        /* entity */
        PlayerTransform,
        EntityTransform,
        /* block */
        BlockTransform,
        /* inventory */
        ItemStackTransform,
        InventoryTransform,
        InventoryTypeTransform,
        /* command */
        CommandSenderTransform,
        /* messaging */
        PluginMessageListenerTransformer,
        /* utils */
        VelocityTransform {

}
