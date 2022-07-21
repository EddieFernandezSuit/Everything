package com.defensetower.game.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.defensetower.game.MainGame;
import com.defensetower.game.managers.GameKeys;
import com.defensetower.game.managers.GameStateManager;

import static com.defensetower.game.MainGame.cam;


public class MenuState extends GameState{

    private SpriteBatch sb;
    private GlyphLayout layout;

    private final String title = "Attack Speed Defense";
    private BitmapFont titleFont;
    private BitmapFont font;

    private int currentItem;
    private String[] menuItems;

    public MenuState(GameStateManager gsm){
        super(gsm);
    }

    public void init(){

        sb = new SpriteBatch();
        layout = new GlyphLayout();


        font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
        titleFont = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
        menuItems = new String[]{
                "Play",
                "Quit"
        };
    }

    public void update(float dt){

        handleInput();
    }

    public void draw(){

        sb.setColor(1, 1, 1, 1);
        sb.begin();
        layout.setText(titleFont, title);
        titleFont.setColor(Color.BLACK);
        titleFont.draw(sb, title, (MainGame.WIDTH - layout.width) / 2, 400);

        for(int i = 0; i < menuItems.length; i++) {
            layout.setText(font, menuItems[i]);
            if(currentItem == i) font.setColor(Color.RED);
            else font.setColor(Color.BLACK);
            font.draw(
                    sb,
                    menuItems[i],
                    (MainGame.WIDTH - layout.width) / 2,
                    300 - 35 * i
            );
        }

        sb.end();

    }

    public void handleInput(){

        if(GameKeys.isPressed(GameKeys.UP)){
            if(currentItem > 0) currentItem--;
        }
        if(GameKeys.isPressed(GameKeys.DOWN))
            if(currentItem < menuItems.length - 1){
                currentItem++;
            }
        if(GameKeys.isPressed(GameKeys.ENTER) || GameKeys.isPressed(GameKeys.SPACE)){
            select();
            }
    }

    private void select(){
        if(currentItem == 0){
            gsm.setState(GameStateManager.State.PLAY);
        }

        else if(currentItem == 1){
            Gdx.app.exit();
        }

    }

    public void dispose(){
        sb.dispose();
        titleFont.dispose();
        font.dispose();
    }
}
