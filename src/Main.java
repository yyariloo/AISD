import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int numOfSets = random.nextInt(50, 101);

        DataGenerator.generateAndSaveDataSets("test_data.txt", numOfSets, 100, 10000);

        List<int[]> dataSets = DataLoader.loadDataSets("test_data.txt");
        System.out.println("Loaded " + dataSets.size() + " data sets");

        int num = 0;
        for (int[] dataSet : dataSets) {
            num++;
            TreeSort treeSort = new TreeSort();

            long startTime = System.nanoTime();
            int[] sorted = treeSort.sort(dataSet);
            long endTime = System.nanoTime();

            System.out.println("Набор " + num +
                    " | Время: " + (endTime - startTime)/1000 +
                    " | Размер: " + sorted.length +
                    " | Итерации: " + treeSort.getIterations());
        }
    }
}
