package com.defensetower.game.entities;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.defensetower.game.components.*;

public class ParticleEntity {

    public static Entity create(Vector2 position, Engine engine){
        Entity particle = new Entity();
        particle.add(new TransformComponent());
        particle.add(new SpeedComponent());
        particle.add(new RectComponent());
        particle.add(new RemoveComponent());
        particle.getComponent(RemoveComponent.class).time = MathUtils.random( .7f, 1.2f );
        particle.getComponent(TransformComponent.class).position.set(position);
        particle.getComponent(SpeedComponent.class).speed = 100;
        particle.getComponent(SpeedComponent.class).radians = MathUtils.random(2 * 3.1415f);
        particle.getComponent(RectComponent.class).rect = new Rectangle(particle.getComponent(TransformComponent.class).position.x - 2, particle.getComponent(TransformComponent.class).position.y - 2, 4,4);
        particle.getComponent(RectComponent.class).color = particle.getComponent(RectComponent.class).BLACK;
        engine.addEntity(particle);
        return particle;
    }
}
