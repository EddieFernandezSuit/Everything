package com.defensetower.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class BulletComponent implements Component {
    public int damage = 0;
    public Entity maxId = null;
}
