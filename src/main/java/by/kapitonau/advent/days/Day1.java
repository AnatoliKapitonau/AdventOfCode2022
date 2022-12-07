package by.kapitonau.advent.days;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day1 {
    private static final Logger log = Logger.getLogger("Day1");

    private final String fileName;

    public Day1(String fileName) {
        this.fileName = fileName;
    }

    private List<Integer> caloriesCalculation() {
        List<Integer> elfCaloriesList = new ArrayList<>();

        ClassLoader classLoader = getClass().getClassLoader();

        try (var inputStream = classLoader.getResourceAsStream(fileName)) {
            assert inputStream != null;
            try (var streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 var bufferedReader = new BufferedReader(streamReader)) {

                String line = bufferedReader.readLine();

                int currentSum = 0;

                while (line != null) {
                    if (line.isEmpty()) {
                        elfCaloriesList.add(currentSum);

                        currentSum = 0;
                    } else {
                        currentSum += Integer.parseInt(line);
                    }

                    line = bufferedReader.readLine();
                }

                elfCaloriesList.sort(Collections.reverseOrder());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return elfCaloriesList;
    }

    public static void main(String[] args) {
        var result = new Day1("Day1.txt").caloriesCalculation();

        var firstElf = result.get(0);

        var secondElf = result.get(1);
        var thirdElf = result.get(2);

        log.log(Level.INFO, () -> "First Event: \n Main Elf : ".concat(String.valueOf(firstElf)));

        log.log(Level.INFO, () -> "Second Event: \n 1st Elf : ".concat(String.valueOf(firstElf)
                .concat("\n 2nd Elf : ").concat(String.valueOf(secondElf))
                .concat("\n 3rd Elf : ").concat(String.valueOf(thirdElf))
                .concat("\n Total : ").concat(String.valueOf(firstElf + secondElf + thirdElf))));
    }
}
