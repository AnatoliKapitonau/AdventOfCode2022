package by.kapitonau.adventofcode.days2022;

import by.kapitonau.adventofcode.AocDay;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.tuple.Pair;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Day4 extends AocDay {
    public Day4(String input, PrintStream output) {
        super(input, output);
    }

    @Override
    public String part1() {
        return String.valueOf(this.input.lines()
                .map(this::toAssignment)
                .filter(this::contains)
                .count());
    }

    @Override
    public String part2() {
        return String.valueOf(this.input.lines()
                .map(this::toAssignment)
                .filter(this::isOverlappedBy)
                .count());
    }

    private boolean isOverlappedBy(Pair<Range<Integer>, Range<Integer>> a) {
        return a.getLeft().isOverlappedBy(a.getRight());
    }

    private boolean contains(Pair<Range<Integer>, Range<Integer>> a) {
        return a.getLeft().containsRange(a.getRight()) || a.getRight().containsRange(a.getLeft());
    }

    private Pair<Range<Integer>, Range<Integer>> toAssignment(String line) {
        List<Range<Integer>> ranges = Arrays.stream(line.split(","))
                .map(elf -> Range.between(parseInt(elf.split("-")[0]), parseInt(elf.split("-")[1])))
                .toList();
        return Pair.of(ranges.get(0), ranges.get(1));
    }

}
