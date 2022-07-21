using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Ope;
using Ope.objects.Component;
using System;

public class PlayState : GameState
{

    public Texture2D trainer;
    public PlayState(Game1 game, GameStateManager gsm):base(game, gsm)

    {
    }
    
    public override void init()
    {
        base.init();

        Random random = new Random();

        Texture2D grass = Content.Load<Texture2D>("grass");
        trainer = Content.Load<Texture2D>("Trainer");
        
        void createTrainerEnt()
        {
            Entity trainerEnt = e1.CreateEntity();
            trainerEnt.AddComponent(new TransformComponent());
            trainerEnt.AddComponent(new TrainerComponent());
            trainerEnt.AddComponent(new TextureComponent());
            trainerEnt.AddComponent(new RectangleComponent());
            trainerEnt.GetComponent<RectangleComponent>().Rect = new Rectangle(0,0,32,32);
            trainerEnt.GetComponent<TransformComponent>().position = new Vector2(32+16, 32+16);
            trainerEnt.GetComponent<TransformComponent>().angle = 0;
            trainerEnt.GetComponent<TextureComponent>().texture = trainer;
            trainerEnt.GetComponent<TextureComponent>().origin = new Vector2(16, 16);
        }
        void createGrassEnt()
        {
            Entity grassEnt = e1.CreateEntity();
            grassEnt.AddComponent(new TextureComponent());
            grassEnt.AddComponent(new TransformComponent());
            grassEnt.AddComponent(new GrassComponent());
            grassEnt.AddComponent(new RectangleComponent());
            grassEnt.GetComponent<TextureComponent>().texture = grass;
            Vector2 tempV = grassEnt.GetComponent<TransformComponent>().position = new Vector2(32 * random.Next(25), 32 * random.Next(15));
            grassEnt.GetComponent<RectangleComponent>().Rect = new Rectangle((int)tempV.X,(int)tempV.Y,32,32);
        }
        void createOverWorldCreature()
        {
            //Vector2[] pos =
            //{
                
            //}
            Entity ent = e1.CreateEntity();
            ent.AddComponent(new TransformComponent());
            ent.AddComponent(new TextureComponent());
            ent.AddComponent(new RectangleComponent());
            ent.GetComponent<TransformComponent>().position = Vector2.Zero; 
        }

        for(int i = 0; i < 100; i++)
        {
            createGrassEnt();
        }

        createTrainerEnt();

    }
    
    public void update(GameTime gameTime)
    {

    }
    public void draw()
    {

    }
    public override void handleInput()
    {

    }
    public override void EnterBattle()
    {
        throw new NotImplementedException();
    }
}