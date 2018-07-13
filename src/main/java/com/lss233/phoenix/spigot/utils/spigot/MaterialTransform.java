package com.lss233.phoenix.spigot.utils.spigot;

import com.lss233.phoenix.item.inventory.ItemType;
import org.bukkit.Material;

public interface MaterialTransform {
    default ItemType toPhoenix(Material material){
        return ItemType.valueOf(material.toString());
    }
    default Material toSpigot(ItemType type){
        return Material.valueOf(type.toString());
    }
}
