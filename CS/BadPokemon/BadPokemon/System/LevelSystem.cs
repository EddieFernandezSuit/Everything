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
    public class LevelSystem
    {
        Engine engine;
        public LevelSystem(Engine e1)
        {
            engine = e1;
        }

        public void update(GameTime gameTime)
        {
            ImmutableList<Entity> Ents = engine.GetEntitiesFor(Family.All(typeof(LevelComponent)).Get());
            for(int i = 0; i < Ents.Count; i++)
            {
                LevelComponent lvlComp = Ents[i].GetComponent<LevelComponent>();
                RectangleComponent rectComp = Ents[i].GetComponent<RectangleComponent>();
                TextComponent textComp = Ents[i].GetComponent<LevelComponent>().levelText.GetComponent<TextComponent>();

                textComp.str = "Lvl: " + lvlComp.level.ToString();
                rectComp.Rect.Width = (int)(320 * (lvlComp.exp / 10.0));
                
                if(lvlComp.exp >= 10)
                {
                    lvlComp.exp = 0;
                    lvlComp.level++;

                    ImmutableList<Entity> Ents2 = engine.GetEntitiesFor(Family.All(typeof(StatShellComponent)).Get());
                    StatShellComponent statComp = Ents2[0].GetComponent<StatShellComponent>();
                    if(statComp.exp >= 10)
                    {
                        statComp.exp = 0;
                        statComp.lvl++;
                    }
                    
                }
            }

        }
    }
}
