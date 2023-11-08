import java.io.BufferedWriter;
import java.io.IOException;

//TODO: fix insert, printMemberIn to print the bosses in the correct order
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

    public AVLTree(V GMS, K name, BufferedWriter writer) {
        this.writer = writer;
        this.root = null;
        insert(name, GMS);
    }


    public AVLTree(BufferedWriter writer) {
        this.root = null;
        this.writer = writer;
    }

    private static class Node<K, V> {
        K name;
        V GMS;
        Node<K, V> leftChild;
        Node<K, V> rightChild;
        int height;
        Node<K, V> boss;
        int rank;

        Node(K name, V GMS) {
            this.name = name;
            this.GMS = GMS;
            this.leftChild = null;
            this.rightChild = null;
            this.height = 1;
            this.boss = null;
            this.rank = 1;
        }
    }
    public void insert(K name, V GMS) {
        this.root = insert(name, GMS, this.root);
        find(name, this.root, true);
    }

    private Node<K, V> insert(K name, V GMS, Node<K, V> node) {
        if (node == null) {
            return new Node<>(name, GMS);
        }
        int compareResult = name.compareTo(node.name);
        if (compareResult < 0) {
            node.leftChild = insert(name, GMS, node.leftChild);
        } else if (compareResult > 0) {
            node.rightChild = insert(name, GMS, node.rightChild);
        }
        return balance(node);
    }

    private Node<K, V> balance(Node<K, V> node) {
        if (node == null) {
            return node;
        }
        int balance = height(node.leftChild) - height(node.rightChild);
        if (balance > 1) {
            if (height(node.leftChild.leftChild) >= height(node.leftChild.rightChild)) {
                node = rotateWithLeftChild(node);
            } else {
                node = doubleWithLeftChild(node);
            }
        } else if (balance < -1) {
            if (height(node.rightChild.rightChild) >= height(node.rightChild.leftChild)) {
                node = rotateWithRightChild(node);
            } else {
                node = doubleWithRightChild(node);
            }
        }
        return node;
    }

    private Node<K, V> rotateWithLeftChild(Node<K, V> k2) {
        Node<K, V> k1 = k2.leftChild;
        k2.leftChild = k1.rightChild;
        k1.rightChild = k2;
        k2.height = Math.max(height(k2.leftChild), height(k2.rightChild)) + 1;
        k1.height = Math.max(height(k1.leftChild), k2.height) + 1;
        return k1;
    }

    private Node<K, V> rotateWithRightChild(Node<K, V> k1) {
        Node<K, V> k2 = k1.rightChild;
        k1.rightChild = k2.leftChild;
        k2.leftChild = k1;
        k1.height = Math.max(height(k1.leftChild), height(k1.rightChild)) + 1;
        k2.height = Math.max(height(k2.rightChild), k1.height) + 1;
        return k2;
    }

    private Node<K, V> doubleWithLeftChild(Node<K, V> k3) {
        k3.leftChild = rotateWithRightChild(k3.leftChild);
        return rotateWithLeftChild(k3);
    }

    private Node<K, V> doubleWithRightChild(Node<K, V> k1) {
        k1.rightChild = rotateWithLeftChild(k1.rightChild);
        return rotateWithRightChild(k1);
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
        Node<K, V> node = find(name, this.root, false);
        if (node != null) {
            return rank(node);
        } else {
            return -1; // Node not found
        }
    }

    private Node<K, V> find(K name, Node<K, V> node, boolean print) {
        if (node == null) {
            return null;
        }
        int compareResult = name.compareTo(node.name);
        if (compareResult < 0) {
            if(print){
                printMemberIn(node, name, this.writer);
            }
            return find(name, node.leftChild, print);
        } else if (compareResult > 0) {
            if(print){
                printMemberIn(node, name, this.writer);
            }
            return find(name, node.rightChild,print);
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


}
