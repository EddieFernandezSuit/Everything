package com.mygdx.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;

public class TextureSystem extends EntitySystem {

    private SpriteBatch spriteBatch;
    private Family family = Family.all(TextureComponent.class, TransformComponent.class).get();
    private ImmutableArray<Entity> entities;
    private ShapeRenderer sr;

    public TextureSystem() {
        super();
        spriteBatch = new SpriteBatch();
        sr = new ShapeRenderer();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(family);
    }

    @Override
    public void update(float dt) {
        spriteBatch.begin();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);

            TextureComponent textureComp = entity.getComponent(TextureComponent.class);
            TransformComponent transformComp = entity.getComponent(TransformComponent.class);

            spriteBatch.draw(
                    textureComp.texture,
                    transformComp.position.x - textureComp.texture.getWidth() / 2,
                    transformComp.position.y - textureComp.texture.getHeight() / 2,
                    textureComp.texture.getWidth() / 2,
                    textureComp.texture.getHeight() / 2,
                    textureComp.texture.getWidth(),
                    textureComp.texture.getHeight(),
                    1,
                    1,
                    transformComp.rotation,
                    0,
                    0,
                    textureComp.texture.getWidth(),
                    textureComp.texture.getHeight(),
                    false,
                    false
            );
        }
        spriteBatch.end();
    }
}

