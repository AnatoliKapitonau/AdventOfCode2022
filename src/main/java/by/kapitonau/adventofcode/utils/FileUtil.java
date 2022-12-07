package by.kapitonau.adventofcode.utils;

import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class FileUtil {
    public static final String BASE_PATH = "src/main/resources/";

    private FileUtil() {}

    public static int[] loadCSVNumbers(String file) throws IOException {
        return StreamEx.of(loadInputs(file).get(0).split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public static int[] loadIntInputs(String file) throws IOException {
        return StreamEx.of(loadInputs(file)).mapToInt(Integer::parseInt).toArray();
    }

    public static List<String> loadInputs(String file) throws IOException {
        try (Reader reader = new BufferedReader(new FileReader(BASE_PATH + file))) {
            return StreamEx.ofLines(reader).toList();
        }
    }

    public static int[][] loadMapInt(String file) throws IOException {
        var outputValues = FileUtil.loadInputs(file).stream()
                .map(l -> l.chars().mapToObj(i -> (char) i).map(Object::toString).map(Integer::parseInt)
                        .toList()).toList();

        int size = outputValues.size();
        var map = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = outputValues.get(i).get(j);
            }
        }
        return map;
    }

    public static char[][] loadMapChar(String file) throws IOException {
        var outputValues = FileUtil.loadInputs(file).stream()
                .map(l -> l.chars().mapToObj(i -> (char) i).toList()).toList();

        int size = outputValues.size();
        int size2 = outputValues.get(0).size();
        var map = new char[size][size2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size2; j++) {
                map[i][j] = outputValues.get(i).get(j);
            }
        }
        return map;
    }

}
