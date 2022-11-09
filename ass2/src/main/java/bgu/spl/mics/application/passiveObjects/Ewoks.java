package bgu.spl.mics.application.passiveObjects;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks {
    private static Ewoks e = null;
    private HashMap<Integer,Ewok> ewoks;

    private Ewoks() {
        ewoks = new HashMap<>();
    }

    public synchronized static Ewoks getInstance(){
        if(e == null){
            e = new Ewoks();
        }
        return e;
    }

    public Ewok deleteEwok(int e){
        return ewoks.remove(e);
    }

    public Ewok getE(int e){
        return ewoks.get(e);
    }

    public synchronized void addEwok(Ewok e){
        if(!ewoks.containsKey(e.getSerialNumber()))
            ewoks.put(e.getSerialNumber(),e);
    }

    public void addEwok(int serialNumber){
        Ewok e = new Ewok(serialNumber);
        addEwok(e);
    }

    public synchronized void acquireEwok(int serialNumber){
        if(ewoks.containsKey(serialNumber)){
            while(!ewoks.get(serialNumber).isAvailable()){
                try{
                    this.wait();
                }catch(InterruptedException exception){ }
            }
            ewoks.get(serialNumber).acquire();
        }
    }

    public synchronized void releaseEwok(int serialNumber){
        if(ewoks.containsKey(serialNumber)){
            ewoks.get(serialNumber).release();
            notifyAll();
        }
    }
    public boolean isAvailableEwok (int serialNumber){
        if(ewoks.containsKey(serialNumber)){
            return ewoks.get(serialNumber).isAvailable();
        }
        return false;
    }
}
