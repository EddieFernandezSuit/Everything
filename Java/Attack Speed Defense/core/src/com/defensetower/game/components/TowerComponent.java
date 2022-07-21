package com.defensetower.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class TowerComponent implements Component{
    public int damage = 0;
    public int cost = 0;
    public int state = 0;
    public int upgradeCost = 10;
    public int level = 0;
    public float range = 0;
    public float attackSpeed = 0;
    public float bulletSpeed = 0;
    public float attackTimer = 0;
    public float attackTime = 1;
    public boolean stats;
    public Entity radius = null;
    public int type;


}
