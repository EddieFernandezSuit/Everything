package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class TransformComponent implements Component {

    public Vector2 position = new Vector2();
    public float rotation;
    public float initialdx;
    public float dy;
    public float dx;
    public float radians;
    public float speed;
}
