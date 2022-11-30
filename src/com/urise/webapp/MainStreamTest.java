package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MainStreamTest {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6};

        int i = Arrays.stream(arr).parallel().filter(x -> x != 2).sum();
        System.out.println(i);
        Arrays.stream(arr).forEach(System.out::println);
        List<Integer> list = Arrays.stream(arr).boxed().toList();
        System.out.println(list);

        Integer sum = Stream.of("1", "2", "3", "4").mapToInt(Integer::parseInt).sum();
        System.out.println(sum);

        int count = Math.toIntExact(Stream.of(1, 2, 3, 4,12,3,1).count());
        System.out.println(count);
    }
}
