package com.company;

public class CountTo implements Runnable {
    private int c;
    public static EvenCounter e = null;
    public CountTo( int _c){
        c = _c;
        if(e == null){
            e = new EvenCounter();
        }
    }

    @Override
    public void run() {
        for(int i = 0; i < c; i++){
            e.next();
            System.out.println(e.get());
        }
    }
}
