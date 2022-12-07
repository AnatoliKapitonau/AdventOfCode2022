package by.kapitonau.adventofcode.utils.datastructur;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SuffixArray {
    private int alphabetSz = 256;

    private final int size;
    private final int[] txt;
    private final int[] sa;
    private final int[] c;

    private int[] lcp;
    private int[] sa2;
    private int[] rank;

    public SuffixArray(String s) {
        this(toIntArray(s));
    }

    public SuffixArray(int[] text) {
        if (text.length == 0)
            throw new IllegalArgumentException("Text cannot be null.");
        txt = text;
        size = text.length;
        sa = new int[size];
        sa2 = new int[size];
        rank = new int[size];
        c = new int[Math.max(alphabetSz, size)];

        construct();
        kasai();
    }

    public int getTextLength() {
        return size;
    }

    // Returns the suffix array.
    public int[] getSa() {
        return sa;
    }

    // Returns the LCP array.
    public int[] getLcp() {
        return lcp;
    }

    public String sub(int i, int len) {
        return new String(txt, sa[i], len);
    }

    public int[] getTxt() {
        return txt;
    }

    public int uniqueSubStrings() {
        return size * (size + 1) / 2 - Arrays.stream(lcp).sum();
    }

    /**
     * Longest Repeated Substring
     **/
    public SortedSet<String> lrs() {
        int maxLen = 0;
        SortedSet<String> res = new TreeSet<>();
        for (int i = 0; i < size; i++) {
            if (lcp[i] > 0 && lcp[i] >= maxLen) {
                if (lcp[i] > maxLen) res.clear();
                maxLen = lcp[i];
                res.add(sub(i, maxLen));
            }
        }
        return res;
    }

    private void construct() {
        int i;
        int p;
        int r;
        for (i = 0; i < size; ++i) c[rank[i] = txt[i]]++;
        for (i = 1; i < alphabetSz; ++i) c[i] += c[i - 1];
        for (i = size - 1; i >= 0; --i) sa[--c[txt[i]]] = i;
        for (p = 1; p < size; p <<= 1) {
            for (r = 0, i = size - p; i < size; ++i) sa2[r++] = i;
            for (i = 0; i < size; ++i) if (sa[i] >= p) sa2[r++] = sa[i] - p;
            Arrays.fill(c, 0, alphabetSz, 0);
            for (i = 0; i < size; ++i) c[rank[i]]++;
            for (i = 1; i < alphabetSz; ++i) c[i] += c[i - 1];
            for (i = size - 1; i >= 0; --i) sa[--c[rank[sa2[i]]]] = sa2[i];
            for (sa2[sa[0]] = r = 0, i = 1; i < size; ++i) {
                if (!(rank[sa[i - 1]] == rank[sa[i]]
                        && sa[i - 1] + p < size
                        && sa[i] + p < size
                        && rank[sa[i - 1] + p] == rank[sa[i] + p])) r++;
                sa2[sa[i]] = r;
            }
            int[] tmp = rank;
            rank = sa2;
            sa2 = tmp;
            if (r == size - 1) break;
            alphabetSz = r + 1;
        }
    }

    // Use Kasai algorithm to build LCP array
    // http://www.mi.fu-berlin.de/wiki/pub/ABI/RnaSeqP4/suffix-array.pdf
    private void kasai() {
        lcp = new int[size];
        int[] inv = new int[size];
        for (int i = 0; i < size; i++) inv[sa[i]] = i;
        for (int i = 0, len = 0; i < size; i++) {
            if (inv[i] > 0) {
                int k = sa[inv[i] - 1];
                while ((i + len < size) && (k + len < size) && txt[i + len] == txt[k + len]) {len++;}
                lcp[inv[i]] = len;
                if (len > 0) len--;
            }
        }
    }

    private static int[] toIntArray(String s) {
        if (s == null) return new int[0];
        int[] t = new int[s.length()];
        for (int i = 0; i < s.length(); i++) t[i] = s.charAt(i);
        return t;
    }

    /**
     * Longest Common Substring
     **/
    public static SortedSet<String> lcs(String[] strs, int commonInHowManyStrings) {
        return new LCS(strs).solve(commonInHowManyStrings);
    }

    public static SortedSet<String> lcs(String[] strs) {
        return lcs(strs, strs.length);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----i-----SA-----LCP---Suffix\n");

        for (int i = 0; i < size; i++) {
            int suffixLen = size - sa[i];
            char[] suffixArray = new char[suffixLen];
            for (int j = sa[i], k = 0; j < size; j++, k++) suffixArray[k] = (char) txt[j];
            sb.append(String.format("% 7d % 7d % 7d %s%n", i, sa[i], lcp[i], new String(suffixArray)));
        }
        return sb.toString();
    }

    private static class LCS {

        private static final char[] JOINTS = {'#', '$', '%', '&', '!', '*'};

        private final String[] strs;
        private SuffixArray sa;
        private int[] imap;

        public LCS(String[] strs) {
            this.strs = strs;
            if (strs.length > JOINTS.length)
                throw new IllegalArgumentException("The list of string is too long, max " + JOINTS.length);

            getSuffixArray();
            colorMapping();
        }

        private void getSuffixArray() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < strs.length; i++) sb.append(strs[i]).append(JOINTS[i]);
            this.sa = new SuffixArray(sb.toString());
        }

        private void colorMapping() {
            imap = new int[sa.getTextLength()];
            for (int i = 0, k = 0; i < strs.length; i++) {
                for (int j = 0; j < strs[i].length(); j++)
                    imap[k++] = i;
                imap[k++] = i; // sentinel belongs to string i
            }
        }

        private boolean enoughUniqueInWindow(int lo, int hi, int k) {
            Set<Integer> set = new HashSet<>();
            for (int i = lo; i < hi; i++)
                set.add(imap[sa.getSa()[i]]);
            return set.size() == k;
        }

        public SortedSet<String> solve(int k) {
            int maxLen = 0;
            SortedSet<String> res = new TreeSet<>();
            SlidingWindowMinimum win = new SlidingWindowMinimum(sa.getLcp());
            while (true) {
                if (enoughUniqueInWindow(win.getLo(), win.getHi(), k)) win.shrink();
                else if (win.getHi() == sa.getTextLength()) break;
                else win.advance();

                if (win.getLo() == win.getHi()) continue;

                // Update max len found
                int winMin = win.getMin();
                if (winMin > maxLen) {
                    maxLen = winMin;
                    res.clear();
                }

                if (winMin == maxLen)
                    res.add(sa.sub(win.getLo(), maxLen));
            }
            return res;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("-----i-----SA------IM-----LCP---Suffix\n");

            for (int i = 0; i < sa.getTextLength(); i++) {
                int suffixLen = sa.getTextLength() - sa.getSa()[i];
                char[] suffixArray = new char[suffixLen];
                for (int j = sa.getSa()[i], k = 0; j < sa.getTextLength(); j++, k++) suffixArray[k] = (char) sa.getTxt()[j];
                sb.append(String.format("% 7d % 7d % 7d % 7d %s%n", i, sa.getSa()[i], imap[sa.getSa()[i]], sa.getLcp()[i], new String(suffixArray)));
            }
            return sb.toString();
        }
    }

}
