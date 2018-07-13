package com.lss233.phoenix.spigot.utils.spigot.enchantment;

import org.bukkit.enchantments.Enchantment;

import static com.lss233.phoenix.spigot.SpigotMain.getTransformer;

/**
 * This class is used to link enchantment and level together.
 */
public class EnchantmentWrapper {

    private Enchantment enchantment;
    private int level;

    /**
     * Wrapping Spigot Enchantment and level up.
     * @param enchantment The enchantment.
     * @param level The level.
     * @return Wrapped enchantment.
     */
    public static EnchantmentWrapper of(Enchantment enchantment, int level){
        return new EnchantmentWrapper()
                    .setEnchantment(enchantment)
                    .setLevel(level);
    }
    /**
     * Gets the Enchantment.
     * @return The Enchantment.
     */
    public Enchantment getEnchantment() {
        return enchantment;
    }

    /**
     * Sets the Enchantment.
     * @param enchantment The Enchantment to set.
     * @return EnchantmentWrapper instance.
     */
    public EnchantmentWrapper setEnchantment(Enchantment enchantment) {
        this.enchantment = enchantment;
        return this;
    }

    /**
     * Gets the level of this Enchantment.
     * @return The level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level of this Enchantment.
     * @param level The level to set.
     * @return EnchantmentWrapper instance.
     */
    public EnchantmentWrapper setLevel(int level) {
        this.level = level;
        return this;
    }

    public boolean isSimilar(Enchantment enchantment) {
        return enchantment.equals(getEnchantment());
    }

    public boolean isSimilar(com.lss233.phoenix.item.enchantment.Enchantment enchantment) {
        return enchantment.getType().equals(getTransformer().toPhoenix(getEnchantment()));
    }
}
