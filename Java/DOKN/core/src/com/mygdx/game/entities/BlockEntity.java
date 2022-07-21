package com.mygdx.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.components.*;

public class BlockEntity {

    public static Entity create(Engine engine, int x, int y){
        Entity entity = new Entity();
        entity.add(new TransformComponent());
        entity.add(new TextureComponent());
        entity.add(new AccelerationComponent());
        entity.add(new BlockComponent());
        entity.add(new BlockStopComponent());
        entity.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/block.png"));
        entity.getComponent(TransformComponent.class).position.x = x;
        entity.getComponent(TransformComponent.class).position.y = y;
        engine.addEntity(entity);
        return entity;
    }
}
