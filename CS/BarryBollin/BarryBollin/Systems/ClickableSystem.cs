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
    public class ClickableSystem
    {

        private MouseState currentMouseState;
        private MouseState oldState;
        Engine engine;

        public ClickableSystem(Engine e1)
        {
            engine = e1;
        }

        public void update(GameTime gameTime)
        {
            ImmutableList<Entity> Entities = engine.GetEntitiesFor(Family.All(typeof(ClickableComponent)).Get());
            for (int i = 0; i < Entities.Count; i++)
            {
                currentMouseState = Mouse.GetState();

                TextComponent textComponent = Entities[i].GetComponent<TextComponent>();
                ClickableComponent clickableComponent = Entities[i].GetComponent<ClickableComponent>();
                TransformComponent transformComponent = Entities[i].GetComponent<TransformComponent>();
                                                                      
                if ((transformComponent.position.X <= currentMouseState.X && transformComponent.position.Y <= currentMouseState.Y) && (transformComponent.position.X + (textComponent.font.MeasureString(textComponent.str).X) >= currentMouseState.X && transformComponent.position.Y + (textComponent.font.MeasureString(textComponent.str).Y) >= currentMouseState.Y))
                {
                    textComponent.color = Color.Gold;
                    if (currentMouseState.LeftButton == ButtonState.Pressed && oldState.LeftButton == ButtonState.Released)
                    {
                        if(clickableComponent.action != null)
                        {
                            clickableComponent.action.Invoke();
                        }
                    }                                       
                    oldState = currentMouseState;
                }
                else
                {
                    textComponent.color = textComponent.normColor;
                }
                
            }   
        }
        

    }
}
