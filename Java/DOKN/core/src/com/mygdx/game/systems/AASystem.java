package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.AAComponent;
import com.mygdx.game.components.AccelerationComponent;
import com.mygdx.game.components.RectComponent;
import com.mygdx.game.components.TransformComponent;

import static com.badlogic.gdx.Gdx.input;
import static com.mygdx.game.DOKN.HEIGHT;
import static com.mygdx.game.base.PlayState.blastLines;
import static com.mygdx.game.base.PlayState.blastMode;
import static com.mygdx.game.base.PlayState.createBlastLines;

public class AASystem extends IteratingSystem {

    public AASystem() {
        super(Family.all(AAComponent.class, TransformComponent.class, RectComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        RectComponent rectComp = entity.getComponent(RectComponent.class);

        if(rectComp.rect.contains(input.getX(),HEIGHT - input.getY()) && input.justTouched()){
            createBlastLines();
        }

    }
}
