package com.eddie.sshooter.Entities.Enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Main.MainGame;

public class EnemyBullet extends Enemy {

    public EnemyBullet(Player player){
        x = MainGame.WIDTH;
        y = player.gety();

        speed = 350;
        width = 16;
        height = 16;
        scaledSize = 1;
    }
    public void draw(ShapeRenderer sr){
        sr.setColor(Color.RED);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(x+width/2, y+height/2, width/2);
        sr.end();}

}
