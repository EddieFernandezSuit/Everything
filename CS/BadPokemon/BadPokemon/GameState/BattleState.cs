using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Ope;
using Ope.objects.Component;

public class BattleState : GameState
{

    SpriteFont font;
    public BattleState(Game1 game, GameStateManager gsm) : base(game, gsm)
    {

    }
       
    public override void handleInput() {


    }

    
    public override void EnterBattle()
    {        
        
        font = Content.Load<SpriteFont>("font");           

        
        Entity MakeClickable(Vector2 pos, SpriteFont fon, Color colo, string strin, Action action)
        {
            Entity fontEnt = e1.CreateEntity();
            fontEnt.AddComponent(new TextComponent());
            fontEnt.AddComponent(new TransformComponent());
            fontEnt.AddComponent(new ClickableComponent());
            fontEnt.GetComponent<TransformComponent>().position = pos;
            fontEnt.GetComponent<TextComponent>().font = fon;
            fontEnt.GetComponent<TextComponent>().color = colo;
            fontEnt.GetComponent<TextComponent>().str = strin;
            fontEnt.GetComponent<ClickableComponent>().action = action;
            return fontEnt;
        }
        Entity makeBattleUI()
        {
            Entity battleUI = e1.CreateEntity();
            battleUI.AddComponent(new TextureComponent());
            battleUI.AddComponent(new TransformComponent());
            battleUI.AddComponent(new RectangleComponent());
            battleUI.GetComponent<TextureComponent>().texture = Content.Load<Texture2D>("battleUI");
            battleUI.GetComponent<TransformComponent>().position = new Vector2(0, 352);
            battleUI.GetComponent<RectangleComponent>().Rect = new Rectangle(0, 0, 800, 128);
            return battleUI;
        }

        makeCreature(loadTexture("trek"), "trek", 0, creature1.GetComponent<StatShellComponent>().exp,creature1.GetComponent<StatShellComponent>().hp,creature1.GetComponent<StatShellComponent>().damage, creature1.GetComponent<StatShellComponent>().lvl);
        makeCreature(loadTexture("meta"), "meta", 1,0,100,1, 1);
        makeBattleUI();

        void emptyAct()
        {

        }
        
        Entity MakeMove(int type, Texture2D texture)
        {
            Entity move1Ent = e1.CreateEntity();
            move1Ent.AddComponent(new TextureComponent());
            move1Ent.AddComponent(new TransformComponent());
            move1Ent.AddComponent(new RectangleComponent());
            move1Ent.AddComponent(new AttackComponent());
            move1Ent.GetComponent<AttackComponent>().type = type;            
            move1Ent.GetComponent<AttackComponent>().moveEffect = emptyAct;
            move1Ent.GetComponent<TextureComponent>().origin = new Vector2(32, 32);
            move1Ent.GetComponent<TextureComponent>().size = 0.5f;
            move1Ent.GetComponent<TextureComponent>().texture = texture;
            move1Ent.GetComponent<TransformComponent>().position = creaturePos[type];
            move1Ent.GetComponent<RectangleComponent>().Rect = new Rectangle(0, 0, 128, 128);
            return move1Ent;            
        }

        Random rand = new Random();
        int n = 5;

        void move1Act()
        {
            MakeMove(0, loadTexture("trek"));
            Entity ent = MakeMove(1, loadTexture("meta"));
            ent.GetComponent<AttackComponent>().moveEffect = moveAct;

            void moveAct()
            {
                TransformComponent transformComp = ent.GetComponent<TransformComponent>();
                transformComp.position.Y -= rand.Next(-n, n);
                transformComp.position.X -= rand.Next(-n, n);
            }

        }    
        void fightAct()
        {
            ImmutableList<Entity> clickEntities = e1.GetEntitiesFor(Family.All(typeof(ClickableComponent)).Get());
            ImmutableList<Entity> creatureEnt = e1.GetEntitiesFor(Family.All(typeof(CreatureComponent)).Get());
            for (int i = 0; i < clickEntities.Count; i++)
            {
                TextComponent textComp = clickEntities[i].GetComponent<TextComponent>();
                textComp.str = "";

            }
            for (int j = 0; j < creatureEnt.Count; j++)
            {
                CreatureComponent creatureComp = creatureEnt[j].GetComponent<CreatureComponent>();
                TransformComponent transformComp = creatureEnt[j].GetComponent<TransformComponent>();
                creatureComp.move1 = MakeClickable(new Vector2(UILeftX, UIUpY), font, Color.Red, "get em", move1Act);
                creatureComp.move2 = MakeClickable(new Vector2(UIRightX, UIUpY), font, Color.Red, "shoot em", move1Act);
                creatureComp.move3 = MakeClickable(new Vector2(UILeftX, UIDownY), font, Color.Red, "I roll to seduce", move1Act);
                creatureComp.move4 = MakeClickable(new Vector2(UIRightX, UIDownY), font, Color.Red, "do the thing", move1Act);
            }
        }

        MakeClickable(new Vector2(UILeftX, UIUpY), font, Color.Black, "FIGHT", fightAct);
        MakeClickable(new Vector2(UIRightX, UIUpY), font, Color.Black, "PKMN", emptyAct);
        MakeClickable(new Vector2(UILeftX, UIDownY), font, Color.Black, "BAG", emptyAct);
        MakeClickable(new Vector2(UIRightX, UIDownY), font, Color.Black, "RUN", emptyAct);
    }

    public override void init()
    {
        base.init();
               

    }


    public override void update(GameTime gameTime)
    {
        base.update(gameTime);
    }
}