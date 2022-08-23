// Online C# Editor for free
// Write, Edit and Run your C# code using C# Online Compiler

using System;
using System.Collections.Generic;

public class HelloWorld
{
    public static void Main(string[] args)
    {
        // Print("100");
        // Print(100);
        // string str = Print("100");
        // int num = Print(100);
        // int num2 = Add("100", "100");
        // int num3 = Add(100,100);
        // Console.WriteLine(num2);
        // Console.WriteLine(num3);
        
        int[] res = Intertwine<int>(new int[]{1,2,3}, new int[]{4,5,6});
        // int[] result = Intertwine<char>(new char[]{'1', '2', '3'}, new char[]{'4', '5', '6'});
        int[] result = Intertwine<string>("123", "456");
        
        // string str = "abc";
        // char ch = 'a';
        
        // Console.WriteLine(ch.GetType());
        // Console.WriteLine(str[0].GetType());
        
        for(int i = 0; i < res.Length; i++)
        {
            Console.WriteLine(res[i]);
        }
        
        for(int i = 0; i < result.Length; i++)
        {
            Console.WriteLine(result[i]);
        }
    }
    
    public static T Print<T>(T toPrint)
    {
        Console.WriteLine(toPrint);
        return toPrint;
    }
    
    public static int Add<T>(T addend1, T addend2)
    {
        int a1 = Convert.ToInt32(addend1);
        int a2 = Convert.ToInt32(addend2);
        
        return a1 + a2;
    }
    
    public static int[] Intertwine<T>(T[] first, T[] second)
    {
        string str1 = (string)(object) first;
        string str2 = (string)(object) second;
        
        
        int[] result = new int[str1.Length + str2.Length];
        int j = 0;
        
        for(int i = 0; i < str1.Length; i++)
        {
            // result[i + j] = Int32.Parse(str1[i]);
            result[i + j] = str1[i] - '0';
            j++;
            // result[i + j] = Int32.Parse(str2[i]);
            result[i + j] = str2[i] - '0';
        }
        
        return  result;
    }
}