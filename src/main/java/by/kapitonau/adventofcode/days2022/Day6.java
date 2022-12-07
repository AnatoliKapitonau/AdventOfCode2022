package by.kapitonau.adventofcode.days2022;

import by.kapitonau.adventofcode.AocDay;

import java.io.PrintStream;

public class Day6  extends AocDay {

    private final String msg;

    public Day6(String input, PrintStream output) {
        super(input, output);
        this.msg = this.input.strip();
    }

    @Override
    public String part1() {
        return String.valueOf(charsBeforePacket(4));
    }

    @Override
    public String part2() {
        return String.valueOf(charsBeforePacket(14));
    }

    private int charsBeforePacket(int size) {
        for (int i = 0, j = size; j < msg.length(); i++, j++) {
            if (allUnique(msg.substring(i, j)))
                return j;
        }
        return 0;
    }

    private boolean allUnique(String substring) {
        return substring.chars().distinct().count() == substring.length();
    }

}
