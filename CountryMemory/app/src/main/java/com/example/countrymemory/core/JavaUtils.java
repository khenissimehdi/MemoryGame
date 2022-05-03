package com.example.countrymemory.core;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JavaUtils {
    @SuppressLint("NewApi")
    public static <E> List<E> pickRandom(List<E> list, int n) {
        if (n > list.size()) {
            throw new IllegalArgumentException("not enough elements");
        }
        Random random = new Random();
        return IntStream
                .generate(() -> random.nextInt(list.size()))
                .distinct()
                .limit(n)
                .mapToObj(list::get)
                .collect(Collectors.toList());
    }
}
