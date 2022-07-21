package com.defensetower.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.defensetower.game.components.RectComponent;
import com.defensetower.game.components.TransformComponent;


public class RectSystem extends EntitySystem {

    private ShapeRenderer sr;
    private Family family = Family.all(RectComponent.class, TransformComponent.class).get();
    private ImmutableArray<Entity> entities;

    public RectSystem() {
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
        sr.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            RectComponent rectComp = entity.getComponent(RectComponent.class);
            TransformComponent transformComp = entity.getComponent(TransformComponent.class);

            if(rectComp.color == rectComp.GRAY){
                sr.setColor(Color.GRAY);
            }
            if(rectComp.color == rectComp.BLUE){
                sr.setColor(Color.BLUE);
            }
            if(rectComp.color == rectComp.BLACK){
                sr.setColor(Color.BLACK);
            }
            sr.rect(transformComp.position.x - rectComp.rect.width/2, transformComp.position.y - rectComp.rect.height/2, rectComp.rect.width, rectComp.rect.height);

        }
        sr.end();


    }
}
