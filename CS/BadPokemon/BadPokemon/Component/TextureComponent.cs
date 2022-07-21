using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ope
{
    class TextureComponent : IComponent
    {
        public Texture2D texture;
        public Vector2 origin;
        public int horizontalFLip = 0;
        public float size = 1.0f;
    }
}
