import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

//TODO: look at other operations: MEMBER_IN, MEMBER_OUT, INTEL_TARGET, INTEL_DIVIDE, INTEL_RANK
//MEMBER_IN DONE
//MEMBER_OUT
//INTEL_TARGET DONE
//INTEL_DIVIDE DONE
//INTEL_RANK DONE

//TODO: add necessary comments


public class AVLTree<K extends String, V extends Double> {
    public Node<K, V> root;
    private final BufferedWriter writer;


    /**
     * function to print the member in
     *
     * @param name   is the name of the member
     * @param GMS    is the GMS of the member
     * @param writer is the BufferedWriter to write to the output file
     * @throws IOException prints necessary output for MEMBER_IN
     */
    public void printMemberIn(K name, V GMS, BufferedWriter writer) throws IOException {
        printMemberIn(this.root, name, GMS, writer);
    }

    private void printMemberIn(Node<K, V> node, K name, V GMS, BufferedWriter writer) throws IOException {
        if (node == null) {
            return;
        }

        if (GMS.compareTo(node.GMS) < 0) {

            writer.write(node.name + " welcomed " + name + "\n");

            printMemberIn(node.leftChild, name, GMS, writer);
        } else if (GMS.compareTo(node.GMS) > 0) {
            writer.write(node.name + " welcomed " + name + "\n");
            printMemberIn(node.rightChild, name, GMS, writer);
        }


    }

    /**
     * function to print the member out
     *
     * @param name   is the name of the member
     * @param GMS    is the GMS of the member
     * @param writer is the BufferedWriter to write to the output file
     * @throws IOException prints necessary output for MEMBER_OUT
     */

    private void printMemberOut(Node<K, V> node, K name, V GMS, BufferedWriter writer) throws IOException {
        if (node == null) {
            writer.write(name + " left the family, replaced by nobody\n");
            return;
        }
        if (name.equals(node.name)) {
            return;
        }
        writer.write(name + " left the family, replaced by " + node.name + "\n");

    }

    /**
     * Node class for the AVLTree
     *
     * @param <K> is the name of the member
     * @param <V> is the GMS of the member
     */

    private static class Node<K, V> {
        K name;
        V GMS;
        Node<K, V> leftChild;
        Node<K, V> rightChild;
        int height;
        int depth = -1;
        int[] res;

        /**
         * Constructor for the Node class
         *
         * @param name is the name of the member
         * @param GMS  is the GMS of the member
         */
        Node(K name, V GMS) {
            this.name = name;
            this.GMS = GMS;
            this.leftChild = null;
            this.rightChild = null;
            this.height = 1;
            this.res = new int[]{1, 0};
        }
    }

    /**
     * Constructor for the AVLTree class
     *
     * @param GMS    is the GMS of the root
     * @param name   is the name of the root
     * @param writer is the BufferedWriter to write to the output file
     */
    public AVLTree(V GMS, K name, BufferedWriter writer) {
        this.writer = writer;
        this.root = new Node<>(name, GMS);
    }

    /**
     * Constructor for the AVLTree class
     *
     * @param writer is the BufferedWriter to write to the output file
     */
    public AVLTree(BufferedWriter writer) {
        this.root = null;
        this.writer = writer;
    }

    /**
     * function to get the height of a node
     *
     * @param node is the node to get the height of
     * @return the height of the node
     */
    private int height(Node<K, V> node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    /**
     * function to insert a node into the AVLTree
     *
     * @param name is the name of the member
     * @param GMS  is the GMS of the member
     */
    public void insert(K name, V GMS) {
        root = insert(root, name, GMS);
    }

    private Node<K, V> insert(Node<K, V> node, K name, V GMS) {
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

    /**
     * function to balance the AVLTree
     *
     * @param node is the node to balance
     * @return the balanced node
     */

    private Node<K, V> balance(Node<K, V> node) {

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
        update(node);
        return node;
    }

    /**
     * function to right rotate the AVLTree
     *
     * @param node is the node to rotate
     * @return the rotated node
     */

    private Node<K, V> rightRotate(Node<K, V> node) {
        Node<K, V> newRoot = node.leftChild;
        Node<K, V> temp = newRoot.rightChild;
        newRoot.rightChild = node;
        node.leftChild = temp;
        node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;
        newRoot.height = Math.max(height(newRoot.leftChild), height(newRoot.rightChild)) + 1;
        update(node);
        update(newRoot);
        return newRoot;
    }

    /**
     * function to left rotate the AVLTree
     *
     * @param node is the node to rotate
     * @return the rotated node
     */
    private Node<K, V> leftRotate(Node<K, V> node) {
        Node<K, V> newRoot = node.rightChild;
        Node<K, V> temp = newRoot.leftChild;
        newRoot.leftChild = node;
        node.rightChild = temp;
        node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;
        newRoot.height = Math.max(height(newRoot.leftChild), height(newRoot.rightChild)) + 1;
        update(node);
        update(newRoot);
        return newRoot;
    }

    /**
     * function to remove a node from the AVLTree
     *
     * @param name is the name of the member
     * @param GMS  is the GMS of the member
     * @throws IOException
     */

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
                printMemberOut(node.rightChild, name, GMS, writer);
                return node.rightChild;
            } else if (node.rightChild == null) {
                printMemberOut(node.leftChild, name, GMS, writer);
                return node.leftChild;
            } else {
                Node<K, V> temp = min(node.rightChild);
                node.name = temp.name;
                node.GMS = temp.GMS;
                node.rightChild = removeMin(node.rightChild);
                printMemberOut(node, name, GMS, writer);
            }
        }
        node.height = 1 + Math.max(height(node.leftChild), height(node.rightChild));

        return balance(node);
    }


    private Node<K, V> min(Node<K, V> node) {
        if (node.leftChild == null) {
            return node;
        }
        return min(node.leftChild);
    }

    /**
     * function to remove the minimum node from the AVLTree
     *
     * @param node is the node to remove
     * @return the node that was removed
     */

    private Node<K, V> removeMin(Node<K, V> node) {
        if (node.leftChild == null) {
            return node.rightChild;
        }
        node.leftChild = removeMin(node.leftChild);
        node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;
        return balance(node);
    }

    /**
     * function to find a node in the AVLTree
     *
     * @param name1 is the name of the member
     * @param GMS1  is the GMS of the member
     * @param name2 is the name of the member
     * @param GMS2  is the GMS of the member
     * @return the node that was found
     */
    public void intelTarget(V GMS1, K name1, V GMS2, K name2) throws IOException {
        Node<K, V> node = intelTarget(root, GMS1, name1, GMS2, name2);
        writer.write("Target Analysis Result: " + node.name + " " + String.format(Locale.US, "%.3f", node.GMS) + "\n");

    }

    private Node<K, V> intelTarget(Node<K, V> node, V GMS1, K name1, V GMS2, K name2) {
        if (node == null) {
            return null;
        }
        if (GMS1.compareTo(node.GMS) < 0 && GMS2.compareTo(node.GMS) > 0) {
            return node;
        } else if (GMS1.compareTo(node.GMS) < 0) {
            return intelTarget(node.leftChild, GMS1, name1, GMS2, name2);
        } else if (GMS2.compareTo(node.GMS) > 0) {
            return intelTarget(node.rightChild, GMS1, name1, GMS2, name2);
        }
        return null;
    }


    public void intelDivide() throws IOException {
        Map<Node<K, V>, int[]> memo = new HashMap<>();
        int[] arr = root.res;
        int num = maxCompare(arr[0], arr[1]);
        writer.write("Division Analysis Result: " + num + "\n");
    }

    /**
     * function to update intelDivide during rotations
     *
     * @param node is the node to find the maximum of
     * @return the maximum node
     */
    private void update(Node<K, V> node) {
        if (node == null) {
            return;
        }
        int[] leftResult = (node.leftChild != null ? node.leftChild.res : new int[]{0, 0});
        int[] rightResult = (node.rightChild != null ? node.rightChild.res : new int[]{0, 0});

        int c0 = leftResult[1] + rightResult[1] + 1;
        int c1 = maxCompare(leftResult[0], leftResult[1]) + maxCompare(rightResult[0], rightResult[1]);

        int[] result = new int[]{c0, c1};
        node.res = result;
    }


    private int maxCompare(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    /**
     * function to find the depth of a node
     *
     * @param node  is the node to find the depth of
     * @param GMS   is the GMS of the node
     * @param depth is the depth of the node
     * @return the depth of the node
     */

    public int findDepth(Node<K, V> node, V GMS, int depth) {
        if (node == null) {
            return 0;
        }
        int ret = 0;
        if (GMS.compareTo(node.GMS) == 0) {
            ret = depth;
        }
        int l = findDepth(node.leftChild, GMS, depth + 1);
        if (l > 0) {
            ret = l;
        }
        int r = findDepth(node.rightChild, GMS, depth + 1);
        if (r > 0) {
            ret = r;
        }


        node.depth = depth;
        return ret;
    }


    public void intelRank(K name, V GMS) throws IOException {
        int test = findDepth(root, GMS, 0);
        writer.write("Rank Analysis Result:");
        intelRankBFS(root, test);
        writer.write("\n");
    }

    /**
     * function to print the intelRank
     *
     * @param node is the node to print
     * @param rank is the rank of the node
     * @throws IOException
     */

    private void intelRankBFS(Node<K, V> node, int rank) throws IOException {
        if (node == null) {
            return;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            Node<K, V> temp = queue.poll();
            int curr = temp.depth;
            if (curr > rank) {
                return;
            }
            if (curr == rank) {
                writer.write(" " + temp.name + " " + String.format(Locale.US, "%.3f", temp.GMS));
            }
            if (temp.leftChild != null) {
                queue.add(temp.leftChild);
            }
            if (temp.rightChild != null) {
                queue.add(temp.rightChild);

            }
        }
    }

}