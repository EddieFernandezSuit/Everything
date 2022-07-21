package com.defensetower.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Timer;

public class TextComponent implements Component {
    public String text = "";
    public int type;
    public boolean isStats;
}
