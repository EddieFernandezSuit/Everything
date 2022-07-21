package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.base.Jukebox;
import com.mygdx.game.base.PlayState;
import com.mygdx.game.components.*;
import com.mygdx.game.entities.ParticleEntity;

import static com.mygdx.game.DOKN.HEIGHT;
import static com.mygdx.game.DOKN.WIDTH;
import static com.mygdx.game.base.PlayState.*;

public class BulletSystem extends IteratingSystem {


    public BulletSystem() {
        super(Family.all(TransformComponent.class, BulletComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        RectComponent hitBoxComp = entity.getComponent(RectComponent.class);
        BulletComponent bulletComp = entity.getComponent(BulletComponent.class);
        AccelerationComponent accelComp = entity.getComponent(AccelerationComponent.class);

        ImmutableArray<Entity> enemies = this.getEngine().getEntitiesFor(Family.all(EnemyComponent.class).get());

        if(transformComp.dy < 0 || bulletComp.type == 10) {
            for (int i = 0; i < enemies.size(); i++) {

                if (hitBoxComp.rect.overlaps(enemies.get(i).getComponent(RectComponent.class).rect)) {
                    Entity enemy = enemies.get(i);
                    EnemyComponent enemyComp = enemy.getComponent(EnemyComponent.class);
                    TextureComponent textureComp = enemy.getComponent(TextureComponent.class);
                    TransformComponent enTransComp = enemy.getComponent(TransformComponent.class);

                    enemyComp.hitPoints -= bulletComp.damage;
                    enemyComp.animation = true;
                    textureComp.texture = new Texture(Gdx.files.internal(eHurt));
                    Jukebox.play("hitmarker.mp3");
                    for (int j = 0; j < 30; j++) {
                        ParticleEntity.create(new Vector2(transformComp.position.x, transformComp.position.y), this.getEngine());
                    }

                    if (bulletComp.bounce > 0) {
                        transformComp.dy = 80;
                        accelComp.acceleration = GRAVITY / 2;
                        bulletComp.bounce--;
                    } else {
                        this.getEngine().removeEntity(entity);
                    }

                    if(enemyComp.hitPoints <= 0) {
                        this.getEngine().removeEntity(enemy);

                        if(enemyComp.chanceA >= randRange(0,100)){
                            PlayState.Z ++;
                            showZ = true;
                            createScore(this.getEngine(), 2, enTransComp.position.x, enTransComp.position.y);
                            if(trade4 == null) {
                                trade4 = createShopButton(new Vector2(WIDTH - ENTITY_SIZE * 13 / 2, HEIGHT - ENTITY_SIZE / 2), 4);
                            }
                            if(trade3 == null) {
                                trade3 = createShopButton(new Vector2(WIDTH - ENTITY_SIZE * 13 / 2, HEIGHT - ENTITY_SIZE * 3 / 2), 3);
                            }
                        }
                        if(enemyComp.chanceB >= randRange(0, 100)){
                            Y++;
                            showY = true;
                            createScore(this.getEngine(), 1, enTransComp.position.x, enTransComp.position.y);
                            if(trade1 == null) {
                                trade1 = createShopButton(new Vector2(WIDTH - ENTITY_SIZE * 19 / 2, HEIGHT - ENTITY_SIZE / 2), 1);
                            }
                            if(trade2 == null) {
                                trade2 = createShopButton(new Vector2(WIDTH - ENTITY_SIZE * 13 / 2, HEIGHT - ENTITY_SIZE * 2), 2);
                            }
                        }
                        if(enemyComp.chanceC >= randRange(0, 100)){
                            X++;
                            createScore(this.getEngine(),0, enTransComp.position.x, enTransComp.position.y);
                        }
                    }
                    break;
                }
            }
        }

        if(transformComp.position.x < HALF_BULLET_SIZE * -1|| transformComp.position.x > WIDTH_MINUS + HALF_BULLET_SIZE || transformComp.position.y < HALF_BULLET_SIZE * -1 ){
            this.getEngine().removeEntity(entity);
        }
    }
}
