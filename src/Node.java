import java.util.List;

class Node {
    int value;
    Node left, right;
    private final TreeSort treeSort;

    public Node(int value, TreeSort treeSort) {
        this.value = value;
        this.treeSort = treeSort;
    }

    public void insert(int newValue) {
        treeSort.incrementIterations();
        if (newValue <= value) {
            if (left == null) {
                left = new Node(newValue, treeSort);
            } else {
                left.insert(newValue);
            }
        } else {
            if (right == null) {
                right = new Node(newValue, treeSort);
            } else {
                right.insert(newValue);
            }
        }
    }

    public void traverseInOrder(List<Integer> result) {
        if (left != null) left.traverseInOrder(result);
        result.add(value);
        if (right != null) right.traverseInOrder(result);
    }
}