import java.util.ArrayList;
import java.util.List;

public class TreeSort {
    private Node root;
    private int iterations;

    public int[] sort(int[] array) {
        if (array == null || array.length == 0) return array;

        root = null;

        root = new Node(array[0], this);
        for (int i = 1; i < array.length; i++) {
            root.insert(array[i]);
        }

        List<Integer> sortedList = new ArrayList<>();
        root.traverseInOrder(sortedList);

        if (sortedList.size() != array.length) {
            throw new IllegalStateException("Потеряны элементы при сортировке!");
        }

        int[] result = new int[sortedList.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = sortedList.get(i);
        }

        return result;
    }

    public void incrementIterations() {
        iterations++;
    }

    public int getIterations() {
        return iterations;
    }
}