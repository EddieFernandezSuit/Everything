package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.RectComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;

public class HitBoxSystem extends IteratingSystem {

    public HitBoxSystem() {
        super(Family.all(TextureComponent.class, RectComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        RectComponent hitBoxComp =  entity.getComponent(RectComponent.class);
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        TextureComponent textComp = entity.getComponent(TextureComponent.class);

        hitBoxComp.rect.x = transformComp.position.x - textComp.texture.getWidth()/2;
        hitBoxComp.rect.y = transformComp.position.y - textComp.texture.getHeight()/2;

    }
}
