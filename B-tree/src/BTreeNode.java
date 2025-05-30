import java.util.ArrayList;
import java.util.List;

class BTreeNode {
    List<Integer> keys;
    int t;
    List<BTreeNode> children;
    boolean leaf;
    int operationCount;

    public BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public SearchResult search(int k) {
        operationCount = 0;
        long startTime = System.nanoTime();

        int idx = 0;
        while (idx < keys.size() && keys.get(idx) < k) {
            idx++;
            operationCount++;
        }
        operationCount++;

        if (idx < keys.size() && keys.get(idx) == k) {
            operationCount++;
            long endTime = System.nanoTime();
            return new SearchResult(this, operationCount, endTime - startTime);
        }

        if (leaf) {
            long endTime = System.nanoTime();
            return new SearchResult(null, operationCount, endTime - startTime);
        }

        operationCount++;
        long endTime = System.nanoTime();
        SearchResult childResult = children.get(idx).search(k);
        return new SearchResult(
                childResult.node,
                operationCount + childResult.operations,
                (endTime - startTime) + childResult.timeNanos
        );
    }

    public InsertResult insertNonFull(int k) {
        operationCount = 0;
        long startTime = System.nanoTime();

        int i = keys.size() - 1;

        if (leaf) {
            keys.add(0);
            while (i >= 0 && keys.get(i) > k) {
                keys.set(i + 1, keys.get(i));
                i--;
                operationCount += 2;
            }
            keys.set(i + 1, k);
            operationCount += 2;

            long endTime = System.nanoTime();
            return new InsertResult(operationCount, endTime - startTime);
        } else {
            while (i >= 0 && keys.get(i) > k) {
                i--;
                operationCount++;
            }
            i++;
            operationCount++;

            if (children.get(i).keys.size() == 2 * t - 1) {
                SplitResult splitResult = splitChild(i, children.get(i));
                operationCount += splitResult.operations;

                if (keys.get(i) < k) {
                    i++;
                    operationCount++;
                }
            }

            InsertResult childResult = children.get(i).insertNonFull(k);
            long endTime = System.nanoTime();
            return new InsertResult(
                    operationCount + childResult.operations,
                    (endTime - startTime) + childResult.timeNanos
            );
        }
    }

    public SplitResult splitChild(int i, BTreeNode y) {
        operationCount = 0;
        long startTime = System.nanoTime();

        BTreeNode z = new BTreeNode(y.t, y.leaf);

        for (int j = 0; j < t - 1; j++) {
            z.keys.add(y.keys.remove(t));
            operationCount += 2;
        }

        if (!y.leaf) {
            for (int j = 0; j < t; j++) {
                z.children.add(y.children.remove(t));
                operationCount += 2;
            }
        }

        children.add(i + 1, z);
        keys.add(i, y.keys.remove(t - 1));
        operationCount += 3;

        long endTime = System.nanoTime();
        return new SplitResult(operationCount, endTime - startTime);
    }

    public DeleteResult remove(int k) {
        operationCount = 0;
        long startTime = System.nanoTime();

        int idx = findKey(k);
        operationCount++;

        if (idx < keys.size() && keys.get(idx) == k) {
            operationCount++;
            if (leaf) {
                keys.remove(idx);
                operationCount++;
                long endTime = System.nanoTime();
                return new DeleteResult(operationCount, endTime - startTime);
            } else {
                DeleteResult result = removeFromNonLeaf(idx);
                long endTime = System.nanoTime();
                return new DeleteResult(
                        operationCount + result.operations,
                        (endTime - startTime) + result.timeNanos
                );
            }
        } else {
            if (leaf) {
                long endTime = System.nanoTime();
                return new DeleteResult(operationCount, endTime - startTime);
            }

            boolean flag = (idx == keys.size());
            operationCount++;

            if (children.get(idx).keys.size() < t) {
                FillResult fillResult = fill(idx);
                operationCount += fillResult.operations;
            }

            if (flag && idx > keys.size()) {
                DeleteResult childResult = children.get(idx - 1).remove(k);
                long endTime = System.nanoTime();
                return new DeleteResult(
                        operationCount + childResult.operations,
                        (endTime - startTime) + childResult.timeNanos
                );
            } else {
                DeleteResult childResult = children.get(idx).remove(k);
                long endTime = System.nanoTime();
                return new DeleteResult(
                        operationCount + childResult.operations,
                        (endTime - startTime) + childResult.timeNanos
                );
            }
        }
    }

    private int findKey(int k) {
        int idx = 0;
        while (idx < keys.size() && keys.get(idx) < k) {
            idx++;
        }
        return idx;
    }

    private DeleteResult removeFromNonLeaf(int idx) {
        int k = keys.get(idx);
        operationCount++;

        if (children.get(idx).keys.size() >= t) {
            int pred = getPred(idx);
            keys.set(idx, pred);
            operationCount += 2;
            DeleteResult childResult = children.get(idx).remove(pred);
            return new DeleteResult(
                    operationCount + childResult.operations,
                    childResult.timeNanos
            );
        } else if (children.get(idx + 1).keys.size() >= t) {
            int succ = getSucc(idx);
            keys.set(idx, succ);
            operationCount += 2;
            DeleteResult childResult = children.get(idx + 1).remove(succ);
            return new DeleteResult(
                    operationCount + childResult.operations,
                    childResult.timeNanos
            );
        } else {
            merge(idx);
            operationCount++;
            DeleteResult childResult = children.get(idx).remove(k);
            return new DeleteResult(
                    operationCount + childResult.operations,
                    childResult.timeNanos
            );
        }
    }

    private int getPred(int idx) {
        BTreeNode cur = children.get(idx);
        while (!cur.leaf) {
            cur = cur.children.get(cur.children.size() - 1);
        }
        return cur.keys.get(cur.keys.size() - 1);
    }

    private int getSucc(int idx) {
        BTreeNode cur = children.get(idx + 1);
        while (!cur.leaf) {
            cur = cur.children.get(0);
        }
        return cur.keys.get(0);
    }

    private FillResult fill(int idx) {
        operationCount = 0;
        long startTime = System.nanoTime();

        if (idx != 0 && children.get(idx - 1).keys.size() >= t) {
            borrowFromPrev(idx);
            operationCount++;
        } else if (idx != keys.size() && children.get(idx + 1).keys.size() >= t) {
            borrowFromNext(idx);
            operationCount++;
        } else {
            if (idx != keys.size()) {
                merge(idx);
            } else {
                merge(idx - 1);
            }
            operationCount++;
        }

        long endTime = System.nanoTime();
        return new FillResult(operationCount, endTime - startTime);
    }

    private void borrowFromPrev(int idx) {
        BTreeNode child = children.get(idx);
        BTreeNode sibling = children.get(idx - 1);

        child.keys.add(0, keys.get(idx - 1));
        if (!child.leaf) {
            child.children.add(0, sibling.children.remove(sibling.children.size() - 1));
        }

        keys.set(idx - 1, sibling.keys.remove(sibling.keys.size() - 1));
    }

    private void borrowFromNext(int idx) {
        BTreeNode child = children.get(idx);
        BTreeNode sibling = children.get(idx + 1);

        child.keys.add(keys.get(idx));
        if (!child.leaf) {
            child.children.add(sibling.children.remove(0));
        }

        keys.set(idx, sibling.keys.remove(0));
    }

    private void merge(int idx) {
        BTreeNode child = children.get(idx);
        BTreeNode sibling = children.get(idx + 1);

        child.keys.add(keys.remove(idx));
        child.keys.addAll(sibling.keys);

        if (!child.leaf) {
            child.children.addAll(sibling.children);
        }

        children.remove(idx + 1);
    }

    static class SearchResult {
        BTreeNode node;
        int operations;
        long timeNanos;

        SearchResult(BTreeNode node, int operations, long timeNanos) {
            this.node = node;
            this.operations = operations;
            this.timeNanos = timeNanos;
        }
    }

    static class InsertResult {
        int operations;
        long timeNanos;

        InsertResult(int operations, long timeNanos) {
            this.operations = operations;
            this.timeNanos = timeNanos;
        }
    }

    static class SplitResult {
        int operations;
        long timeNanos;

        SplitResult(int operations, long timeNanos) {
            this.operations = operations;
            this.timeNanos = timeNanos;
        }
    }

    static class DeleteResult {
        int operations;
        long timeNanos;

        DeleteResult(int operations, long timeNanos) {
            this.operations = operations;
            this.timeNanos = timeNanos;
        }
    }

    static class FillResult {
        int operations;
        long timeNanos;

        FillResult(int operations, long timeNanos) {
            this.operations = operations;
            this.timeNanos = timeNanos;
        }
    }
}