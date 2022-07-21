package com.defensetower.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class RadiusComponent implements Component {
    public float radius = 0;
    public Entity towerId = null;
}
