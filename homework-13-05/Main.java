package cw1305;

public class Main {
    public static void main(String[] args) {
        Node r = new Node(5);
        BinarySearchTree bst = new BinarySearchTree(r);

        System.out.println(bst.search(5, r));

        r.left = new Node(3);
        r.right = new Node(7);
        r.right.right = new Node(9);

        Node maxNode = bst.findMax();
        if (maxNode != null) {
            System.out.println("Max value: " + maxNode.value);
        }
    }
}
