using Audrey;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Acacia_Builder
{

    public class TextSystem
    {

        Engine engine;
        SpriteBatch spriteBatch;
        public TextSystem(Engine e, SpriteBatch sb)
        {
            engine = e;
            spriteBatch = sb;
            
        }

        public void draw()
        {
            spriteBatch.Begin();
            ImmutableList<Entity> textEntities = engine.GetEntitiesFor(Family.All(typeof(TextComponent)).Get());
            for(int i = 0; i < textEntities.Count; i++)
            {
                TextComponent textComponent = textEntities[i].GetComponent<TextComponent>();
                TransformComponent transformComponent = textEntities[i].GetComponent<TransformComponent>();
                spriteBatch.DrawString(textComponent.font, textComponent.str, transformComponent.position, textComponent.color);

            }
            spriteBatch.End();
        }
    }
}
