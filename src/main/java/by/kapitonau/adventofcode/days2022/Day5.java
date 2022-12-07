package by.kapitonau.adventofcode.days2022;

import by.kapitonau.adventofcode.AocDay;
import by.kapitonau.adventofcode.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class Day5 extends AocDay {
    public Day5(String input, PrintStream output) {
        super(input, output);
    }

    @Override
    public String part1() {
        List<Deque<Character>> stacks = toStacks(this.input);
        this.input.lines().filter(s -> s.startsWith("move"))
                .map(this::move).forEach(m -> craneMove(m, stacks));

        return stacks.stream().map(Deque::pop)
                .map(c -> "" + c).collect(Collectors.joining());
    }

    @Override
    public String part2() {
        List<Deque<Character>> stacks = toStacks(this.input);
        this.input.lines().filter(s -> s.startsWith("move"))
                .map(this::move).forEach(m -> craneMove9001(m, stacks));

        return stacks.stream().map(Deque::pop)
                .map(c -> "" + c).collect(Collectors.joining());
    }

    private void craneMove9001(Movement m, List<Deque<Character>> stacks) {
        Deque<Character> tmp = new ArrayDeque<>();
        for (int i = 0; i < m.move; i++) {
            tmp.push(stacks.get(m.from - 1).pop());
        }
        while (!tmp.isEmpty()) {
            stacks.get(m.to-1).push(tmp.pop());
        }
    }

    private void craneMove(Movement m, List<Deque<Character>> stacks) {
        for (int i = 0; i < m.move; i++) {
            stacks.get(m.to-1).push(stacks.get(m.from-1).pop());
        }
    }

    private Movement move(String t) {
        String[] s = t.split(" to ");
        int to = parseInt(s[1]);

        String[] f = s[0].split(" from ");
        int from = parseInt(f[1]);

        String[] m = f[0].split("move ");
        int move = parseInt(m[1]);

        return new Movement(to, from, move);
    }

    private List<Deque<Character>> toStacks(String input) {
        List<List<Character>> cranes = input.lines()
                .takeWhile(StringUtils::isNotEmpty)
                .map(StringUtil::stringToChars)
                .toList();

        List<Deque<Character>> stacks = new ArrayList<>();
        for (int i = cranes.size() - 1; i >= 0; i--) {
            List<Character> characters = cranes.get(i);
            if (i == cranes.size() - 1) {
                characters.stream().filter(Character::isDigit)
                        .forEach(s -> stacks.add(new ArrayDeque<>()));
            } else {
                for (int k = 1, j = 0; k < characters.size(); k+=4, j++) {
                    Character e = characters.get(k);
                    if (Character.isLetter(e)) {
                        stacks.get(j).push(e);
                    }
                }
            }
        }
        return stacks;
    }

    record Movement(int to, int from, int move) {}

}
