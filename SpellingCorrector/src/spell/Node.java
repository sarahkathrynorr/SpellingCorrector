package spell;

public class Node implements ITrie.INode {

    public int counter; //the # of words that end on this node
    public Node[] children;

    Node () {
        counter = 0;
        children = new Node[26];
    }

    @Override
    public int getValue() {
        return counter;
    }
}
