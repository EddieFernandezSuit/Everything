package com.eddie.sshooter.Entities.Enemies;

import com.badlogic.gdx.math.MathUtils;
import com.eddie.sshooter.Main.MainGame;

public class EnemyBasic extends com.eddie.sshooter.Entities.Enemies.Enemy {

    public EnemyBasic(){
        x = MainGame.WIDTH;
        y = MathUtils.random(MainGame.HEIGHT);

        speed = 160;
        width = 32;
        height = 32;
        scaledSize = 1;
    }
}
