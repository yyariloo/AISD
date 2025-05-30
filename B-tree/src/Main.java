import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        BTree bTree = new BTree(3);

        List<Integer> numbers = generateRandomNumbers(10000, 0, 100000);

        List<BTreeNode.InsertResult> insertResults = new ArrayList<>();
        System.out.println("Добавление 10000 элементов...");
        for (int num : numbers) {
            insertResults.add(bTree.insert(num));
        }

        List<BTreeNode.SearchResult> searchResults = new ArrayList<>();
        Collections.shuffle(numbers);
        List<Integer> searchKeys = numbers.subList(0, 100);
        System.out.println("\nПоиск 100 случайных элементов...");
        for (int key : searchKeys) {
            searchResults.add(bTree.search(key));
        }

        List<BTreeNode.DeleteResult> deleteResults = new ArrayList<>();
        Collections.shuffle(numbers);
        List<Integer> deleteKeys = numbers.subList(0, 1000);
        System.out.println("\nУдаление 1000 случайных элементов...");
        for (int key : deleteKeys) {
            deleteResults.add(bTree.remove(key));
        }

        printStatistics(insertResults, searchResults, deleteResults);
    }

    private static List<Integer> generateRandomNumbers(int count, int min, int max) {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            numbers.add(random.nextInt(max - min) + min);
        }
        return numbers;
    }

    private static void printStatistics(
            List<BTreeNode.InsertResult> insertResults,
            List<BTreeNode.SearchResult> searchResults,
            List<BTreeNode.DeleteResult> deleteResults
    ) {
        System.out.println("\n=== Статистика ===");

        double avgInsertOps = insertResults.stream()
                .mapToInt(r -> r.operations)
                .average().orElse(0);
        double avgInsertTime = insertResults.stream()
                .mapToLong(r -> r.timeNanos)
                .average().orElse(0) / 1000000.0; // в миллисекундах

        System.out.printf("Вставка (среднее): %.2f операций, %.4f мс%n", avgInsertOps, avgInsertTime);

        double avgSearchOps = searchResults.stream()
                .mapToInt(r -> r.operations)
                .average().orElse(0);
        double avgSearchTime = searchResults.stream()
                .mapToLong(r -> r.timeNanos)
                .average().orElse(0) / 1000000.0;

        System.out.printf("Поиск (среднее): %.2f операций, %.4f мс%n", avgSearchOps, avgSearchTime);

        double avgDeleteOps = deleteResults.stream()
                .mapToInt(r -> r.operations)
                .average().orElse(0);
        double avgDeleteTime = deleteResults.stream()
                .mapToLong(r -> r.timeNanos)
                .average().orElse(0) / 1000000.0;

        System.out.printf("Удаление (среднее): %.2f операций, %.4f мс%n", avgDeleteOps, avgDeleteTime);
    }
}