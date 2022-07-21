package com.defensetower.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.defensetower.game.MainGame;
import com.defensetower.game.components.*;

import static com.badlogic.gdx.Gdx.input;
import static com.defensetower.game.gamestates.PlayState.lives;

public class EnemySystem extends IteratingSystem{
    ShapeRenderer sr = new ShapeRenderer();

    public EnemySystem() {
        super(Family.all(EnemyComponent.class, TransformComponent.class, SpeedComponent.class).get());


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        EnemyComponent enemyComp = entity.getComponent(EnemyComponent.class);
        SpeedComponent speedComp = entity.getComponent(SpeedComponent.class);
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);

        if(enemyComp.distance >= 200 & enemyComp.healthBar == null) {
            enemyComp.healthBar = new Rectangle();
            enemyComp.healthBar.width = 32;
            enemyComp.healthBar.height = 3;
        }

        if(enemyComp.healthBar != null) {
            enemyComp.healthBar.x = transformComp.position.x - 16;
            enemyComp.healthBar.y = transformComp.position.y + 20;
            enemyComp.healthBar.width = enemyComp.hitPoints * enemyComp.totalBarWidth / enemyComp.totalHitPoints;
        }

        float distance = enemyComp.distance;
        enemyComp.distance += speedComp.speed * deltaTime;

        if (distance < 128) {
            speedComp.radians = 0;
        }
        if (distance >= 128 && distance < 544){
            speedComp.radians = 1.5f * 3.1415f;
        }
        if(distance >= 544 && distance < 992){
            speedComp.radians = 0;
        }
        if(distance >= 992 && distance < 1376){
            speedComp.radians = .5f * 3.1415f;
        }
        if(distance >= 1376 && distance < 1504){
            speedComp.radians = 3.1415f;
        }
        if(distance >= 1504 && distance < 1632){
            speedComp.radians = 1.5f * 3.1415f;
        }
        if(distance >= 1632 && distance < 1792){
            speedComp.radians = 3.1415f;
        }
        if(distance >= 1792 && distance < 2156){
            speedComp.radians = .5f * 3.1415f;
        }
        if(distance >= 2124){
            this.getEngine().removeEntity(entity);
            lives -= 1;
        }

        if(enemyComp.healthBar != null) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.GREEN);
            sr.rect(enemyComp.healthBar.x, enemyComp.healthBar.y, enemyComp.healthBar.width, enemyComp.healthBar.height);
            sr.end();
        }
    }
}
