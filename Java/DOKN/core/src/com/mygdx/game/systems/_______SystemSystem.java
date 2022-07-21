package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.AccelerationComponent;
import com.mygdx.game.components.TransformComponent;

public class _______SystemSystem extends IteratingSystem {

    public _______SystemSystem() {
        super(Family.all(AccelerationComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);


    }
}
