import java.io.BufferedWriter;
import java.io.IOException;

//TODO: look at other operations: MEMBER_IN, MEMBER_OUT, INTEL_TARGET, INTEL_DIVIDE, INTEL_RANK
//MEMBER_IN DONE
//MEMBER_OUT DONE


public class AVLTree<K extends String, V extends Double> {
    private Node<K, V> root;
    final private BufferedWriter writer;

    public void printTree() {
        printTree(root);
    }

    private void printTree(Node<K, V> node) {
        if (node == null) {
            return;
        }
        if (node == root){
            System.out.println("Root: " + node.name + " " + node.GMS);
        }
        printTree(node.leftChild);
        System.out.println(node.name + " " + node.GMS);
        printTree(node.rightChild);
    }

    public void printMemberIn(K name, V GMS, BufferedWriter writer) throws IOException {
        printMemberIn(this.root, name, GMS, writer);
    }

    private void printMemberIn(Node<K, V> node, K name, V GMS,BufferedWriter writer) throws IOException{
        if(node == null){
            return;
        }
        if(name.equals(node.name)){
            return;
        }
        if(GMS.compareTo(node.GMS) < 0){

            writer.write(node.name + " welcomed " + name + "\n");
            printMemberIn(node.leftChild, name, GMS, writer);
        } else if(GMS.compareTo(node.GMS) > 0){
            writer.write(node.name + " welcomed " + name + "\n");
            printMemberIn(node.rightChild, name, GMS, writer);
        }
    }

    private void printMemberOut(Node<K, V> node, K name, V GMS,BufferedWriter writer) throws IOException{
        if(node == null){
            writer.write(name+" left the family, replaced by nobody\n");
        }
        if(name.equals(node.name)){
            return;
        }
        writer.write(name+" left the family, replaced by "+node.name+"\n");

    }

    /**
     * Node class for the AVLTree
     * @param <K> is the name of the member
     * @param <V> is the GMS of the member
     */

    private static class Node<K, V> {
        K name;
        V GMS;
        Node<K, V> leftChild;
        Node<K, V> rightChild;
        int height;

        /**
         * Constructor for the Node class
         * @param name is the name of the member
         * @param GMS is the GMS of the member
         */
        Node(K name, V GMS) {
            this.name = name;
            this.GMS = GMS;
            this.leftChild = null;
            this.rightChild = null;
            this.height = 1;
        }
    }

    /**
     * Constructor for the AVLTree class
     * @param GMS is the GMS of the root
     * @param name is the name of the root
     * @param writer is the BufferedWriter to write to the output file
     */
    public AVLTree(V GMS, K name, BufferedWriter writer) {
        this.writer = writer;
        this.root = new Node<>(name, GMS);
    }

    /**
     * Constructor for the AVLTree class
     * @param writer is the BufferedWriter to write to the output file
     */
    public AVLTree(BufferedWriter writer) {
        this.root = null;
        this.writer = writer;
    }

    /**
     * function to get the height of a node
     * @param node is the node to get the height of
     * @return the height of the node
     */
    private int height(Node<K, V> node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    public void insert(K name, V GMS) {
        root = insert(root, name, GMS);
    }

    private Node<K, V> insert(Node<K, V>node, K name, V GMS) {
        if (node == null) {
            return new Node<>(name, GMS);
        }


        if (GMS.compareTo(node.GMS) < 0) {
            node.leftChild = insert(node.leftChild, name, GMS);
        } else if (GMS.compareTo(node.GMS) > 0) {
            node.rightChild = insert(node.rightChild, name, GMS);
        } else {
            return node;
        }
        node.height = 1 + Math.max(height(node.leftChild), height(node.rightChild));
        return balance(node);
    }

    private Node<K,V> balance(Node<K, V> node) {

        int balance = height(node.leftChild) - height(node.rightChild);
        if (balance > 1) {
            if (height(node.leftChild.leftChild) >= height(node.leftChild.rightChild)) {
                return rightRotate(node);
            } else {
                node.leftChild = leftRotate(node.leftChild);
                return rightRotate(node);
            }
        } else if (balance < -1) {
            if (height(node.rightChild.rightChild) >= height(node.rightChild.leftChild)) {
                return leftRotate(node);
            } else {
                node.rightChild = rightRotate(node.rightChild);
                return leftRotate(node);
            }
        }
        return node;
    }

    private Node<K, V> rightRotate(Node<K, V> node) {
        Node<K, V> newRoot = node.leftChild;
        Node<K, V> temp = newRoot.rightChild;
        newRoot.rightChild = node;
        node.leftChild = temp;
        node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;
        newRoot.height = Math.max(height(newRoot.leftChild), height(newRoot.rightChild)) + 1;
        return newRoot;
    }

    private Node<K, V> leftRotate(Node<K, V> node) {
        Node<K, V> newRoot = node.rightChild;
        Node<K, V> temp = newRoot.leftChild;
        newRoot.leftChild = node;
        node.rightChild = temp;
        node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;
        newRoot.height = Math.max(height(newRoot.leftChild), height(newRoot.rightChild)) + 1;
        return newRoot;
    }



    public void remove(K name, V GMS) throws IOException {
        root = remove(root, name, GMS);
    }


    private Node<K, V> remove(Node<K, V> node, K name, V GMS) throws IOException {
        if (node == null) {
            return null;
        }


        if (GMS.compareTo(node.GMS) < 0) {
            node.leftChild = remove(node.leftChild, name, GMS);
        } else if (GMS.compareTo(node.GMS) > 0) {
            node.rightChild = remove(node.rightChild, name, GMS);
        } else {
            if (node.leftChild == null) {
                return node.rightChild;
            } else if (node.rightChild == null) {
                return node.leftChild;
            } else {
                Node<K, V> temp = node;
                node = min(temp.rightChild);
                node.rightChild = removeMin(temp.rightChild);
                node.leftChild = temp.leftChild;
            }
        }
        node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;
        printMemberOut(node, name, GMS, writer);
        return balance(node);
    }

    private Node<K, V> min(Node<K, V> node) {
        if (node.leftChild == null) {
            return node;
        }
        return min(node.leftChild);
    }

    private Node<K, V> removeMin(Node<K, V> node) {
        if (node.leftChild == null) {
            return node.rightChild;
        }
        node.leftChild = removeMin(node.leftChild);
        node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;
        return balance(node);
    }







}
