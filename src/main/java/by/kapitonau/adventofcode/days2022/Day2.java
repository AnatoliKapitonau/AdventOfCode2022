package by.kapitonau.adventofcode.days2022;

import by.kapitonau.adventofcode.AocDay;
import lombok.Getter;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class Day2 extends AocDay {
    @Getter
    private enum RPS implements Comparable<RPS> {
        ROCK(List.of("A","X"), 1),
        PAPER(List.of("B","Y"), 2),
        SCISSOR(List.of("C","Z"), 3);

        private final List<String> acronyms;
        private final int score;

        RPS(List<String> acronyms, int score) {
            this.acronyms = acronyms;
            this.score = score;
        }

        public static RPS getByAcronym(String c) {
            return Arrays.stream(RPS.values())
                    .filter(v -> v.getAcronyms().contains(c))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("Acronym not defined"));
        }

        public static int game(RPS a, RPS b) {
            if (a.equals(loose(b)))
                return 0;
            if (a.equals(win(b)))
                return 6;
            return 3;
        }

        public static RPS win(RPS hand) {
            return switch (hand) {
                case ROCK -> PAPER;
                case PAPER -> SCISSOR;
                case SCISSOR -> ROCK;
            };
        }

        public static RPS loose(RPS hand) {
            return switch (hand) {
                case ROCK -> SCISSOR;
                case PAPER -> ROCK;
                case SCISSOR -> PAPER;
            };
        }

        public static RPS responseByExpectedResult(RPS hand, String expected) {
            if (expected.equals("Y")) return hand;
            if (expected.equals("Z")) return win(hand);
            return loose(hand);
        }
    }

    public Day2(String input, PrintStream output) {
        super(input, output);
    }

    @Override
    public String part1() {
        var sum = this.input.lines()
                .map(list -> Arrays.stream(list.split(" ")).map(RPS::getByAcronym).toList())
                .mapToInt(match -> RPS.game(match.get(1), match.get(0)) + match.get(1).getScore())
                .sum();

        return String.valueOf(sum);
    }

    @Override
    public String part2() {
        var sum = this.input.lines()
                .map(list -> Arrays.stream(list.split(" ")).toList())
                .mapToInt(this::getSum)
                .sum();

        return String.valueOf(sum);

    }

    private int getSum(List<String> list) {
        RPS enemyPlay = RPS.getByAcronym(list.get(0));
        RPS selfPlay = RPS.responseByExpectedResult(enemyPlay, list.get(1));
        return RPS.game(selfPlay, enemyPlay) + selfPlay.getScore();
    }

}
