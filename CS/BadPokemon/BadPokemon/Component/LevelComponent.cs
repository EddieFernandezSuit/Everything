using Audrey;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ope.objects.Component
{
    class LevelComponent : IComponent
    {
        public int level = 1;
        public Entity levelText;
        public int exp = 5;
    }
}
