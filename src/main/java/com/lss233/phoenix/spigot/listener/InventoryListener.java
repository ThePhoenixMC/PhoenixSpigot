package com.lss233.phoenix.spigot.listener;

import com.lss233.phoenix.Phoenix;
import com.lss233.phoenix.entity.living.Player;
import com.lss233.phoenix.event.cause.Cause;
import com.lss233.phoenix.event.item.inventory.InteractInventoryEvent;
import com.lss233.phoenix.item.inventory.Inventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import static com.lss233.phoenix.spigot.SpigotMain.getTransformer;

public class InventoryListener implements Listener {
    @EventHandler
    public void InventoryOpenEvent(InventoryOpenEvent event) {

        Cause cause = Cause.builder()
                .add("player", getTransformer().toPhoenix(event.getPlayer()))
                .add("inventory", getTransformer().toPhoenix(event.getInventory()))
                .build();
        Phoenix.getEventManager().fire(new InteractInventoryEvent.Open() {

            @Override
            public Cause getCause() {
                return cause;
            }

            @Override
            public Player getTargetEntity() {
                return getTransformer().toPhoenix((org.bukkit.entity.Player) event.getPlayer());
            }

            @Override
            public Inventory getTargetInventory() {
                return getTransformer().toPhoenix(event.getInventory());
            }

            @Override
            public boolean isCancelled() {
                return event.isCancelled();
            }

            @Override
            public void setCancelled(boolean b) {
                event.setCancelled(b);
            }
        });
    }

    @EventHandler
    public void InventoryCloseEvent(InventoryCloseEvent event) {

        Cause cause = Cause.builder()
                .add("player", getTransformer().toPhoenix(event.getPlayer()))
                .add("inventory", getTransformer().toPhoenix(event.getInventory()))
                .build();
        Phoenix.getEventManager().fire(new InteractInventoryEvent.Closed() {

            @Override
            public Cause getCause() {
                return cause;
            }

            @Override
            public Player getTargetEntity() {
                return getTransformer().toPhoenix((org.bukkit.entity.Player) event.getPlayer());
            }

            @Override
            public Inventory getTargetInventory() {
                return getTransformer().toPhoenix(event.getInventory());
            }
        });
    }
}
