package com.lss233.phoenix.spigot.utils;

import com.lss233.phoenix.spigot.utils.spigot.*;
import com.lss233.phoenix.spigot.utils.spigot.block.*;
import com.lss233.phoenix.spigot.utils.spigot.command.*;
import com.lss233.phoenix.spigot.utils.spigot.entity.*;
import com.lss233.phoenix.spigot.utils.spigot.messaging.*;
import com.lss233.phoenix.spigot.utils.spigot.utils.*;

/**
 *
 */
public interface Transform extends
        /* Root */
        WorldBorderTransform,
        WorldTransform,
        ChunkTransform,
        LocationTransform,
        /* entity */
        PlayerTransform,
        EntityTransform,
        /* block */
        BlockTransform,
        /* command */
        CommandSenderTransform,
        /* messaging */
        PluginMessageListenerTransformer,
        /* utils */
        VelocityTransform {

}
