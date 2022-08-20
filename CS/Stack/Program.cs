using System;

public class Program{
    public static void Main(){
        // Console.WriteLine("Hello World");
        List<string> tasks = new List<string>();
        string task = string.Empty;
        int place = 0;
        int counter = 0;

        foreach (string line in System.IO.File.ReadLines("new"))
        {  
            // System.Console.WriteLine(line);
            tasks.Add(line);
            Console.WriteLine(counter.ToString() + ". " + line);
            counter++;
        }
        
        while(task != "exit"){
            // Console.
            Console.Write("Enter Task: ");
            task = Console.ReadLine();
            if (task == "exit")break;

            if(tasks.Count != 0) 
            {
                Console.Write("Enter Place: ");
                place = Int32.Parse(Console.ReadLine());
            }
            else place = 0;
            tasks.Insert(place, task);

            Console.WriteLine("Tasks");
            for(int i = 0; i < tasks.Count; i++){
                Console.WriteLine(i.ToString() + ". " + tasks[i]);
            }
            Console.WriteLine();
        }
        // await File.WriteAllLinesAsync("n", tasks);
        using(StreamWriter writer = new StreamWriter("new"))
        {
            for(int i = 0; i < tasks.Count; i++){
                writer.WriteLine(tasks[i]);
            }
        }

    }
}