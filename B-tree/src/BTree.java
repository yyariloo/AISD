public class BTree {
    BTreeNode root;
    int t;

    public BTree(int t) {
        this.t = t;
        this.root = null;
    }

    public BTreeNode.SearchResult search(int k) {
        if (root == null) {
            return new BTreeNode.SearchResult(null, 1, 0);
        }
        return root.search(k);
    }

    public BTreeNode.InsertResult insert(int k) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys.add(k);
            return new BTreeNode.InsertResult(2, 0);
        }

        if (root.keys.size() == 2 * t - 1) {
            BTreeNode s = new BTreeNode(t, false);
            s.children.add(root);

            BTreeNode.SplitResult splitResult = s.splitChild(0, root);
            int operations = splitResult.operations;
            long timeNanos = splitResult.timeNanos;

            int i = 0;
            if (s.keys.get(0) < k) {
                i++;
                operations++;
            }

            BTreeNode.InsertResult insertResult = s.children.get(i).insertNonFull(k);
            operations += insertResult.operations;
            timeNanos += insertResult.timeNanos;

            root = s;
            return new BTreeNode.InsertResult(operations, timeNanos);
        } else {
            return root.insertNonFull(k);
        }
    }

    public BTreeNode.DeleteResult remove(int k) {
        if (root == null) {
            return new BTreeNode.DeleteResult(1, 0);
        }

        BTreeNode.DeleteResult result = root.remove(k);

        if (root.keys.size() == 0) {
            if (root.leaf) {
                root = null;
            } else {
                root = root.children.get(0);
            }
        }

        return result;
    }

    public void print() {
        if (root != null) {
            printNode(root, 0);
        }
    }

    private void printNode(BTreeNode node, int level) {
        System.out.print("Level " + level + ": ");
        for (int key : node.keys) {
            System.out.print(key + " ");
        }
        System.out.println();

        if (!node.leaf) {
            for (BTreeNode child : node.children) {
                printNode(child, level + 1);
            }
        }
    }
}