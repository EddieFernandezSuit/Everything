using Audrey;
using Microsoft.Xna.Framework;
using Ope.objects.Component;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ope.objects.System
{
    public class RectangleSystem
    {
        Engine engine;
        public RectangleSystem(Engine e1)
        {
            engine = e1;
        }
        
        public void update(GameTime gameTime)
        {
            ImmutableList<Entity> Entities = engine.GetEntitiesFor(Family.All(typeof(RectangleComponent)).Get());
            for(int i = 0; i < Entities.Count; i++)
            {
                RectangleComponent rectComponent = Entities[i].GetComponent<RectangleComponent>();
                TransformComponent transformComponent = Entities[i].GetComponent<TransformComponent>();
                rectComponent.Rect.X = (int)transformComponent.position.X;
                rectComponent.Rect.Y = (int)transformComponent.position.Y;
            }
        }
    }
}
