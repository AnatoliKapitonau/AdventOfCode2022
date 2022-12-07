package by.kapitonau.adventofcode.utils;

import lombok.NonNull;
import one.util.streamex.IntStreamEx;

import java.util.Arrays;
import java.util.List;

public class StringUtil {
    public static final List<String> ALPHA = Arrays.stream("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".split("")).toList();

    private StringUtil() {
    }

    public static List<Character> stringToChars(@NonNull String s) {
        return IntStreamEx.ofChars(s).mapToObj(c -> (char) c).toList();
    }

    public static String replaceAt(String str, String in, String out, int position) {
        return str.substring(0, position) + out + str.substring(position + in.length());
    }

    public static List<String> splitInHalf(String s) {
        int mid = s.length() / 2;
        return List.of(s.substring(0, mid), s.substring(mid));
    }

    public static String replaceNthOccurrence(String str, String toReplace, String replacement, int n) {
        StringBuilder res = new StringBuilder();
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            String s = String.valueOf(str.charAt(i));
            if (str.startsWith(toReplace, i)) {
                if (count == n) {
                    s = replacement;
                    i += toReplace.length() - 1;
                }
                count++;
            }
            res.append(s);
        }
        return res.toString();
    }

}
