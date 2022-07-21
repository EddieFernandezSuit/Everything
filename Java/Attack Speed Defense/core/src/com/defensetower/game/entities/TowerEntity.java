package com.defensetower.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.defensetower.game.components.TextureComponent;
import com.defensetower.game.components.TowerComponent;
import com.defensetower.game.components.TransformComponent;

import static com.defensetower.game.gamestates.PlayState.*;

public class TowerEntity {


    public static Entity create(Engine engine,Entity radius, Texture texture, int damage, float range, int cost, float attackSpeed, float bulletSpeed, int type){

        Entity tower = new Entity();
        tower.add(new TransformComponent());
        tower.add(new TextureComponent());
        tower.add(new TowerComponent());

        tower.getComponent(TextureComponent.class).texture = texture;
        tower.getComponent(TowerComponent.class).attackSpeed = attackSpeed;
        tower.getComponent(TowerComponent.class).damage = damage;
        tower.getComponent(TowerComponent.class).cost = cost;
        tower.getComponent(TowerComponent.class).range = range;
        tower.getComponent(TowerComponent.class).radius = radius;
        tower.getComponent(TowerComponent.class).bulletSpeed = bulletSpeed;
        tower.getComponent(TowerComponent.class).stats = false;
        tower.getComponent(TowerComponent.class).type = type;

        engine.addEntity(tower);
        return tower;
    }

    public static Entity createBasic(Engine engine, Entity radius){
        Entity tower = create(engine, radius, new Texture(Gdx.files.internal("sprites/Player.png")), 1, 130, basicCost, 1, 500,0);
        return tower;
    }
    public static Entity createFast(Engine engine, Entity radius){
        Entity tower = create(engine, radius, new Texture(Gdx.files.internal("sprites/redPlayer.png")), 1, 110, fastCost, 2, 400, 1);
        return tower;
    }
    public static Entity createDamage(Engine engine, Entity radius){
        Entity tower = create(engine, radius, new Texture(Gdx.files.internal("sprites/yellowPlayer.png")), 3, 110, damageCost, 1, 400, 2);
        return tower;
    }
    public static Entity createRange(Engine engine, Entity radius){
        Entity tower = create(engine, radius, new Texture(Gdx.files.internal("sprites/greenPlayer.png")), 2, 210, rangeCost, 1, 650, 3);
        return tower;
    }
    public static Entity createSpeed(Engine engine, Entity radius){
        Entity tower = create(engine, radius, new Texture(Gdx.files.internal("sprites/torqPlayer.png")), 2, 150, speedCost, 1, 1000, 4);
        return tower;
    }
}
