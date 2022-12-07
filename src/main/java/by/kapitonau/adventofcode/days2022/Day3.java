package by.kapitonau.adventofcode.days2022;

import by.kapitonau.adventofcode.AocDay;
import by.kapitonau.adventofcode.utils.StringUtil;
import by.kapitonau.adventofcode.utils.datastructur.SuffixArray;
import one.util.streamex.StreamEx;

import java.io.PrintStream;
import java.util.List;

public class Day3 extends AocDay {

    public Day3(String input, PrintStream output) {
        super(input, output);
    }

    @Override
    public String part1() {
        var res = this.input.lines()
                .map(StringUtil::splitInHalf)
                .map(this::getCommonChar)
                .mapToInt(this::computePriority)
                .sum();

        return String.valueOf(res);
    }

    @Override
    public String part2() {
        var res = StreamEx.ofSubLists(this.input.lines().toList(), 3)
                .map(this::getCommonChar)
                .mapToInt(this::computePriority)
                .sum();

        return String.valueOf(res);
    }

    private int computePriority(String i) {
        return StringUtil.ALPHA.indexOf(i) + 1;
    }

    public String getCommonChar(List<String> inputs) {
        String first = SuffixArray.lcs(inputs.toArray(new String[0])).first();
        return first.length() > 1 ? first.split("")[0] : first;
    }

}
