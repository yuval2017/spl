package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {
    private long duration;

    public LandoMicroservice(long duration) {
        super("Lando");
        this.duration = duration;
    }

    @Override
    protected void initialize() {
       super.subscribeEvent(BombDestroyerEvent.class, (BombDestroyerEvent b)->performDestroy(b));
    }

    @Override
    protected void setTerminateInDiary() {
        diary.setLandoTerminate(System.currentTimeMillis());
    }

    private void performDestroy(BombDestroyerEvent b) {

        try{
            //Destroy the bomb by sleeping for the duration needed
            Thread.sleep(duration);
        }
        catch(InterruptedException e){}
        super.complete(b,true);
        super.sendBroadcast(new TerminateBroadcast());
    }
}
