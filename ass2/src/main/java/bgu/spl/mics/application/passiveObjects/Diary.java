package bgu.spl.mics.application.passiveObjects;


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
    private static AtomicInteger totalAttacks; // the total number of attacks executed by HanSolo and C3PO.
    private long HanSoloFinish; //a timestamp indicating when HanSolo finished the execution of all his attacks.
    private long C3POFinish; // a timestamp indicating when C3PO finished the execution of all his attacks.
    private long R2D2Deactivate; //a timestamp indicating when R2D2 finished deactivationthe shield generator.
    private long LeiaTerminate; // a time stamp that Leia puts in right before termination.
    private long HanSoloTerminate; // a time stamp that HanSolo puts in right before termination.
    private long C3POTerminate; // a time stamp that C3PO puts in right before termination.
    private long R2D2Terminate; //a time stamp that R2d2 puts in right before termination.
    private long LandoTerminate; //a time stamp that Lando puts in right before termination.
    private long startTime; // the start time of the program;

    private static Diary d = null;
    private Diary(){
        totalAttacks = new AtomicInteger(0);
    }
    public static Diary getInstance(){
        if(d == null){
            d = new Diary();
        }
        return d;
    }

    public void increaseTotalAttack(){
        totalAttacks.incrementAndGet();
    }
    public void setHanSoloFinish(long other){
        HanSoloFinish = other - startTime;
    }
    public void setC3POFinish(long other){
        C3POFinish = other - startTime;
    }
    public void setR2D2Deactivate(long other){
        R2D2Deactivate  = other - startTime;
    }
    public void setLeiaTerminate(long other){
        LeiaTerminate = other - startTime;
    }
    public void setHanSoloTerminate(long other){
        HanSoloTerminate = other - startTime;
    }
    public void setC3POTerminate(long other){ C3POTerminate = other - startTime; }
    public void setR2D2Terminate(long other){ R2D2Terminate = other - startTime;
    }
    public void setLandoTerminate(long other){ LandoTerminate = other - startTime; }
    public void setStartTime(long other){startTime = other;}

    public int getTotalAttacks(){
        return totalAttacks.get();
    }
    public long getHanSoloFinish(){
        return HanSoloFinish;
    }
    public long getC3POFinish(){
        return C3POFinish;
    }
    public long getR2D2Deactivate(){
        return R2D2Deactivate;
    }
    public long getLeiaTerminate(){
        return LeiaTerminate;
    }
    public long getHanSoloTerminate(){
        return HanSoloTerminate;
    }
    public long getC3POTerminate(){
        return C3POTerminate;
    }
    public long getR2D2Terminate(){
        return R2D2Terminate;
    }
    public long getLandoTerminate(){
        return LandoTerminate;
    }
}
