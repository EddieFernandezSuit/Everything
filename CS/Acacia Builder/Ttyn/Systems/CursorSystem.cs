using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Tyn.objects.Component;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tyn.objects.System
{
    public class CursorSystem
    {

        Engine engine;
        private SpriteBatch sb;

        public CursorSystem(Engine e1, SpriteBatch sprb)
        {
            engine = e1;
            sb = sprb;
        }

        public void update(GameTime gameTime)
        {
            MouseState currentMouseState = Mouse.GetState();
            ImmutableList<Entity> cursorEntities = engine.GetEntitiesFor(Family.All(typeof(CursorComponent)).Get());
            for (int i = 0; i < cursorEntities.Count; i++)
            {
                TransformComponent transformComponent = cursorEntities[i].GetComponent<TransformComponent>();
                transformComponent.position = new Vector2(currentMouseState.X, currentMouseState.Y);

            }
        }

        public void draw()
        {
            sb.Begin();
            ImmutableList<Entity> Entities = engine.GetEntitiesFor(Family.All(typeof(CursorComponent)).Get());
            for (int i = 0; i < Entities.Count; i++)
            {
                TextureComponent textureComponent = Entities[i].GetComponent<TextureComponent>();
                TransformComponent transformComponent = Entities[i].GetComponent<TransformComponent>();
                sb.Draw(textureComponent.texture, transformComponent.position, Color.LightGoldenrodYellow);

            }
            sb.End();
        }
    }
}
