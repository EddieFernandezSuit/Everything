package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.base.Jukebox;
import com.mygdx.game.base.PlayState;
import com.mygdx.game.components.*;
import com.mygdx.game.entities.BulletEntity;

import static com.mygdx.game.DOKN.HEIGHT;
import static com.mygdx.game.base.PlayState.*;

public class TowerSystem extends IteratingSystem {

    public TowerSystem() {
        super(Family.all(TransformComponent.class, TowerComponent.class, AccelerationComponent.class).get());
    }

    protected void processEntity(Entity entity, float deltaTime) {
        TowerComponent towerComp = entity.getComponent(TowerComponent.class);
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        TextureComponent textureComp = entity.getComponent(TextureComponent.class);

        towerComp.attackTimer += deltaTime / 7;

        if (towerComp.attackTimer * towerComp.attackSpeed > towerComp.attackTime + + PlayState.randRange(0,10)/5f) {

            ImmutableArray<Entity> enemies = this.getEngine().getEntitiesFor(Family.all(EnemyComponent.class).get());
            Array<Entity> sortedEnemies = new Array<Entity>();
            sortedEnemies.addAll(enemies.toArray());
            sortedEnemies.sort(EnemyComponent.enemyDistanceSorter); // Last enemy in array is guarenteed to be furthest

            for (int i = sortedEnemies.size - 1; i >= 0; i--) {
                TransformComponent enTransComp = sortedEnemies.get(i).getComponent(TransformComponent.class);
                float d = (float) Math.sqrt(((enTransComp.position.x - transformComp.position.x) * (enTransComp.position.x - transformComp.position.x)) + ((enTransComp.position.y - transformComp.position.y) * (enTransComp.position.y - transformComp.position.y)));
                if (d < towerComp.range) {

                    int a = (int) (enTransComp.position.x - ((MAX_ACCURACY - towerComp.accuracy) * ACCURACY_GAP));
                    int b = (int) (enTransComp.position.x + ((MAX_ACCURACY - towerComp.accuracy) * ACCURACY_GAP));
                    if (a < HALF_BULLET_SIZE) {
                        a = HALF_BULLET_SIZE;
                    }
                    if (b > WIDTH_MINUS - HALF_BULLET_SIZE) {
                        b = (int) WIDTH_MINUS - HALF_BULLET_SIZE;
                    }

                    float predDist = PlayState.randRange(a, b) + (enTransComp.dx * (HEIGHT - enTransComp.position.y) / towerComp.bulletSpeed);
                    if (predDist > WIDTH_MINUS - HALF_BULLET_SIZE) {
                        predDist -= (enTransComp.dx * (HEIGHT - enTransComp.position.y) / towerComp.bulletSpeed);
                    }
                    towerComp.attackTimer = 0;
                    towerComp.lockOnCount += 1;
                    if (towerComp.lockOnCount < towerComp.lockOn) {
                        BulletEntity.create(this.getEngine(), predDist, HEIGHT, towerComp.damage *  (1 + towerComp.rightBoost + towerComp.leftBoost), null, towerComp.bulletSpeed, towerComp.bounce, towerComp.type);
                    Jukebox.play("kat");
                        break;
                    } else {
                        BulletEntity.create(this.getEngine(), predDist, HEIGHT, towerComp.damage * (1 + towerComp.leftBoost + towerComp.leftBoost), sortedEnemies.get(i), towerComp.bulletSpeed, towerComp.bounce, towerComp.type);
                        towerComp.lockOnCount = 0;
                    Jukebox.play("kat");
                        break;
                    }
                }
            }
        }

        ImmutableArray<Entity> towers = this.getEngine().getEntitiesFor(Family.all(TowerComponent.class).get());
        for(int i = towers.size() - 1; i >= 0; i--){

            TransformComponent blockTransformComp = towers.get(i).getComponent(TransformComponent.class);
            TowerComponent otherTowerComp = towers.get(i).getComponent(TowerComponent.class);

            if(towers.get(i) != entity && otherTowerComp.type == towerComp.type) {
                if (transformComp.position.x - 32 <= blockTransformComp.position.x + ENTITY_SIZE/2 &&
                        transformComp.position.x - 32 >= blockTransformComp.position.x - ENTITY_SIZE/ 2&&
                        transformComp.position.y <= blockTransformComp.position.y + ENTITY_SIZE/2 &&
                        transformComp.position.y >= blockTransformComp.position.y - ENTITY_SIZE/2) {
                    towerComp.leftBoost = 1;
                }
                if (transformComp.position.x + 32 <= blockTransformComp.position.x + ENTITY_SIZE/2 &&
                        transformComp.position.x + 32 >= blockTransformComp.position.x - ENTITY_SIZE/ 2&&
                        transformComp.position.y <= blockTransformComp.position.y + ENTITY_SIZE/2 &&
                        transformComp.position.y >= blockTransformComp.position.y - ENTITY_SIZE/2) {
                    towerComp.rightBoost = 1;
                }
            }
        }

        if(towerComp.leftBoost == 1 || towerComp.rightBoost == 1){
            if(towerComp.leftBoost == 1 && towerComp.rightBoost == 1){
                switch(towerComp.type) {
                    case 1:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tRed.png"));
                        break;
                    case 2:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tOrange.png"));
                        break;
                    case 3:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tYellow.png"));
                        break;
                    case 4:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tYGreen.png"));
                        break;
                    case 5:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tGreen.png"));
                        break;
                    case 6:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tCyan.png"));
                        break;
                    case 7:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tBlue.png"));
                        break;
                    case 8:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tPBlue.png"));
                        break;
                    case 9:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tPurple.png"));
                        break;
                }
            }
            else {
                switch (towerComp.type) {
                    case 1:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tRed2.png"));
                        break;
                    case 2:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tOrange2.png"));
                        break;
                    case 3:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tYellow2.png"));
                        break;
                    case 4:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tYGreen2.png"));
                        break;
                    case 5:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tGreen2.png"));
                        break;
                    case 6:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tCyan2.png"));
                        break;
                    case 7:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tBlue2.png"));
                        break;
                    case 8:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tPBlue2.png"));
                        break;
                    case 9:
                        textureComp.texture = new Texture(Gdx.files.internal("sprites/towers/tPurple2.png"));
                        break;
                }
            }
        }
    }
}
