package com.defensetower.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.defensetower.game.components.TextComponent;
import com.defensetower.game.components.TransformComponent;

public class TowerStatsText {

    public static Entity create(Engine engine, float x, float y,  String stat, boolean type, boolean isStats) {

        Entity entity = new Entity();
        entity.add(new TransformComponent());
        entity.add(new TextComponent());

        entity.getComponent(TransformComponent.class).position.x = x;
        entity.getComponent(TransformComponent.class).position.y = y;
        entity.getComponent(TextComponent.class).text = stat;
        entity.getComponent(TextComponent.class).type = 1;
        entity.getComponent(TextComponent.class).isStats = isStats;

        engine.addEntity(entity);
        return entity;
    }

}

