using Acacia_Builder;
using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using System;
using static GameStateManager;

public class MenuState : GameState {

    protected Texture2D battleground;
    protected Texture2D player;
    protected Texture2D player2;
    protected Texture2D player3;
    protected Texture2D player4;
    Random rnd = new Random();

    public static Texture2D actionImage1;
    public static Texture2D actionAttack;
    SpriteFont font;

    public MenuState(Game1 game, GameStateManager gsm):base(game,gsm)
    {

    }

    public static void MakeAttackAnimation(Vector2 pos, Engine e1, float angle)
    {
        Entity entity = e1.CreateEntity();
        entity.AddComponent(new TransformComponent());
        entity.AddComponent(new TextureComponent());
        entity.AddComponent(new RectangleComponent());
        entity.AddComponent(new AttackAnimationComponent());
        entity.GetComponent<TransformComponent>().position = pos;
        entity.GetComponent<RectangleComponent>().Rect = new Rectangle((int)pos.X, (int)pos.Y, 32, 32);
        entity.GetComponent<TextureComponent>().origin = new Vector2(15, 15);
        entity.GetComponent<TextureComponent>().texture = actionAttack;
        entity.GetComponent<TransformComponent>().angle = angle;
    }


    public override void init(){
        base.init();
        battleground = Content.Load<Texture2D>("battleground");
        player = Content.Load<Texture2D>("player");
        player2 = Content.Load<Texture2D>("player2");
        player3 = Content.Load<Texture2D>("player3");
        player4 = Content.Load<Texture2D>("player4");
        actionImage1 = Content.Load<Texture2D>("actionImage");
        font = Content.Load<SpriteFont>("font");
        actionAttack = Content.Load<Texture2D>("attack");

        void MakeBattleGround()
        {
            Vector2 pos = new Vector2(Game1.screenWidth/2 - 288/2, Game1.screenHeight/2 - 288/2);
            Entity entity = e1.CreateEntity();
            entity.AddComponent(new TransformComponent());
            entity.AddComponent(new TextureComponent());
            entity.AddComponent(new RectangleComponent());
            entity.GetComponent<TransformComponent>().position = pos;
            entity.GetComponent<RectangleComponent>().Rect = new Rectangle((int)pos.X, (int)pos.Y, 288, 288);
            entity.GetComponent<TextureComponent>().origin = new Vector2(0, 0);
            entity.GetComponent<TextureComponent>().texture = battleground;
        }
        void MakePlayer(Vector2 pos, Keys[] keys, int playerNum, Texture2D texture)
        {
            Entity entity = e1.CreateEntity();
            entity.AddComponent(new TransformComponent());
            entity.AddComponent(new TextureComponent());
            entity.AddComponent(new RectangleComponent());
            entity.AddComponent(new PlayerComponent());
            entity.GetComponent<TransformComponent>().position = pos;
            entity.GetComponent<PlayerComponent>().button1 = keys[0];
            entity.GetComponent<PlayerComponent>().button2 = keys[1];
            entity.GetComponent<PlayerComponent>().button3 = keys[2];
            entity.GetComponent<PlayerComponent>().button1Action[0] = rnd.Next(1,9);
            entity.GetComponent<PlayerComponent>().button2Action[0] = rnd.Next(1,9);
            entity.GetComponent<PlayerComponent>().button3Action[0] = rnd.Next(1,9);
            entity.GetComponent<PlayerComponent>().button1Action[1] = rnd.Next(1,9);
            entity.GetComponent<PlayerComponent>().button2Action[1] = rnd.Next(1,9);
            entity.GetComponent<PlayerComponent>().button3Action[1] = rnd.Next(1,9);
            entity.GetComponent<PlayerComponent>().playerNumber = playerNum;
            entity.GetComponent<RectangleComponent>().Rect = new Rectangle((int)pos.X, (int)pos.Y, 96, 96);
            entity.GetComponent<TextureComponent>().origin = new Vector2(0, 0);
            entity.GetComponent<TextureComponent>().texture = texture;
            void MakeProperAction(int bNumA, int butNum, int movePlacement, int symbolSeq)
            {
                Vector2 vec = new Vector2();
                switch (playerNum)
                {
                    case 1:
                        vec = new Vector2(32 + 32 * movePlacement, -16 + 48 * butNum);
                        break;
                    case 2:
                        vec = new Vector2(Game1.screenWidth - 256 + 16 + 32 * movePlacement, -16 + 48 * butNum);
                        break;
                    case 3:
                        vec = new Vector2(32 + 32 * movePlacement, Game1.screenHeight-128 -32  + 48 * butNum);
                        break;
                    case 4:
                        vec = new Vector2(Game1.screenWidth - 256 + 16 + 32 * movePlacement, Game1.screenHeight - 128 - 32 + 48 * butNum);
                        break;
                }
                switch (bNumA)
                {
                    case 1:

                        entity.GetComponent<PlayerComponent>().symbol[symbolSeq] = MakeActionImage(vec, 0);
                        break;
                    case 2:
                        entity.GetComponent<PlayerComponent>().symbol[symbolSeq] = MakeActionImage(vec, (float)3.14 / 2);
                        break;
                    case 3:
                        entity.GetComponent<PlayerComponent>().symbol[symbolSeq] = MakeActionImage(vec, (float)3.14);
                        break;
                    case 4:
                        entity.GetComponent<PlayerComponent>().symbol[symbolSeq] = MakeActionImage(vec, (float)3.14 * 3 / 2);
                        break;
                    case 5:
                        entity.GetComponent<PlayerComponent>().symbol[symbolSeq] = MakeActionAttack(vec, (float)0);
                        break;
                    case 6:
                        entity.GetComponent<PlayerComponent>().symbol[symbolSeq] = MakeActionAttack(vec, (float)3.14 / 2);
                        break;
                    case 7:
                        entity.GetComponent<PlayerComponent>().symbol[symbolSeq] = MakeActionAttack(vec, (float)3.14);
                        break;
                    case 8:
                        entity.GetComponent<PlayerComponent>().symbol[symbolSeq] = MakeActionAttack(vec, (float)3.14 * 3 / 2);
                        break;
                }
            }

            MakeProperAction(entity.GetComponent<PlayerComponent>().button1Action[0], 1, 1,0);
            MakeProperAction(entity.GetComponent<PlayerComponent>().button2Action[0], 2, 1,1);
            MakeProperAction(entity.GetComponent<PlayerComponent>().button3Action[0], 3, 1,2);

            MakeProperAction(entity.GetComponent<PlayerComponent>().button1Action[1], 1, 2,3);
            MakeProperAction(entity.GetComponent<PlayerComponent>().button2Action[1], 2, 2,4);
            MakeProperAction(entity.GetComponent<PlayerComponent>().button3Action[1], 3, 2,5);

            string[] str = new string[3];
            int[] posControls = new int[2];
            switch (playerNum)
            {
                case 1:
                    str[0] = "Q";
                    str[1] = "W";
                    str[2] = "E";
                    posControls[0] = 16;
                    posControls[1] = 16;                    
                    break;
                case 2:
                    //MakeControlsText("Q", new Vector2(16, 16));
                    // MakeControlsText("W", new Vector2(16, 16 + 48));
                    //MakeControlsText("E", new Vector2(16, 16 + 48 + 48));

                    str[0] = "I";
                    str[1] = "O";
                    str[2] = "P";
                    posControls[0] = Game1.screenWidth - 256;
                    posControls[1] = 16;
                    break;
                case 3:
                    str[0] = "Z";
                    str[1] = "X";
                    str[2] = "C";
                    posControls[0] = 16;
                    posControls[1] = Game1.screenHeight - 128;
                    break;
                case 4:
                    str[0] = "B";
                    str[1] = "N";
                    str[2] = "M";
                    posControls[0] = Game1.screenWidth - 256;
                    posControls[1] = Game1.screenHeight - 128;
                    break;
            }
            for (int i = 0; i < 3; i++)
            {
                MakeControlsText(str[i], new Vector2(posControls[0], posControls[1] + 48 * i));
            }
        }
        
        Entity MakeActionImage(Vector2 pos, float angle)
        {
            Entity entity = e1.CreateEntity();
            entity.AddComponent(new TransformComponent());
            entity.AddComponent(new TextureComponent());
            entity.AddComponent(new RectangleComponent());
            entity.AddComponent(new ActionSymbolComponent());
            entity.GetComponent<TransformComponent>().position = pos;
            entity.GetComponent<RectangleComponent>().Rect = new Rectangle((int)pos.X, (int)pos.Y, 32, 32);
            entity.GetComponent<TextureComponent>().origin = new Vector2(16, 16);
            entity.GetComponent<TextureComponent>().texture = actionImage1;
            entity.GetComponent<TransformComponent>().angle = angle;
            return entity;
        }
        Entity MakeActionAttack(Vector2 pos, float angle)
        {
            Entity entity = e1.CreateEntity();
            entity.AddComponent(new TransformComponent());
            entity.AddComponent(new TextureComponent());
            entity.AddComponent(new RectangleComponent());
            entity.AddComponent(new ActionSymbolComponent());
            entity.GetComponent<TransformComponent>().position = pos;
            entity.GetComponent<RectangleComponent>().Rect = new Rectangle((int)pos.X, (int)pos.Y, 32, 32);
            entity.GetComponent<TextureComponent>().origin = new Vector2(16, 16);
            entity.GetComponent<TextureComponent>().texture = actionAttack;
            entity.GetComponent<TransformComponent>().angle = angle;
            return entity;
        }
        void MakeControlsText(string str, Vector2 pos)
        {
            Entity entity = e1.CreateEntity();
            entity.AddComponent(new TransformComponent());
            entity.AddComponent(new TextComponent());
            entity.AddComponent(new RectangleComponent());
            entity.GetComponent<TransformComponent>().position = pos;
            entity.GetComponent<TextComponent>().font = font;
            entity.GetComponent<TextComponent>().normColor = Color.Black;
            entity.GetComponent<TextComponent>().color = Color.Black;
            entity.GetComponent<TextComponent>().str = str;
            
        }
        
        MakeBattleGround();
        Keys[] keys1 = { Keys.Q, Keys.W, Keys.E };
        Keys[] keys2 = { Keys.I, Keys.O, Keys.P };
        Keys[] keys3 = { Keys.Z, Keys.X, Keys.C };
        Keys[] keys4 = { Keys.B, Keys.N, Keys.M };
        MakePlayer(new Vector2(Game1.screenWidth / 2 - 288 / 2, (Game1.screenHeight / 2) - 288 / 2 ), keys1, 1, player);
        MakePlayer(new Vector2(Game1.screenWidth / 2 - 288 / 2 + 192, (Game1.screenHeight / 2) - 288 / 2), keys2, 2,player2);
        MakePlayer(new Vector2(Game1.screenWidth / 2 - 288 / 2, (Game1.screenHeight / 2) - 288 / 2 + 192), keys3, 3,player3);
        MakePlayer(new Vector2(Game1.screenWidth / 2 - 288 / 2 + 192, (Game1.screenHeight / 2) - 288 / 2 + 192), keys4, 4, player4);

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

