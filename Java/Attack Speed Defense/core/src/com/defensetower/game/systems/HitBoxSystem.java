package com.defensetower.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.defensetower.game.components.*;

public class HitBoxSystem extends IteratingSystem {

    public HitBoxSystem() {
        super(Family.all(TextureComponent.class, HitBoxComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HitBoxComponent hitBoxComp =  entity.getComponent(HitBoxComponent.class);
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        TextureComponent textComp = entity.getComponent(TextureComponent.class);

        hitBoxComp.rect.x = transformComp.position.x - textComp.texture.getWidth()/2;
        hitBoxComp.rect.y = transformComp.position.y - textComp.texture.getHeight()/2;
    }
}
