package com.defensetower.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.defensetower.game.MainGame;
import com.defensetower.game.components.RadiusComponent;
import com.defensetower.game.components.TransformComponent;


public class RadiusSystem extends EntitySystem {

    private Family family = Family.all(RadiusComponent.class, TransformComponent.class).get();
    private ImmutableArray<Entity> entities;
    private ShapeRenderer sr;

    public RadiusSystem() {
        super();
        sr = new ShapeRenderer();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(family);
    }

    @Override
    public void update(float dt) {
        for(int i = 0 ;entities.size() > i; i++) {

            Entity entity = entities.get(i);
            TransformComponent transformComp = entity.getComponent(TransformComponent.class);
            RadiusComponent radiusComp = entity.getComponent(RadiusComponent.class);

            transformComp.position.x = radiusComp.towerId.getComponent(TransformComponent.class).position.x;
            transformComp.position.y = radiusComp.towerId.getComponent(TransformComponent.class).position.y;

            sr.setColor(Color.BLACK);
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.circle(transformComp.position.x, transformComp.position.y, radiusComp.radius);
            sr.end();
        }
    }
}
