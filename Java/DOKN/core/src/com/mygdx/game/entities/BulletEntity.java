package com.mygdx.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.DOKN;
import com.mygdx.game.components.*;

import static com.mygdx.game.base.PlayState.GRAVITY;


public class BulletEntity {

    public static Entity create(Engine engine, float x, float y, int damage, Entity maxId, int bulletSpeed, int bounce, int type){
        Entity entity = new Entity();
        entity.add(new AccelerationComponent());
        entity.add(new TextureComponent());
        entity.add(new TransformComponent());
        entity.add(new RectComponent());
        entity.add(new BulletComponent());
        entity.getComponent(RectComponent.class).rect = new Rectangle(x, y, 16, 16);
        entity.getComponent(BulletComponent.class).damage = damage;
        entity.getComponent(BulletComponent.class).maxId = maxId;
        entity.getComponent(BulletComponent.class).bounce = bounce;
        entity.getComponent(BulletComponent.class).type = type;
        entity.getComponent(TransformComponent.class).position.x = x;
        entity.getComponent(TransformComponent.class).position.y = y;
        entity.getComponent(TransformComponent.class).speed = bulletSpeed;
        switch(entity.getComponent(BulletComponent.class).type){
            case 0: entity.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/bullets/bullet.png"));
                break;
            case 1: entity.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/bullets/bulletRed.png"));
                break;
            case 2: entity.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/bullets/bulletOrange.png"));
                break;
            case 3: entity.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/bullets/bulletYellow.png"));
                break;
            case 4: entity.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/bullets/bulletYGreen.png"));
                break;
            case 5: entity.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/bullets/bulletGreen.png"));
                break;
            case 6: entity.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/bullets/bulletCyan.png"));
                break;
            case 7: entity.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/bullets/bulletBlue.png"));
                break;
            case 8: entity.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/bullets/bulletPurple.png"));
                break;
            case 9: entity.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/bullets/bulletPink.png"));
                break;
            case 10: entity.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/bullets/bullet.png"));
        }
        entity.getComponent(AccelerationComponent.class).acceleration = 0;
        if (maxId != null) {
            entity.getComponent(BulletComponent.class).maxId = maxId;
            entity.getComponent(TransformComponent.class).radians = MathUtils.atan2(
                    maxId.getComponent(TransformComponent.class).position.y - entity.getComponent(TransformComponent.class).position.y,
                    maxId.getComponent(TransformComponent.class).position.x + (maxId.getComponent(TransformComponent.class).dx * (DOKN.HEIGHT - maxId.getComponent(TransformComponent.class).position.y)/bulletSpeed) - entity.getComponent(TransformComponent.class).position.x
            );
        }
        else
        {
            entity.getComponent(BulletComponent.class).maxId = null;
            entity.getComponent(TransformComponent.class).radians = 3 * 3.1415f / 2;
        }
        entity.getComponent(TransformComponent.class).dx =  entity.getComponent(TransformComponent.class).speed * MathUtils.cos(entity.getComponent(TransformComponent.class).radians);
        entity.getComponent(TransformComponent.class).dy =  entity.getComponent(TransformComponent.class).speed * MathUtils.sin(entity.getComponent(TransformComponent.class).radians);
        engine.addEntity(entity);
        return entity;
    }

}
