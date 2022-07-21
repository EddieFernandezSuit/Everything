package com.eddie.sshooter.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.eddie.sshooter.Entities.Enemies.Enemy;
import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Main.MainGame;

import static com.badlogic.gdx.math.MathUtils.random;

public class Paralax extends Enemy {

    public Paralax(){
        x = MainGame.WIDTH;
        y = random(MainGame.HEIGHT);

        width = height = 2;
        speed = 400;
    }
    public void draw(ShapeRenderer sr){
        sr.setColor(Color.WHITE);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(x-width/ 2, y-height/2, width/2);
        sr.end();
    }
}
