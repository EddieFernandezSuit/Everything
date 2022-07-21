package com.eddie.sshooter.Entities;


import com.badlogic.gdx.math.Vector2;
import com.eddie.sshooter.GameStates.PlayState;
import com.eddie.sshooter.Main.MainGame;

import java.util.ArrayList;

// import static sun.audio.AudioPlayer.player;

public class Player extends Entity {


    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean space;

    private float shootTimer;
    private float shootTime;

    ArrayList<Bullet> bullets;

    public Player(ArrayList<Bullet> bullets, float x, float y){

        this.bullets = bullets;
        this.x = x;
        this.y = y;




        speed = 260;
        width = height = 32;
        scaledSize = 1;
        scaledSpeed = 1;
        shootTimer = 0;
        shootTime = .4f;

    }

    public void setLeft(boolean b){left = b;}
    public void setRight(boolean b){right = b;}
    public void setUp(boolean b){up = b;}
    public void setDown(boolean b){down = b;}
    public void setSpace(boolean b){space = b;}

    public void hit(){

        setPosition( MainGame.WIDTH/2, y = MainGame.HEIGHT/2 );
    }

    public void shoot(){
        if(shootTimer * PlayState.getBulletReload() > shootTime ){
            bullets.add(new Bullet( x , y  , PlayState.getBulletSpeed(), PlayState.getBulletSize()));
            shootTimer = 0;
        }
    }

    public void update(float dt){
        Vector2 direction = new Vector2();
        if(left) {
            direction.x = -1;
        }
        if(right) {
            direction.x = 1;
        }
        if(up) {
            direction.y = 1;
        }
        if(down) {
            direction.y = -1;
        }
        direction.nor();



        direction.scl(speed * dt);

        y += direction.y;
        x += direction.x;

        shootTimer += dt * 2/3;
        if(space) {
            shoot();
        }
    }
}
