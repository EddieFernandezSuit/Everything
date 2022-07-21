package com.defensetower.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.defensetower.game.components.RadiusComponent;
import com.defensetower.game.components.TextureComponent;
import com.defensetower.game.components.TransformComponent;


public class RadiusEntity {

    public static Entity create(Engine engine, float radius, Entity tower){

        Entity entity = new Entity();
        entity.add(new TransformComponent());
        entity.add(new RadiusComponent());
        entity.getComponent(RadiusComponent.class).radius = radius;
        entity.getComponent(RadiusComponent.class).towerId = tower;
        engine.addEntity(entity);

        return entity;
    }
}
