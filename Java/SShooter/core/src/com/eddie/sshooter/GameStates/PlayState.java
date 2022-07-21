package com.eddie.sshooter.GameStates;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.eddie.sshooter.Entities.*;
import com.eddie.sshooter.Entities.Enemies.EnemyBullet;
import com.eddie.sshooter.Main.MainGame;
import com.eddie.sshooter.Managers.GameKeys;
import com.eddie.sshooter.Managers.GameStateManager;

import java.util.ArrayList;

public class PlayState extends GameState{

    protected Player player;
    protected Upgrade upgrade;

    protected ArrayList<Bullet> bullets;
    protected ArrayList<com.eddie.sshooter.Entities.Enemies.EnemyBasic> basicEnemies;
    protected ArrayList<com.eddie.sshooter.Entities.Enemies.EnemyFast> fastEnemies;
    protected ArrayList<com.eddie.sshooter.Entities.Enemies.EnemyBig> bigEnemies;
    protected ArrayList<EnemyBullet> enemyBullets;
    protected ArrayList<Particle> particles;
    protected ArrayList<Paralax> paralaxes;


    protected ShapeRenderer sr;
    protected SpriteBatch sb;
    protected BitmapFont font;

    protected Texture plspr;
    protected Texture buspr;
    protected Texture ebspr;
    protected Texture efspr;
    protected Texture ebigspr;
    protected Texture upspr;

    private static int bulletSpeed;
    private static int bulletReload;
    private static int bulletSize;

    protected float ebTimer;
    protected float ebTime;
    protected float efTimer;
    protected float efTime;
    protected float ebigTimer;
    protected float ebigTime;
    protected float enbuTime;
    protected float enbuTimer;
    protected float upTimer;
    protected float upTime;
    protected float pTimer;
    protected float pTime;
    protected float upgradeTime;

    protected static float upgradeScore;
    protected static int level;

    protected static float difTimer;
    protected static float lvlTimer;
    protected static float lvlTime;

    public PlayState(GameStateManager gsm){
        super(gsm);
    }

    public void init(){

        basicEnemies = new ArrayList<com.eddie.sshooter.Entities.Enemies.EnemyBasic>();
        fastEnemies = new ArrayList<com.eddie.sshooter.Entities.Enemies.EnemyFast>();
        bigEnemies = new ArrayList<com.eddie.sshooter.Entities.Enemies.EnemyBig>();
        enemyBullets = new ArrayList<EnemyBullet>();
        particles = new ArrayList<Particle>();
        paralaxes = new ArrayList<Paralax>();
        bullets = new ArrayList<Bullet>();
        player = new Player(bullets, MainGame.WIDTH/2, MainGame.HEIGHT/2);

        sr = new ShapeRenderer();
        sb = new SpriteBatch();

        plspr = new Texture("sprites/Player.png");
        upspr = new Texture("sprites/Upgrade.png");
        ebspr = new Texture("sprites/BasicEnemy.png");
        efspr = new Texture("sprites/FastEnemy.png");
        ebigspr = new Texture("sprites/BigEnemy.png");
        buspr = new Texture("sprites/Bullet.png");

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/HyperspaceBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter para = new FreeTypeFontGenerator.FreeTypeFontParameter();
        para.size = 26;
        font = gen.generateFont(para);

        bulletSpeed = 1;
        bulletSize = 1;
        bulletReload = 1;

        ebTimer = efTimer = ebigTimer = enbuTimer = upTimer = lvlTimer = pTimer = 0;
        upgradeTime = 8;
        enbuTime = 2;
        upTime = 4;
        lvlTime = 30;
        pTime = .05f;
        difTimer = 1;

        upgradeScore = 0;
    }

    public static float getDifTimer(){ return difTimer; }
    public static int getBulletSpeed(){ return bulletSpeed; }
    public static int getBulletReload(){ return bulletReload; }
    public static int getBulletSize() { return bulletSize; }

    public void createParticles(float x, float y){
        for(int i = 0; i < 10; i++ ){
            particles.add(new Particle(x, y));
        }
    }

    public void powerUp(){

        upgradeScore += 1;
        if ( upgradeScore >= 4 ){
            if (upgrade.type == upgrade.BULLETSPEED) {
                bulletSpeed += 2;
            }
            if (upgrade.type == upgrade.BULLETRELOAD) {
                bulletReload += 2;
            }
            if (upgrade.type == upgrade.BULLETSIZE) {
                bulletSize += 2;
            }
            upgradeScore = 0;
        }

    }

    protected void checkCollisions(){

        for (int i = 0; i < basicEnemies.size(); i++) {
            for (int j = 0; j < bullets.size(); j++) {
                if (basicEnemies.get(i).getBounds().overlaps(bullets.get(j).getBounds())) {
                    createParticles(basicEnemies.get(i).getx(),basicEnemies.get(i).gety());
                    basicEnemies.remove(i);
                    bullets.remove(j);
                    i--;
                    j--;

                    break;
                }
            }
            if(i < 0){
                break;
            }
            if (player.getBounds().overlaps(basicEnemies.get(i).getBounds())) {
                createParticles(player.getx(), player.gety());
                player.hit();
                gsm.setState(GameStateManager.MENU );
                basicEnemies.remove(i);
                i--;
            }
        }


        for (int i = 0; i < fastEnemies.size(); i++) {
            for (int j = 0; j < bullets.size(); j++) {
                if (fastEnemies.get(i).getBounds().overlaps(bullets.get(j).getBounds())) {
                    createParticles(fastEnemies.get(i).getx(),fastEnemies.get(i).gety());
                    fastEnemies.remove(i);
                    bullets.remove(j);
                    i--;
                    j--;
                    break;
                }
            }
            if (i < 0 ){ break; }
            if (player.getBounds().overlaps(fastEnemies.get(i).getBounds())) {
                createParticles(player.getx(),player.gety());
                player.hit();
                gsm.setState(GameStateManager.MENU );
                fastEnemies.remove(i);
                i--;
                break;
            }
        }

        for (int i = 0; i < bigEnemies.size(); i++) {
            for (int j = 0; j < bullets.size(); j++) {
                if (bigEnemies.get(i).getBounds().overlaps(bullets.get(j).getBounds())) {
                    createParticles(bigEnemies.get(i).getx(),bigEnemies.get(i).gety());
                    bigEnemies.remove(i);
                    bullets.remove(j);
                    i--;
                    j--;
                    break;
                }
            }
            if( i < 0 ){ break; }
            if (player.getBounds().overlaps(bigEnemies.get(i).getBounds())) {
                createParticles(player.getx(),player.gety());
                player.hit();
                gsm.setState(GameStateManager.MENU );
                bigEnemies.remove(i);
                i--;
                break;
            }
        }

        for (int i = 0; i < enemyBullets.size(); i++) {
            if( i < 0 ){ break; }
            if (player.getBounds().overlaps(enemyBullets.get(i).getBounds())) {
                createParticles(player.getx(),player.gety());
                player.hit();
                gsm.setState(GameStateManager.MENU );
                enemyBullets.remove(i);
                i--;
                break;
            }
        }

        if (upgrade != null) {
            if (player.getBounds().overlaps(upgrade.getBounds())) {
                powerUp();
                upgrade = null;
            }
        }
    }

    public void update(float dt) {

        Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond());
        handleInput();
        checkCollisions();

        difTimer += dt/80;
        ebTimer += dt;
        efTimer += dt;
        ebigTimer += dt;
        enbuTimer += dt;
        upTimer += dt;
        pTimer += dt;
        lvlTimer += dt;

        player.update(dt);
        if(upgrade != null){
            upgrade.update(dt);
            if(upgrade.shouldRemove()){
                upgrade = null;
            }
        }

        if(player.getx() > MainGame.WIDTH - 32){
            gsm.setState(GameStateManager.MENU );
        }
        if(player.getx() < 0 ){
            gsm.setState(GameStateManager.MENU );
        }
        if(player.gety() > MainGame.HEIGHT - 32){
            gsm.setState(GameStateManager.MENU );
        }
        if(player.gety() < 0){
            gsm.setState(GameStateManager.MENU );
        }

        if (ebTimer >= ebTime) {
            ebTimer = 0;
            basicEnemies.add(new com.eddie.sshooter.Entities.Enemies.EnemyBasic());
        }

        if (efTimer >= efTime) {
            efTimer = 0;
            fastEnemies.add(new com.eddie.sshooter.Entities.Enemies.EnemyFast());
        }

        if(ebigTimer >= ebigTime){
            ebigTimer = 0;
            bigEnemies.add(new com.eddie.sshooter.Entities.Enemies.EnemyBig());
        }

        if(enbuTimer >= enbuTime){
            enbuTimer = 0;
            enemyBullets.add(new EnemyBullet(player));
        }

        if(pTimer >= pTime){
            pTimer = 0;
            paralaxes.add(new Paralax());
        }

        if(upTimer >= upTime){
            upgrade = new Upgrade(MathUtils.random(2));
            upTimer = 0;

        }


        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update(dt);
            if (bullets.get(i).shouldRemove()) {
                bullets.remove(i);
                i--;
            }
        }

        for (int i = 0; i < basicEnemies.size(); i++){
            basicEnemies.get(i).update(dt);
            if (basicEnemies.get(i).shouldRemove()) {
                basicEnemies.remove(i);
                i--;
            }
        }

        for (int i = 0; i < fastEnemies.size(); i++){
            fastEnemies.get(i).update(dt);
            if (fastEnemies.get(i).shouldRemove()) {
                fastEnemies.remove(i);
                i--;
            }
        }

        for (int i = 0; i < bigEnemies.size(); i++){
            bigEnemies.get(i).update(dt);
            if (bigEnemies.get(i).shouldRemove()) {
                bigEnemies.remove(i);
                i--;
            }
        }

        for (int i = 0; i < enemyBullets.size(); i++){
            enemyBullets.get(i).update(dt);
            if (enemyBullets.get(i).shouldRemove()) {
                enemyBullets.remove(i);
                i--;
            }
        }

        for(int i = 0; i < particles.size(); i++){
            particles.get(i).update(dt);
            if(particles.get(i).shouldRemove()){
                particles.remove(i);
                i--;
            }
        }
        for(int i = 0; i < paralaxes.size(); i++){
            paralaxes.get(i).update(dt);
            if(paralaxes.get(i).shouldRemove()){
                paralaxes.remove(i);
                i--;
            }
        }
    }

    public void draw(){
        sr.setColor(Color.WHITE);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        for(int i = 0; i < upgradeScore;i++){
            int a = 40 + i * 15;
            sr.rect( a, 450, 10 , 10);
        }
        sr.end();
        for(int i = 0; i < paralaxes.size(); i++){
            paralaxes.get(i).draw(sr);
        }
        for(int i = 0; i < particles.size(); i++){
            particles.get(i).draw(sr);
        }
        for(int i = 0; i < enemyBullets.size();i++){
            enemyBullets.get(i).draw(sr);
        }

        sb.begin();
        player.draw(sb, plspr);

        if(upgrade != null) {
            upgrade.draw(sb, upspr);
        }

        for(int i = 0; i < bullets.size();i++){
            bullets.get(i).draw(sb, buspr);
        }

        for(int i = 0; i < basicEnemies.size();i++){
            basicEnemies.get(i).draw(sb, ebspr);
        }

        for(int i = 0; i < bigEnemies.size();i++){
            bigEnemies.get(i).draw(sb, ebigspr);
        }
        for(int i = 0; i < fastEnemies.size();i++){
            fastEnemies.get(i).draw(sb, efspr);
        }
        font.setColor(Color.WHITE);
        font.draw(sb, Long.toString(level), 10 , 400);
        sb.end();
    }

    public void handleInput(){

        player.setLeft(GameKeys.isDown(GameKeys.LEFT));
        player.setRight(GameKeys.isDown(GameKeys.RIGHT));
        player.setUp(GameKeys.isDown(GameKeys.UP));
        player.setDown(GameKeys.isDown(GameKeys.DOWN));
        player.setSpace(GameKeys.isDown(GameKeys.SPACE));
        //if (GameKeys.isPressed(GameKeys.SPACE)){
        //    player.shoot();
        //}
    }

    public void dispose(){
        sr.dispose();
        sb.dispose();
        plspr.dispose();
        ebspr.dispose();
        efspr.dispose();
        buspr.dispose();
        ebigspr.dispose();
    }
}
