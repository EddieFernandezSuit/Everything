package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.DOKN;
import com.mygdx.game.base.PlayState;
import com.mygdx.game.components.*;
import com.mygdx.game.entities.BulletEntity;

import java.awt.*;

import static com.mygdx.game.DOKN.HEIGHT;
import static com.mygdx.game.base.PlayState.*;

public class EnemySystem extends IteratingSystem {
    private static final int HPX = 18;
    private static final int HPY = 22;

    ShapeRenderer sr = new ShapeRenderer();

    public EnemySystem() {
        super(Family.all(TransformComponent.class, EnemyComponent.class).get());}

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        EnemyComponent enemyComp = entity.getComponent(EnemyComponent.class);
        TextureComponent textureComp = entity.getComponent(TextureComponent.class);
        ImmutableArray<Entity> blocks = this.getEngine().getEntitiesFor(Family.all(BlockComponent.class).get());

        enemyComp.inFront = 0;

        if(enemyComp.animation){
            enemyComp.timerAnimation++;
            if(!enemyComp.timerSwitch) {
                enemyComp.timerBlink++;
                if(enemyComp.timerBlink > enemyComp.timeBlink){
                    textureComp.texture = enemyComp.origTexture;
                    enemyComp.timerBlink = 0;
                    enemyComp.timerSwitch = true;
                }
            }
            else
            {
                enemyComp.timerBlink2++;
                if(enemyComp.timerBlink2 > enemyComp.timeBlink){
                    textureComp.texture = new Texture(Gdx.files.internal(eHurt));
                    enemyComp.timerBlink2 = 0;
                    enemyComp.timerSwitch = false;
                }
            }

            if(enemyComp.timeAnimation < enemyComp.timerAnimation){
                enemyComp.timerAnimation = 0;
                textureComp.texture = enemyComp.origTexture;
                enemyComp.animation = false;
            }

        }
        for(int i = blocks.size() - 1; i >= 0; i--){

            TransformComponent blockTransformComp = blocks.get(i).getComponent(TransformComponent.class);

            if(blocks.get(i) != entity) {
                if (transformComp.position.x <= blockTransformComp.position.x - (transformComp.dx) - (ENTITY_SIZE/2) + 3 &&
                        transformComp.position.x >= blockTransformComp.position.x - (transformComp.dx) - (ENTITY_SIZE * 3/2) + 3&&
                        transformComp.position.y <= blockTransformComp.position.y + ENTITY_SIZE/2 &&
                        transformComp.position.y >= blockTransformComp.position.y - ENTITY_SIZE/2) {
                    enemyComp.inFront += 1;
                    break;
                }
            }
        }

        if(transformComp.position.x > WIDTH_MINUS){
            this.getEngine().removeEntity(entity);
            lives -= 1;
            for(int i = 0; i <= (10 - lives) * wave; i++) {
                Entity bullet = BulletEntity.create(this.getEngine(), transformComp.position.x, transformComp.position.y, (10 - lives) *  wave * 10, null, randRange(60, 70), (10 - lives) * wave, 0);
                bullet.getComponent(TransformComponent.class).radians = MathUtils.random(3.1415f * 3/4, 3.1415f * 5/4);
                bullet.getComponent(TransformComponent.class).dx =  bullet.getComponent(TransformComponent.class).speed * MathUtils.cos(bullet.getComponent(TransformComponent.class).radians);
                bullet.getComponent(TransformComponent.class).dy =  bullet.getComponent(TransformComponent.class).speed * MathUtils.sin(bullet.getComponent(TransformComponent.class).radians);

            }
        }

        enemyComp.healthBar.x = (int) transformComp.position.x - HPX;
        enemyComp.healthBar.y = (int) transformComp.position.y + HPY;
        enemyComp.healthBar.width = (int) (enemyComp.hitPoints * enemyComp.totalBarWidth / enemyComp.totalHitPoints);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.RED);
        sr.rect(enemyComp.healthBar.x, enemyComp.healthBar.y, enemyComp.healthBar.width, enemyComp.healthBar.height);
        sr.end();

        enemyComp.distance = transformComp.position.x;
    }
}
