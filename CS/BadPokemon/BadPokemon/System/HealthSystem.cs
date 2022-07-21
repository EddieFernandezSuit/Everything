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
    public class HealthSystem
    {
        Engine engine;
        public HealthSystem(Engine e1)
        {
            engine = e1;
        }

        public void update(GameTime gameTime)
        {
            ImmutableList<Entity> Ents = engine.GetEntitiesFor(Family.All(typeof(CreatureComponent)).Get());
            for(int i = 0; i< Ents.Count; i++)
            {
                HealthComponent HealthComp = Ents[i].GetComponent<CreatureComponent>().healthBar.GetComponent<HealthComponent>();
                RectangleComponent rectComp = Ents[i].GetComponent<CreatureComponent>().healthBar.GetComponent<RectangleComponent>();
                TextComponent healthTextComp = HealthComp.healthText.GetComponent<TextComponent>();


                healthTextComp.str = HealthComp.hpStat.ToString();                
                rectComp.Rect.Width = (int)(320 * (HealthComp.hpStat / 100.0));
            }
        }
    }
}
