package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Lesson12HW {
    public static void main(String[] args) {

        int[] values = {3, 3, 2, 1, 1};
        System.out.println(minValue(values));

        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 2));
        System.out.println(oddOrEven(integers));

    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers
                .stream()
                .filter(integers.stream().mapToInt(Integer::intValue)
                        .sum() % 2 != 0 ? n -> n % 2 == 0 : n -> n % 2 != 0)
                .collect(Collectors.toList());
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> 10 * a + b);
    }
}
