package com.defensetower.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.defensetower.game.components.*;

public class BulletEntity {

    public static Entity create(Vector2 position, Engine engine, int damage, Entity maxId, float speed) {

        Entity bullet = new Entity();
        bullet.add(new TransformComponent());
        bullet.add(new TextureComponent());
        bullet.add(new BulletComponent());
        bullet.add(new SpeedComponent());
        bullet.add(new HitBoxComponent());

        bullet.getComponent(TransformComponent.class).position.set(position);
        bullet.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/Bullet.png"));
        bullet.getComponent(SpeedComponent.class).speed = speed;
        bullet.getComponent(HitBoxComponent.class).rect = new Rectangle(position.x, position.y, 16, 16);
        bullet.getComponent(BulletComponent.class).damage = damage;

        //ImmutableArray<Entity> enemies = engine.getEntitiesFor(Family.all(EnemyComponent.class).get());
        //for(int i = 0; i < enemies.size(); i++){
        //    Entity iii = enemies.get(i);
        //    if (iii.getComponent(EnemyComponent.class).distance > maxDistance) {
        //        maxDistance = iii.getComponent(EnemyComponent.class).distance;
        //        maxId = iii;
        //    }
        //}

        if (maxId != null){
            bullet.getComponent(BulletComponent.class).maxId = maxId;
            bullet.getComponent(SpeedComponent.class).radians = MathUtils.atan2(
                    maxId.getComponent(TransformComponent.class).position.y - bullet.getComponent(TransformComponent.class).position.y,
                    maxId.getComponent(TransformComponent.class).position.x - bullet.getComponent(TransformComponent.class).position.x
            );
        }
        engine.addEntity(bullet);
        return bullet;
    }

}