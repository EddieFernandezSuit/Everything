package com.defensetower.game.gamestates;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.defensetower.game.MainGame;
import com.defensetower.game.components.*;
import com.defensetower.game.entities.EnemyEntity;
import com.defensetower.game.entities.TowerStatsText;
import com.defensetower.game.managers.GameKeys;
import com.defensetower.game.managers.GameStateManager;
import com.defensetower.game.systems.*;
// import org.w3c.dom.css.Rect;
// import sun.applet.Main;

import static com.defensetower.game.MainGame.cam;


public class PlayState extends GameState {

    private final int HEALTH = 0;
    private final int RATE = 1;
    private final int SPEED = 2;

    public static int score = 15;
    public static int basicCost = 10;
    public static int fastCost = 20;
    public static int damageCost = 20;
    public static int rangeCost = 20;
    public static int speedCost = 20;
    public static int lives = 20;
    public static int level = 0;

    private int enemySpeed;
    private int enemyHealth;
    private int spawnLength;

    private float spawnRate;
    private float np = 0;
    private float spawnTimer = 0;
    private boolean spawn = false;
    Entity levelText;

    private Engine engine;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {

        engine = new Engine();
        engine.addSystem(new RectSystem());
        engine.addSystem(new TextureSystem());
        engine.addSystem(new EnemySystem());
        engine.addSystem(new TowerSystem());
        engine.addSystem(new BulletSystem());
        engine.addSystem(new MotionSystem());
        engine.addSystem(new HitBoxSystem());
        engine.addSystem(new RadiusSystem());
        engine.addSystem(new TextTowerSystem());
        engine.addSystem(new ScoreSystem());
        engine.addSystem(new RemoveSystem());
        engine.addSystem(new TextSystem());
        engine.addSystem(new UpgradeSystem());
        engine.addSystem(new TiledSystem());
        engine.addSystem(new livesSystem());

        createShop();
        createBasicTextTower();
        createFastTextTower();
        createDamageTextTower();
        createRangeTextTower();
        createSpeedTextTower();
        createScoreText();
        createLivesText();
        createBackground();

        levelText = createLevelText();

    }

    public Entity createTextTower(Texture texture, float invertY, int type, int cost){
        Entity TextTower = new Entity();
        TextTower.add(new TextureComponent());
        TextTower.add(new TransformComponent());
        TextTower.add(new TextTowerComponent());
        TextTower.getComponent(TextureComponent.class).texture = texture;
        TextTower.getComponent(TransformComponent.class).position.set(MainGame.WIDTH - 32, MainGame.HEIGHT - invertY);
        TextTower.getComponent(TextTowerComponent.class).type = type;
        engine.addEntity(TextTower);

        TowerStatsText.create(engine, TextTower.getComponent(TransformComponent.class).position.x - 12, TextTower.getComponent(TransformComponent.class).position.y + 36, Integer.toString(cost), false, false);
        return TextTower;
    }
    public Entity createBasicTextTower(){
        Entity TextTower = createTextTower(new Texture(Gdx.files.internal("sprites/Player.png")), 60, 0, basicCost);
        return TextTower;
    }
    public Entity createFastTextTower(){
        Entity TextTower = createTextTower(new Texture(Gdx.files.internal("sprites/redPlayer.png")), 124 , 1, fastCost);
        return TextTower;
    }
    public Entity createDamageTextTower(){
        Entity TextTower = createTextTower(new Texture(Gdx.files.internal("sprites/yellowPlayer.png")), 188, 2, damageCost);
        return TextTower;
    }
    public Entity createRangeTextTower(){
        Entity TextTower = createTextTower(new Texture(Gdx.files.internal("sprites/greenPlayer.png")), 252, 3, rangeCost);
        return TextTower;
    }
    public Entity createSpeedTextTower(){
        Entity TextTower = createTextTower(new Texture(Gdx.files.internal("sprites/torqPlayer.png")), 316, 4, speedCost);
        return TextTower;
    }
    public Entity createScoreText(){
        Entity entity = new Entity();
        entity.add(new TransformComponent());
        entity.add(new TextComponent());
        entity.add(new ScoreComponent());
        entity.getComponent(TransformComponent.class).position.set(MainGame.WIDTH - 128, MainGame.HEIGHT - 64);
        entity.getComponent(TextComponent.class).type = 0;
        engine.addEntity(entity);
        return entity;
    }
    public Entity createLivesText(){
        Entity entity = new Entity();
        entity.add(new TransformComponent());
        entity.add(new TextComponent());
        entity.add(new livesComponent());
        entity.getComponent(TransformComponent.class).position.set(MainGame.WIDTH - 128, MainGame.HEIGHT - 96);
        entity.getComponent(TextComponent.class).type = 0;
        engine.addEntity(entity);
        return entity;
    }
    public Entity createLevelText(){
        Entity entity = new Entity();
        entity.add(new TransformComponent());
        entity.add(new TextComponent());
        entity.getComponent(TransformComponent.class).position.set(MainGame.WIDTH - 128, MainGame.HEIGHT - 32);
        entity.getComponent(TextComponent.class).text = Integer.toString(level);
        entity.getComponent(TextComponent.class).type = 0;
        engine.addEntity(entity);
        return entity;
    }
    public Entity createBackground(){
        Entity entity = new Entity();
        entity.add(new TiledComponent());
        engine.addEntity(entity);
        return entity;
    }
    public Entity createShop(){
        Entity upBox = new Entity();
        upBox.add(new TransformComponent());
        upBox.add(new RectComponent());
        upBox.getComponent(TransformComponent.class).position.x = MainGame.WIDTH - 32;
        upBox.getComponent(TransformComponent.class).position.y = MainGame.HEIGHT/2;
        upBox.getComponent(RectComponent.class).color = upBox.getComponent(RectComponent.class).GRAY;
        upBox.getComponent(RectComponent.class).rect = new Rectangle(upBox.getComponent(TransformComponent.class).position.x - 32, upBox.getComponent(TransformComponent.class).position.x - MainGame.HEIGHT/2, 64, MainGame.HEIGHT);
        engine.addEntity(upBox);
        return upBox;
    }
    public static Entity createUpgradeBox(Engine engine, float x, float y, Entity tower){
        Entity upBox = new Entity();
        upBox.add(new TransformComponent());
        upBox.add(new RectComponent());
        upBox.add(new UpgradeComponent());
        upBox.getComponent(TransformComponent.class).position.x = x;
        upBox.getComponent(TransformComponent.class).position.y = y;
        upBox.getComponent(RectComponent.class).color = upBox.getComponent(RectComponent.class).BLUE;
        upBox.getComponent(RectComponent.class).rect = new Rectangle(upBox.getComponent(TransformComponent.class).position.x - 20, upBox.getComponent(TransformComponent.class).position.y - 20, 40, 40);
        upBox.getComponent(UpgradeComponent.class).tower = tower;

        engine.addEntity(upBox);
        return upBox;
    }
    public static Entity createTowerLevel(Engine engine, float x, float y){
        Entity entity = new Entity();
        entity.add(new RectComponent());
        entity.add(new TransformComponent());
        entity.getComponent(TransformComponent.class).position.x = x;
        entity.getComponent(TransformComponent.class).position.y = y;
        entity.getComponent(RectComponent.class).rect = new Rectangle(x - 3, y - 3, 6, 6);
        entity.getComponent(RectComponent.class).color = entity.getComponent(RectComponent.class).BLACK;

        engine.addEntity(entity);
        return entity;
    }

    private void newWave(){


        spawnLength = ( level/2) + 10;
        switch(level){
            case 1: setWave(.5f ,1 , 1, HEALTH); //.5
                break;
            case 2:setWave(.5f ,1, 2, HEALTH); //1
                break;
            case 3:setWave(.5f, 1, 3, HEALTH); //1.5
                break;
            case 4:setWave(1, 1, 4, RATE); //3
                break;
            case 5:setWave(1.5f, 1, 4, RATE); //4.5
                break;
            case 6:setWave(2.5f, 1, 3, RATE); // 5
                break;
            case 7:setWave(2f, 2, 2, SPEED); //6
                break;
            case 8:setWave(1.5f, 2, 5,SPEED); // 8
                break;
            case 9:setWave(1f, 2, 5, SPEED); //10
                break;
            case 10:setWave(1, 1, 12, HEALTH); //10
                break;
            case 11:setWave(.25f, 2, 30, HEALTH); //11
                break;
            case 12:setWave(.5f, 1, 34, HEALTH); //12
                break;
            case 13:setWave(2, 1, 10, RATE); //14
                break;
            case 14:setWave(2.5f, 2, 6, RATE); //15
                break;
            case 15:setWave(2.5f, 1, 9, RATE); //17
                break;
            case 16:setWave(1, 2, 16, SPEED); //20
                break;
            case 17:setWave(1.5f, 2, 13, SPEED); // 24
                break;
            case 18:setWave(.5f, 3, 20, SPEED); //30
                break;
            case 19:setWave(.5f, 2, 40, HEALTH); //34
                break;
            case 20:setWave(.25f, 2, 80, HEALTH); //38
                break;
            case 21:setWave(1f, 1, 62, HEALTH); //42
                break;
            case 22:setWave(2, 1, 40, RATE); //44
                break;
            case 23:setWave(2.5f, 2, 15, RATE); //45
                break;
            case 24:setWave(3f, 2, 16, RATE); //48
                break;
            case 25:setWave(2f, 2, 27, SPEED); //52
                break;
            case 26:setWave(1.5f, 2, 27, SPEED); //51
                break;
            case 27:setWave(1f, 3, 31, SPEED); //54
                break;
            case 28:setWave(.25f, 2, 200, HEALTH); //56
                break;
            case 29:setWave(.5f, 1, 212, HEALTH); //58
                break;
            case 30:setWave(1, 1, 120, HEALTH); //60
                break;
            case 31:setWave(2.5f, 1, 50, RATE); //62
                break;
            case 32:setWave(3f, 2, 20, RATE); //66
                break;
            case 33:setWave(3.5f, 2, 18, RATE); //63
                break;
            case 34:setWave(2, 2, 27, SPEED); //68
                break;
            case 35:setWave(1.5f, 2, 37, SPEED); //69
                break;
            case 36:setWave(1, 3, 41, SPEED); //72
                break;
            case 37:setWave(.5f, 2, 148, HEALTH); //74
                break;
            case 38:setWave(1, 1, 150, HEALTH); //76
                break;
            case 39:setWave(.25f, 1, 612, HEALTH); //78
                break;
            case 40:setWave(2.5f, 1, 62, RATE); //80
                break;
            case 41:setWave(3, 2, 24, RATE); //84
                break;
            case 42:setWave(4, 2, 21, RATE); //88
                break;
            case 43:setWave(2.5f, 2, 30, SPEED); //85
                break;
            case 44:setWave(2, 3, 30, SPEED); //90
                break;
            case 45:setWave(1.5f, 3, 32, SPEED); //90
                break;
            case 46:setWave(.5f, 2, 182, HEALTH); //92
                break;
            case 47:setWave(1, 1,  184, HEALTH); //94
                break;
            case 48:setWave(.25f, 1, 704, HEALTH); //96
                break;
            case 49:setWave(2.5f, 1, 80, RATE); //97
                break;
            case 50:setWave(3.5f, 2, 34, RATE); //100
                break;
            case 51:setWave(4.5f, 2, 45, RATE); //135
                break;
            case 52:setWave(2.5f, 3, 20, SPEED); //105
                break;
            case 53:setWave(2, 3, 32, SPEED); //108
                break;
            case 54:setWave(1.5f, 4, 44, SPEED); //150
                break;
            case 55:setWave(.5f, 2, 200, HEALTH); //110
                break;
            case 56:setWave(1, 1, 232, HEALTH); //112
                break;
            case 57:setWave(.25f, 1, 2080, HEALTH); //114
                break;
        }
    }
    public void setWave(float rate, int speed, int health, int type){
        spawnRate = rate;
        enemySpeed = speed;
        enemyHealth = health;
    }

    @Override
    public void update(float dt) {

        engine.update(dt);
        np += dt;
        spawnTimer += dt;

        if(spawnTimer > spawnLength){
            spawn = false;
        }

        if(lives < 1){
            gsm.setState(GameStateManager.State.GAMEOVER);
        }

        if(!spawn){
            if(GameKeys.isPressed(GameKeys.SPACE)){
                np = 10;
                spawn = true;
                spawnTimer = 0;
                level++;
                score += level;
                newWave();
            }
        }
        if (np * spawnRate > 1 && spawn) {
            EnemyEntity.create(engine, enemyHealth, enemySpeed);
            np = 0;
        }

        levelText.getComponent(TextComponent.class).text = Integer.toString(level);
    }

    @Override
    public void draw() {
    }
    @Override
    public void handleInput() {

    }
    @Override
    public void dispose() {

    }
}
