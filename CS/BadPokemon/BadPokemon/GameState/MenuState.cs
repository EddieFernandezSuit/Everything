using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Ope;
using Ope.objects;
using Ope.objects.Component;
using System;
using static GameStateManager;

public class MenuState : GameState {

    protected Texture2D dogeimage;
    private SpriteFont font;
    
    public MenuState(Game1 game, GameStateManager gsm):base(game,gsm)
    {

    }

    
    public override void init(){
        base.init();

        
        dogeimage = Content.Load<Texture2D>("doge");
        font = Content.Load<SpriteFont>("font");

        Entity dog = e1.CreateEntity();
        Entity fontEnt;
        


        void clickPlay()
        {
            gsm.setState(State.PLAY);
        }
        void clickEmpty()
        {

        }
        void MakeClickable(Vector2 pos, SpriteFont fon, Color colo, string strin, Action action)
        {
            fontEnt = e1.CreateEntity();
            fontEnt.AddComponent(new TextComponent());
            fontEnt.AddComponent(new TransformComponent());
            fontEnt.AddComponent(new ClickableComponent());
            fontEnt.GetComponent<TransformComponent>().position = pos;
            fontEnt.GetComponent<TextComponent>().font = fon;
            fontEnt.GetComponent<TextComponent>().color = colo;
            fontEnt.GetComponent<TextComponent>().str = strin;
            fontEnt.GetComponent<ClickableComponent>().action = action;
        }

        fontEnt = e1.CreateEntity();
        fontEnt.AddComponent(new TextComponent());
        fontEnt.AddComponent(new TransformComponent());
        fontEnt.GetComponent<TransformComponent>().position = new Vector2(100,100);
        fontEnt.GetComponent<TextComponent>().font = font;
        fontEnt.GetComponent<TextComponent>().color = Color.Black;
        fontEnt.GetComponent<TextComponent>().str = "OPE";

        MakeClickable(new Vector2(100, 200), font, Color.Black, "Play", clickPlay);
        MakeClickable(new Vector2(100, 300), font, Color.Black, "Quit", clickEmpty);


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

    public override void EnterBattle()
    {
        throw new NotImplementedException();
    }
}

