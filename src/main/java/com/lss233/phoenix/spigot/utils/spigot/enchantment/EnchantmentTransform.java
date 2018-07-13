package com.lss233.phoenix.spigot.utils.spigot.enchantment;

import com.lss233.phoenix.item.enchantment.Enchantment;
import com.lss233.phoenix.item.enchantment.EnchantmentType;

import static com.lss233.phoenix.spigot.SpigotMain.getTransformer;


public interface EnchantmentTransform {
    default Enchantment toPhoenix(EnchantmentWrapper wrapper){
        return new Enchantment() {
            @Override
            public int getLevel() {
                return wrapper.getLevel();
            }

            @Override
            public EnchantmentType getType() {
                return getTransformer().toPhoenix(wrapper.getEnchantment());
            }
        };
    }

    default EnchantmentWrapper toSpigot(Enchantment enchantment){
        return EnchantmentWrapper.of(org.bukkit.enchantments.Enchantment.getByName(enchantment.getType().getName()), enchantment.getLevel());
    }

    default EnchantmentType toPhoenix(org.bukkit.enchantments.Enchantment enchantment) {
        return EnchantmentType.valueOf(enchantment.toString());
    }

    default org.bukkit.enchantments.Enchantment toSpigot(EnchantmentType enchantmentType) {
        return org.bukkit.enchantments.Enchantment.getByName(enchantmentType.getName());
    }
}
