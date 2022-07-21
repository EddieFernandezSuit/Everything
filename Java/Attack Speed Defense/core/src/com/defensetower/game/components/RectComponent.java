package com.defensetower.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class RectComponent implements Component {
    public boolean filled;
    public Rectangle rect = null;
    public float rectX;
    public float rectY;
    public float color;
    public final float GRAY = 0;
    public final float BLACK = 1;
    public final float BLUE = 2;
}
