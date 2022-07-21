package com.defensetower.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.defensetower.game.components.*;

public class PlusScoreEntity {

    public static Entity create(Vector2 position, Engine engine, String score) {

        Entity entity = new Entity();
        entity.add(new TransformComponent());
        entity.add(new TextComponent());
        entity.add(new SpeedComponent());
        entity.add(new RemoveComponent());

        entity.getComponent(RemoveComponent.class).time = 1.5f;
        entity.getComponent(TransformComponent.class).position.set(position);
        entity.getComponent(SpeedComponent.class).speed = 100;
        entity.getComponent(SpeedComponent.class).radians = 3.1415f * .5f;
        entity.getComponent(TextComponent.class).text = "+" + score;
        entity.getComponent(TextComponent.class).type = 2;
        entity.getComponent(TextComponent.class).isStats = false;

        engine.addEntity(entity);
        return entity;
    }

}