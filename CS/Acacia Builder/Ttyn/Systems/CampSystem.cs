using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Tyn.objects.Component;
using Tyn.Objects.Components;

namespace Tyn.Objects.Systems
{
    public class CampSystem
    {
        Engine engine;
        ContentManager Content;

        int timer = 60, time = 2, alt = 0, end = 0, textfight = 0, i = 0;
        static Vector2 textPos = new Vector2(450, 320);

        Entity[] text = new Entity[20];
        ImmutableList<Entity> player;
        PlayerComponent playerComp;
        PlayerComponent campComp;
        Entity winClick;
        Entity recText = null;
        Entity[] unitText = new Entity[3];
        void DestroyText(Unit unit)
        {
            engine.DestroyEntity(unit.textArmySize);
            engine.DestroyEntity(unit.textHp);
            engine.DestroyEntity(unit.textSprite);
        }
        void winAction()
        {
            int m = 0;
            while (text[m] != null)
            {
                engine.DestroyEntity(text[m]);
                m++;
            }
            if (playerComp.unit[glob.fightingCamp - 1] != null)
            {
                playerComp.unit[glob.fightingCamp - 1].armySize++;
            }

            for (int i = 0; i < glob.unitNumTypes; i++)
            {
                if (playerComp.unit[i] != null)
                {
                    playerComp.unit[i].distance = 0;
                }
            }

            for (int i = 0; i < glob.unitNumTypes; i++)
            {
                if(playerComp.unit[i] != null)
                {
                    DestroyText(playerComp.unit[i]);
                }
            }
            for (int i = 0; campComp.unit[i] != null; i++)
            {
                DestroyText(campComp.unit[i]);
            }
            engine.DestroyEntity(player[1]);
            engine.DestroyEntity(winClick);
            i = 0;
            end = 0;
            glob.fightingCamp = 0;

        }
        public CampSystem(Engine e1, ContentManager content)
        {
            engine = e1;
            Content = content;
        }
        public Entity MakeText(Vector2 pos, string str)
        {
            SpriteFont font = Content.Load<SpriteFont>("font");

            Entity healthNum = engine.CreateEntity();
            healthNum.AddComponent(new TransformComponent());
            healthNum.AddComponent(new TextComponent());
            healthNum.GetComponent<TransformComponent>().position = pos;
            healthNum.GetComponent<TextComponent>().font = font;
            healthNum.GetComponent<TextComponent>().str = str;
            healthNum.GetComponent<TextComponent>().color = Color.Black;
            healthNum.GetComponent<TextComponent>().normColor = Color.Black;
            return healthNum;
        }
        public Entity MakeClickable(Vector2 pos, string strin, Action action)
        {
            Entity fontEnt = MakeText(pos, strin);
            fontEnt.AddComponent(new ClickableComponent());
            fontEnt.GetComponent<ClickableComponent>().action = action;
            return fontEnt;
        }
        public void newCascadingText(string str)
        {
            for (int j = 0; text[j] != null; j++)
            {
                text[j].GetComponent<TransformComponent>().position.Y -= 32;
            }
            text[i] = MakeText(textPos, str);
            i++;
        }
        public void update(GameTime gameTime)
        {
            player = engine.GetEntitiesFor(Family.All(typeof(PlayerComponent)).Get());           

            if (player.Count > 0)
            {                
                playerComp = player[0].GetComponent<PlayerComponent>();

                if (player.Count > 1)
                {
                    campComp = player[1].GetComponent<PlayerComponent>();

                    timer++;
                    {
                        //if (textfight == 1)
                        //{


                        //    if (campComponent.student.hp <= 0 && timer > time)
                        //    {
                        //        campComponent.student.armySize--;
                        //        campComponent.student.hp = campComponent.student.startingHp;
                        //        newCascadingText("Opp Student Slain; " + campComponent.student.armySize.ToString() + " opp students left");
                        //        timer = 0;
                        //    }
                        //    if (playerComp.nathan.hp <= 0 && timer > time)
                        //    {
                        //        playerComp.nathan.armySize--;
                        //        playerComp.nathan.hp = playerComp.nathan.startingHp;
                        //        newCascadingText("Nathan Slain; " + playerComp.nathan.armySize.ToString() + " nathans left");
                        //        timer = 0;
                        //    }

                        //    while (playerComp.armyNum > 0 && campComponent.student.armySize > 0 && timer > time)
                        //    {
                        //        if (alt == 0)
                        //        {
                        //            campComponent.student.hp -= playerComp.nathan.armySize;
                        //            newCascadingText("Nathan deals " + (playerComp.nathan.armySize * playerComp.nathan.damage).ToString() + "; Opp hp: " + campComponent.student.hp.ToString());
                        //            alt = 1;
                        //        }
                        //        else if (alt == 1)
                        //        {
                        //            playerComp.nathan.hp -= campComponent.student.damage;
                        //            newCascadingText("Opp dealt " + campComponent.student.damage.ToString() + "; Nathan hp: " + playerComp.nathan.hp.ToString());
                        //            alt = 0;
                        //        }
                        //        timer = 0;
                        //    }

                        //    if ((playerComp.armyNum <= 0 || campComponent.student.armySize <= 0) && timer > time && win == 0)
                        //    {

                        //        winClick = MakeClickable(new Vector2(textPos.X, textPos.Y + 64), "You Win", winAction);
                        //        win = 1;
                        //    }
                        ////}
                    }
                    {
                        if (timer > time)
                        {
                            void UnitDead(Unit unit, Entity pla)
                            {
                                if (unit.hp <= 0 && unit.armySize > 0)
                                {
                                    unit.armySize--;
                                    unit.hp = unit.startingHp;
                                    unit.textHp.GetComponent<TextComponent>().str = unit.hp.ToString();
                                    unit.textArmySize.GetComponent<TextComponent>().str = unit.armySize.ToString();
                                }
                                if(unit.armySize <= 0)
                                {
                                    DestroyText(unit);
                                }

                                PlayerComponent plaComp = pla.GetComponent<PlayerComponent>();
                                int m = 0;
                                for (int j = 0; j < glob.unitNumTypes; j++)
                                {
                                    
                                    if (plaComp.unit[j] != null)
                                    {
                                        m += plaComp.unit[j].armySize;
                                    }
                                    
                                }
                                plaComp.armyNum = m;

                            }

                            for(int i = 0; i < glob.unitNumTypes; i++)
                            {

                                if (campComp.unit[i] != null)
                                {
                                    UnitDead(campComp.unit[i], player[1]);
                                }

                                if (playerComp.unit[i] != null)
                                {
                                    UnitDead(playerComp.unit[i], player[0]);
                                }
                            }


                            if (playerComp.armyNum > 0 && campComp.armyNum > 0) 
                            {
                                int CheckSpace(Unit unit)
                                {
                                    int free = 1;
                                    for (int i = 0; i < glob.unitNumTypes; i++)
                                    {
                                        if (playerComp.unit[i] != null && playerComp.unit[i].armySize > 0)
                                        {
                                            if (unit.distance + 1 == playerComp.unit[i].distance)
                                            {
                                                free = 0;
                                            }
                                        }
                                    }
                                    return free;
                                }
                                void UnitMove(Unit unit, int isAlly)
                                {
                                    if (isAlly == 0)
                                    {
                                        
                                        unit.distance -= 1;

                                    }
                                    else
                                    {
                                        //if (CheckSpace(unit)==1)
                                        //{

                                            unit.distance += 1;
                                        //}

                                    }
                                    unit.textArmySize.GetComponent<TransformComponent>().position.Y = glob.startPosy + unit.distance * 32;
                                    unit.textHp.GetComponent<TransformComponent>().position.Y = glob.startPosy + unit.distance * 32;
                                    unit.textSprite.GetComponent<TransformComponent>().position.Y = glob.startPosy + unit.distance * 32;
                                }
                                void UnitFight(Unit UnitDeals, Unit[] UnitTakes, int isUnitDealsAlly)
                                {
                                    Unit closeUnit;
                                    if (UnitTakes[0] != null && UnitTakes[0].armySize > 0)
                                    {
                                        closeUnit = UnitTakes[0];
                                    }
                                    else
                                    {
                                        int l;
                                        for (l = 0; UnitTakes[l].armySize <= 0 || UnitTakes[l] == null; l++)
                                        {

                                        }
                                        closeUnit = UnitTakes[l];
                                    }

                                    for(int i = 1; i < glob.unitNumTypes; i++)
                                    {

                                        if(UnitTakes[i] != null && UnitTakes[i].armySize > 0 && (Math.Abs(UnitTakes[i].distance - UnitDeals.distance) <= Math.Abs(closeUnit.distance - UnitDeals.distance)))
                                        {
                                            closeUnit = UnitTakes[i];
                                        }
                                    }

                                    if (Math.Abs(closeUnit.distance - UnitDeals.distance) <= UnitDeals.range)
                                    {
                                        closeUnit.hp -= UnitDeals.armySize * UnitDeals.damage;
                                        closeUnit.textHp.GetComponent<TextComponent>().str = closeUnit.hp.ToString();
                                    }
                                    else
                                    {
                                        UnitMove(UnitDeals, isUnitDealsAlly);
                                    }
                                }

                                if (alt == 0)
                                {
                                    for (int i = 0; i < glob.unitNumTypes; i++)
                                    {
                                        if (playerComp.unit[i] != null && playerComp.unit[i].armySize > 0)
                                        {
                                            UnitFight(playerComp.unit[i], campComp.unit, 1);

                                            if (playerComp.unit[i].upgradeType == 1 && playerComp.unit[i].upgradeLevel > 0)
                                            {
                                                
                                                playerComp.unit[i].upgradeSethTimer++;
                                                if (playerComp.unit[i].upgradeSethTimer >= 5)
                                                {
                                                    playerComp.unit[i].upgradeSethTimer = 0;

                                                    Vector2 posUnitText = new Vector2(320, glob.startPosy);
                                                    int UnitTextDist = 64;
                                                    
                                                    Unit MakeUnit(Unit unit, int armysize, int hp, int damage, string symbol, string name, int range, int isAlly)
                                                    {
                                                        unit.armySize = armysize;
                                                        unit.hp = unit.startingHp = hp;
                                                        unit.damage = damage;
                                                        unit.symbol = symbol;
                                                        unit.range = range;
                                                        unit.name = name;


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
                                                    Entity MakeUnitText(Unit unit)
                                                    {

                                                        unit.textSprite = MakeText(new Vector2(posUnitText.X, posUnitText.Y + unit.distance * 32), unit.symbol);
                                                        unit.textHp = MakeText(new Vector2(posUnitText.X + UnitTextDist, posUnitText.Y + (unit.distance * 32)), unit.hp.ToString());
                                                        unit.textArmySize = MakeText(new Vector2(posUnitText.X - UnitTextDist + 16, posUnitText.Y + (unit.distance * 32)), unit.armySize.ToString());
                                                        return unit.textSprite;
                                                    }

                                                    if (playerComp.unit[10] == null)
                                                    {
                                                        playerComp.unit[10] = MakeUnit(new Unit(), 1, 1, 1, "R", "Recruit", 1, 1);
                                                    }
                                                    else
                                                    {
                                                        playerComp.unit[10].armySize++;
                                                        playerComp.unit[10].textArmySize.GetComponent<TextComponent>().str = playerComp.unit[10].armySize.ToString();
                                                    }
                                                    if(recText == null)
                                                    {
                                                        recText = MakeUnitText(playerComp.unit[10]);
                                                    }                                                    

                                                }
                                            }
                                        }
                                        alt = 1;
                                    }
                                    
                                }
                                else
                                {
                                    for (int i = 0; i < glob.unitNumTypes; i++)
                                    {
                                        if (campComp.unit[i] != null && campComp.unit[i].armySize > 0)
                                        {
                                            UnitFight(campComp.unit[i], playerComp.unit, 0);
                                        }
                                    }
                                    alt = 0;
                                }
                            }
                            else if (end == 0)
                            {
                                
                                if(glob.isRepeat == 1)
                                {
                                    glob.newS = 0;
                                    winAction();
                                    
                                }
                                else
                                {
                                    winClick = MakeClickable(new Vector2(450, textPos.Y + 64), "You Win", winAction);
                                    end = 1;
                                }
                            }
                            timer = 0;
                        }


                    }
                }

            }
        }
    }
}
