package com.neet.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.FloatFrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.neet.eddie.playground.PlaygroundGame;
import com.neet.entities.*;
import com.neet.managers.GameKeys;
import com.neet.managers.GameStateManager;
import com.neet.managers.Jukebox;
import com.neet.managers.Save;

import java.util.ArrayList;

public class PlayState extends GameState {

    private SpriteBatch sb;
    private ShapeRenderer sr;

    private BitmapFont font;
    private Player hudPlayer;

    private Player player;
    private ArrayList<Bullet> bullets;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Bullet> enemyBullets;

    private FlyingSaucer flyingSaucer;
    private float fsTimer;
    private float fsTime;

    private ArrayList<Particle>  particles;

    private int level;
    private int totalAsteroids;
    private int numAsteroidsLeft;
    private int hyperspace;

    private float maxDelay;
    private float minDelay;
    private float currentDelay;
    private float bgTimer;
    private boolean playLowPulse;

    private int upgradescore;


    public PlayState(GameStateManager gsm){
        super(gsm);
    }

    public void init(){

        sb = new SpriteBatch();
        sr = new ShapeRenderer();

        // Generate font
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/HyperspaceBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 20;
        font = gen.generateFont(param);

        bullets = new ArrayList<Bullet>();
        player = new Player(bullets);
        asteroids = new ArrayList<Asteroid>();
        particles = new ArrayList<Particle>();
        enemyBullets = new ArrayList<Bullet>();
        hudPlayer = new Player(null);

        level = 1;
        hyperspace = 3;

        spawnAsteroids();

        fsTimer = 0;
        fsTime = 15;


        //set up music
        maxDelay = 1;
        minDelay = 0.25f;
        currentDelay = maxDelay;
        bgTimer = maxDelay;
        playLowPulse = true;

    }

    private void createParticles(float x, float y){
        for(int i = 0; i < 15; i++ ){
            particles.add(new Particle(x, y));
        }
    }

    private void splitAsteroids(Asteroid a){
        createParticles(a.getx(), a.gety());
        numAsteroidsLeft--;
        currentDelay = ((maxDelay - minDelay) * numAsteroidsLeft/ totalAsteroids) + minDelay;
        if(a.getType()== Asteroid.LARGE){
            asteroids.add(
                    new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
            asteroids.add(
                    new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
        }
        if(a.getType()== Asteroid.MEDIUM) {
            asteroids.add(
                    new Asteroid(a.getx(), a.gety(), Asteroid.SMALL));
            asteroids.add(
                    new Asteroid(a.getx(), a.gety(), Asteroid.SMALL));
        }
    }

    private void spawnAsteroids(){
        asteroids.clear();

        int numToSpawn = 4 + level - 1;
        totalAsteroids = numToSpawn * 7;
        numAsteroidsLeft = totalAsteroids;
        currentDelay = maxDelay;

        for(int i = 0; i <numToSpawn; i++){
            float x = MathUtils.random(PlaygroundGame.WIDTH);
            float y = MathUtils.random(PlaygroundGame.HEIGHT);

            float dx = x - player.getx();
            float dy = y - player.gety();
            float dist = (float) Math.sqrt(dx * dx + dy * dy);

            while(dist<100){
                x = MathUtils.random(PlaygroundGame.WIDTH);
                y = MathUtils.random(PlaygroundGame.HEIGHT);

                dx = x - player.getx();
                dy = y - player.gety();
                dist = (float) Math.sqrt(dx * dx + dy * dy);
            }
            asteroids.add(new Asteroid(x,y,Asteroid.LARGE));
        }

    }

    public void update(float dt){

        //get user input
        handleInput();

        //next level
        if(asteroids.size() == 0){
            level++;
            spawnAsteroids();
        }

        //update player
        player.update(dt);
        if(player.isDead()){
            if(player.getLives() == 0){
                Jukebox.stopAll();
                Save.gd.setTentativeScore(player.getScore());
                gsm.setState(GameStateManager.GAMEOVER);
                return;
            }
            player.reset();
            player.loseLife();
            flyingSaucer = null;
            Jukebox.stop("smallsaucer");
            Jukebox.stop("largesaucer");
            return;
        }

        //update player bullets
        for(int i=0; i< bullets.size(); i++) {
            bullets.get(i).update(dt);
            if(bullets.get(i).shouldRemove()) {
                bullets.remove(i);
                i--;
            }
        }

        //upfate flying saucer
        if(flyingSaucer == null){
            fsTimer += dt;
            if(fsTimer >= fsTime){
                fsTimer = 0;
                int type = MathUtils.random() < 0.5 ?
                    FlyingSaucer.SMALL : FlyingSaucer.LARGE;
                int direction = MathUtils.random() < .5 ?
                        FlyingSaucer.RIGHT : FlyingSaucer.LEFT;
                flyingSaucer = new FlyingSaucer(type, direction,player, enemyBullets);
            }
        }
        else{
            flyingSaucer.update(dt);
            if(flyingSaucer.shouldRemove()){
                flyingSaucer = null;
                Jukebox.stop("smallsaucer");
                Jukebox.stop("largesaucer");
            }
        }
        //flying saucer bullets
        for(int i=0; i< enemyBullets.size(); i++) {
            enemyBullets.get(i).update(dt);
            if(enemyBullets.get(i).shouldRemove()) {
                enemyBullets.remove(i);
                i--;
            }
        }

        //if there is a flying saucer already


        //update asteroids
        for(int i =0; i < asteroids.size(); i++){
            asteroids.get(i).update(dt);
            if(asteroids.get(i).shouldRemove())
            {
                asteroids.remove(i);
                i--;
            }
        }
        // update particles
        for(int i = 0; i < particles.size(); i++){
            particles.get(i).update(dt);
            if(particles.get(i).shouldRemove()){
                particles.remove(i);
                i--;
            }
        }

        // check collisions
        checkCollisions();

        //play bg music
        bgTimer += dt;
        if(!player.isHit() && bgTimer >= currentDelay){
            if(playLowPulse){
                Jukebox.play("pulselow");
            }
            else{
                Jukebox.play("pulsehigh");
            }
            playLowPulse = !playLowPulse;
            bgTimer = 0;
        }
    }

    private void checkCollisions() {

        // player asteroid collision
        if(!player.isHit()){
            for(int i =0; i < asteroids.size(); i++){
                Asteroid a = asteroids.get(i);
                if(a.intersects(player)){
                    player.hit();
                    asteroids.remove(i);
                    i--;
                    splitAsteroids(a);
                    Jukebox.play("explode");
                    break;
                }
                // if player intersects asteroid
            }
        }
        // bullet asteroid collision
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            for (int j = 0; j < asteroids.size(); j++) {
                Asteroid a = asteroids.get(j);
                if(a.contains(b.getx(), b.gety())){
                    bullets.remove(i);
                    i--;
                    asteroids.remove(j);
                    j--;
                    splitAsteroids(a);
                    player.incrementScore(a.getScore());
                    Jukebox.play("explode");
                    break;
                }
                // if asteroid contains the point b
            }
        }
        if(flyingSaucer != null){
            if(player.intersects(flyingSaucer)){
                player.hit();
                createParticles(player.getx(),player.gety());
                createParticles(flyingSaucer.getx(),flyingSaucer.gety());
                flyingSaucer = null;
                Jukebox.stop("smallsaucer");
                Jukebox.stop("largesaucer");
                Jukebox.play("explode");
            }
        }
        if(flyingSaucer != null){
            for(int i = 0; i < bullets.size(); i++){
                Bullet b = bullets.get(i);
                if(flyingSaucer.contains(b.getx(), b.gety())){
                    bullets.remove(i);
                    i--;
                    createParticles(flyingSaucer.getx(),flyingSaucer.gety());
                    player.incrementScore(flyingSaucer.getScore());
                    flyingSaucer = null;
                    Jukebox.stop("smallsaucer");
                    Jukebox.stop("largesaucer");
                    Jukebox.play("explode");
                    break;
                }
            }

        }
        //player-enemy bullets collision
        if(!player.isHit()) {
            for( int i = 0; i < enemyBullets.size(); i++){
                Bullet b = enemyBullets.get(i);
                if(player.contains(b.getx(), b.gety())){
                    player.hit();
                    enemyBullets.remove(i);
                    i--;
                    Jukebox.play("explode");
                    break;
                }
            }
        }
        //flying sacuer- asteroid collision
        if(flyingSaucer != null){
            for(int i = 0; i < asteroids.size(); i++){
                Asteroid a = asteroids.get(i);
                if(a.intersects(flyingSaucer)){
                    asteroids.remove(i);
                    i--;
                    splitAsteroids(a);
                    createParticles(a.getx(),a.gety());
                    createParticles(flyingSaucer.getx(),flyingSaucer.gety());
                    flyingSaucer = null;
                    Jukebox.stop("smallsaucer");
                    Jukebox.stop("largesaucer");
                    Jukebox.play("explode");
                    break;
                }

            }
        }
        //asteroid-enemy bullet collision
        for(int i = 0; i < enemyBullets.size();i++){
            Bullet b = enemyBullets.get(i);
            for(int j = 0; j < asteroids.size(); j++){
                Asteroid a = asteroids.get(j);
                if(a.contains(b.getx(),b.gety())){
                    asteroids.remove(j);
                    j--;
                    splitAsteroids(a);
                    enemyBullets.remove(i);
                    i--;
                    createParticles(a.getx(),a.gety());
                    Jukebox.play("explode");
                    break;
                }
            }
        }
    }

    public void draw() {

        //draw player
        player.draw(sr);

        //draw bullets
        for(int i = 0; i < bullets.size();i++){
            bullets.get(i).draw(sr);
        }

        // draw flying saucer
        if(flyingSaucer != null){
            flyingSaucer.draw(sr);
        }

        //draw fs bulets
        for(int i = 0; i < enemyBullets.size();i++){
            enemyBullets.get(i).draw(sr);
        }
        //draw asteroids
        for(int i = 0; i < asteroids.size();i++){
            asteroids.get(i).draw(sr);
        }
        //draw particles
        for(int i = 0; i < particles.size(); i++){
            particles.get(i).draw(sr);
        }
        //draw score
        sb.setColor(1, 1, 1, 1);
        sb.begin();
        font.draw(sb, Long.toString(player.getScore()), 40, 390);
        font.draw(sb, Long.toString(hyperspace), 40, 330);
        sb.end();

        //draw lives
        for(int i = 0; i < player.getLives();i++){
            hudPlayer.setPosition(40 + i * 15, 350);
            hudPlayer.draw(sr);
        }
    }

    public void handleInput(){


        player.setLeft(GameKeys.isDown(GameKeys.LEFT));
        player.setRight(GameKeys.isDown(GameKeys.RIGHT));
        player.setUp(GameKeys.isDown(GameKeys.UP));
        if (GameKeys.isPressed(GameKeys.SPACE) && !player.isHit()) {
            player.shoot();
        }
        if(GameKeys.isPressed(GameKeys.R) && !player.isHit()){
            if(hyperspace > 0) {
                player.hyperSpaceJump();
                hyperspace--;
            }
        }
    }

    public void dispose(){
        sb.dispose();
        sr.dispose();
        font.dispose();
    }
}
