package com.mygdx.game.base;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.DOKN;
import com.mygdx.game.components.*;
import com.mygdx.game.entities.BlockEntity;
import com.mygdx.game.entities.BulletEntity;
import com.mygdx.game.entities.EnemyEntity;
import com.mygdx.game.entities.TextEntity;
import com.mygdx.game.systems.*;

// import javax.xml.soap.Text;

import static com.badlogic.gdx.Gdx.input;
import static com.mygdx.game.DOKN.HEIGHT;
import static com.mygdx.game.DOKN.WIDTH;

public class PlayState extends GameState {

    public static final float deltaTime = 1/10f;
    public static final float GRAVITY = -6 ;
    public static final float JUMP = 40;
    public static final float ENEMY_SPEED = 6;
    public static final float WIDTH_MINUS = WIDTH - 64;

    public static final int HEALTH_BAR_THICKNESS = 5;
    public static final int HALF_BULLET_SIZE = 8;
    public static final int ACCURACY_GAP = 50;
    public static final int MAX_ACCURACY = 10;
    public static final int ENTITY_SIZE = 32;
    public static final int HEIGHT_MINUS = HEIGHT - 96;
    public static final int RandomCeil = 100;
    public static final int WAVE_TIME_INITIAL = 170;

    public static final int T1DAMAGE = 10;
    public static final int T1ATTACK_SPEED = 1;
    public static final int T7ATTACKSPEED = T1ATTACK_SPEED * 2;
    public static final int T1ACCURACY = 0;
    public static final int T1BOUNCE = 1;
    public static final int T1LOCK_ON = 7;
    public static final int T7LOCK_ON = T1LOCK_ON - 7;
    public static final int T2DAMAGE = T1DAMAGE * 2;
    public static final int T1BULLET_SPEED = 40;
    public static final int T2BULLET_SPEED = T1BULLET_SPEED + 2;
    public static final int T3BULLET_SPEED = T1BULLET_SPEED * 2;
    public static final int T4BULLET_SPEED = T1BULLET_SPEED + 4;
    public static final int T5BULLET_SPEED = T1BULLET_SPEED + 6;
    public static final int T6BULLET_SPEED = T1BULLET_SPEED + 8;
    public static final int T7BULLET_SPEED = T1BULLET_SPEED + 10;
    public static final int T8BULLET_SPEED = T1BULLET_SPEED + 30;
    public static final int T9BULLET_SPEED = T1BULLET_SPEED + 40;
    public static final int T10BULLET_SPEED = T1BULLET_SPEED + 60;
    public static final int T4ATTACK_SPEED = T1ATTACK_SPEED * 3;
    public static final int T5ACCURACY = 10;
    public static final int T6BOUNCE = T1BOUNCE * 10;
    public static final int T8DAMAGE = T1DAMAGE * 5;
    public static final int T9DAMAGE = T1DAMAGE * 7;
    public static final int T10DAMAGE = T1DAMAGE * 9;
    public static final int T8ATTACK_SPEED = T1ATTACK_SPEED * 6;
    public static final int T8ACCURACY = T1ACCURACY + 4;
    public static final int T9ACCURACY = T1ACCURACY + 7;
    public static final int T10ACCURACY = T1ACCURACY + 10;
    public static final int T8BOUNCE = T1BOUNCE * 5;
    public static final int T9BOUNCE = T1BOUNCE * 8;
    public static final int T10BOUNCE = T1BOUNCE * 12;
    public static final int T8LOCK_ON = T1LOCK_ON - 2;
    public static final int T9LOCK_ON = T1LOCK_ON - 4;
    public static final int T10LOCK_ON = T1LOCK_ON - 6;
    public static final int TRADE1 = 4;
    public static final int TRADE2 = 1;
    public static final int TRADE3 = 7;
    public static final int TRADE4 = 1;
    public static final int TRADE_GAIN1 = 1;
    public static final int TRADE_GAIN2 = 3;
    public static final int TRADE_GAIN3 = 1;
    public static final int TRADE_GAIN4 = 4;

    private static final int SHOP_SPACING = 46;
    private static final int BLOCK_SPAN = (int) (WIDTH_MINUS/ENTITY_SIZE);

    public static final String tGrey = "sprites/towers/tGrey.png";
    public static final String tRed = "sprites/towers/tRed1.png";
    public static final String tBlue = "sprites/towers/tBlue1.png";
    public static final String tCyan = "sprites/towers/tCyan1.png";
    public static final String tGreen = "sprites/towers/tGreen1.png";
    public static final String tOrange = "sprites/towers/tOrange1.png";
    public static final String tPBlue = "sprites/towers/tPBlue1.png";
    public static final String tPink = "sprites/towers/tPink1.png";
    public static final String tPurple = "sprites/towers/tPurple1.png";
    public static final String tYellow = "sprites/towers/tYellow1.png";
    public static final String tYGreen = "sprites/towers/tYGreen1.png";
    public static final String eHurt = "sprites/enemies/eHurt.png";
//    public static final int


    public static int lives = 9;
    public static int Z = 0;
    public static int Y = 0;
    public static int X;
    public static int t1Num;
    public static int t2Num;
    public static int t3Num;
    public static int t4Num;
    public static int t5Num;
    public static int t6Num;
    public static int t7Num;
    public static int t8Num;
    public static int t9Num;
    public static int t10Num;
    public static int wave = 1;
    public static int mostT;
    private static int time;
    private static int time2 = 100;

    private int timeSwitch = 1;
    private int timer = 1500;
    private int timer2 = 0;
    private int timerWave = 0;
    private int timeWave = 1100;
    private int timeBlast = 100;
    private int timerBlast = 0;
    private int timerTimerBlast = 0;
    private int blastX;
    private static int timeTimeBlast =  5;
    private static int blastZoneSize = 16;
    private static int blastDamage = 1;
    private static int blastBbounce = 2;
    private static int blastSpeed = 0;
    private static int blasttype = 0;
    private static int enemyCreate;
    private static int enemyCreate2;

    public static boolean spawn = false;
    public static boolean blastMode = false;
    public static boolean blastLines = false;
    public static boolean grid = false;
    public static boolean showBulletSpeed = false;
    public static boolean showAttackSpeed = false;
    public static boolean showAccuracy = false;
    public static boolean showBounce = false;
    public static boolean showLockOn = false;
    public static boolean showX = false;
    public static boolean showY = false;
    public static boolean showZ = false;

    private ShapeRenderer sr = new ShapeRenderer();
    private Entity livesText = TextEntity.create(engine,DOKN.WIDTH - 4 * ENTITY_SIZE, DOKN.HEIGHT - ENTITY_SIZE/3);
    private Entity wavesText = TextEntity.create(engine,DOKN.WIDTH - 4 * ENTITY_SIZE, DOKN.HEIGHT - ENTITY_SIZE * 4 / 3 );
    private Entity CText ;

    private static Entity blastID = null;
    public static Entity AText;
    public static Entity BText;
    public static Entity cost;
    public static Entity statDamage;
    public static Entity statBulletSpeed;
    public static Entity statAttackSpeed;
    public static Entity statAccuracy;
    public static Entity statBounce;
    public static Entity statLockOn;

    public static Entity trade1 = null;
    public static Entity trade2 = null;
    public static Entity trade3 = null;
    public static Entity trade4 = null;

    private static Engine engine;

//    public timer wavetimertimer = new timer(1200);

    public static int randRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        engine = new Engine();
        cost = TextEntity.create(engine, 0, DOKN.HEIGHT - ENTITY_SIZE/3);
        statDamage = TextEntity.create(engine, 5 * ENTITY_SIZE, DOKN.HEIGHT - ENTITY_SIZE/3);
        statBulletSpeed = TextEntity.create(engine, 8 * ENTITY_SIZE, DOKN.HEIGHT - ENTITY_SIZE/3);
        statAttackSpeed = TextEntity.create(engine, 11 * ENTITY_SIZE, DOKN.HEIGHT - ENTITY_SIZE/3);
        statAccuracy = TextEntity.create(engine,  14 * ENTITY_SIZE, DOKN.HEIGHT - ENTITY_SIZE/3);
        statBounce = TextEntity.create(engine, 17 * ENTITY_SIZE, DOKN.HEIGHT - ENTITY_SIZE/3);
        statLockOn = TextEntity.create(engine, 20 * ENTITY_SIZE, DOKN.HEIGHT - ENTITY_SIZE/3);
        BText = TextEntity.create(engine, DOKN.WIDTH - 6 * ENTITY_SIZE, DOKN.HEIGHT - ENTITY_SIZE * 4/3);
        AText = TextEntity.create(engine, DOKN.WIDTH - 6 * ENTITY_SIZE, DOKN.HEIGHT - ENTITY_SIZE/3);
        CText = TextEntity.create(engine, DOKN.WIDTH - 9 * ENTITY_SIZE, DOKN.HEIGHT - ENTITY_SIZE/3);
        showX = true;

        X = 2;
        Y = 0;
        Z = 0;

        enemyCreate = enemyCreate2 = 0;
        t1Num = t2Num = t3Num = t4Num = t5Num = t6Num = t7Num = t8Num = t9Num = t10Num = 0;
        mostT = 1;

        addSystems();

        for(int i = 0; i < BLOCK_SPAN; i++){
            BlockEntity.create(engine, ENTITY_SIZE * i + ENTITY_SIZE /2, DOKN.HEIGHT);
        }

        createShop();
        createShopTower();
        createBackground();
        createPlay();

    }

    private void addSystems(){

        engine.addSystem(new TextureSystem());
        engine.addSystem(new PhysicsSystem());
        engine.addSystem(new ShopTowerSystem());
        engine.addSystem(new BlockSystem());
        engine.addSystem(new TowerSystem());
        engine.addSystem(new EnemySystem());
        engine.addSystem(new BulletSystem());
        engine.addSystem(new HitBoxSystem());
        engine.addSystem(new TextSystem());
        engine.addSystem(new RemoveSystem());
        engine.addSystem(new WalkingSystem());
        engine.addSystem(new RectDrawSystem());
        engine.addSystem(new TiledSystem());
        engine.addSystem(new AASystem());
        engine.addSystem(new PlaySystem());
        engine.addSystem(new TradeSystem());
    }

    public Entity createShop(){
        Entity entity = new Entity();
        entity.add(new TransformComponent());
        entity.add(new RectComponent());
        entity.add(new TextureComponent());
        entity.getComponent(TransformComponent.class).position.x = WIDTH - ENTITY_SIZE;
        entity.getComponent(TransformComponent.class).position.y = DOKN.HEIGHT/2;
        entity.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/shop.png"));
        entity.getComponent(RectComponent.class).color = entity.getComponent(RectComponent.class).GRAY;
        entity.getComponent(RectComponent.class).rect = new Rectangle(entity.getComponent(TransformComponent.class).position.x - ENTITY_SIZE, entity.getComponent(TransformComponent.class).position.x - DOKN.HEIGHT/2, 64, DOKN.HEIGHT);
        engine.addEntity(entity);
        return entity;
    }

    public static Entity createTextTower(Engine engine,Texture texture, int type){
        Entity TextTower = new Entity();
        TextTower.add(new TextureComponent());
        TextTower.add(new TransformComponent());
        TextTower.add(new ShopTowerComponent());
        TextTower.getComponent(TextureComponent.class).texture = texture;
        TextTower.getComponent(ShopTowerComponent.class).type = type;
        TextTower.getComponent(TransformComponent.class).position.set(WIDTH - ENTITY_SIZE, HEIGHT - ((type * SHOP_SPACING) + 30));
        TextTower.getComponent(ShopTowerComponent.class).state = 0;
        engine.addEntity(TextTower);
//        TowerStatsText.create(engine, TextTower.getComponent(TransformComponent.class).position.x - 12, TextTower.getComponent(TransformComponent.class).position.y + 36, Integer.toString(cost), false, false);
        return TextTower;
    }
    public static Entity createShopTower(){
        Entity TextTower = createTextTower(engine,new Texture(Gdx.files.internal(tGrey)), 0);
        return TextTower;
    }
    public static Entity createShopTower2(){
        Entity TextTower = createTextTower(engine, new Texture(Gdx.files.internal(tRed)), 1);
        return TextTower;
    }
    public static Entity createShopTower3(){
        Entity TextTower = createTextTower(engine, new Texture(Gdx.files.internal(tOrange)), 2);
        return TextTower;
    }
    public static Entity createShopTower4(){
        Entity TextTower = createTextTower(engine, new Texture(Gdx.files.internal(tYellow)), 3);
        return TextTower;
    }
    public static Entity createShopTower5(){
        Entity TextTower = createTextTower(engine, new Texture(Gdx.files.internal(tYGreen)), 4);
        return TextTower;
    }
    public static Entity createShopTower6(){
        Entity TextTower = createTextTower(engine, new Texture(Gdx.files.internal(tGreen)), 5);
        return TextTower;
    }
    public static Entity createShopTower7(){
        Entity TextTower = createTextTower(engine, new Texture(Gdx.files.internal(tCyan)), 6);
        return TextTower;
    }
    public static Entity createShopTower8(){
        Entity TextTower = createTextTower(engine, new Texture(Gdx.files.internal(tBlue)), 7);
        return TextTower;
    }
    public static Entity createShopTower9(){
        Entity TextTower = createTextTower(engine, new Texture(Gdx.files.internal(tPBlue)), 8);
        return TextTower;
    }
    public static Entity createShopTower10(){
        Entity TextTower = createTextTower(engine, new Texture(Gdx.files.internal(tPurple)), 9);
        return TextTower;
    }
    public static Entity createShopTower11() {
        Entity entity = createTextTower(engine, new Texture(Gdx.files.internal("sprites/towers/bulletLazerBeam.png")), 10);
        entity.remove(ShopTowerComponent.class);
        entity.add(new AAComponent());
        entity.add(new RectComponent());
        entity.getComponent(RectComponent.class).rect = new Rectangle(0, DOKN.HEIGHT, ENTITY_SIZE, ENTITY_SIZE);
        entity.getComponent(TransformComponent.class).position.set(DOKN.WIDTH - 11 * ENTITY_SIZE, DOKN.HEIGHT - ENTITY_SIZE);
        return entity;
    }
    public static Entity createScore(Engine engine, int type, float x, float y){
        Entity entity = new Entity();
        entity.add(new TransformComponent());
        entity.add(new RemoveComponent());
        entity.add(new TextComponent());
        entity.add(new AccelerationComponent());

        //MathUtils.random( 5f, 5.5f );
        entity.getComponent(AccelerationComponent.class).acceleration = -1;
        entity.getComponent(TransformComponent.class).position.x = x;
        entity.getComponent(TransformComponent.class).position.y = y;
        switch(type){
            case 0: entity.getComponent(TextComponent.class).text = "+X";
                entity.getComponent(RemoveComponent.class).time = 4f;
                entity.getComponent(TransformComponent.class).dy = 40;break;
            case 1: entity.getComponent(TextComponent.class).text = "+Y";
                entity.getComponent(RemoveComponent.class).time = 5.5f;
                entity.getComponent(TransformComponent.class).dy = 50;break;
            case 2: entity.getComponent(TextComponent.class).text = "+Z";
                entity.getComponent(RemoveComponent.class).time = 7f;
                entity.getComponent(TransformComponent.class).dy = 60;break;
        }
        engine.addEntity(entity);
        return entity;
    }
    public Entity createPlay(){
        Entity TextTower = new Entity();
        TextTower.add(new TextureComponent());
        TextTower.add(new TransformComponent());
        TextTower.add(new RectComponent());
        TextTower.add(new PlayComponent());
        TextTower.getComponent(TextureComponent.class).texture = new Texture(Gdx.files.internal("sprites/play.png"));
        TextTower.getComponent(TransformComponent.class).position.set(WIDTH - ENTITY_SIZE * 17 / 2, HEIGHT - ENTITY_SIZE * 2);
        TextTower.getComponent(RectComponent.class).rect = new Rectangle(WIDTH - ENTITY_SIZE * 5, HEIGHT - ENTITY_SIZE * 3,ENTITY_SIZE,ENTITY_SIZE);
        engine.addEntity(TextTower);
//        TowerStatsText.create(engine, TextTower.getComponent(TransformComponent.class).position.x - 12, TextTower.getComponent(TransformComponent.class).position.y + 36, Integer.toString(cost), false, false);
        return TextTower;
    }
    public Entity createBackground(){
        Entity entity = new Entity();
        entity.add(new TiledComponent());
        engine.addEntity(entity);
        return entity;
    }
    public static Entity createShopButton(Vector2 xy, int type){
        Entity entity = new Entity();
        entity.add(new RectComponent());
        entity.add(new RectDrawComponent());
        entity.add(new TransformComponent());
        entity.add(new TradeComponent());
        Vector2 position = entity.getComponent(TransformComponent.class).position.set(xy);
        entity.getComponent(RectComponent.class).rect = new Rectangle(position.x -6, position.y-6, 12, 12);
        entity.getComponent(RectComponent.class).color = entity.getComponent(RectComponent.class).BLACK;
        entity.getComponent(TradeComponent.class).type = type;
        engine.addEntity(entity);
        return entity;
    }

    public void switchEnemy(){
        switch(enemyCreate){
            case 0: EnemyEntity.create(engine, wave);break;
            case 1: EnemyEntity.createFast(engine, wave);break;
            case 2: EnemyEntity.createFlying(engine, wave);break;
            case 3: EnemyEntity.createJump(engine, wave);break;
        }
    }
    public void switchEnemy2(){
        switch(enemyCreate2){
            case 0: EnemyEntity.create(engine, wave);break;
            case 1: EnemyEntity.createFast(engine, wave);break;
            case 2: EnemyEntity.createFlying(engine, wave);break;
            case 3: EnemyEntity.createJump(engine, wave);break;
        }
    }
    public static void setEnemyCreate(){
        switch(wave){
            case 1: enemyCreate = 0;break;
            case 4: enemyCreate = 1;break;
            case 8: enemyCreate = 2;break;
            case 12: enemyCreate = 3;break;
            case 16: enemyCreate = 0;break;
            case 20: enemyCreate = 1;break;
            case 24: enemyCreate = 2;break;
            case 28: enemyCreate = 3;break;
            case 32: enemyCreate = 0;break;
            case 36: enemyCreate = 1;break;
            case 40: enemyCreate = 2;break;
            case 44: enemyCreate = 3;break;
            case 48: enemyCreate = 0;break;
            case 52: enemyCreate = 1;break;
            case 56: enemyCreate = 2;break;
            case 60: enemyCreate = 3;break;
            case 64: enemyCreate = 0;break;
            case 68: enemyCreate = 1;break;
            case 72: enemyCreate = 2;break;
            case 76: enemyCreate = 3;break;
            case 80: enemyCreate = 0;break;
            case 84: enemyCreate = 1;break;
            case 88: enemyCreate = 2;break;
            case 92: enemyCreate = 3;break;
            case 96: enemyCreate = 0;break;
            case 100: enemyCreate = 1;break;
        }
    }
    public static void setEnemyCreate2(){
        switch(wave){
            case 1: enemyCreate2 = 0;break;
            case 4: enemyCreate2 = 1;break;
            case 8: enemyCreate2 = 2;break;
            case 12: enemyCreate2 = 3;break;
            case 16: enemyCreate2 = 0;break;
            case 18: enemyCreate2 = 1;break;
            case 22: enemyCreate2 = 2;break;
            case 26: enemyCreate2 = 3;break;
            case 30: enemyCreate2 = 0;break;
            case 34: enemyCreate2 = 2;break;
            case 38: enemyCreate2 = 3;break;
            case 42: enemyCreate2 = 0;break;
            case 46: enemyCreate2 = 1;break;
            case 50: enemyCreate2 = 2;break;
            case 54: enemyCreate2 = 0;break;
            case 58: enemyCreate2 = 1;break;
            case 62: enemyCreate2 = 2;break;
            case 66: enemyCreate2 = 1;break;
            case 70: enemyCreate2 = 2;break;
            case 74: enemyCreate2 = 1;break;
            case 78: enemyCreate2 = 2;break;
            case 82: enemyCreate2 = 1;break;
            case 86: enemyCreate2 = 2;break;
            case 90: enemyCreate2 = 3;break;
            case 94: enemyCreate2 = 2;break;
            case 98: enemyCreate2 = 3;break;
        }
    }
    public static void createBlastLines(){
        if(!blastMode){
            blastLines = true;
        }
    }
    public static void blastSpecs(int blastdamage, int blastspeed, int timetimeblast, int blastzonesize, Entity blastid, int blastbounce, int type){
        timeTimeBlast = timetimeblast;
        blastDamage = blastdamage;
        blastSpeed = blastspeed;
        blastZoneSize = blastzonesize;
        blastID = blastid;
        blastBbounce = blastbounce;
        blasttype = type;
    }
    public static void setHighestTower(){
        compareTowers(t10Num, 10);
        compareTowers(t9Num, 9);
        compareTowers(t8Num, 8);
        compareTowers(t7Num, 7);
        compareTowers(t6Num, 6);
        compareTowers(t5Num, 5);
        compareTowers(t4Num, 4);
        compareTowers(t3Num, 3);
        compareTowers(t2Num, 2);
        compareTowers(t1Num, 1);

        switch(mostT){
            case 1: blastSpecs(wave,0,2,ENTITY_SIZE + 8,null,2, 0);break;
            case 2: blastSpecs(wave * 3,0,2,ENTITY_SIZE + 8,null,2,1);break;
            case 3: blastSpecs(wave,80,2,ENTITY_SIZE + 8,null,2,2);break;
            case 4: blastSpecs(wave,0,0,ENTITY_SIZE + 8,null,2,3);break;
            case 5: blastSpecs(wave,0,2,0,null,2, 4);break;
            case 6: blastSpecs(wave,0,2,ENTITY_SIZE + 8,null,0, 5);break;
            case 7: blastSpecs(wave,0,2,ENTITY_SIZE + 8,null,2, 6);break;
            case 8: blastSpecs(wave * 3/2,20,2,ENTITY_SIZE/2,null,5, 7);break;
            case 9: blastSpecs(wave * 2,40,1,ENTITY_SIZE/4,null,8, 8);break;
            case 10: blastSpecs(wave * 3,60,0,4,null,11, 9);break;
        }
    }
    private static void compareTowers(int tower, int type){
        if(tower >= t1Num && tower >= t2Num && tower >= t3Num && tower >= t4Num && tower >= t5Num && tower >= t6Num && tower >= t7Num && tower >= t8Num && tower >= t9Num && tower >= t10Num){
            mostT = type;
        }
    }
    public static void nextWave(){

//            EnemyEntity.create(engine,wave);
        spawn = true;

        if(wave == 1) {
            time = WAVE_TIME_INITIAL;
        }
        if(wave >= 3){
            time = WAVE_TIME_INITIAL - 100 - (wave / 2);
        }

        switch(wave){
            case 1: time2 = time; break;
            case 2:createShopTower2();break;
            case 10: time2 = time /2;createShopTower11();createShopTower6();showBounce = true;break;
            case 4:createShopTower3();showBulletSpeed = true;break;
            case 6:createShopTower4();showAttackSpeed = true;break;
            case 20: time2 = time / 2;break;
            case 8:createShopTower5();showAccuracy = true;break;
            case 30: time2 = time / 3;break;
            case 12:createShopTower7();showLockOn = true;break;
            case 40: time2 = time / 3;break;
            case 14:createShopTower8();break;
            case 16:createShopTower9();break;
            case 50: time2 = time;break;
            case 18:createShopTower10();break;
            case 60: time2 = time / 4;break;
            case 70: time2 = time / 5;break;
            case 80: time2 = time / 5;break;
            case 90: time2 = time;break;
            case 100: time2 = time/6;break;
        }
        setEnemyCreate();
        setEnemyCreate2();
    }
    private void checkSpawn(){
        if(spawn){
            timerWave += 1;
            if(timeSwitch == 1){
                timer += 1;
                if(time < timer){
                    switchEnemy();
//                    switch(enemyCreate){
//                        case 0: EnemyEntity.create(engine, wave);break;
//                        case 1: EnemyEntity.createFast(engine, wave);break;
//                        case 2: EnemyEntity.createFlying(engine, wave);break;
//                        case 3: EnemyEntity.createJump(engine, wave);break;
//                    }
                    timer = 0;
                    timeSwitch = 2;
                }
            }
            else{
                timer2 += 1;
                if(time2 < timer2){
                    switchEnemy2();
//                    switch(enemyCreate){
//                        case 0: EnemyEntity.create(engine, wave);break;
//                        case 1: EnemyEntity.createFast(engine, wave);break;
//                        case 2: EnemyEntity.createFlying(engine, wave);break;
//                        case 3: EnemyEntity.createJump(engine, wave);break;
//                    }
                    timer2 = 0;
                    timeSwitch = 1;
                }
            }

            if(timeWave < timerWave){
                timerWave = 0;
                wave++;
                spawn = false;
                timer = 1000;
                timer2 = 0;
                timeSwitch = 1;
                X++;
            }

//            if(wavetimertimer.event){
//                wave++;
//                spawn = false;
//                timeSwitch = 1;
//                wavetimertimer.event = false;
//            }
        }
    }

    public static void tradeX(){
        X -= TRADE1;
        Y+=TRADE_GAIN1;
    }
    public static void tradeY(){
        Y -= TRADE2;
        X += TRADE_GAIN2;
    }
    public static void tradeY2(){
        Y -= TRADE3;
        Z+= TRADE_GAIN3;
    }
    public static void tradeZ(){
        Z-=TRADE4;
        Y += TRADE_GAIN4;
    }

//    public class timer {
//
//        int timerTimer;
//        int timeTime;
//        boolean event;
//
//        timer(int counter) {
//            timerTimer = 0;
//            timeTime = counter;
//            boolean event = false;
//        }
//
//        public void update(float dt){
//            timerTimer++;
//            if(timeTime<timerTimer)
//            {
//                event = true;
//            }
//        }
//    }


    @Override
    public void update(float dt) {
        engine.update(deltaTime);
//        wavetimertimer.update(dt);
        handleInput();
        checkSpawn();

        if(lives < 1){
            gsm.setState(GameStateManager.State.MENU);
            lives = wave;
            wave = 1;
        }

        if(blastMode) {
            timerBlast++;
            timerTimerBlast++;
            if(timeTimeBlast < timerTimerBlast) {
                BulletEntity.create(engine, randRange(blastX - blastZoneSize,blastX + blastZoneSize), HEIGHT, blastDamage, null, randRange(80 + blastSpeed,90 + blastSpeed) , blastBbounce, blasttype);
                timerTimerBlast = 0;
            }
            if(timeBlast < timerBlast){
                timerBlast = 0;
                blastMode = false;
            }
        }

        livesText.getComponent(TextComponent.class).text = Integer.toString(lives) + "L";
        wavesText.getComponent(TextComponent.class).text = Integer.toString(wave) + "W";
        if(showX) {
            CText.getComponent(TextComponent.class).text = Integer.toString(X) + "X";
        }
        if(showY){
            BText.getComponent(TextComponent.class).text = Integer.toString(Y) + "Y";
        }
        if(showZ){
            AText.getComponent(TextComponent.class).text = Integer.toString(Z) + "Z";
        }
    }

    @Override
    public void draw() {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.BLACK);
        sr.line(0, HEIGHT_MINUS, WIDTH_MINUS, HEIGHT_MINUS);
        if(grid){
            sr.setColor(Color.DARK_GRAY);
            for(int i = 0; i < (int) (WIDTH_MINUS/ENTITY_SIZE ); i++){
                sr.line(i * ENTITY_SIZE , 0, i * ENTITY_SIZE, WIDTH_MINUS);
            }
        }
        if(blastLines) {
            sr.setColor(Color.RED);
            sr.line(input.getX() - 8, HEIGHT, input.getX() - 8, ENTITY_SIZE);
            sr.line(input.getX() + 8, HEIGHT, input.getX() + 8, ENTITY_SIZE);
        }
        sr.end();
    }

    @Override
    public void handleInput()    {

//        if(input.justTouched()) {
//            for (int i = 0; i < blocks.size(); i++) {
//                TransformComponent blockTransformComp = blocks.get(i).getComponent(TransformComponent.class);
//
//                if (input.getX() < blockTransformComp.position.x + (ENTITY_SIZE/2) + 1 &&
//                        input.getX() > blockTransformComp.position.x - (ENTITY_SIZE/2) - 1 &&
//                        DOKN.HEIGHT - input.getY() < blockTransformComp.position.y + (ENTITY_SIZE/2) + 1 &&
//                        DOKN.HEIGHT - input.getY() > blockTransformComp.position.y - (ENTITY_SIZE/2) - 1) {
//                    numBlocks += 1;
//                }
//            }
//
//            if(numBlocks == 0) {
//                TowerEntity.createBasic(engine, ENTITY_SIZE/2 + input.getX()- (input.getX() % ENTITY_SIZE), HEIGHT - input.getY());
//            }
//            numBlocks = 0;
//        }

//        if(GameKeys.isPressed(GameKeys.A)){}
        if(Gdx.input.isKeyPressed(Input.Keys.Z) && showBounce) {createBlastLines();}

        if(blastLines) {
            if ((input.justTouched() && HEIGHT - input.getY() < HEIGHT_MINUS && X >= 1 || input.justTouched() && input.getX() > WIDTH - ENTITY_SIZE * 10 && X >= 1)) {
                blastMode = true;
                blastLines = false;
                blastX = input.getX();
                X--;
            }
            else if(input.justTouched() && HEIGHT - input.getY() > HEIGHT_MINUS  && input.getX() < WIDTH - ENTITY_SIZE * 12 || X < 1 ){
                blastLines = false;
            }
        }

        if(GameKeys.isPressed(GameKeys.SPACE) && !spawn){nextWave();}

        if(Gdx.input.isKeyJustPressed(Input.Keys.A) && X >= TRADE1){
            tradeX();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S) && Y >= TRADE2){
            tradeY();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D) && Y >= TRADE3){
            tradeY2();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F) && Z >= TRADE4){
            tradeZ();
        }
    }

    @Override
    public void dispose() {

    }
}
