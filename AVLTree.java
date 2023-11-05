/*
@Author: Beyza Nur Deniz
@Date: 4.11.2023
@Student ID: 2021400285
 */


public class AVLTree<AnyType extends Comparable<? super AnyType>> {
    private Node<AnyType> root;

    /*
    @GMS is the data of the node
    @leftChild is the left child of the node
    @rightChild is the right child of the node
     */
    private static class Node<AnyType> {
        AnyType GMS;
        Node<AnyType> leftChild;
        Node<AnyType> rightChild;

        Node(AnyType GMS) {
            this(GMS, null, null);
        }

        Node(AnyType GMS, Node<AnyType> lc, Node<AnyType> rc) {
            this.GMS = GMS;
            this.leftChild = lc;
            this.rightChild = rc;
        }
    }

    AVLTree() {
        this.root = null;
    }

    /*
    clear method is used to clear the tree
     */
    public void clear() {
        this.root = null;
    }

    /*
    isEmpty method is used to check if the tree is empty or not
    @return true if the tree is empty
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    private int height() {
        return height(this.root);
    }

    /*
    height method is used to find the height of the tree
    @param node is the node that we want to find the height of
    @return -1 if the node is null
     */
    private int height(Node<AnyType> node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.leftChild), height(node.rightChild));
    }

    /*
    rank method is used to find the rank defined in the homework
    @param node is the node that we want to find the rank of
     */
    private int rank(Node<AnyType> node) {
        return height(root) - height(node);
    }

    public int rank(AnyType GMS) {
        return rank(find(GMS));
    }

    /*
    find method is used to find the node that has the given data
    @param GMS is the data that we want to find
     */
    public Node<AnyType> find(AnyType GMS) {
        return find(GMS, this.root);
    }

    /*
    find method is used to find the node that has the given data
    @param GMS is the data that we want to find
    @param node is the node that we want to find the data in

    @return null if the node is null
    @return node if the node has the given data
    @return find(GMS, node.leftChild) if the node is smaller than the given data
    @return find(GMS, node.rightChild) if the node is bigger than the given data
     */
    private Node<AnyType> find(AnyType GMS, Node<AnyType> node) {
        if (node == null) {
            return null;
        }
        if (GMS.compareTo(node.GMS) < 0) {
            return find(GMS, node.leftChild);
        } else if (GMS.compareTo(node.GMS) > 0) {
            return find(GMS, node.rightChild);
        } else {
            return node;
        }
    }

    /*
    isBalanced method is used to check if the tree is balanced or not
    @return true if the tree is balanced
    */
    public boolean isBalanced() {
        return isBalanced(this.root);
    }
    private boolean isBalanced(Node<AnyType> node) {
        return Math.abs(height(node.leftChild) - height(node.rightChild)) <= 1;
    }

    /*
    findMin method is used to find the node that has the minimum data
    @return null if the tree is empty
    @return findMin(node.leftChild) if the node has a left child
    @return node if the node does not have a left child
     */

    public AnyType findMin() {
        return findMin(this.root).GMS;
    }
    private Node<AnyType> findMin(Node<AnyType> node) {
        if (node == null) {
            return null;
        }
        if (node.leftChild != null) {
            return findMin(node.leftChild);
        }
        return node;
    }

    /*
    findMax method is used to find the node that has the maximum data
    @return null if the tree is empty
    @return findMax(node.rightChild) if the node has a right child
    @return node if the node does not have a right child
     */

    public AnyType findMax() {
        return findMax(this.root).GMS;
    }
    private Node<AnyType> findMax(Node<AnyType> node) {
        if (node == null) {
            return null;
        }
        if (node.rightChild != null) {
            return findMax(node.rightChild);
        }
        return node;
    }













}
