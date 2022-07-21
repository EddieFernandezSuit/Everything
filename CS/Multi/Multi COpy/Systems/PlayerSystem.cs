using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Acacia_Builder
{
    
    public class PlayerSystem
    {

        int playerMoveCount = 0;
        int playerMoveSpeed = 4;
        int playerTurnOrder = 1;
        Engine engine;
        KeyboardState oldState = new KeyboardState();
        Random rnd = new Random();

        public PlayerSystem(Engine e1)
        {
            engine = e1;
        }

        public void update(GameTime gameTime)
        {
            KeyboardState newState = Keyboard.GetState();
            ImmutableList<Entity> Entities = engine.GetEntitiesFor(Family.All(typeof(PlayerComponent)).Get());
            for (int i = 0; i < Entities.Count; i++)
            {
                PlayerComponent playerComp = Entities[i].GetComponent<PlayerComponent>();
                TransformComponent transformComponent = Entities[i].GetComponent<TransformComponent>();
                void destroyPlayer(int j)
                {
                    int p = Entities[j].GetComponent<PlayerComponent>().playerNumber;
                    for (int k = 0; k < Entities.Count; k++)
                    {
                        if (p == 1)
                        {
                            Entities[k].GetComponent<PlayerComponent>().playerNumber--;
                        }
                        else if (p == 2)
                        {
                            if (Entities[k].GetComponent<PlayerComponent>().playerNumber > 2)
                            {
                                Entities[k].GetComponent<PlayerComponent>().playerNumber--;
                            }
                        }
                        else if (p == 3)
                        {
                            if (Entities[k].GetComponent<PlayerComponent>().playerNumber > 3)
                            {
                                Entities[k].GetComponent<PlayerComponent>().playerNumber--;
                            }
                        }
                        else
                        {
                            p = 1;
                        }
                    }
                    engine.DestroyEntity(Entities[j]);
                    for (int k = 0; k < Entities.Count; k++)
                    {
                        if(p == Entities[k].GetComponent<PlayerComponent>().playerNumber)
                        {
                            playerTurnOrder--;                            
                            Entities[k].GetComponent<PlayerComponent>().moveCount = 0;
                            Move(Entities[k].GetComponent<PlayerComponent>().storedAction[0, Entities[k].GetComponent<PlayerComponent>().moveSequenceOn], Entities[k]);
                        }
                    }
                        
                }

                void Move(int buttonNumAction, Entity p)
                {

                    PlayerComponent pComp = p.GetComponent<PlayerComponent>();
                    TransformComponent tComp = p.GetComponent<TransformComponent>();
                    if (pComp.playerNumber == playerTurnOrder)
                    {
                        pComp.moveTimer = 0;
                        pComp.isPlayerMove = 1;
                        
                        switch (buttonNumAction)
                        {
                            case 1:
                                //Game1.screenWidth / 2 - 288 / 2, (Game1.screenHeight / 2) - 288 / 2 + 192
                                if (tComp.position.Y > (Game1.screenHeight / 2) - 288 / 2)
                                {
                                    tComp.dy = -playerMoveSpeed;
                                }
                                break;
                            case 2:
                                if (tComp.position.X < Game1.screenWidth / 2 - 288 / 2 + 96 + 96)
                                {
                                    tComp.dx = playerMoveSpeed;
                                }
                                break;
                            case 3:
                                if (tComp.position.Y < Game1.screenHeight / 2 - 288 / 2 + 192)
                                {
                                    tComp.dy = playerMoveSpeed;
                                }
                                break;
                            case 4:
                                if (tComp.position.X > Game1.screenWidth / 2 - 288 / 2)
                                {
                                    tComp.dx = -playerMoveSpeed;
                                }
                                break;
                            case 5:
                                MenuState.MakeAttackAnimation(new Vector2(tComp.position.X + 48, tComp.position.Y + 48), engine, 0);
                                for(int j = 0;j < Entities.Count; j++)
                                {
                                    if (tComp.position.Y - 96 == Entities[j].GetComponent<TransformComponent>().position.Y && tComp.position.X == Entities[j].GetComponent<TransformComponent>().position.X)
                                    {

                                        destroyPlayer(j);
                                    }
                                }
                                break;
                            case 6:
                                MenuState.MakeAttackAnimation(new Vector2(tComp.position.X + 48, tComp.position.Y + 48), engine, (float)3.14 / 2);
                                for (int j = 0; j < Entities.Count; j++)
                                {
                                    if (tComp.position.X + 96 == Entities[j].GetComponent<TransformComponent>().position.X && tComp.position.Y == Entities[j].GetComponent<TransformComponent>().position.Y)
                                    {
                                        destroyPlayer(j);
                                    }
                                }
                                break;
                            case 7:
                                MenuState.MakeAttackAnimation(new Vector2(tComp.position.X + 48, tComp.position.Y + 48), engine, (float)3.14);
                                for (int j = 0; j < Entities.Count; j++)
                                {
                                    if (tComp.position.Y + 96 == Entities[j].GetComponent<TransformComponent>().position.Y && tComp.position.X == Entities[j].GetComponent<TransformComponent>().position.X)
                                    {
                                        destroyPlayer(j);
                                    }
                                }
                                break;
                            case 8:
                                MenuState.MakeAttackAnimation(new Vector2(tComp.position.X + 48, tComp.position.Y + 48), engine, (float)3.14 * 3 / 2);
                                for (int j = 0; j < Entities.Count; j++)
                                {
                                    if (tComp.position.X - 96 == Entities[j].GetComponent<TransformComponent>().position.X && tComp.position.Y == Entities[j].GetComponent<TransformComponent>().position.Y)
                                    {
                                        destroyPlayer(j);
                                    }
                                }
                                break;
                        }
                    }

                }
                void StopMove()
                {
                     if (playerComp.isPlayerMove != 0)
                    {
                        playerComp.moveTimer++;
                        if (playerComp.moveTimer > playerComp.moveTime)
                        {
                            playerComp.isPlayerMove = 0;
                            transformComponent.dy = 0;
                            transformComponent.dx = 0;

                            int roundDown(int value, int multiplier)
                            {
                                return value / multiplier * multiplier;
                            }
                            transformComponent.position.X = roundDown((int)transformComponent.position.X ,96) + 48 ;
                            transformComponent.position.Y = roundDown((int)transformComponent.position.Y,96)  + 48;

                            
                            
                            playerComp.moveCount++;
                            if (playerComp.moveCount == 1)
                            {
                                playerTurnOrder++;
                                for (int j = 0; j < Entities.Count; j++)
                                {
                                    if (playerTurnOrder <= Entities.Count)
                                    {
                                        if (Entities[j].GetComponent<PlayerComponent>().playerNumber == playerTurnOrder)
                                        {
                                            Move(Entities[j].GetComponent<PlayerComponent>().storedAction[0, Entities[j].GetComponent<PlayerComponent>().moveSequenceOn], Entities[j]);
                                        }
                                    }
                                    else
                                    {
                                        if (Entities[j].GetComponent<PlayerComponent>().playerNumber == 1)
                                        {
                                            playerTurnOrder = 1;
                                            Move(Entities[j].GetComponent<PlayerComponent>().storedAction[1, Entities[j].GetComponent<PlayerComponent>().moveSequenceOn], Entities[j]);
                                        }
                                    }
                                    
                                }
                                //playerComp.storedAction[0, playerComp.moveSequenceOn] = 0;
                                //Move(playerComp.storedAction[1, playerComp.moveSequenceOn], Entities[i]);
                            }
                            else
                            {
                                    
                                playerComp.moveCount = 0;

                                playerTurnOrder++;
                                if (playerTurnOrder <= Entities.Count)
                                {
                                    
                                    //playerComp.storedAction[1, playerComp.moveSequenceOn] = 0;
                                    for(int j = 0; j < Entities.Count; j++)
                                    {

                                        if(Entities[j].GetComponent<PlayerComponent>().playerNumber == playerTurnOrder)
                                        {
                                             Move(Entities[j].GetComponent<PlayerComponent>().storedAction[1, Entities[j].GetComponent<PlayerComponent>().moveSequenceOn],Entities[j]);
                                            
                                        }
                                    }
                                }
                                else
                                {
                                    playerTurnOrder = 1;
                                    playerMoveCount = 0;
                                    if (playerComp.moveSequence-1 > playerComp.moveSequenceOn)
                                    {                                        
                                        for (int j = 0; j < Entities.Count; j++)
                                        {
                                            Entities[j].GetComponent<PlayerComponent>().moveSequenceOn++;
                                            if (Entities[j].GetComponent<PlayerComponent>().playerNumber == playerTurnOrder)
                                            {
                                                Move(Entities[j].GetComponent<PlayerComponent>().storedAction[0, Entities[j].GetComponent<PlayerComponent>().moveSequenceOn], Entities[j]);

                                            }
                                        }
                                    }
                                    else
                                    {

                                        playerMoveCount = 0;
                                        playerTurnOrder = 1;
                                        for (int j = 0; j < Entities.Count; j++)
                                        {
                                            Entities[j].GetComponent<PlayerComponent>().moveSequenceOn = 0;
                                            Entities[j].GetComponent<PlayerComponent>().moveSequence = 0;
                                            Entities[j].GetComponent<PlayerComponent>().isPlayerMove = 0;
                                            Entities[j].GetComponent<PlayerComponent>().button1Action[1] = rnd.Next(1, 9);
                                            Entities[j].GetComponent<PlayerComponent>().button2Action[1] = rnd.Next(1, 9);
                                            Entities[j].GetComponent<PlayerComponent>().button3Action[1] = rnd.Next(1, 9);
                                            switch (Entities[j].GetComponent<PlayerComponent>().button1Action[1])
                                            {
                                                case 1:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TransformComponent>().angle = 0;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TextureComponent>().texture = MenuState.actionImage1;
                                                    break;
                                                case 2:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TransformComponent>().angle = (float)3.14 / 2;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TextureComponent>().texture = MenuState.actionImage1;
                                                    break;
                                                case 3:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TransformComponent>().angle = (float)3.14;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TextureComponent>().texture = MenuState.actionImage1;
                                                    break;
                                                case 4:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TransformComponent>().angle = (float)3.14 * 3 / 2;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TextureComponent>().texture = MenuState.actionImage1;
                                                    break;
                                                case 5:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TransformComponent>().angle = 0;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TextureComponent>().texture = MenuState.actionAttack;
                                                    break;
                                                case 6:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TransformComponent>().angle = (float)3.14 / 2;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TextureComponent>().texture = MenuState.actionAttack;
                                                    break;
                                                case 7:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TransformComponent>().angle = (float)3.14;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TextureComponent>().texture = MenuState.actionAttack;
                                                    break;
                                                case 8:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TransformComponent>().angle = (float)3.14 * 3/ 2;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[3].GetComponent<TextureComponent>().texture = MenuState.actionAttack;
                                                    break;
                                            }
                                            switch (Entities[j].GetComponent<PlayerComponent>().button2Action[1])
                                            {
                                                case 1:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TransformComponent>().angle = 0;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TextureComponent>().texture = MenuState.actionImage1;
                                                    break;
                                                case 2:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TransformComponent>().angle = (float)3.14 / 2;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TextureComponent>().texture = MenuState.actionImage1;
                                                    break;
                                                case 3:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TransformComponent>().angle = (float)3.14;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TextureComponent>().texture = MenuState.actionImage1;
                                                    break;
                                                case 4:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TransformComponent>().angle = (float)3.14 * 3 / 2;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TextureComponent>().texture = MenuState.actionImage1;
                                                    break;
                                                case 5:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TransformComponent>().angle = 0;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TextureComponent>().texture = MenuState.actionAttack;
                                                    break;
                                                case 6:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TransformComponent>().angle = (float)3.14 / 2;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TextureComponent>().texture = MenuState.actionAttack;
                                                    break;
                                                case 7:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TransformComponent>().angle = (float)3.14;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TextureComponent>().texture = MenuState.actionAttack;
                                                    break;
                                                case 8:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TransformComponent>().angle = (float)3.14 * 3 / 2;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[4].GetComponent<TextureComponent>().texture = MenuState.actionAttack;
                                                    break;
                                            }
                                            switch (Entities[j].GetComponent<PlayerComponent>().button3Action[1])
                                            {
                                                case 1:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TransformComponent>().angle = 0;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TextureComponent>().texture = MenuState.actionImage1;
                                                    break;
                                                case 2:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TransformComponent>().angle = (float)3.14 / 2;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TextureComponent>().texture = MenuState.actionImage1;
                                                    break;
                                                case 3:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TransformComponent>().angle = (float)3.14;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TextureComponent>().texture = MenuState.actionImage1;
                                                    break;
                                                case 4:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TransformComponent>().angle = (float)3.14 * 3 / 2;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TextureComponent>().texture = MenuState.actionImage1;
                                                    break;
                                                case 5:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TransformComponent>().angle = 0;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TextureComponent>().texture = MenuState.actionAttack;
                                                    break;
                                                case 6:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TransformComponent>().angle = (float)3.14 / 2;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TextureComponent>().texture = MenuState.actionAttack;
                                                    break;
                                                case 7:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TransformComponent>().angle = (float)3.14;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TextureComponent>().texture = MenuState.actionAttack;
                                                    break;
                                                case 8:
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TransformComponent>().angle = (float)3.14 * 3 / 2;
                                                    Entities[j].GetComponent<PlayerComponent>().symbol[5].GetComponent<TextureComponent>().texture = MenuState.actionAttack;
                                                    break;
                                            }

                                        }
                                        
                                    }
                                }
                                    
                            }
                            
                        }
                        
                    }

                    
                }
                
                if (playerComp.isPlayerMove == 0)
                {
                    
                    //KeyboardState newState = new KeyboardState();
                    void storeActionKey(Keys actKey, int[] buttonNum)
                    {                        
                        if (newState.IsKeyDown(actKey) && oldState.IsKeyUp(actKey) && playerComp.moveSequence < 3)
                        {
                            playerComp.storedAction[0,playerComp.moveSequence] = buttonNum[0];
                            playerComp.storedAction[1,playerComp.moveSequence] = buttonNum[1];
                            playerMoveCount++;
                            playerComp.moveSequence++;
                        }                        
                    }
                    storeActionKey(playerComp.button1, playerComp.button1Action);
                    storeActionKey(playerComp.button2, playerComp.button2Action);
                    storeActionKey(playerComp.button3, playerComp.button3Action);
                    
                    //if (newState.IsKeyDown(Keys.Space))
                    //{
                        if (playerMoveCount >= 3*Entities.Count)
                        {
                            Move(playerComp.storedAction[0,0], Entities[i]);
                        }
                        

                    //}
                }     
                
                StopMove();

            }
            oldState = newState;
        
        }
        

    }
}