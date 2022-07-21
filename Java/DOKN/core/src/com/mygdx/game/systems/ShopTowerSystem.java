package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.DOKN;
import com.mygdx.game.base.PlayState;
import com.mygdx.game.components.*;
import com.mygdx.game.entities.TowerEntity;

import static com.badlogic.gdx.Gdx.input;
import static com.mygdx.game.DOKN.HEIGHT;
import static com.mygdx.game.base.PlayState.*;
import static com.mygdx.game.base.PlayState.ENTITY_SIZE;

public class ShopTowerSystem extends IteratingSystem {

    private int checker = 0;
    private int numBlocks = 0;
    private final int GRID_SNAP = ENTITY_SIZE/2 + input.getX() - (input.getX() % ENTITY_SIZE);
    private boolean isOverTower;

    public ShopTowerSystem() {
        super(Family.all(ShopTowerComponent.class, TransformComponent.class).get());

    }
    
    @Override
    public void update(float deltaTime) {
        isOverTower = false;

        // processEntity over all Entities matching the family
        super.update(deltaTime);

        if(!isOverTower) {
            if(cost != null) {
                cost.getComponent(TextComponent.class).text =
                        statDamage.getComponent(TextComponent.class).text =
                                statBulletSpeed.getComponent(TextComponent.class).text =
                                        statAttackSpeed.getComponent(TextComponent.class).text =
                                                statAccuracy.getComponent(TextComponent.class).text =
                                                        statBounce.getComponent(TextComponent.class).text =
                                                                statLockOn.getComponent(TextComponent.class).text = "";

            }
        }
    }

    public void order66(int damage, int bulletSpeed, int attackSpeed, int accuracy, int bounce, int lockOn){
        statDamage.getComponent(TextComponent.class).text = Integer.toString(damage) + "D";
        if(showBulletSpeed) {
            statBulletSpeed.getComponent(TextComponent.class).text = Integer.toString(bulletSpeed) + "S";
        }
        if(showAttackSpeed) {
            statAttackSpeed.getComponent(TextComponent.class).text = Integer.toString(attackSpeed) + "A";
        }
        if(showAccuracy) {
            statAccuracy.getComponent(TextComponent.class).text = Integer.toString(accuracy) + "C";
        }
        if(showBounce) {
            statBounce.getComponent(TextComponent.class).text = Integer.toString(bounce) + "B";
        }
        if(showLockOn) {
            statLockOn.getComponent(TextComponent.class).text = Integer.toString(lockOn) + "L";
        }
    }

    private void createTower(Entity entity){
        switch(entity.getComponent(ShopTowerComponent.class).type){
            case 0:
                if( X >= 2){
                    TowerEntity.createBasic(this.getEngine(), ENTITY_SIZE/2 + input.getX() - (input.getX() % ENTITY_SIZE), HEIGHT - input.getY());
                    X -= 2;
                    t1Num++;
                }PlayState.createShopTower();
                break;
            case 1:
                if(X >= 3) {
                    TowerEntity.createTower2(this.getEngine(), ENTITY_SIZE/2 + input.getX() - (input.getX() % ENTITY_SIZE), HEIGHT - input.getY());
                    X -= 3;
                    t2Num++;
                }PlayState.createShopTower2();
                break;
            case 2:
                if(X >= 1 && Y >= 1) {
                    TowerEntity.createTower3(this.getEngine(), ENTITY_SIZE / 2 + input.getX() - (input.getX() % ENTITY_SIZE), HEIGHT - input.getY());
                    X--;
                    Y--;
                    t3Num++;
                }PlayState.createShopTower3();
                break;
            case 3:
                if(X >= 1 && Z >= 1) {
                    TowerEntity.createTower4(this.getEngine(), ENTITY_SIZE / 2 + input.getX() - (input.getX() % ENTITY_SIZE), HEIGHT - input.getY());
                    X--;
                    Z--;
                    t4Num++;
                }PlayState.createShopTower4();
                break;
            case 4:
                if(Y >= 2) {
                    TowerEntity.createTower5(this.getEngine(), ENTITY_SIZE / 2 + input.getX() - (input.getX() % ENTITY_SIZE), HEIGHT - input.getY());
                    Y -= 2;
                    t5Num++;
                }PlayState.createShopTower5();
                break;
            case 5:
                if(Y >= 3) {
                    TowerEntity.createTower6(this.getEngine(), ENTITY_SIZE / 2 + input.getX() - (input.getX() % ENTITY_SIZE), HEIGHT - input.getY());
                    Y -= 3;
                    t6Num++;
                }PlayState.createShopTower6();
                break;
            case 6:
                if(Y >= 1 && Z >= 1) {
                    TowerEntity.createTower7(this.getEngine(), ENTITY_SIZE / 2 + input.getX() - (input.getX() % ENTITY_SIZE), HEIGHT - input.getY());
                    Y--;
                    Z--;
                    t7Num++;
                }PlayState.createShopTower7();
                break;
            case 7:
                if(Z >= 4) {
                    TowerEntity.createTower8(this.getEngine(), ENTITY_SIZE / 2 + input.getX() - (input.getX() % ENTITY_SIZE), HEIGHT - input.getY());
                    Z -= 4 ;
                    t8Num++;
                }PlayState.createShopTower8();
                break;
            case 8:
                if(Z >= 6) {
                TowerEntity.createTower9(this.getEngine(), ENTITY_SIZE / 2 + input.getX() - (input.getX() % ENTITY_SIZE), HEIGHT - input.getY());
                Z -= 6;
                t9Num++;
            }PlayState.createShopTower9();
                break;
            case 9:
                if(Z >= 10) {
                TowerEntity.createTower10(this.getEngine(), ENTITY_SIZE / 2 + input.getX() - (input.getX() % ENTITY_SIZE), HEIGHT - input.getY());
                Z -= 10;
                t10Num++;
            }PlayState.createShopTower10();
                break;
        }
        setHighestTower();
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        ShopTowerComponent shopTowerComp = entity.getComponent(ShopTowerComponent.class);

        if(Gdx.input.isKeyPressed(Input.Keys.Q) && shopTowerComp.type == 0){
            shopTowerComp.state = 1;
            grid = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W) && shopTowerComp.type == 1){
            shopTowerComp.state = 1;
            grid = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.E) && shopTowerComp.type == 2){
            shopTowerComp.state = 1;
            grid = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.R) && shopTowerComp.type == 3){
            shopTowerComp.state = 1;
            grid = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.T) && shopTowerComp.type == 4){
            shopTowerComp.state = 1;
            grid = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Y) && shopTowerComp.type == 5){
            shopTowerComp.state = 1;
            grid = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.U) && shopTowerComp.type == 6){
            shopTowerComp.state = 1;
            grid = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.I) && shopTowerComp.type == 7){
            shopTowerComp.state = 1;
            grid = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.O) && shopTowerComp.type == 8){
            shopTowerComp.state = 1;
            grid = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.P) && shopTowerComp.type == 9){
            shopTowerComp.state = 1;
            grid = true;
        }

        if(input.getX() < transformComp.position.x + 16 &&
                input.getX() > transformComp.position.x - 16 &&
                HEIGHT - input.getY() > transformComp.position.y - 16 &&
                HEIGHT - input.getY() < transformComp.position.y + 16){
            switch(shopTowerComp.type) {
                case 0:
                    cost.getComponent(TextComponent.class).text = "X+X";
                    order66(T1DAMAGE,T1BULLET_SPEED,T1ATTACK_SPEED,T1ACCURACY,T1BOUNCE,T1LOCK_ON);
                    isOverTower = true;
                    break;
                case 1:
                    cost.getComponent(TextComponent.class).text = "X+X+X";
                    order66(T2DAMAGE,T2BULLET_SPEED,T1ATTACK_SPEED,T1ACCURACY,T1BOUNCE,T1LOCK_ON);
                    isOverTower = true;
                    break;
                case 2:
                    cost.getComponent(TextComponent.class).text = "X+Y";
                    order66(T2DAMAGE,T3BULLET_SPEED,T1ATTACK_SPEED,T1ACCURACY,T1BOUNCE,T1LOCK_ON);
                    isOverTower = true;
                    break;
                case 3:
                    cost.getComponent(TextComponent.class).text = "X+Z";
                    order66(T1DAMAGE,T4BULLET_SPEED,T4ATTACK_SPEED,T1ACCURACY,T1BOUNCE,T1LOCK_ON);
                    isOverTower = true;
                    break;
                case 4:
                    cost.getComponent(TextComponent.class).text = "Y+Y";
                    order66(T1DAMAGE,T5BULLET_SPEED,T1ATTACK_SPEED,T5ACCURACY,T1BOUNCE,T1LOCK_ON);
                    isOverTower = true;
                    break;
                case 5:
                    cost.getComponent(TextComponent.class).text = "Y+Y+Y";
                    order66(T1DAMAGE,T6BULLET_SPEED,T1ATTACK_SPEED,T1ACCURACY,T6BOUNCE,T1LOCK_ON);
                    isOverTower = true;
                    break;
                case 6:
                    cost.getComponent(TextComponent.class).text = "Y+Z";
                    order66(T1DAMAGE,T7BULLET_SPEED,T7ATTACKSPEED,T1ACCURACY,T1BOUNCE,T7LOCK_ON);
                    isOverTower = true;
                    break;
                case 7:
                    cost.getComponent(TextComponent.class).text = "Z+Z+Z+Z";
                    order66(T8DAMAGE,T8BULLET_SPEED,T8ATTACK_SPEED,T8ACCURACY,T8BOUNCE,T8LOCK_ON);
                    isOverTower = true;
                    break;
                case 8:
                    cost.getComponent(TextComponent.class).text = "6 Z";
                    order66(T9DAMAGE,T9BULLET_SPEED,T8ATTACK_SPEED,T9ACCURACY,T9BOUNCE,T9LOCK_ON);
                    isOverTower = true;
                    break;
                case 9:
                    cost.getComponent(TextComponent.class).text = "10 Z";
                    order66(T10DAMAGE,T10BULLET_SPEED,T8ATTACK_SPEED,T10ACCURACY,T10BOUNCE,T10LOCK_ON);
                    isOverTower = true;
                    break;
            }

        }

        if(shopTowerComp.state == 0){

//            transformComp.position.x = WIDTH - ENTITY_SIZE;
//            transformComp.position.y = HEIGHT - (shopTowerComp.type * SHOP_SPACING) - ENTITY_SIZE;

            if (input.justTouched() &&
                    input.getX() < transformComp.position.x + 16 &&
                    input.getX() > transformComp.position.x - 16 &&
                    HEIGHT - input.getY() > transformComp.position.y - 16 &&
                    HEIGHT - input.getY() < transformComp.position.y + 16){
                shopTowerComp.state = 1;
                PlayState.grid = true;
            }
        }

//        if(entity.getComponent(EnemyComponent.class).costA < A ){
//            A += entity.getComponent(EnemyComponent.class).costA;
//            engine.removeEntity(entity);
//        }
//        else{
//            A -= entity.getComponent(EnemyComponent.class).costA;
//        }
//
//        if(entity.getComponent(EnemyComponent.class).costB < Y){
//            Y += entity.getComponent(EnemyComponent.class).costB;
//            engine.removeEntity(entity);
//        }
//        else{
//            Y -= entity.getComponent(EnemyComponent.class).costB;
//        }
//
//        if(entity.getComponent(EnemyComponent.class).costC < X){
//            X += entity.getComponent(EnemyComponent.class).costC;
//            engine.removeEntity(entity);
//        }
//        else{
//            X -= entity.getComponent(EnemyComponent.class).costC;
//        }
//
//        if(entity.getComponent(EnemyComponent.class).costA >= A &&
//                entity.getComponent(EnemyComponent.class).costB >= Y &&
//                entity.getComponent(EnemyComponent.class).costC >= X){
//            A -= entity.getComponent(EnemyComponent.class).costA;
//            Y -= entity.getComponent(EnemyComponent.class).costB;
//            X -= entity.getComponent(EnemyComponent.class).costC;
//        }

        if(shopTowerComp.state == 1) {

            transformComp.position.x = input.getX();
            transformComp.position.y = HEIGHT - input.getY();

            ImmutableArray<Entity> blocks = this.getEngine().getEntitiesFor(Family.all(BlockComponent.class).get());

            if(input.justTouched() && input.getX() < WIDTH_MINUS && HEIGHT - input.getY() < HEIGHT_MINUS) {
                for (int i = 0; i < blocks.size(); i++) {
                    TransformComponent blockTransformComp = blocks.get(i).getComponent(TransformComponent.class);

                    if (input.getX() < blockTransformComp.position.x + (ENTITY_SIZE / 2) + 1 &&
                            input.getX() > blockTransformComp.position.x - (ENTITY_SIZE / 2) - 1 &&
                            DOKN.HEIGHT - input.getY() < blockTransformComp.position.y + (ENTITY_SIZE / 2) + 1 &&
                            DOKN.HEIGHT - input.getY() > blockTransformComp.position.y - (ENTITY_SIZE / 2) - 1 &&
                            blocks.get(i) != entity) {
                        numBlocks += 1;
                    }
                }

                if (numBlocks == 0) {
                    transformComp.position.x = ENTITY_SIZE / 2 + input.getX() - (input.getX() % ENTITY_SIZE);
                    transformComp.position.y = HEIGHT - input.getY();
                    this.getEngine().removeEntity(entity);
                    createTower(entity);
                    grid = false;
                }
                numBlocks = 0;
            }
        }
    }
}
