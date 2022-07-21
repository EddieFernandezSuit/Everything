using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;
using Ope.objects.Component;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static GameStateManager;

namespace Ope.objects.System
{
    public class trainerSystem
    {
        Engine engine;
        GameStateManager gsm;

        int tspeed = 1;
        int timer = 60;
        int shake = -3;
        public trainerSystem(Engine e1, GameStateManager gsm1)
        {
            engine = e1;
            gsm = gsm1;
        }
        
        public void update(GameTime gameTime)
        {
            KeyboardState state = Keyboard.GetState();
            

            ImmutableList<Entity> Entities = engine.GetEntitiesFor(Family.All(typeof(TrainerComponent)).Get());
            ImmutableList<Entity> grassEnt = engine.GetEntitiesFor(Family.All(typeof(GrassComponent)).Get());

            for (int i = 0; i < Entities.Count; i++)
            {
                TransformComponent transformComponent = Entities[i].GetComponent<TransformComponent>();

                if (timer < 32/tspeed) {
                    if (shake > 0 && shake <= 5)
                    {
                        transformComponent.angle += (float)Math.PI / 64;
                    }
                    else if (shake == 6)
                    {
                        shake = -5;
                    }
                    else
                    {
                        transformComponent.angle -= (float)Math.PI / 64;
                    }
                    shake += 1;
                }


                for(int j = 0; j < grassEnt.Count; j++)
                {
                    TransformComponent grassTransformComp = grassEnt[j].GetComponent<TransformComponent>();
                    if(transformComponent.position.X-16 == grassTransformComp.position.X && transformComponent.position.Y-16 == grassTransformComp.position.Y)
                    {
                        gsm.battleState.EnterBattle();
                        engine.DestroyEntity(grassEnt[j]);
                        gsm.setState(State.BATTLE);                        
                        gsm.gameState.MakeCursor();
                    }
                }

                if (state.IsKeyDown(Keys.Space))
                {
                    gsm.setState(State.MENU);
                }
                timer++;
                if(timer >= 32/tspeed)
                {
                    transformComponent.dy = transformComponent.dx = 0;
                    if(state.IsKeyDown(Keys.Up))
                    {
                        //transformComponent.position.Y -= tspeed;
                        transformComponent.dy = -tspeed;
                        transformComponent.angle = (float) Math.PI * 2;
                        timer = 0;
                    
                    }
                    else if (state.IsKeyDown(Keys.Down))
                    {
                        //.position.Y += tspeed;
                        transformComponent.dy = tspeed;
                        transformComponent.angle = (float) Math.PI;
                        timer = 0;
                    }
                    else if (state.IsKeyDown(Keys.Right))
                    {
                        //transformComponent.position.X += tspeed;
                        transformComponent.dx = tspeed;
                        transformComponent.angle = (float) Math.PI / 2;
                        timer = 0;
                    }
                    else if (state.IsKeyDown(Keys.Left))
                    {
                        //transformComponent.position.X -= tspeed;
                        transformComponent.dx = -tspeed;
                        transformComponent.angle = (float) Math.PI * 3 / 2;
                        timer = 0;
                    }
                }
            }
        }
    }
}
