package com.lss233.phoenix.spigot.utils.spigot.inventory;

import com.lss233.phoenix.item.inventory.Inventory;
import com.lss233.phoenix.item.inventory.InventoryType;
import com.lss233.phoenix.item.inventory.ItemStack;
import com.lss233.phoenix.item.inventory.ItemType;
import com.lss233.phoenix.item.inventory.property.InventoryDimension;
import com.lss233.phoenix.item.inventory.property.InventoryProperty;
import com.lss233.phoenix.item.inventory.property.InventoryTitle;
import com.lss233.phoenix.text.Text;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

import java.util.*;

import static com.lss233.phoenix.spigot.SpigotMain.getTransformer;

public interface InventoryTransform {
    default Inventory toPhoenix(org.bukkit.inventory.Inventory inventory) {
        return new Inventory() {
            @Override
            public ListIterator<ItemStack> iterator() {
                return new ListIterator<ItemStack>() {
                    @Override
                    public boolean hasNext() {
                        return inventory.iterator().hasNext();
                    }

                    @Override
                    public ItemStack next() {
                        org.bukkit.inventory.ItemStack item = inventory.iterator().next();
                        if (item == null)
                            return null;
                        return getTransformer().toPhoenix(item);
                    }

                    @Override
                    public boolean hasPrevious() {
                        return inventory.iterator().hasPrevious();
                    }

                    @Override
                    public ItemStack previous() {
                        org.bukkit.inventory.ItemStack item = inventory.iterator().previous();
                        if (item == null)
                            return null;
                        return getTransformer().toPhoenix(item);
                    }

                    @Override
                    public int nextIndex() {
                        return inventory.iterator().nextIndex();
                    }

                    @Override
                    public int previousIndex() {
                        return inventory.iterator().previousIndex();
                    }

                    @Override
                    public void remove() {
                        inventory.iterator().remove();
                    }

                    @Override
                    public void set(ItemStack itemStack) {
                        if (itemStack == null)
                            itemStack = ItemStack.builder().setItemType(ItemType.AIR).build();
                        inventory.iterator().set(getTransformer().toSpigot(itemStack));
                    }

                    @Override
                    public void add(ItemStack itemStack) {
                        if (itemStack == null)
                            itemStack = ItemStack.builder().setItemType(ItemType.AIR).build();
                        inventory.iterator().add(getTransformer().toSpigot(itemStack));
                    }
                    /*
                    int index = 0;
                    @Override
                    public boolean hasNext() {
                        return getItem(index + 1).isPresent();
                    }

                    @Override
                    public ItemStack next() {
                        index++;
                        if(getItem(index).isPresent())
                            return getItem(index).get();
                        else
                            return null;
                    }

                    @Override
                    public boolean hasPrevious() {
                        return getItem(index - 1).isPresent();
                    }

                    @Override
                    public ItemStack previous() {
                        index--;
                        if(getItem(index).isPresent())
                            return getItem(index).get();
                        else
                            return null;
                    }

                    @Override
                    public int nextIndex() {
                        return index + 1;
                    }

                    @Override
                    public int previousIndex() {
                        return index - 1;
                    }

                    @Override
                    public void remove() {
                        inventory.remove(inventory.getItem(index));
                    }

                    @Override
                    public void set(ItemStack itemStack) {
                        inventory.setItem(index, getTransformer().toSpigot(itemStack));
                    }

                    @Override
                    public void add(ItemStack itemStack) {
                    }
                    */
                };
            }

            @Override
            public void clear() {
                inventory.clear();
            }

            @Override
            public boolean contains(ItemStack itemStack) {
                return inventory.contains(getTransformer().toSpigot(itemStack));
            }

            @Override
            public boolean contains(ItemType itemType) {
                return inventory.contains(getTransformer().toSpigot(itemType));
            }

            @Override
            public boolean containsAny(ItemStack itemStack) {
                return contains(itemStack);
            }

            @Override
            public int getMaxStackSize() {
                return inventory.getMaxStackSize();
            }

            @Override
            public void setMaxStackSize(int i) {
                inventory.setMaxStackSize(i);
            }

            @Override
            public int capacity() {
                return inventory.getContents().length;
            }

            @Override
            public int size() {
                return inventory.getSize();
            }

            @Override
            public InventoryType getInventoryType() {
                return getTransformer().toPhoenix(inventory.getType());
            }

            @Override
            public Optional<? extends InventoryProperty<?, ?>> getInventoryProperty(Class<? extends InventoryProperty> aClass) {
                InventoryProperty<?, ?> result = null;
                if (aClass.equals(InventoryTitle.class)) {
                    result = InventoryTitle.of(Text.of(inventory.getTitle()));
                } else if (aClass.equals(InventoryDimension.class)) {
                    result = new InventoryDimension(9, inventory.getSize() / 9);
                }
                return Optional.ofNullable(result);
            }

            @Override
            public void setItem(int i, ItemStack itemStack) {
                System.out.println("InventoryTransform.setItem");
                inventory.setItem(i, getTransformer().toSpigot(itemStack));
            }

            @Override
            public Optional<ItemStack> getItem(int i) {
                if (inventory.getItem(i) == null)
                    return Optional.empty();
                else
                    return Optional.of(getTransformer().toPhoenix(inventory.getItem(i)));
            }

            @Override
            public String getName() {
                return inventory.getName();
            }
        };
    }

    default org.bukkit.inventory.Inventory toSpigot(Inventory inventory) {
        System.out.println("InventoryTransform.toSpigot");
        return new org.bukkit.inventory.Inventory() {
            @Override
            public int getSize() {
                return inventory.size();
            }

            @Override
            public int getMaxStackSize() {
                return inventory.getMaxStackSize();
            }

            @Override
            public void setMaxStackSize(int i) {
                inventory.setMaxStackSize(i);
            }

            @Override
            public String getName() {
                return inventory.getName();
            }

            @Override
            public org.bukkit.inventory.ItemStack getItem(int i) {
                if (inventory.getItem(i).isPresent())
                    return getTransformer().toSpigot(inventory.getItem(i).get());
                else
                    return null;
            }

            @Override
            public void setItem(int i, org.bukkit.inventory.ItemStack itemStack) {
                inventory.setItem(i, getTransformer().toPhoenix(itemStack));
            }

            @Override
            public HashMap<Integer, org.bukkit.inventory.ItemStack> addItem(org.bukkit.inventory.ItemStack... itemStacks) throws IllegalArgumentException {
                while (inventory.iterator().hasNext()) {

                }
                return null;// TODO
            }

            @Override
            public HashMap<Integer, org.bukkit.inventory.ItemStack> removeItem(org.bukkit.inventory.ItemStack... itemStacks) throws IllegalArgumentException {
                return null;
            }

            @Override
            public org.bukkit.inventory.ItemStack[] getContents() {
                return new org.bukkit.inventory.ItemStack[0];
            }

            @Override
            public void setContents(org.bukkit.inventory.ItemStack[] itemStacks) throws IllegalArgumentException {
            }

            @Override
            public org.bukkit.inventory.ItemStack[] getStorageContents() {
                return new org.bukkit.inventory.ItemStack[0];
            }

            @Override
            public void setStorageContents(org.bukkit.inventory.ItemStack[] itemStacks) throws IllegalArgumentException {
            }

            @Override
            public boolean contains(int i) {
                return false;
            }

            @Override
            public boolean contains(Material material) throws IllegalArgumentException {
                return false;
            }

            @Override
            public boolean contains(org.bukkit.inventory.ItemStack itemStack) {
                return false;
            }

            @Override
            public boolean contains(int i, int i1) {
                return false;
            }

            @Override
            public boolean contains(Material material, int i) throws IllegalArgumentException {
                return false;
            }

            @Override
            public boolean contains(org.bukkit.inventory.ItemStack itemStack, int i) {
                return false;
            }

            @Override
            public boolean containsAtLeast(org.bukkit.inventory.ItemStack itemStack, int i) {
                return false;
            }

            @Override
            public HashMap<Integer, ? extends org.bukkit.inventory.ItemStack> all(int i) {
                return null;
            }

            @Override
            public HashMap<Integer, ? extends org.bukkit.inventory.ItemStack> all(Material material) throws IllegalArgumentException {
                return null;
            }

            @Override
            public HashMap<Integer, ? extends org.bukkit.inventory.ItemStack> all(org.bukkit.inventory.ItemStack itemStack) {
                return null;
            }

            @Override
            public int first(int i) {
                return 0;
            }

            @Override
            public int first(Material material) throws IllegalArgumentException {
                return 0;
            }

            @Override
            public int first(org.bukkit.inventory.ItemStack itemStack) {
                return 0;
            }

            @Override
            public int firstEmpty() {
                return 0;
            }

            @Override
            public void remove(int i) {

            }

            @Override
            public void remove(Material material) throws IllegalArgumentException {

            }

            @Override
            public void remove(org.bukkit.inventory.ItemStack itemStack) {

            }

            @Override
            public void clear(int i) {
                inventory.setItem(i, ItemStack.builder()
                        .setItemType(ItemType.AIR)
                        .build());
            }

            @Override
            public void clear() {
                inventory.clear();
            }

            @Override
            public List<HumanEntity> getViewers() {
                System.out.println("inventory = " + inventory);
                return null; // TODO
            }

            @Override
            public String getTitle() {
                return inventory.getName();
            }

            @Override
            public org.bukkit.event.inventory.InventoryType getType() {
                return getTransformer().toSpigot(inventory.getInventoryType());
            }

            @Override
            public InventoryHolder getHolder() {
                return null; // TODO
            }

            @Override
            public ListIterator<org.bukkit.inventory.ItemStack> iterator() {
                return new ListIterator<org.bukkit.inventory.ItemStack>() {
                    @Override
                    public boolean hasNext() {
                        return inventory.iterator().hasNext();
                    }

                    @Override
                    public org.bukkit.inventory.ItemStack next() {
                        ItemStack itemStack = inventory.iterator().next();
                        if (itemStack == null)
                            return null;
                        else
                            return getTransformer().toSpigot(itemStack);
                    }

                    @Override
                    public boolean hasPrevious() {
                        return inventory.iterator().hasPrevious();
                    }

                    @Override
                    public org.bukkit.inventory.ItemStack previous() {
                        ItemStack itemStack = inventory.iterator().previous();
                        if (itemStack == null)
                            return null;
                        else
                            return getTransformer().toSpigot(itemStack);
                    }

                    @Override
                    public int nextIndex() {
                        return inventory.iterator().nextIndex();
                    }

                    @Override
                    public int previousIndex() {
                        return inventory.iterator().previousIndex();
                    }

                    @Override
                    public void remove() {
                        inventory.iterator().remove();
                    }

                    @Override
                    public void set(org.bukkit.inventory.ItemStack itemStack) {
                        if (itemStack == null)
                            itemStack = new org.bukkit.inventory.ItemStack(Material.AIR);
                        inventory.iterator().set(getTransformer().toPhoenix(itemStack));
                    }

                    @Override
                    public void add(org.bukkit.inventory.ItemStack itemStack) {
                        if (itemStack == null)
                            itemStack = new org.bukkit.inventory.ItemStack(Material.AIR);
                        inventory.iterator().add(getTransformer().toPhoenix(itemStack));
                    }
                };
            }

            @Override
            public ListIterator<org.bukkit.inventory.ItemStack> iterator(int i) {
                return Arrays.asList(getContents()).listIterator(i); // TODO: bug checking.
                /*
                int currentIndex = 0;
                do {
                    currentIndex = iterator().nextIndex() - iterator().previousIndex();
                    if(i > currentIndex)
                        iterator().next();
                    else if(i < currentIndex)
                        iterator().previous();
                } while(currentIndex == i)
                return iterator();
                */
            }

            @Override
            public Location getLocation() {
                return null;
            }
        };
    }
}
