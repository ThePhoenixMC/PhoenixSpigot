package com.lss233.phoenix.spigot.utils.spigot.inventory;

import com.lss233.phoenix.item.inventory.InventoryType;

public interface InventoryTypeTransform {
    default InventoryType toPhoenix(org.bukkit.event.inventory.InventoryType inventoryType){
        return InventoryType.valueOf(inventoryType.toString());
    }

    default org.bukkit.event.inventory.InventoryType toSpigot(InventoryType inventoryType){
        return org.bukkit.event.inventory.InventoryType.valueOf(inventoryType.toString());
    }
}
