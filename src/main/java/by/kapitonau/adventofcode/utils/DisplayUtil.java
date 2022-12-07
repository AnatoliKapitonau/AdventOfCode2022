package by.kapitonau.adventofcode.utils;

import java.util.Map;

public class DisplayUtil {
    private DisplayUtil(){}

    public static final String GREEN = "GREEN";
    public static final String RED = "RED";
    public static final String GREY = "GREY";
    public static final String RESET = "RESET";
    public static final String ESC = "\u001b[";
    public static final Map<String, String> COLORS = Map.of(
            RESET, "0",
            RED, "31",
            GREEN, "32",
            GREY, "37"
    );

    public static final String SGR = "m";

    public static String prefixColorBold(String color) {
        return ESC + COLORS.getOrDefault(color.toUpperCase(), COLORS.get(RESET)) + ";1" + SGR;
    }

    public static String prefixColor(String color) {
        return ESC + COLORS.getOrDefault(color.toUpperCase(), COLORS.get(RESET)) + SGR;
    }
}
