using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Acacia_Builder
{
    public class PlayerComponent : IComponent
    {
        public int playerNumber;
        public Keys button1;
        public Keys button2;
        public Keys button3;
        public int[] button1Action = new int[2]; //0 is no action, 1 move up, 2 move right, 3 move down, 4 move left
        public int[] button2Action = new int[2];
        public int[] button3Action = new int[2];
        public int moveTimer;
        public int moveTime = (96/4);
        public int isPlayerMove = 0; //0 is dont move, 1 move up, 2 move right, 3 move down, 4 move left
        public int[,] storedAction = new int[2,3];
        public int moveCount = 0;
        public int moveSequence = 0;
        public int moveSequenceOn = 0;
        public int numberOfMoves = 2;
        public Entity[] symbol = new Entity[6];
    }
}
