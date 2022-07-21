using Audrey;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Tyn.Objects.Components
{
    class PlayerComponent : IComponent
    {
        public int armyNum = 1;
        //public Unit nathan;
        //public Unit seth;
        //public Unit erik;

        public Unit[] unit= new Unit[30];
        public PlayerComponent()
        {
            for (int i= 0;i<30;i++)
            {
                unit[i] = null;
            }
        }
        

    }
}
