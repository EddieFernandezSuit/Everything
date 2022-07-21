package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class RectComponent implements Component{
    public Rectangle rect = null;
    public final float GRAY = 0;
    public final float BLACK = 1;
    public final float BLUE = 2;
    public float color;
}
