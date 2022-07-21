package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.base.PlayState;

public class TowerComponent implements Component {
    public float attackTimer = 0;
    public float attackTime = 1 ;
    public float attackSpeed;
    public float range = 1000;
    public int lockOnCount = 0;
    public int lockOn = 3;
    public int accuracy = 9;
    public int damage = 10;
    public int bounce;
    public int bulletSpeed;
    public int state = 0;
    public int type;
    public int leftBoost;
    public int rightBoost;
}
