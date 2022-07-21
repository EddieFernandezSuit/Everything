/// &lt;summary&gt;
/// Input: A singly linked list that contains references to random nodes within the list
/// Output: A duplicate copy of the list with no dependency on the original
/// &lt;/summary&gt;


class LinkedList
{
    Random rnd = new Random();
    public Node head;
    public void Append(string tag)
    {
        Node nextNode = new Node(tag);
        nextNode.next = head;
        head = nextNode;
        head.reference = RandomNode(head);
    }

    //input: 
    //output: random node within the input linkedlist
    public Node RandomNode(Node node)
    {
        Node first = node;
        int num = rnd.Next(10);

        for(int i = 0; i < num; i++){
            if(node.next != null)
            {
                node = node.next;
            }
            else
            {
                node = first;
            }
        }
        return node;
    }

    static Node DuplicateList(Node list)
    {
        Node node = new Node(list.tag);
        Node head = node;

        while(list.next != null)
        {
            node.next = new Node(list.next.tag);
            node.reference = new Node(list.reference.tag);
            node = node.next;
            list = list.next;
        }
        node.reference = new Node(list.reference.tag);
        return head;
    }   
    
    static void Main(string[] args)
    {
        LinkedList linkedList = new LinkedList();

        linkedList.Append("a");
        linkedList.Append("b");
        linkedList.Append("c");
        linkedList.Append("d");
        linkedList.Append("e");

        linkedList.head.Print();
        Node dupLinkedList = DuplicateList(linkedList.head);
        dupLinkedList.Print();
    }
}

class Node
{
    public Node next;
    public string tag;
    public Node reference;
    public Node(string newTag)
    {
        this.next = null;
        this.tag = newTag;
        this.reference = null;
    }

    public void Print()
    {
        Node node = this;
        while(node != null)
        {
            Console.WriteLine("Tag: " + node.tag + " Reference Tag: " + node.reference.tag);
            node = node.next;
        }
    }
}
