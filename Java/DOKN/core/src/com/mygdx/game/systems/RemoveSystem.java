package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.RemoveComponent;

public class RemoveSystem extends IteratingSystem {
    public RemoveSystem() {
        super(Family.all(RemoveComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        RemoveComponent remComp = entity.getComponent(RemoveComponent.class);
        remComp.timer += deltaTime;
        if(remComp.time < remComp.timer){
            this.getEngine().removeEntity(entity);
        }
    }
}
