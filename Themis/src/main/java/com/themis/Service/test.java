package com.themis.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class test {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        HashMap<Integer, Integer> map = new HashMap<>();
        long start = System.currentTimeMillis();
        numbers.parallelStream().forEach(i -> {
            int show = show(i);
            System.out.println("show: "+show);
            map.put(show, show);
            System.out.println("i: "+i);
        });
        long stop = System.currentTimeMillis();
        System.out.println(stop-start);
        start = System.currentTimeMillis();
        numbers.forEach(test::show);
        stop = System.currentTimeMillis();
        System.out.println(stop-start);
        System.out.println(map);
    }

    public static int show(int i){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
}
