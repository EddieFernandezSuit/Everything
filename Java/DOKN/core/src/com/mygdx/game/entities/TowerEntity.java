package com.mygdx.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.components.*;

import static com.mygdx.game.base.PlayState.*;

public class TowerEntity {

    public static Entity create(Engine engine, int x, int y, Texture texture, int damage, int bulletSpeed, float attackSpeed, int accuracy, int bounce, int lockOn, int type){
        Entity entity = new Entity();
        entity.add(new TransformComponent());
        entity.add(new TextureComponent());
        entity.add(new AccelerationComponent());
        entity.add(new TowerComponent());
        entity.add(new BlockComponent());
        entity.add(new BlockStopComponent());
        entity.getComponent(TextureComponent.class).texture = texture;
        entity.getComponent(TransformComponent.class).position.x = x;
        entity.getComponent(TransformComponent.class).position.y = y;
        entity.getComponent(AccelerationComponent.class).acceleration = 0;
        entity.getComponent(TowerComponent.class).damage = damage;
        entity.getComponent(TowerComponent.class).bulletSpeed = bulletSpeed;
        entity.getComponent(TowerComponent.class).attackSpeed = attackSpeed;
        entity.getComponent(TowerComponent.class).accuracy = accuracy;
        entity.getComponent(TowerComponent.class).bounce = bounce;
        entity.getComponent(TowerComponent.class).lockOn = lockOn;
        entity.getComponent(TowerComponent.class).type = type;
        entity.getComponent(TowerComponent.class).state = 0;
        engine.addEntity(entity);
        return entity;

    }

    public static Entity createBasic(Engine engine, int x, int y){
        Entity entity = create(engine, x, y, new Texture(Gdx.files.internal(tGrey)), T1DAMAGE, T1BULLET_SPEED, T1ATTACK_SPEED, 0, T1BOUNCE,T1LOCK_ON, 0);
        return entity;
    }

    public static Entity createTower2(Engine engine, int x, int y){
        Entity entity = create(engine, x, y, new Texture(Gdx.files.internal(tRed)), T2DAMAGE, T2BULLET_SPEED, T1ATTACK_SPEED, T1ACCURACY,T1BOUNCE,T1LOCK_ON, 1);
        return entity;
    }

    public static Entity createTower3(Engine engine, int x, int y){
        Entity entity = create(engine, x, y, new Texture(Gdx.files.internal(tOrange)), T2DAMAGE, T3BULLET_SPEED, T1ATTACK_SPEED, T1ACCURACY,T1BOUNCE,T1LOCK_ON, 2);
        return entity;
    }

    public static Entity createTower4(Engine engine, int x, int y){
        Entity entity = create(engine, x, y, new Texture(Gdx.files.internal(tYellow)), T1DAMAGE, T4BULLET_SPEED, T4ATTACK_SPEED, T1ACCURACY, T1BOUNCE, T1LOCK_ON, 3);
        return entity;
    }

    public static Entity createTower5(Engine engine, int x, int y){
        Entity entity = create(engine, x, y, new Texture(Gdx.files.internal(tYGreen)), T1DAMAGE, T5BULLET_SPEED, T1ATTACK_SPEED, T5ACCURACY,T1BOUNCE,T1LOCK_ON, 4);
        return entity;
    }

    public static Entity createTower6(Engine engine, int x, int y){
        Entity entity = create(engine, x, y, new Texture(Gdx.files.internal(tGreen)), T1DAMAGE, T6BULLET_SPEED, T1ATTACK_SPEED, T1ACCURACY, T6BOUNCE,T1LOCK_ON, 5);
        return entity;
    }

    public static Entity createTower7(Engine engine, int x, int y){
        Entity entity = create(engine, x, y, new Texture(Gdx.files.internal(tCyan)), T1DAMAGE, T7BULLET_SPEED, T1ATTACK_SPEED, T1ACCURACY,T1BOUNCE,T7LOCK_ON, 6);
        return entity;
    }

    public static Entity createTower8(Engine engine, int x, int y){
        Entity entity = create(engine, x, y, new Texture(Gdx.files.internal(tBlue)), T8DAMAGE, T8BULLET_SPEED, T8ATTACK_SPEED, T8ACCURACY,T8BOUNCE,T8LOCK_ON, 7);
        return entity;
    }

    public static Entity createTower9(Engine engine, int x, int y){
        Entity entity = create(engine, x, y, new Texture(Gdx.files.internal(tPBlue)), T9DAMAGE, T9BULLET_SPEED, T8ATTACK_SPEED, T9ACCURACY,T9BOUNCE,T9LOCK_ON, 8);
        return entity;
    }

    public static Entity createTower10(Engine engine, int x, int y){
        Entity entity = create(engine, x, y, new Texture(Gdx.files.internal(tPurple)), T10DAMAGE, T10BULLET_SPEED, T8ATTACK_SPEED, T10ACCURACY,T10BOUNCE,T10LOCK_ON, 9);
        return entity;
    }
    //ryu ga waga teki o kurau﻿
}
