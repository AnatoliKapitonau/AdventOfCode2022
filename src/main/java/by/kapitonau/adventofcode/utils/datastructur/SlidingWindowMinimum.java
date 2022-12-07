package by.kapitonau.adventofcode.utils.datastructur;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class SlidingWindowMinimum {
    private final int[] values;
    private int lo;
    private int hi;

    private final Deque<Integer> deque = new ArrayDeque<>();

    public SlidingWindowMinimum(int[] values) {
        this(values, 1);
    }

    public SlidingWindowMinimum(int[] values, int winSize) {
        if (values == null) throw new IllegalArgumentException();
        this.values = values;
        advance(winSize);
    }

    // advance the front window by one unit
    public void advance() {
        // remove all the worse values in the back of the deque
        while (!deque.isEmpty() && values[deque.peekLast()] > values[hi]) {deque.removeLast();}
        deque.addLast(hi);
        hi++;
    }

    public void advance(int length) {
        for (int i = 0; i < length; i++) advance();
    }

    public void shrink() {
        lo++;
        while (!deque.isEmpty() && deque.peekFirst() < lo) {deque.removeFirst();}
    }

    public void slide() {
        advance();
        shrink();
    }

    public int getMin() {
        if (lo >= hi) throw new IllegalArgumentException("Make sure lo < hi");
        return values[deque.peekFirst()];
    }

    public int[] getWindow() {
        return Arrays.copyOfRange(values, lo, hi);
    }

    public int getLo() {
        return lo;
    }

    public int getHi() {
        return hi;
    }

    public static List<Integer> allMins(int[] values, int winSize) {
        SlidingWindowMinimum win = new SlidingWindowMinimum(values, winSize);
        ArrayList<Integer> res = new ArrayList<>();
        res.add(win.getMin());
        while (win.getHi() < values.length) {
            win.slide();
            res.add(win.getMin());
        }
        return res;
    }

    @Override
    public String toString() {
        return "SlidingWindowMinimum{" +
                "values=" + Arrays.toString(values) +
                ", lo=" + lo +
                ", hi=" + hi +
                ", deque=" + deque +
                ", min=" + getMin() +
                '}';
    }

}
