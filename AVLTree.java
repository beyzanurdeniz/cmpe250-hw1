import java.io.BufferedWriter;
import java.io.IOException;

//TODO: fix insert, printMemberIn to print the bosses in the correct order
//TODO: figure out how to change the root after balancing
//TODO: look at other operations: MEMBER_OUT, INTEL_TARGET, INTEL_DIVIDE, INTEL_RANK



public class AVLTree<K extends String, V extends Double> {
    private Node<K, V> root;
    private BufferedWriter writer;


    private void printMemberIn(Node<K, V> node, String name, BufferedWriter writer) {
        try {
            writer.write(node.name + " welcomed " + name +  "\n");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static class Node<K, V> {
        K name;
        V GMS;
        Node<K, V> leftChild;
        Node<K, V> rightChild;
        int height;

        Node(K name, V GMS) {
            this.name = name;
            this.GMS = GMS;
            this.leftChild = null;
            this.rightChild = null;
            this.height = 1;
        }
    }
    public AVLTree(V GMS, K name, BufferedWriter writer) {
        this.writer = writer;
        this.root = null;
        insert(name, GMS);
    }

    public AVLTree(BufferedWriter writer) {
        this.root = null;
        this.writer = writer;
    }




    public boolean isEmpty() {
        return this.root == null;
    }

    public int height() {
        return height(this.root);
    }

    private int height(Node<K, V> node) {
        return (node != null) ? node.height : 0;
    }

    public int rank(K name) {
        Node<K, V> node = find(name, this.root);
        if (node != null) {
            return rank(node);
        } else {
            return -1; // Node not found
        }
    }

    private Node<K, V> find(K name, Node<K, V> node) {
        if (node == null) {
            return null;
        }
        int compareResult = name.compareTo(node.name);
        if (compareResult < 0) {
            return find(name, node.leftChild);
        } else if (compareResult > 0) {
            return find(name, node.rightChild);
        } else {
            return node;
        }
    }

    private int rank(Node<K, V> node) {
        return height(this.root) - height(node);
    }

    public void clear() {
        this.root = null;
    }

    public void print(){
        print(this.root);
    }

    private void print(Node<K, V> node) {
        if(node == null) {
            return;
        }
        if(node == this.root) {
            System.out.print("Root:" + node.name + " " + node.GMS + " ");
        }
        if (node.leftChild == null) {
            System.out.println("No left child");
        }
        else{
            print(node.leftChild);
        }
        if (node.rightChild == null) {
            System.out.println("No right child");
        }
        else{
            print(node.rightChild);
        }
        System.out.println(node.name + " " + node.GMS);

    }


}
