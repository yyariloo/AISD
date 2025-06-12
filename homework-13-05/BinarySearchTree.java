package cw1305;

public class BinarySearchTree {

    private Node root;

    public BinarySearchTree(Node root) {
        this.root = root;
    }

    public boolean search(int num, Node r) {
        if (r == null) return false;
        if (r.value == num) {
            return true;
        }

        else if (num < r.value) {
            return search(num, r.left);
        }

        return search(num, r.right);
    }

    public void add(int num) {
        if (root == null) {
            root = new Node(num);
        }
    }

    public Node findMax(Node node) {
        if (node == null) {
            return null;
        }

        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public Node findMax() {
        return findMax(root);
    }
}
