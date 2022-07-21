package com.defensetower.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.defensetower.game.MainGame;
import com.defensetower.game.components.*;
import com.defensetower.game.entities.BulletEntity;
import com.defensetower.game.entities.ParticleEntity;
import com.defensetower.game.entities.PlusScoreEntity;
import com.defensetower.game.gamestates.PlayState;

import static com.badlogic.gdx.Gdx.input;
// import static jdk.nashorn.internal.parser.TokenType.OR;

public class BulletSystem extends IteratingSystem {


    public BulletSystem() {
        super(Family.all(TransformComponent.class, SpeedComponent.class, BulletComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        HitBoxComponent hitBoxComp = entity.getComponent(HitBoxComponent.class);
        BulletComponent bulletComp = entity.getComponent(BulletComponent.class);

        ImmutableArray<Entity> enemies = this.getEngine().getEntitiesFor(Family.all(EnemyComponent.class).get());

        for(int i = 0; i < enemies.size(); i++) {

            if (hitBoxComp.rect.overlaps(enemies.get(i).getComponent(HitBoxComponent.class).rect)) {
                Entity enemy = enemies.get(i);
                EnemyComponent enemyComp = enemy.getComponent(EnemyComponent.class);

                if(enemyComp.distance > 200) {
                    enemyComp.hitPoints -= bulletComp.damage;
                    for(int j = 0; j < bulletComp.damage * 5; j++) {
                        ParticleEntity.create(new Vector2(transformComp.position.x, transformComp.position.y), this.getEngine());
                    }
                    this.getEngine().removeEntity(entity);
                }

                if(enemyComp.hitPoints <= 0) {
                    int score = 1;
                    PlayState.score += score;
                    PlusScoreEntity.create(new Vector2(transformComp.position.x, transformComp.position.y), this.getEngine(), Integer.toString(score));

                    this.getEngine().removeEntity(enemy);
                }
            }
        }

        if(transformComp.position.x < 0 || transformComp.position.x > MainGame.WIDTH || transformComp.position.y < 0 || transformComp.position.y > MainGame.HEIGHT){
            this.getEngine().removeEntity(entity);
        }
    }
}
