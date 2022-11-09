package com.company;

import java.util.Stack;

public class Main {

    public static void main(String[] args) throws InterruptedException {

       // Thread th1 = new Thread(new CountTo(10));
       // Thread th2 = new Thread(new CountTo(10));
        //th1.start();
        //th2.start();
        Stack<Integer>  t = new Stack<>();
        t.push(5);
        Stack<Integer> s = (Stack<Integer>) t.clone();
        s.pop();
        System.out.println(t.peek());
    }
}
