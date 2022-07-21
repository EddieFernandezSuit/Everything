package com.mygdx.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.components.TiledComponent;

import static com.mygdx.game.DOKN.cam;

public class TiledSystem extends EntitySystem {

    private Family family = Family.all(TiledComponent.class).get();
    private ImmutableArray<Entity> entities;

    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    public TiledSystem() {
        super(-1);
        tiledMap = new TmxMapLoader().load("BackMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(family);
    }

    public void update(float dt){
        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();
    }
}
