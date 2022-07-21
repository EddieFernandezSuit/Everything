using Audrey;
using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tyn.objects.System
{
    public class TransformSystem
    {
        Engine engine;
        public TransformSystem(Engine e1)
        {
            engine = e1;
        }

        public void update(GameTime gameTime)
        {
            ImmutableList<Entity> Entities = engine.GetEntitiesFor(Family.All(typeof(TransformComponent)).Get());
            for(int i = 0; i < Entities.Count; i++)
            {
                TransformComponent transformComponent = Entities[i].GetComponent<TransformComponent>();
                transformComponent.position.X += transformComponent.dx;
                transformComponent.position.Y += transformComponent.dy;
            }
        }
    }
}
