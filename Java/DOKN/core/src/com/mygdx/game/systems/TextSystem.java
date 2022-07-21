package com.mygdx.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.components.TextComponent;
import com.mygdx.game.components.TransformComponent;


public class TextSystem extends EntitySystem {

    private SpriteBatch spriteBatch;
    private Family family = Family.all(TextComponent.class, TransformComponent.class).get();
    private ImmutableArray<Entity> entities;
    private BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
    private BitmapFont smallFont = new BitmapFont(Gdx.files.internal("fonts/smallFont.fnt"));
    private BitmapFont greenFont = new BitmapFont(Gdx.files.internal("fonts/greenFont.fnt"));

    public TextSystem() {
        super();
        spriteBatch = new SpriteBatch();
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

            TextComponent textComp = entity.getComponent(TextComponent.class);
            TransformComponent transformComp = entity.getComponent(TransformComponent.class);
            if(textComp.type == 0) {
                font.setColor(Color.BLACK);
                font.draw(spriteBatch, textComp.text, transformComp.position.x, transformComp.position.y);
            }
            if(textComp.type == 1) {
                smallFont.setColor(Color.BLACK);
                smallFont.draw(spriteBatch, textComp.text, transformComp.position.x, transformComp.position.y);
            }
            if(textComp.type == 2){
                greenFont.draw(spriteBatch, textComp.text, transformComp.position.x, transformComp.position.y);
            }
            if(textComp.type == 3) {
                font.setColor(Color.GREEN);
                font.draw(spriteBatch, textComp.text, transformComp.position.x, transformComp.position.y);
            }
//            if(textComp.isStats) {
//                if (!showStats) {
//                    this.getEngine().removeEntity(entity);
//                }d
//            }
        }
        spriteBatch.end();


    }
}
