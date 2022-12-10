package by.kapitonau.adventofcode.utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CollectionUtil {

    private CollectionUtil() {
    }

    public static <T> T lastElem(List<T> list) {
        return list.get(list.size() - 1);
    }

    /**
     * Create a mutable list using list and elements
     *
     * @param list     of element to add in the new mutable list
     * @param elements to add to the new mutable list
     * @return a new mutable List containing the elements
     */
    @SafeVarargs
    public static <T> List<T> asList(List<T> list, T... elements) {
        var newList = new ArrayList<>(list);
        newList.addAll(List.of(elements));
        return newList;
    }

    /**
     * Create a mutable list
     *
     * @param elements of the new mutable list
     * @return a new mutable List containing the elements
     */
    @SafeVarargs
    public static <T> List<T> asList(T... elements) {
        return new ArrayList<>(List.of(elements));
    }

    /**
     * Create a mutable set using set and elements
     *
     * @param set      of element to add in the new mutable set
     * @param elements to add to the new mutable set
     * @return a new mutable Set containing the elements
     */
    @SafeVarargs
    public static <T> Set<T> asSet(Set<T> set, T... elements) {
        var newSet = new LinkedHashSet<>(set);
        newSet.addAll(Set.of(elements));
        return newSet;
    }

    /**
     * Create a mutable set
     *
     * @param elements of the new mutable set
     * @return a new mutable Set containing the elements
     */
    @SafeVarargs
    public static <T> Set<T> asSet(T... elements) {
        return new HashSet<>(Set.of(elements));
    }

    public static <T> Deque<T> asStack(Deque<T> stack) {
        Deque<T> ts = new ArrayDeque<>();
        stack.forEach(ts::push);
        return ts;
    }

    @SafeVarargs
    public static <T> Deque<T> asStack(Deque<T> stack, T... elements) {
        Deque<T> ts = new ArrayDeque<>();
        stack.forEach(ts::push);
        Arrays.stream(elements).forEach(ts::push);
        return ts;
    }

    @SafeVarargs
    public static <T> Deque<T> asStack(T... elements) {
        Deque<T> ts = new ArrayDeque<>();
        Arrays.stream(elements).forEach(ts::push);
        return ts;
    }

    public static <T> Deque<T> asDeque(T... elements) {
        return new ArrayDeque<>(Arrays.asList(elements));
    }


    public static List<List<Object>> flattenList(List<List<Object>> list) {
        return list.stream().map(CollectionUtil::flattenListRec).toList();
    }

    public static List<Object> flattenListRec(List<Object> list) {
        List<Object> res = new ArrayList<>();
        for (Object l : list) {
            if (l instanceof List li) {
                res.addAll(flattenListRec(li));
            } else {
                res.add(l);
            }
        }
        return res;
    }

    public record EnumeratedItem<T>(T item, int index) {}

    private record ListEnumerator<T>(Iterable<T> target, int start) implements Iterable<EnumeratedItem<T>> {

        @Override
        public Iterator<EnumeratedItem<T>> iterator() {
            final Iterator<T> targetIterator = target.iterator();
            return new Iterator<>() {
                int index = start;

                @Override
                public boolean hasNext() {
                    return targetIterator.hasNext();
                }

                @Override
                public EnumeratedItem<T> next() {
                    EnumeratedItem<T> nextIndexedItem = new EnumeratedItem<>(targetIterator.next(), index);
                    index++;
                    return nextIndexedItem;
                }
            };
        }
    }

    public static <T> Iterable<EnumeratedItem<T>> enumerate(Iterable<T> iterable, int start) {
        return new ListEnumerator<>(iterable, start);
    }

    public static <T> Iterable<EnumeratedItem<T>> enumerate(Iterable<T> iterable) {
        return enumerate(iterable, 0);
    }

    public static <T> Stream<EnumeratedItem<T>> enumerateStream(Iterable<T> iterable, int start) {
        Iterable<EnumeratedItem<T>> it = enumerate(iterable, start);
        return StreamSupport.stream(it.spliterator(), false);
    }

    public static <T> Stream<EnumeratedItem<T>> enumerateStream(Iterable<T> iterable) {
        return enumerateStream(iterable, 0);
    }

}
