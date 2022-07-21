package com.neet.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import static com.badlogic.gdx.math.MathUtils.random;

public class Particle extends SpaceObject {
    private float timer;
    private double time;
    private boolean remove;

    public Particle(float x, float y){

        this.x = x;
        this.y = y;
        width = height = random(3);


        speed = random(50) + 50;
        radians = random(2 * 3.1415f);
        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;

        timer = 0;
        time = .5;
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
        sr.setColor(1,1,1,1);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(x-width/ 2, y-height/2, width/2);
        sr.end();
    }
}
