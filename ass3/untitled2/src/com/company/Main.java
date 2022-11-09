package com.company;

import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {
        System.out.println(LocalTime.now().compareTo(LocalTime.parse("23:50")));
    }
}
