package com.company;

import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Driver d1 = new Driver(1, "bar");
        Driver d2 = new Driver(1, "bar");
        List<Driver> ddd = new LinkedList<>();
        ddd.add(d1);
        System.out.println(ddd.contains(d2));
    }
}
