package com.lss233.phoenix.spigot.utils.spigot.inventory;

import com.lss233.phoenix.data.key.Key;
import com.lss233.phoenix.data.key.Keys;
import com.lss233.phoenix.item.enchantment.Enchantment;
import com.lss233.phoenix.item.inventory.ItemStack;
import com.lss233.phoenix.item.inventory.ItemType;
import com.lss233.phoenix.spigot.utils.spigot.enchantment.EnchantmentWrapper;
import com.lss233.phoenix.text.Text;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

import static com.lss233.phoenix.spigot.SpigotMain.getTransformer;

public interface ItemStackTransform {
    default ItemStack toPhoenix(org.bukkit.inventory.ItemStack itemStack) {
        return new ItemStack() {
            @Override
            public <E> Optional<E> get(Key<E> key) {
                if (itemStack.hasItemMeta())
                    if (key.equals(Keys.ITEM_LORE)) {
                        return Optional.of((E) itemStack.getItemMeta().getLore().stream().map(Text::of).collect(Collectors.toList()));
                    } else if (key.equals(Keys.ITEM_DURABILITY)) {
                        return Optional.of((E) Integer.valueOf(itemStack.getDurability()));
                    } else if (key.equals(Keys.ITEM_ENCHANTMENTS)) {
                        List<com.lss233.phoenix.item.enchantment.Enchantment> enchantmentArray = new ArrayList<>();
                        itemStack.getEnchantments().forEach((e, l) -> enchantmentArray.add(getTransformer().toPhoenix(EnchantmentWrapper.of(e, l))));
                        return Optional.of((E) enchantmentArray);
                    }
                return Optional.empty();
            }

            @Override
            public <E> void set(Key<E> key, E value) {
                if (key.equals(Keys.ITEM_DURABILITY))
                    itemStack.setDurability((Short) value);
                else if (key.equals(Keys.ITEM_ENCHANTMENTS)) {
                    List<Enchantment> enchantments = (List<Enchantment>) value;
                    enchantments.forEach(enchantment -> {
                        EnchantmentWrapper enchantmentWrapper = getTransformer().toSpigot(enchantment);
                        if (!itemStack.containsEnchantment(enchantmentWrapper.getEnchantment())) {
                            itemStack.addEnchantment(enchantmentWrapper.getEnchantment(), enchantment.getLevel());
                        }
                    });
                } else if (key.equals(Keys.ITEM_LORE)) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setLore(((List<Text>) value).stream().map(Text::toString).collect(Collectors.toList()));
                    itemStack.setItemMeta(itemMeta);
                }
            }

            @Override
            public ItemType getType() {
                return getTransformer().toPhoenix(itemStack.getType());
            }

            @Override
            public void setType(ItemType itemType) {
                itemStack.setType(getTransformer().toSpigot(itemType));
            }

            @Override
            public int getQuantity() {
                return itemStack.getAmount();
            }

            @Override
            public void setQuantity(int i) {
                itemStack.setAmount(i);
            }

            @Override
            public int getMaxStackQuantity() {
                return itemStack.getMaxStackSize();
            }
        };
    }

    default org.bukkit.inventory.ItemStack toSpigot(ItemStack itemStack) {
        org.bukkit.inventory.ItemStack result = new org.bukkit.inventory.ItemStack(getTransformer().toSpigot(itemStack.getType())) {
            @Override
            public Material getType() {
                return getTransformer().toSpigot(itemStack.getType());
            }

            @Override
            public void setType(Material type) {
                itemStack.setType(getTransformer().toPhoenix(type));
            }

            @Override
            public int getAmount() {
                return itemStack.getQuantity();
            }

            @Override
            public void setAmount(int amount) {
                itemStack.setQuantity(amount);
            }

            @Override
            public short getDurability() {
                return itemStack.getOrElse(Keys.ITEM_DURABILITY, 0).shortValue();
            }

            @Override
            public void setDurability(short durability) {
                itemStack.set(Keys.ITEM_DURABILITY, (int) durability);
            }

            @Override
            public int getMaxStackSize() {
                return itemStack.getMaxStackQuantity();
            }

            @Override
            public boolean setItemMeta(ItemMeta meta) {
                if (meta == null)
                    return super.setItemMeta(null);
                if (meta.hasDisplayName())
                    itemStack.set(Keys.DISPLAY_NAME, Text.of(meta.getDisplayName()));
                if (meta.hasLore())
                    itemStack.set(Keys.ITEM_LORE, meta.getLore().stream().map(Text::of).collect(Collectors.toList()));
                return super.setItemMeta(meta);
            }

            @Override
            public Map<org.bukkit.enchantments.Enchantment, Integer> getEnchantments() {
                Map<org.bukkit.enchantments.Enchantment, Integer> enchantmentIntegerMap = new HashMap<>();
                itemStack.get(Keys.ITEM_ENCHANTMENTS).ifPresent(list -> list.forEach(e -> {
                    EnchantmentWrapper wrapper = getTransformer().toSpigot(e);
                    enchantmentIntegerMap.put(wrapper.getEnchantment(), wrapper.getLevel());
                }));
                return enchantmentIntegerMap;
            }
        };
        ItemMeta itemMeta = result.getItemMeta();

        itemStack.get(Keys.ITEM_LORE).ifPresent(lore -> itemMeta.setLore(lore.stream().map(Text::toString).collect(Collectors.toList())));
        itemStack.get(Keys.DISPLAY_NAME).ifPresent(displayName -> itemMeta.setDisplayName(displayName.toString()));
        itemStack.get(Keys.ITEM_ENCHANTMENTS).ifPresent(enchantments -> {
            itemMeta.getEnchants().forEach((e, l) -> itemMeta.removeEnchant(e));
            enchantments.forEach(e -> {
                itemMeta.addEnchant(getTransformer().toSpigot(e.getType()), e.getLevel(), true);
            });
        });
        result.setItemMeta(itemMeta);
        return result;
    }
}
