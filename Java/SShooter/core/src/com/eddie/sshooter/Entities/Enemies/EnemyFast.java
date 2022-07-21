package com.eddie.sshooter.Entities.Enemies;

import com.badlogic.gdx.math.MathUtils;
import com.eddie.sshooter.Main.MainGame;

public class EnemyFast extends com.eddie.sshooter.Entities.Enemies.Enemy {

    public EnemyFast(){
        x = MainGame.WIDTH;
        y = MathUtils.random(0, MainGame.HEIGHT);

        speed = 320;
        width = 32;
        height = 32;
        scaledSize = 1;
    }
}
