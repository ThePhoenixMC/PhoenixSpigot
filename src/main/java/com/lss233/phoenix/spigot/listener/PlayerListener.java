package com.lss233.phoenix.spigot.listener;

import com.lss233.phoenix.Phoenix;
import com.lss233.phoenix.event.cause.Cause;
import com.lss233.phoenix.event.entity.MoveEntityEvent;
import com.lss233.phoenix.event.network.ClientConnectionEvent;
import com.lss233.phoenix.spigot.SpigotUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

/**
 *
 */
public class PlayerListener implements Listener {
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event) {

        Cause cause = Cause.builder()
                .add("player", SpigotUtils.translatePlayer(event.getPlayer()))
                .build();
        Phoenix.getEventManager().fire(new ClientConnectionEvent.Join() {

            @Override
            public Cause getCause() {
                return cause;
            }
        });
    }

    @EventHandler
    public void transfer(PlayerQuitEvent event) {
        Phoenix.getEventManager().fire(new com.lss233.phoenix.event.player.PlayerQuitEvent() {

            @Override
            public Cause getCause() {
                return Cause.builder()
                        .add("player", SpigotUtils.translatePlayer(event.getPlayer()))
                        .build();
            }
        });
    }

    @EventHandler
    public void transfer(PlayerMoveEvent event) {
        Cause cause = Cause.builder()
                .add("entity", SpigotUtils.translatePlayer(event.getPlayer()))
                .add("to", SpigotUtils.translateLocation(event.getTo()))
                .add("from", SpigotUtils.translateLocation(event.getFrom()))
                .build();
        Phoenix.getEventManager().fire(new MoveEntityEvent() {
            @Override
            public boolean isCancelled() {
                return event.isCancelled();
            }

            @Override
            public void setCancelled(boolean cancel) {
                event.setCancelled(cancel);
            }

            @Override
            public Cause getCause() {
                return cause;
            }
        });
    }

    @EventHandler
    public void transfer(PlayerJoinEvent event) {
        Phoenix.getEventManager().fire(new com.lss233.phoenix.event.player.PlayerJoinEvent() {
            @Override
            public Cause getCause() {
                return Cause.builder()
                        .add("player", SpigotUtils.translatePlayer(event.getPlayer()))
                        .build();
            }
        });
    }

    @EventHandler
    public void transfer(PlayerExpChangeEvent event) {
        Phoenix.getEventManager().fire(new com.lss233.phoenix.event.player.PlayerExpChangeEvent() {
            @Override
            public Cause getCause() {
                return Cause.builder()
                        .add("player", SpigotUtils.translatePlayer(event.getPlayer()))
                        .build();
            }
        });
    }

    @EventHandler
    public void transfer(PlayerToggleSneakEvent event) {
        Phoenix.getEventManager().fire(new com.lss233.phoenix.event.player.PlayerToggleSneakEven() {

            @Override
            public boolean isSneaking() {
                return event.isSneaking();
            }

            @Override
            public Cause getCause() {
                return Cause.builder()
                        .add("player", SpigotUtils.translatePlayer(event.getPlayer()))
                        .build();
            }
        });
    }

}