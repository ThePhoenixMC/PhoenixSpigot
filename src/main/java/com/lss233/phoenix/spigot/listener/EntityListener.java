package com.lss233.phoenix.spigot.listener;

import com.google.common.collect.Lists;
import com.lss233.phoenix.entity.Entity;
import com.lss233.phoenix.event.cause.Cause;
import com.lss233.phoenix.event.entity.SpawnEntityEvent;
import com.lss233.phoenix.spigot.SpigotMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;

import static com.lss233.phoenix.Phoenix.getEventManager;

public class EntityListener implements Listener {
    @EventHandler
    public void transform(CreatureSpawnEvent event){
        SpawnEntityEvent e;
        List<Entity> entities = Lists.newArrayList(SpigotMain.getTransformer().toPhoenix(event.getEntity()));
        Cause cause = Cause.builder()
                .add("reason", event.getSpawnReason().toString())
                .build();
        switch (event.getSpawnReason()){
            case CHUNK_GEN:
                e = new SpawnEntityEvent.ChunkLoad(){
                    @Override
                    public Cause getCause() {
                        return cause;
                    }

                    @Override
                    public boolean isCancelled() {
                        return event.isCancelled();
                    }

                    @Override
                    public void setCancelled(boolean cancel) {
                        event.setCancelled(cancel);
                    }

                    @Override
                    public List<Entity> getEntities() {
                        return entities;
                    }
                };

                break;
            case CUSTOM:
                e = new SpawnEntityEvent.Custom() {
                    @Override
                    public List<Entity> getEntities() {
                        return entities;
                    }

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
                };
                break;
            default:
                e = new SpawnEntityEvent.Spawner() {
                    @Override
                    public List<Entity> getEntities() {
                        return entities;
                    }

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
                };
                break;
        }
        getEventManager().fire(e);
    }
}
