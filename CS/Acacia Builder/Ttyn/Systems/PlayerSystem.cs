using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Tyn.Objects.Components;

namespace Tyn.Objects.Systems
{
    public class PlayerSystem
    {
        
        
        Engine engine;

        public PlayerSystem(Engine e1)
        {
            engine = e1;
        }


        public void update(GameTime gameTime)
        {
            //ImmutableList<Entity> Entities = engine.GetEntitiesFor(Family.All(typeof(PlayerComponent)).Get());
            //for(int i =0; i < Entities.Count; i++)
            //{
            //    PlayerComponent playerComp = Entities[i].GetComponent<PlayerComponent>();

            //    for (int j = 0; j < @static.unitNumTypes;j++) {
            //        int m = 0;
            //        if (playerComp.unit[j] != null)
            //        {
            //            m += playerComp.unit[j].armySize;
            //        }
            //        playerComp.armyNum = m;
            //    }
            //    //for (int j =0; playerComp.unit[j] != null; j++)
            //    //{
            //    //    playerComp.armyNum += playerComp.unit[j].armySize;
            //    //}
            //}

        }
    }
}
