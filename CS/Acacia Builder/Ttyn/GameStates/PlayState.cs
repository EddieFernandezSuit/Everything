using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Tyn;
using Tyn.objects.Component;
using System;
using Tyn.Objects.Components;
using Microsoft.Xna.Framework.Content;
using Tyn.Objects;

public class PlayState : GameState
{

    //Entity menuNathan;
    //Entity menuSeth;
    //Entity menuErik;
    Entity player;
    Entity[] menuUnit = new Entity[30];
    Entity[] camps = new Entity[20];
    Entity auto;
    Entity upg;
    Entity upgradeText;

    PlayerComponent playerComp;
    public Texture2D trainer;


    public PlayState(Game1 game, GameStateManager gsm) : base(game, gsm)
    {

    }
    void destroyCamp()
    {
        for (int i = 0; i < 10; i++)
        {
            e1.DestroyEntity(camps[i]);
        }
        e1.DestroyEntity(auto);
        e1.DestroyEntity(upg);
        glob.secondaryMenu = 0;
    }
    Unit MakeUnit(Unit unit, int type, int armysize, int hp, int damage, string symbol, string name, int range, int isAlly)
    {
        unit.armySize = armysize;
        unit.hp = unit.startingHp = hp;
        unit.damage = damage;
        unit.symbol = symbol;
        unit.range = range;
        unit.name = name;
        unit.upgradeType = 1;


        if (isAlly == 1)
        {
            unit.distance = 0;
        }
        else
        {
            unit.distance = 10;
        }
        return unit;
    }
    Action fightAction(int campNum)
    {
        void act()
        {
            glob.campRepeat = campNum;
            if (glob.fightingCamp == 0)
            {
                glob.fightingCamp = campNum;
                Vector2 posUnitText = new Vector2(320, glob.startPosy);
                int UnitTextDist = 48;                
                string[] enemyUnitName = { "EA", "EB", "EC", "ED", "EE", "EF", "EG", "EH", "EI", "EJ" };
                Unit[] enemyUnits = new Unit[glob.unitNumTypes];

                void MakeUnitText(Unit unit)
                {
                    unit.textSprite = MakeText(new Vector2(posUnitText.X, posUnitText.Y + unit.distance * 32), unit.symbol);
                    unit.textHp = MakeText(new Vector2(posUnitText.X + UnitTextDist, posUnitText.Y + (unit.distance * 32)), unit.hp.ToString());
                    unit.textArmySize = MakeText(new Vector2(posUnitText.X - UnitTextDist + 8, posUnitText.Y + (unit.distance * 32)), unit.armySize.ToString());
                }

                Entity camp = e1.CreateEntity();
                camp.AddComponent(new PlayerComponent());
                PlayerComponent campComp = camp.GetComponent<PlayerComponent>();

                for (int i = 0; i < 10; i++)
                {
                    enemyUnits[i] = MakeUnit(new Unit(), 0,i, i * 10, i, enemyUnitName[i], "Enemy", i, 0);
                }

                campComp.unit[0] = enemyUnits[campNum];

                for (int i = 0; i < glob.unitNumTypes; i++)
                {
                    if (campComp.unit[i] != null && campComp.unit[i].armySize > 0)
                    {
                        MakeUnitText(campComp.unit[i]);
                    }
                    if (playerComp.unit[i] != null && playerComp.unit[i].armySize > 0)
                    {
                        MakeUnitText(playerComp.unit[i]);
                    }
                }
            }
            glob.newS = 1;
        }
        return act;
    }
    public override void init()
    {
        base.init();
        for (int i = 0; i < 30; i++)
        {
            menuUnit[i] = null;
        }

        int menux = 0, menuy = 0;
        int a = 0, indent = 28;

        SpriteFont font = Content.Load<SpriteFont>("font2");
        Entity makeUnitMenuText(Unit unit, int b)
        {
            return MakeText(new Vector2(menux, menuy + b), unit.name + ": " + unit.armySize.ToString());
        }        
        Action upgrade(Unit unit)
        {
            void up()
            {
                unit.upgradeLevel++;                
            }
            return up;
        }
        void campMenu()
        {
            if (glob.secondaryMenu == 0)
            {
                glob.secondaryMenu = 1;
                menux = 690;
                menuy = 32;
                a = 0;
                auto = MakeClickable(new Vector2(menux, menuy + a), "AUTO", autoBattleOn); a += indent;
                camps[0] = MakeClickable(new Vector2(menux, menuy + a), "camp1", fightAction(1)); a += indent;
                camps[1] = MakeClickable(new Vector2(menux, menuy + a), "camp2", fightAction(2)); a += indent;
                camps[2] = MakeClickable(new Vector2(menux, menuy + a), "camp3", fightAction(3)); a += indent;
                camps[3] = MakeClickable(new Vector2(menux, menuy + a), "camp4", fightAction(4)); a += indent;
                camps[4] = MakeClickable(new Vector2(menux, menuy + a), "camp5", fightAction(5)); a += indent;
                camps[5] = MakeClickable(new Vector2(menux, menuy + a), "camp6", fightAction(6)); a += indent;
                camps[6] = MakeClickable(new Vector2(menux, menuy + a), "camp7", fightAction(7)); a += indent;
                camps[7] = MakeClickable(new Vector2(menux, menuy + a), "camp8", fightAction(8)); a += indent;
                camps[8] = MakeClickable(new Vector2(menux, menuy + a), "camp9", fightAction(9)); a += indent;
                camps[9] = MakeClickable(new Vector2(menux, menuy + a), "camp10", fightAction(10)); a += indent;
            }
            else
            {
                destroyCamp();
            }
        }
        void upgradeMenu()
        {
            if (glob.secondaryMenu == 0)
            {
                glob.secondaryMenu = 1;
                menux = 600;
                menuy = 32;
                a = 0;
                upg = MakeClickable(new Vector2(menux, menuy + a), "Seth", upgrade(playerComp.unit[1])); a += indent;
            }
            else
            {
                destroyCamp();
            }
        }
        void autoBattleOn()
        {
            if (glob.isRepeat == 0)
            {
                glob.isRepeat = 1;
                auto.GetComponent<TextComponent>().normColor = Color.Red;
            }
            else
            {
                glob.isRepeat = 0;
                auto.GetComponent<TextComponent>().normColor = Color.Black;
            }
        }

        player = e1.CreateEntity();
        player.AddComponent(new PlayerComponent());
        playerComp = player.GetComponent<PlayerComponent>();

        MakeUnit(playerComp.unit[0] = new Unit(),0, 2, 10, 1, "N", "Nathan", 1, 1);
        MakeUnit(playerComp.unit[1] = new Unit(),1, 0, 20, 2, "S", "Seth", 2, 1);
        MakeUnit(playerComp.unit[2] = new Unit(),2, 0, 30, 3, "E", "Erik OOgsten", 3, 1);
        MakeUnit(playerComp.unit[3] = new Unit(),3, 0, 40, 4, "D", "Doug", 4, 1);
        MakeUnit(playerComp.unit[4] = new Unit(),4, 0, 50, 5, "ED", "EDDIE", 5, 1);
        MakeUnit(playerComp.unit[5] = new Unit(),5, 0, 60, 6, "M", "Matt Nordling", 6, 1);
        MakeUnit(playerComp.unit[6] = new Unit(),6, 0, 70, 7, "C", "Corance", 7, 1);
        MakeUnit(playerComp.unit[7] = new Unit(),7, 0, 80, 8, "T", "Trevor", 8, 1);


        MakeText(new Vector2(menux, menuy), "Troops"); a += indent;

        for (int i = 0; playerComp.unit[i] != null; i++)
        {
            menuUnit[i] = makeUnitMenuText(playerComp.unit[i], a); a += indent;
        }
        a += indent;

        MakeClickable(new Vector2(menux, menuy + a), "Fight", campMenu); a += indent;
        upgradeText = MakeClickable(new Vector2(menux, menuy + a), "Upgrade", upgradeMenu); a += indent;

    }
    override public void update(GameTime gameTime)
    {
        base.update(gameTime);
        void updateMenuUnit(Entity text, Unit unit)
        {
            text.GetComponent<TextComponent>().str = unit.name + ": " + unit.armySize.ToString();
        }
        upgradeText.GetComponent<TextComponent>().str = "Upgrade: " + playerComp.unit[1].upgradeLevel.ToString();
        for (int i = 0; playerComp.unit[i] != null; i++)
        {
            updateMenuUnit(menuUnit[i], playerComp.unit[i]);
        }
        
        if(glob.oldS == 1 && glob.newS == 0)
        {
            if(glob.isRepeat == 1)
            {
                fightAction(glob.campRepeat).Invoke();
            }
        }
        glob.oldS = glob.newS;
    }
    public void draw()
    {

    }
    public override void handleInput()
    {

    }
}