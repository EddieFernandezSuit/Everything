using System;

public class Program
{
    public static void Main(string[] args){
        BinaryTree tree = new BinaryTree(new Node(1));
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
        tree.PrintTree(tree.root);
    }
}
    
public class Node{
    public Node left = null;
    public Node right = null;
    public int data;

    public Node(int newData){
        data = newData;
    }
}

public class BinaryTree{
    public Node root = null;

    public BinaryTree(Node root){
        this.root = root;
    }

    public void PrintTree(Node node){
        Console.WriteLine(node.data);
        if(node.left != null){
            PrintTree(node.left);
        }
        if(node.right != null){
            PrintTree(node.right);
        }
    }
}