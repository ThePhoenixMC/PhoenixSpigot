package com.lss233.phoenix.spigot.utils.spigot;

import com.lss233.phoenix.world.Location;
import com.lss233.phoenix.world.WorldBorder;

import static com.lss233.phoenix.spigot.SpigotMain.getTransformer;

/**
 *
 */
public interface WorldBorderTransform {
    default WorldBorder toPhoenix(org.bukkit.WorldBorder worldBorder){
        return new WorldBorder() {
            @Override
            public Location getCenter() {
                return getTransformer().toPhoenix(worldBorder.getCenter());
            }

            @Override
            public double getDamageAmount() {
                return worldBorder.getDamageAmount();
            }

            @Override
            public double getDamageThreshold() {
                return worldBorder.getDamageBuffer();
            }

            @Override
            public double getDiameter() {
                return worldBorder.getSize();
            }

            @Override
            public int getWarningDistance() {
                return worldBorder.getWarningDistance();
            }

            @Override
            public int getWarningTime() {
                return worldBorder.getWarningTime();
            }

            @Override
            public void setCenter(Location location) {
                worldBorder.setCenter(getTransformer().toSpigot(location));
            }

            @Override
            public void setCenter(double x, double y) {
                worldBorder.setCenter(x, y);
            }

            @Override
            public void setDamageAmount(double damageAmount) {
                worldBorder.setDamageAmount(damageAmount);
            }

            @Override
            public void setDamageThreshold(double damageThreshold) {
                worldBorder.setDamageBuffer(damageThreshold);
            }

            @Override
            public void setDiameter(double diameter) {
                worldBorder.setSize(diameter);
            }

            @Override
            public void setWarningDistance(int warningDistance) {
                worldBorder.setWarningDistance(warningDistance);
            }

            @Override
            public void setWarningTime(int warningTime) {
                worldBorder.setWarningTime(warningTime);
            }
        };
    }
}
