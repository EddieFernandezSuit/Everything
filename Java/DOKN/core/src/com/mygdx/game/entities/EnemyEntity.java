package com.mygdx.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.DOKN;
import com.mygdx.game.base.PlayState;
import com.mygdx.game.components.*;


import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygdx.game.base.PlayState.*;

public class EnemyEntity {

    public static Entity createBase(Engine engine, Texture texture, float speed, int hp, int wave){
        Entity entity = new Entity();
        entity.add(new EnemyComponent());
        entity.add(new TextureComponent());
        entity.add(new TransformComponent());
        entity.add(new AccelerationComponent());
        entity.add(new RectComponent());
        entity.getComponent(RectComponent.class).rect = new Rectangle(0, DOKN.HEIGHT, ENTITY_SIZE, ENTITY_SIZE);
        entity.getComponent(TransformComponent.class).position.x = 0;
        entity.getComponent(TransformComponent.class).position.y = DOKN.HEIGHT / 3;
        entity.getComponent(EnemyComponent.class).healthBar.width = ENTITY_SIZE;
        entity.getComponent(EnemyComponent.class).healthBar.height = HEALTH_BAR_THICKNESS;
        entity.getComponent(EnemyComponent.class).jumpDamage = 1;
        entity.getComponent(EnemyComponent.class).chanceA = 1;
        entity.getComponent(EnemyComponent.class).chanceB = 10;
        entity.getComponent(EnemyComponent.class).chanceC = 30;
        entity.getComponent(EnemyComponent.class).hitPoints = entity.getComponent(EnemyComponent.class).totalHitPoints = 14 + hp * wave ;
        entity.getComponent(TextureComponent.class).texture = entity.getComponent(EnemyComponent.class).origTexture = texture;
        entity.getComponent(TransformComponent.class).initialdx = entity.getComponent(TransformComponent.class).dx = speed + wave/2 - 1;


        engine.addEntity(entity);
        return entity;
    }
    public static Entity create(Engine engine, int wave){
        Entity entity = createBase(engine, new Texture(Gdx.files.internal("sprites/enemies/eBlue.png")), ENEMY_SPEED, wave * 4 - 8, wave);
        entity.add(new WalkingComponent());
        entity.add(new BlockStopComponent());
        return entity;
    }

    public static Entity createFlying(Engine engine, int wave){
        Entity entity = createBase(engine, new Texture(Gdx.files.internal("sprites/enemies/eRed.png")), ENEMY_SPEED * 3 / 4, wave * 3/2, wave) ;
        entity.getComponent(AccelerationComponent.class).acceleration = 0;
        entity.getComponent(TransformComponent.class).position.y = DOKN.HEIGHT - ENTITY_SIZE * 5;
        return entity;
    }

    public static Entity createFast(Engine engine, int wave){
        Entity entity = createBase(engine, new Texture(Gdx.files.internal("sprites/enemies/eGreen.png")), ENEMY_SPEED * 3, wave / 2, wave);
        entity.add(new WalkingComponent());
        entity.add(new BlockStopComponent());
        return entity;
    }
    public static Entity createJump(Engine engine, int wave){
        Entity entity = createBase(engine, new Texture(Gdx.files.internal("sprites/enemies/eYellow.png")), ENEMY_SPEED, wave * 3, wave);
        entity.add(new WalkingComponent());
        entity.add(new BlockStopComponent());
        entity.getComponent(EnemyComponent.class).jumpDamage = 5;
        return entity;
    }
}
