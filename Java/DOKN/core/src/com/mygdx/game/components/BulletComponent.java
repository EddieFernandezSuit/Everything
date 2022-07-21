package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class BulletComponent implements Component {
    public int damage;
    public int bounce;
    public int type;
    public boolean lockOn = false;
    public Entity maxId = null;
}
