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
    public class AttackAnimationSystem
    {

        private MouseState currentMouseState;
        private MouseState oldState;
        Engine engine;

        public AttackAnimationSystem(Engine e1)
        {
            engine = e1;
        }

        public void update(GameTime gameTime)
        {
            ImmutableList<Entity> Entities = engine.GetEntitiesFor(Family.All(typeof(AttackAnimationComponent)).Get());
            for (int i = 0; i < Entities.Count; i++)
            {
                AttackAnimationComponent aac = Entities[i].GetComponent<AttackAnimationComponent>();
                aac.timer++;
                if(aac.timer > aac.time)
                {
                    engine.DestroyEntity(Entities[i]);
                }
                
                
            }   
        }
        

    }
}
