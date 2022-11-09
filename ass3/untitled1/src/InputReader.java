import java.util.ArrayList;
import java.util.Scanner;

public class InputReader implements Observable {
    //list of observers
    private final ArrayList<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObserver(String i1){
        for (Observer o: observers){
            o.update(i1);
        }
    }
    // scan the next input if the player is alive
    public void getUserInput(String done){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()){
            String i = sc.next();
            if(i.equals(done)){
                break;
            }
            notifyObserver(i);
        }
    }
}