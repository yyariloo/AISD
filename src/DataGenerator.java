import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataGenerator {

    public static void generateAndSaveDataSets(String filename, int numOfSets, int minSize, int maxSize) {
        Random random = new Random();

        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < numOfSets; i++) {
                int size = minSize + random.nextInt(maxSize - minSize + 1);

                writer.write(size + "\n");

                for (int j = 0; j < size; j++) {
                    int num = random.nextInt(10000);
                    writer.write(num + (j < size - 1 ? " " : ""));
                }
                writer.write("\n");
            }
            System.out.println("Успешно создано " + numOfSets + " наборов данных в " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}
