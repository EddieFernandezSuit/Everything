

class Program
{
    public static void Main()
    {
        // int[] arr1 = new int[]{1,3,5,7};
        // int[] arr2 = new int[]{2,4,5,6,8};

        string arr1 = "999";
        string arr2 = "001";

        int mergedArray = Program.Merge<string>(arr1, arr2);
        
        // for(int i = 0; i < mergedArray.Length; i++)
        // {
        //     Console.WriteLine(mergedArray[i]);
        // }

        // string str = Program.AddString("999", "1");
        // Console.WriteLine(str);

    }
    public static T Merge<T>(T array1str, T array2str)
    {
        
        int[] array1 = new int[3];
        int[] array2 = new int[3];

        int[] mergedArray = new int[array1.Length + array2.Length];

        int i = 0;
        int j = 0;

        while(i < array1.Length && j < array2.Length)
        {
            if(array1[i] < array2[j])
            {
                mergedArray[i + j] = array1[i];
                i++;
            }
            else
            {
                mergedArray[i + j] = array2[j];
                j++;
            }
        }

        while(i < array1.Length)
        {
            mergedArray[i + j] = array1[i];
            i++;
        }

        while(j < array2.Length)
        {
            mergedArray[i + j] = array2[j];
            j++;
        }

        return mergedArray;
    }

    // public static int[] Merge(int[] array1, int[] array2)
    // {
    //     int[] mergedArray = new int[array1.Length + array2.Length];

    //     int i = 0;
    //     int j = 0;

    //     while(i < array1.Length && j < array2.Length)
    //     {
    //         if(array1[i].Length < array2[j].Length)
    //         {
    //             mergedArray[i + j] = array1[i];
    //             i++;
    //         }
    //         else
    //         {
    //             mergedArray[i + j] = array2[j];
    //             j++;
    //         }
    //     }

    //     while(i < array1.Length)
    //     {
    //         mergedArray[i + j] = array1[i];
    //         i++;
    //     }

    //     while(j < array2.Length)
    //     {
    //         mergedArray[i + j] = array2[j];
    //         j++;
    //     }
        
    //     return mergedArray;
    // }


    public static string AddString(string str1, string str2)
    {
        string zeros = "";
        string resultString = "";
        int difference = str1.Length - str2.Length;

        if(difference < 0)
        {
            difference *= -1;
        }

        for(int i = 0; i < difference; i++)
        {
            zeros += "0";
        }

        if(str1.Length < str2.Length)
        {
            // zeros += str1;
            str1 = zeros + str1;
        }
        else
        {
            // zeros += str2;
            str2 = zeros + str2;
        }

        int carryOver = 0;
        int newNum = 0;
        int remainder = 0;

        Console.WriteLine(str1);
        Console.WriteLine(str2);


        for(int i = str1.Length - 1; i >= 0; i--)
        {
            newNum = str1[i] - '0' + str2[i] - '0' + carryOver;
            remainder = newNum % 10;
            carryOver = newNum / 10;
            
            resultString = remainder.ToString() + resultString;
        }

        if (carryOver > 0)
        {
            resultString = carryOver.ToString() + resultString;
        }

        return resultString;
    }
}