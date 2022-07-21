package com.mygdx.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public class _______EntityEntity {

    public static Entity create(Engine engine){
        Entity entity = new Entity();
        //entity.add();
        //entity.getComponent();
        engine.addEntity(entity);
        return entity;
    }
}
