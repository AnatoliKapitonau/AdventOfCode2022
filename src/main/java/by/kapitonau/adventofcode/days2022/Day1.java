package by.kapitonau.adventofcode.days2022;

import by.kapitonau.adventofcode.AocDay;
import one.util.streamex.IntStreamEx;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class Day1 extends AocDay {

    public static final String EMPTY_ENTRY = "\n\n";

    public Day1(String input, PrintStream output) {
        super(input, output);
    }

    public String part1() {
        List<Integer> caloriesByElf = getCalories(this.input);
        return String.valueOf(IntStreamEx.of(caloriesByElf).max().orElse(-1));
    }

    public String part2() {
        List<Integer> caloriesByElf = getCalories(this.input);
        return String.valueOf(IntStreamEx.of(caloriesByElf).reverseSorted().limit(3).sum());
    }

    private List<Integer> getCalories(String inventory) {
        return Arrays.stream(inventory.split(EMPTY_ENTRY))
                .map(s -> s.lines().mapToInt(Integer::parseInt).sum())
                .toList();
    }
}
