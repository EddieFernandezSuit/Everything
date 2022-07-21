package com.eddie.sshooter.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Main.MainGame;
import com.eddie.sshooter.Managers.GameKeys;
import com.eddie.sshooter.Managers.GameStateManager;


public class MenuState extends GameState{

    private SpriteBatch sb;
    private GlyphLayout layout;
    private ShapeRenderer sr;

    private BitmapFont titleFont;
    private BitmapFont font;

    private int currentItem;
    private String[] menuItems;
    //private ArrayList<Asteroid> asteroids;

    public MenuState(GameStateManager gsm){ super(gsm); }

    public void init(){

        sb = new SpriteBatch();
        layout = new GlyphLayout();
        sr = new ShapeRenderer();

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/HyperspaceBold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter para = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 56;
        para.size = 26;

        font = gen.generateFont(para);

        titleFont = gen.generateFont(param);

        menuItems = new String[]{
                "Play",
        };

        //Save.load();
    }

    public void update(float dt){
        handleInput();
    }

    public void draw(){

        final String title = "Sshooter";


        sb.begin();

        layout.setText(titleFont, title);
        titleFont.setColor(Color.WHITE);
        titleFont.draw(sb, title, (MainGame.WIDTH - layout.width) / 2, 300);

        //draw menu
        for(int i = 0; i < menuItems.length; i++) {
            layout.setText(font, menuItems[i]);
            if(currentItem == i) font.setColor(Color.RED);
            else font.setColor(Color.WHITE);
            font.draw(
                    sb,
                    menuItems[i],
                    (MainGame.WIDTH - layout.width) / 2,
                    180 - 35 * i
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
        if(GameKeys.isPressed(GameKeys.ENTER)){
            select();
        }
    }

    private void select(){
        if(currentItem == 0){
            gsm.setState(GameStateManager.ONE);
        }

        else if(currentItem == 1){
            gsm.setState(GameStateManager.SIXTEEN);
        }

        else if(currentItem == 2){
            Gdx.app.exit();
        }

    }

    public void dispose(){
        sb.dispose();
        sr.dispose();
        titleFont.dispose();
        font.dispose();
    }
}
