using System;

//Algorithm has a worst case scenario time complexity of n^2
//Algorithm has an average case time complexity of linear time
namespace HelloWorld
{
    class Program
    {
        static void Main(string[] args)
        {
            int[] array = new int[]{4,68,4,3,8};
            Program.Sort(array);
        }

        public static void Sort(int[] arr)
        {
            int[] hash = new int[100];
            int[] sortedArray = new int[arr.Length];
			
            int count = 0;

            for(int i = 0; i < arr.Length; i++)
            {
                hash[arr[i]] += 1;
            }

            for(int i = 0; i < hash.Length; i++)
            {
                if(hash[i] != 0)
                {
                    for(int j = 0; j < hash[i]; j++)
                    {
                        sortedArray[count] = i;
                        count += 1;
                    }
                }
            }
            
            for(int i = 0; i < sortedArray.Length;i++)
            {
                Console.WriteLine(sortedArray[i]);
            }
        }
    }
}