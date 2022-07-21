package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.base.PlayState;

import java.awt.*;
import java.util.Comparator;

public class EnemyComponent implements Component{
    public int inFront;
    public int hitPoints;
    public int jumpDamage = 1;
    public int chanceA;
    public int chanceB;
    public int chanceC;
    public int costA;
    public int costB;
    public int costC;
    public int totalHitPoints;

    public boolean climbState = false;
    public boolean animation = false;

    public float distance = 0;
    public float totalBarWidth = 32;
    public float timerAnimation = 0;
    public float timeAnimation = 40;
    public float timeBlink = 8;
    public float timerBlink = 0;
    public float timerBlink2 = 0;
    public boolean timerSwitch = false;

    public Texture origTexture;
    public Rectangle healthBar = new Rectangle();

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
