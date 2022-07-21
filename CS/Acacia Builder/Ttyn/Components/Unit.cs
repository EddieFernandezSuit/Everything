using Audrey;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tyn.Objects.Components
{
    class Unit 
    {
        public int armySize, hp, damage, startingHp, range, distance;
        public Entity textSprite;
        public Entity textHp;
        public Entity textArmySize;
        public string symbol;
        public string name;
        public int upgradeType, upgradeLevel, upgradeSethTimer;
    }
}
