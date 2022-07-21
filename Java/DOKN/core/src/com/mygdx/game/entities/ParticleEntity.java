package com.mygdx.game.entities;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.*;

public class ParticleEntity {

    private static final int PS = 2;

    public static Entity create(Vector2 position, Engine engine){
        Entity particle = new Entity();
        particle.add(new TransformComponent());
        particle.add(new RectComponent());
        particle.add(new RemoveComponent());
        particle.add(new RectDrawComponent());
        particle.add(new AccelerationComponent());
        particle.getComponent(RemoveComponent.class).time = MathUtils.random( 2f, 2.5f );
        particle.getComponent(TransformComponent.class).position.set(position);
        particle.getComponent(TransformComponent.class).speed = 10;
        particle.getComponent(TransformComponent.class).radians = MathUtils.random(2 * 3.1415f);
        particle.getComponent(TransformComponent.class).dx =  particle.getComponent(TransformComponent.class).speed * MathUtils.cos(particle.getComponent(TransformComponent.class).radians);
        particle.getComponent(TransformComponent.class).dy =  particle.getComponent(TransformComponent.class).speed * MathUtils.sin(particle.getComponent(TransformComponent.class).radians);
        particle.getComponent(AccelerationComponent.class).acceleration = 0;
        particle.getComponent(RectComponent.class).rect = new Rectangle(particle.getComponent(TransformComponent.class).position.x - PS/2, particle.getComponent(TransformComponent.class).position.y - PS/2, PS,PS);
        particle.getComponent(RectComponent.class).color = particle.getComponent(RectComponent.class).BLACK;
        engine.addEntity(particle);
        return particle;
    }
}
