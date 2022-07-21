using Audrey;
using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Acacia_Builder
{
    public class ClickableComponent : IComponent
    {
        public Action action = null;
    }
}
