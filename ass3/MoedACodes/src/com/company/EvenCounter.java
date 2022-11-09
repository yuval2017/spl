package com.company;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class EvenCounter {
    private AtomicInteger count = new AtomicInteger(0);
    private int c = 0;
    private AtomicReference<Integer> e = new AtomicReference<>(0);
    public void next(){
        int oldVal;
        int newVal;
        do{
            oldVal = e.get();
            newVal = oldVal;
            newVal+=2;

        }while (!e.compareAndSet(oldVal, newVal));
    }
    /*public void next(){
        int oldVal;
        do{
            oldVal = count.get();
        }while (!count.compareAndSet(oldVal,oldVal + 2));
    }/*
    /*public synchronized void next(){
        c++;
        c++;
    }*/
    public int get() {
        return e.get();
    }
}
