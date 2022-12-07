package by.kapitonau.adventofcode;

import lombok.SneakyThrows;
import java.time.LocalDateTime;

public class AdventOfCodeMain {

    @SneakyThrows
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        AoC.builder(now.getYear(), 1)
                .oneDayOnly()
//        .sampleInput("test.txt")
                .build().run();

    }
}
