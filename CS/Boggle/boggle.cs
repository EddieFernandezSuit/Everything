using System.Collections;

public class Boggle
{
    string[] validWords;
    /// &lt;summary&gt;
    /// Prior to solving any board, configure the legal words.
    /// &lt;/summary&gt;
    public Boggle(string[] validWords)
    {
        this.validWords = validWords;
    }

    /// &lt;summary&gt;
    /// Find all words on the specified board, returning an array of them.
    /// &lt;/summary&gt;
    /// &lt;param name=”width”&gt;Width of the board, e.g. 4 for a retail Boggle game&lt;/param&gt;
    /// &lt;param name=”height”&gt;Height of the board, e.g. 4 for a retail Boggle game&lt;/param&gt;
    /// &lt;param name=”boardLetters”&gt;Board as width*height characters in row-major order &lt;/param&gt;
    public string[] SolveBoard(int width, int height, string boardLetters)
    {
        char[,] board = new char[height,width];
        // boardLetters = "yoxrbaved";
        int h = 0;
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                board[i,j] = boardLetters[h];
                h++;
            }
        }
        bool[,] index = new bool[height,width];
        index[0,0] = true;
        List<string> foundWords = new List<string>();
        for(int i = 0; i < height; i++)
        {
            for(int j =0; j<width;j++)
            {
                Recurse(j,i,height,width,"",board,index,foundWords);
            }
        }
        foreach(string foundWord in foundWords)
        {
            Console.WriteLine(foundWord);
        }

        string[] foundWordsArr = new string[foundWords.Count];

        for(int i = 0; i < foundWords.Count; i++)
        {
            foundWordsArr[i] = foundWords[i];
        }
        return foundWordsArr;
    }

    public void PrintBoard(char[,] board)
    {
        for(int i = 0; i < board.GetLength(0); i++)
        {
            string row = "";
            for(int j = 0; j < board.GetLength(1); j++)
            {
                row += board[i,j];
            }
            Console.WriteLine(row);
        }
    }
    bool CheckWord(string word)
    {
        foreach(string validWord in validWords)
        {            
            if(validWord != null && validWord== word)
            {
                return true;
            }
        }
        return false;
    }

    void Recurse(int rowIndex, int collIndex, int height, int width, string currentWord,char[,] board, bool[,] index, List<string> foundWords)
    {
        bool[,] newIndex = new bool[height,width];
        Array.Copy(index, newIndex,index.Length);
        newIndex[collIndex, rowIndex] = true;
        currentWord += board[collIndex, rowIndex];
        if(currentWord.Length >= 3 && CheckWord(currentWord))
        {
            foundWords.Add(currentWord);
        }
        if(rowIndex < 2 && !index[collIndex,rowIndex+1])
        {
            Recurse(rowIndex+1,collIndex, height, width, currentWord, board, newIndex, foundWords);
        }
        if(rowIndex < 2 && collIndex < 2 && !index[collIndex+1,rowIndex+1])
        {
            Recurse(rowIndex+1,collIndex+1, height, width, currentWord, board, newIndex, foundWords);
        }
        if(collIndex < 2 && !index[collIndex+1,rowIndex])
        {
            Recurse(rowIndex,collIndex+1, height, width, currentWord, board, newIndex, foundWords);
        }
        if(rowIndex > 0 && collIndex < 2 && !index[collIndex+1,rowIndex-1])
        {
            Recurse(rowIndex-1,collIndex+1, height, width, currentWord, board, newIndex, foundWords);
        }
        if(rowIndex > 0 && !index[collIndex,rowIndex-1])
        {
            Recurse(rowIndex-1,collIndex, height, width, currentWord, board, newIndex, foundWords);
        }
        if(rowIndex > 0 && collIndex > 0 && !index[collIndex-1,rowIndex-1])
        {
            Recurse(rowIndex-1,collIndex-1, height, width, currentWord, board, newIndex, foundWords);
        }
        if(collIndex > 0 && !index[collIndex-1,rowIndex])
        {
            Recurse(rowIndex, collIndex-1, height, width, currentWord, board, newIndex, foundWords);
        }
        if(rowIndex < 2 && collIndex > 0 && !index[collIndex-1,rowIndex+1])
        {
            Recurse(rowIndex+1,collIndex-1, height, width, currentWord, board, newIndex, foundWords);
        }
    }

    static void Main(string[] args)
    {
        var wordDictionary = System.IO.File.ReadLines("1000MostCommonWords.txt");
        var dictionary = new string[466550];
        int index = 0;
        foreach(string word in wordDictionary)
        {
            dictionary[index] = word.ToLower();
            index += 1;
        }
        Boggle boggle = new Boggle(dictionary);
        Random rnd = new Random();
        string newBoardLetters = "";
        int width = 3;
        int height = 3;
        for(int i = 0; i < width*height; i++)
        {
            newBoardLetters += (char)rnd.Next('a','z');
        }
        boggle.SolveBoard(height,height,newBoardLetters);

        
    }
}