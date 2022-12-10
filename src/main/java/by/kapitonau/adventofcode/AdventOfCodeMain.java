package by.kapitonau.adventofcode;

import lombok.SneakyThrows;
import java.time.LocalDateTime;

public class AdventOfCodeMain {

    @SneakyThrows
    public static void main(String[] args) {
        var now = LocalDateTime.now();

        AoC.builder(now.getYear(), now.getDayOfMonth())
                .oneDayOnly()
                .build()
                .run();
    }
}
