package com.lss233.phoenix.spigot.utils.spigot.enchantment;

import com.lss233.phoenix.item.enchantment.Enchantment;
import com.lss233.phoenix.item.enchantment.EnchantmentType;


public interface EnchantmentTransform {
    default Enchantment toPhoenix(EnchantmentWrapper wrapper){
        return new Enchantment() {
            @Override
            public int getLevel() {
                return wrapper.getLevel();
            }

            @Override
            public EnchantmentType getType() {
                return EnchantmentType.valueOf(wrapper.getEnchantment().toString());
            }
        };
    }

    default EnchantmentWrapper toSpigot(Enchantment enchantment){
        return EnchantmentWrapper.of(org.bukkit.enchantments.Enchantment.getByName(enchantment.getType().getName()), enchantment.getLevel());
    }
}
