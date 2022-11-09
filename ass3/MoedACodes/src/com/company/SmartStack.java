package com.company;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;

public class SmartStack<T> {
    private AtomicReference<Stack<T>> stack = new AtomicReference(new Stack<T>());
    public void push(T element){
        Stack old = stack.get();

    }
}
