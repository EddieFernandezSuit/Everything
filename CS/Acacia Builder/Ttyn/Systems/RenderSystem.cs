using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Tyn.objects.Component;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tyn
{
    public class RenderSystem
    {
        private Engine engine;
        private SpriteBatch sb;
        public RenderSystem(Engine e, SpriteBatch sprb)
        {
            engine = e;
            sb = sprb;
        }

        public void draw()
        {
            SpriteEffects se;
            sb.Begin();
            ImmutableList<Entity> textureEntities = engine.GetEntitiesFor(Family.All(typeof(TextureComponent)).Get());
            for(int i = 0; i < textureEntities.Count; i++)
            {
                TextureComponent textureComponent = textureEntities[i].GetComponent<TextureComponent>();
                TransformComponent transformComponent = textureEntities[i].GetComponent<TransformComponent>();
                RectangleComponent rectComponent = textureEntities[i].GetComponent<RectangleComponent>();

                if (textureComponent.horizontalFLip == 1)                   
                {
                    se = SpriteEffects.FlipHorizontally;
                }
                else
                {
                    se = SpriteEffects.None;
                }
                sb.Draw(textureComponent.texture, transformComponent.position, new Rectangle(0, 0, rectComponent.Rect.Width, rectComponent.Rect.Height), Color.LightGoldenrodYellow, transformComponent.angle, textureComponent.origin, textureComponent.size, se, 1);


            }
            sb.End();
        }
    }
}
