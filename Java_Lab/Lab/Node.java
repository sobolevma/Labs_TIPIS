

import java.util.ArrayList;


public class Node 
{
    ArrayList<Boolean> way;
    String Letter;
    Integer cost;
    private Node left,right;

    Node(String letter, Integer cost) 
    {
        Letter = letter;
        this.cost = cost;
    }



    public Node(String letter, Integer cost, Node left, Node right) 
    {
        Letter = letter;
        this.cost = cost;
        this.left = left;
        this.right = right;
    }


    public Node getLeft() 
    {
        return left;
    }


    public Node getRight() 
    {
        return right;
    }
}
