package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

import static com.mygdx.game.base.PlayState.GRAVITY;

public class AccelerationComponent implements Component {
    public float acceleration = GRAVITY;
}
