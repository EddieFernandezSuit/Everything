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
    public class AttackSystem
    {
        Engine engine;
        GameStateManager gsm;

        int timer = 0;

        public AttackSystem(Engine e1, GameStateManager gs)
        {
            engine = e1;
            gsm = gs;
        }

        public void update(GameTime gameTime)
        {
            timer++;
            ImmutableList<Entity> enEntities = engine.GetEntitiesFor(Family.All(typeof(CreatureComponent)).Get());
            ImmutableList<Entity> attEntities = engine.GetEntitiesFor(Family.All(typeof(AttackComponent)).Get());
            ImmutableList<Entity> enEntitiesO = engine.GetEntitiesFor(Family.All(typeof(CreatureComponent)).Get());

            for (int i = 0; i < enEntities.Count; i++)
            {
                CreatureComponent creatureComp = enEntities[i].GetComponent<CreatureComponent>();
                TransformComponent enTransformComponent = enEntities[i].GetComponent<TransformComponent>();
                HealthComponent healthComp = creatureComp.healthBar.GetComponent<HealthComponent>();
                LevelComponent lvlComp = creatureComp.lvl.GetComponent<LevelComponent>();
                
                    for (int j = 0; j < attEntities.Count; j++)
                    {
                        TransformComponent attTransformComponent = attEntities[j].GetComponent<TransformComponent>();
                        AttackComponent attComponent = attEntities[j].GetComponent<AttackComponent>();
                        if (attComponent.type != creatureComp.type)
                        {
                            Vector2 direction = enTransformComponent.position - attTransformComponent.position;
                            direction.Normalize();
                            attTransformComponent.position += direction * 10;
                            attComponent.moveEffect();

                            if ((enTransformComponent.position.X <= attTransformComponent.position.X && attComponent.type == 0) ||((enTransformComponent.position.X >= attTransformComponent.position.X && attComponent.type == 1)))
                            {
                                engine.DestroyEntity(attEntities[j]);
                            for(int p = 0; p < enEntitiesO.Count; p++)
                            {
                                if(enEntitiesO[p] != enEntities[i])
                                {
                                    healthComp.hpStat -= enEntitiesO[p].GetComponent<CreatureComponent>().damage;
                                }
                            }                                
                                
                                if (healthComp.hpStat <= 0)
                                {
                                ImmutableList<Entity> statshellEnts = engine.GetEntitiesFor(Family.All(typeof(StatShellComponent)).Get());
                                StatShellComponent ssc = statshellEnts[0].GetComponent<StatShellComponent>();

                                timer = -60;
                                for(int q = 0; q < enEntities.Count; q++)
                                {
                                    if(enEntities[q].GetComponent<CreatureComponent>().type == 0)
                                    {
                                        enEntities[q].GetComponent<CreatureComponent>().lvl.GetComponent<LevelComponent>().exp++;
                                        ssc.exp++;
                                       
                                        
                                        ssc.hp = enEntities[q].GetComponent<CreatureComponent>().healthBar.GetComponent<HealthComponent>().hpStat;

                                    }
                                }
                                
                                    
                                }
                            }
                        }
                    }                 
            }
            if (timer == -1)
            {
                ImmutableList<Entity> ents = engine.GetEntitiesFor(Family.Exclude(typeof(StatShellComponent)).Get());
                timer++;
                while (ents.Count > 0)
                {
                    engine.DestroyEntity(ents[0]);
                }

                gsm.setState(GameStateManager.State.PLAY);
            }
        }
    }
}
