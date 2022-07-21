package com.eddie.sshooter.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import static com.badlogic.gdx.math.MathUtils.random;

public class Particle extends Entity {
    private float timer;
    private double time;
    private boolean remove;
    private float radians;

    public Particle(float x, float y){

        this.x = x + random(16);
        this.y = y + random(16);
        width = height = 4;


        speed = random(50) + 100;
        //radians = random( -.4f * 3.1415f , .4f * 3.1415f ) ;
        radians = random(2 * 3.1415f);
        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;

        timer = 0;
        time = .2;
    }

    public boolean shouldRemove(){ return remove;}

    public void update(float dt){

        x += dx * dt;
        y += dy * dt;

        timer += dt;
        if(timer > time){
            remove = true;
        }
    }
    public void draw(ShapeRenderer sr){
        sr.setColor(Color.WHITE);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(x-width/ 2, y-height/2, width/2);
        sr.end();
    }
}
