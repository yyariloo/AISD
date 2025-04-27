import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public static List<int[]> loadDataSets(String filename) {
        List<int[]> dataSets = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int size = Integer.parseInt(line.trim());

                String dataLine = reader.readLine();
                if (dataLine == null) break;

                String[] parts = dataLine.trim().split(" ");
                int[] data = new int[size];
                for (int i = 0; i < size; i++) {
                    data[i] = Integer.parseInt(parts[i]);
                }

                dataSets.add(data);
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }

        return dataSets;
    }
}