package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.AccelerationComponent;
import com.mygdx.game.components.TransformComponent;

import static com.mygdx.game.base.PlayState.ENTITY_SIZE;
import static com.mygdx.game.base.PlayState.deltaTime;

public class PhysicsSystem extends IteratingSystem {

    public PhysicsSystem() {
        super(Family.all(AccelerationComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float dt) {
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        AccelerationComponent gravityComp = entity.getComponent(AccelerationComponent.class);

        transformComp.dy += gravityComp.acceleration;
        transformComp.position.y += transformComp.dy * deltaTime;
        transformComp.position.x += transformComp.dx * deltaTime;

    }
}
