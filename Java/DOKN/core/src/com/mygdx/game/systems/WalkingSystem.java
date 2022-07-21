package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.AccelerationComponent;
import com.mygdx.game.components.EnemyComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.components.WalkingComponent;

import static com.mygdx.game.base.PlayState.JUMP;

public class WalkingSystem extends IteratingSystem {

    public WalkingSystem() {
        super(Family.all(WalkingComponent.class, TransformComponent.class, EnemyComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        EnemyComponent enemyComp = entity.getComponent(EnemyComponent.class);

        if(enemyComp.inFront > 0){
            transformComp.dy = JUMP/2;
            transformComp.dy += 2 * JUMP;
            transformComp.dx = transformComp.dx * 2/3 + 2;
            enemyComp.hitPoints -= enemyComp.jumpDamage;
            if(enemyComp.hitPoints <= 0){
                this.getEngine().removeEntity(entity);
            }
        }
    }
}
