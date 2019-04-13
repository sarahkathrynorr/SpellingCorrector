package spell;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Dictionary implements ITrie {
    public Dictionary() {
        wordCount = 0;
        root = new Node();
        nodeCount = 1;
    }

    private int wordCount;
    private Node root;
    private int nodeCount; //how many nodes should there be if we are recursively add nodes inside the node class?

    @Override
    public void add(String word) {
        //wordCount++; if the getValue() in node goes from 0 to 1
        //read in first letter of the word and create a node then recursively
        word = word.toLowerCase();
        addNode(root, word);
    }


    private void addNode(Node currentNode, String wordSoFar) {
        if (wordSoFar.length() == 0) {//to see if string is done
            currentNode.counter++;
            if (currentNode.counter == 1) {
                wordCount++;
            }
        }
        else {
            //only add if the index isn't already filled
            //int index = (int) wordSoFar.charAt(0);
            int index = wordSoFar.charAt(0) - 'a'; //add 'a' to get the char
            if (currentNode.children[index] == null) {
                Node newNode = new Node();
                currentNode.children[index] = newNode;
                nodeCount++;
            }
            addNode(currentNode.children[index], wordSoFar.substring(1));
        }
    }

    @Override
    public INode find(String word) {
        return findNode(root, word);
    }

    private INode findNode(Node currentNode, String word) {
        if (word.length() == 0 && currentNode.counter > 0){
            return currentNode; //questionable...
        }
        else if (word.length() == 0 && currentNode.counter == 0) {
            return null;
        }
        int index = word.charAt(0) - 'a'; //I am not sure about this...
        for (int a = 0; a < currentNode.children.length; a++){
            if (currentNode.children[a] != null && a == index) {
                String newWord = word.substring(1);
                INode toReturn = findNode(currentNode.children[a], newWord);
                if (toReturn != null) {
                    return toReturn;
                }
            }
        }
        return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() {

        StringBuilder SB = new StringBuilder();

        for (int i = 0; i < root.children.length; i++) {
            String starter = "";
            if (root.children[i] != null) {
                //char currentLetter = (char) (i + 'a');
                //starter += currentLetter;
                //check for null
                toStringHelper(root.children[i], SB, starter, i);

            }
        }
        return SB.toString();
    }

    private void toStringHelper(Node currentNode, StringBuilder SB, String stringToAdd, int c) {
        char currentLetter = (char) (c + 'a');
        stringToAdd += currentLetter;
        for (int i = 0; i < currentNode.children.length; i++) {
            if (currentNode.children[i] != null && currentNode.children[i].counter == 0) {
                /*char currentLetter = (char) (i + 'a');
                stringToAdd += currentLetter;*/
                toStringHelper(currentNode.children[i], SB, stringToAdd, i);
            }
            else if (currentNode.children[i] != null && currentNode.children[i].counter > 0){
                char otherCurrentLetter = (char) (i + 'a');
                //stringToAdd += currentLetter;
                SB.append(stringToAdd + otherCurrentLetter + "\n");
                toStringHelper(currentNode.children[i], SB, stringToAdd, i);
            }
        }
    }

    @Override
    public int hashCode() {
        int HASHVAL = 7;
        return wordCount * nodeCount * HASHVAL;
    }

    @Override
    public boolean equals(Object o) {

        //return true if hash is equal and if toString is also equal by iterating through.
        if (o == null) {
            return false;
        }

        if (o.getClass() != this.getClass()) {
            return false;
        }

        Dictionary compareMe = (Dictionary) o;

        if (compareMe.hashCode() != hashCode()) {
            return false;
        }

        /*if (compareMe.toString().length() !=  toString().length()) {
            return false;
        }*/

        if (!toString().equals(compareMe.toString())) {
            return false;
        }

        for (int i = 0; i < root.children.length; i++)
            if (root.children[i] != null) {
                if (root.children[i].counter != compareMe.root.children[i].counter) {
                    return false;
                } else {
                    //helper function
                    if (!compareNode(root.children[i], compareMe.root.children[i])) {
                        return false;
                    }
                }
            }
        return true;
    }


    private boolean compareNode(Node ogNode, Node compareNode) {
        for (int i = 0; i < ogNode.children.length; i++) {
           if (ogNode.children[i] != null) {
               if (ogNode.children[i].counter != compareNode.children[i].counter) {
                   return false;
               } else {
                   return compareNode(ogNode.children[i], compareNode.children[i]);
               }
           }
        }
        return true;
    }
}

/* TODO
    -> ask about:
        + package spell ??????? - all files but main inside spell directory
    -> toString() - YEAH THIS NEEDS WORK :E
    -> test cases
    -> equals() - rough draft
    -> mo test cases
 */