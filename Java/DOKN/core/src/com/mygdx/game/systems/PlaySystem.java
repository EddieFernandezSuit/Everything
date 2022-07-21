package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.mygdx.game.DOKN;
import com.mygdx.game.components.AccelerationComponent;
import com.mygdx.game.components.PlayComponent;
import com.mygdx.game.components.RectComponent;
import com.mygdx.game.components.TransformComponent;

import static com.badlogic.gdx.Gdx.input;
import static com.mygdx.game.base.PlayState.nextWave;
import static com.mygdx.game.base.PlayState.spawn;

public class PlaySystem extends IteratingSystem {

    public PlaySystem() {
        super(Family.all(PlayComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        RectComponent rectComp = entity.getComponent(RectComponent.class);

        if(rectComp.rect.contains(input.getX(), DOKN.HEIGHT - input.getY()) && input.justTouched() && !spawn){
            nextWave();
        }
    }
}
