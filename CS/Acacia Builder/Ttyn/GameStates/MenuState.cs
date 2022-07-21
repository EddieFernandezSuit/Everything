using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Tyn;
using Tyn.objects;
using Tyn.objects.Component;
using System;
using static GameStateManager;

public class MenuState : GameState {

    protected Texture2D dogeimage;
    
    public MenuState(Game1 game, GameStateManager gsm):base(game,gsm)
    {

    }

    
    public override void init(){
        base.init();

        
        dogeimage = Content.Load<Texture2D>("doge");

        Entity dog = e1.CreateEntity();


        void clickPlay()
        {
            gsm.setState(State.PLAY);
        }


        MakeText(new Vector2(100, 100), "Tyn");
        MakeClickable(new Vector2(100, 200), "Play", clickPlay);
        MakeClickable(new Vector2(100, 300), "Quit", clickEmpty);


        dog.AddComponent(new TextureComponent());
        dog.AddComponent(new TransformComponent());
        dog.AddComponent(new RectangleComponent());
        dog.GetComponent<TransformComponent>().position = new Vector2(0, 0);
        dog.GetComponent<TextureComponent>().texture = dogeimage;
        dog.GetComponent<RectangleComponent>().Rect = new Rectangle(0, 0, 1000, 500);
        dog.GetComponent<TextureComponent>().origin = new Vector2(0, 0);

       

        
    }

    public void loadContent()
    {

    }

    public void update(GameTime gameTime){
        
    }

    public void draw(){

        

    }

    public override void handleInput() { }

    private void select(){

    }

}

