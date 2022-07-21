package com.defensetower.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.defensetower.game.components.*;

public class EnemyEntity {
    public static Entity create(Engine engine, int hpScalar, int speedScalar){

        Entity enemy = new Entity();
        enemy.add(new TextureComponent());
        enemy.add(new TransformComponent());
        enemy.add(new EnemyComponent());
        enemy.add(new SpeedComponent());
        enemy.add(new HitBoxComponent());

        enemy.getComponent(TransformComponent.class).position.set(-16, 528);
        enemy.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/BasicEnemy.png"));
        enemy.getComponent(SpeedComponent.class).speed = 100 * (speedScalar);
        enemy.getComponent(HitBoxComponent.class).rect = new Rectangle(0, 448, 32, 32);
        enemy.getComponent(EnemyComponent.class).isMarked = false;
        enemy.getComponent(EnemyComponent.class).hitPoints = enemy.getComponent(EnemyComponent.class).totalHitPoints = hpScalar;


        engine.addEntity(enemy);
        return enemy;
    }
}
