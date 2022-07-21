package com.defensetower.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class UpgradeComponent implements Component {
    public int upCost;
    public Entity tower;
}
