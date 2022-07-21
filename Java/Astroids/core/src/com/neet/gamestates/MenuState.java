package com.neet.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.neet.eddie.playground.PlaygroundGame;
import com.neet.entities.Asteroid;
import com.neet.managers.GameKeys;
import com.neet.managers.GameStateManager;
import com.neet.managers.Save;

import java.util.ArrayList;

public class MenuState extends GameState{

    private SpriteBatch sb;
    private GlyphLayout layout;
    private ShapeRenderer sr;

    private BitmapFont titleFont;
    private BitmapFont font;

    private final String title = "Asteroids";

    private int currentItem;
    private String[] menuItems;

    private ArrayList<Asteroid> asteroids;


    public MenuState(GameStateManager gsm){
        super(gsm);
    }

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
                "High Scores",
                "Quit"
        };

        Save.load();
        asteroids = new ArrayList<Asteroid>();
        for(int i = 0; i < 6; i++){
            asteroids.add(new Asteroid(
                    MathUtils.random(PlaygroundGame.WIDTH),
                    MathUtils.random(PlaygroundGame.HEIGHT),
                    Asteroid.LARGE));
        }
    }

    public void update(float dt){

        handleInput();
        for(int i = 0 ; i < asteroids.size(); i++){
            asteroids.get(i).update(dt);
        }
    }

    public void draw(){

        //sb.setProjectionMatrix(PlaygroundGame.cam.combined);
        //sr.setProjectionMatrix(PlaygroundGame.cam.combined);

        //draw asteroids
        for(int i = 0; i < asteroids.size(); i++){
            asteroids.get(i).draw(sr);
        }
        sb.setColor(1, 1, 1, 1);
        sb.begin();


       //float width = titleFont.getBounds(title);
        layout.setText(titleFont, title);
        titleFont.draw(sb, title, (PlaygroundGame.WIDTH - layout.width) / 2, 300);

        //draw menu
        for(int i = 0; i < menuItems.length; i++) {
            layout.setText(font, menuItems[i]);
            if(currentItem == i) font.setColor(Color.RED);
            else font.setColor(Color.WHITE);
            font.draw(
                    sb,
                    menuItems[i],
                    (PlaygroundGame.WIDTH - layout.width) / 2,
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
            gsm.setState(GameStateManager.PLAY);
        }

        else if(currentItem == 1){
            gsm.setState(GameStateManager.HIGHSCORE);
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
