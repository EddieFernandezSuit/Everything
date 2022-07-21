package com.defensetower.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.defensetower.game.components.ScoreComponent;
import com.defensetower.game.components.SpeedComponent;
import com.defensetower.game.components.TextComponent;
import com.defensetower.game.components.TransformComponent;

import static com.defensetower.game.gamestates.PlayState.score;

public class ScoreSystem extends IteratingSystem {
    public ScoreSystem() {
        super(Family.all(ScoreComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ScoreComponent scoreComp = entity.getComponent(ScoreComponent.class);
        TextComponent textComp = entity.getComponent(TextComponent.class);


        scoreComp.score = score;
        textComp.text = Integer.toString(score) + "c";

    }
}
