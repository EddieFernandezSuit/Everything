using Audrey;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ope.objects.Component
{
    class StatShellComponent : IComponent
    {
        public Entity creature;
        public int hp;
        public int exp;
        public int lvl;
        public int damage;
    }
}
