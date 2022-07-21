package com.mygdx.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.mygdx.game.components.TextComponent;
import com.mygdx.game.components.TransformComponent;

public class TextEntity {

    public static Entity create(Engine engine, int x, int y){
        Entity entity = new Entity();
        entity.add(new TransformComponent());
        entity.add(new TextComponent());
        entity.getComponent(TransformComponent.class).position.x = x;
        entity.getComponent(TransformComponent.class).position.y = y;
        entity.getComponent(TextComponent.class).text = "";
        entity.getComponent(TextComponent.class).type = 0;
        engine.addEntity(entity);
        return entity;
    }
}
