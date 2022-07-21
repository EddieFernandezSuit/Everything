using Audrey;
using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ope.objects.Component
{
    public class ClickableComponent : IComponent
    {
        public Action action = null;
    }
}
