package com.example.countrymemory.core;

import android.annotation.SuppressLint;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class JavaUtils {
    @SuppressLint("NewApi")
    public static <E> List<E> pickRandom(List<E> list, int n) {
        return new Random().ints(n, 0, list.size()).mapToObj(list::get).collect(Collectors.toList());
    }
}
