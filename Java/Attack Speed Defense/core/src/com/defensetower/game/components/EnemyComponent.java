package com.defensetower.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.defensetower.game.entities.EnemyEntity;

import java.util.Comparator;

public class EnemyComponent implements Component {

    public float speedIncrease = 0;
    public float speedDecrease = 0;
    public float hitPoints = 0;
    public float totalHitPoints = 0;
    public float totalBarWidth = 32;
    public float healthIncreas = 0;
    public float healthDecrease = 0;
    public float distance = 0;
    public boolean isMarked = false;
    public boolean shouldRemove = false;
    public Rectangle healthBar = null;

    public static class EnemyDistanceSorter implements Comparator<Entity> {

        @Override
        public int compare(Entity o1, Entity o2) {

            EnemyComponent e1 = o1.getComponent(EnemyComponent.class);
            EnemyComponent e2 = o2.getComponent(EnemyComponent.class);

            if(e1.distance < e2.distance) {
                return -1;
            } else if(e1.distance > e2.distance) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    public static final EnemyDistanceSorter enemyDistanceSorter = new EnemyDistanceSorter();

}
