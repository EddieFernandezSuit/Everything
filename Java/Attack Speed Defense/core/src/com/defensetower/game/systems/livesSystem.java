package com.defensetower.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.defensetower.game.components.*;

import static com.defensetower.game.gamestates.PlayState.lives;
import static com.defensetower.game.gamestates.PlayState.score;

public class livesSystem extends IteratingSystem {
    public livesSystem() {
        super(Family.all(livesComponent.class, TextComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        livesComponent livesComp = entity.getComponent(livesComponent.class);
        TextComponent textComp = entity.getComponent(TextComponent.class);


        livesComp.lives = lives;
        textComp.text = Integer.toString(lives) + "L";

    }
}
