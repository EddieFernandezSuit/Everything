package com.defensetower.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.defensetower.game.components.BulletComponent;
import com.defensetower.game.components.SpeedComponent;
import com.defensetower.game.components.TransformComponent;

public class MotionSystem extends IteratingSystem {
    public MotionSystem() {
        super(Family.all(SpeedComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SpeedComponent speedComp = entity.getComponent(SpeedComponent.class);
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        float speed = speedComp.speed * deltaTime;
        float dx = speed * MathUtils.cos(speedComp.radians);
        float dy = speed * MathUtils.sin(speedComp.radians);
        transformComp.position.add(dx,dy);

    }
}
