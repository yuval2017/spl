package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;

/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvents}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvents}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService {
    private Attack[] attacks;
    private Future[] futures;

    public LeiaMicroservice(Attack[] attacks,int numOfEwoks) {
        super("Leia");
        this.attacks = attacks;
        this.futures = new Future[attacks.length];
        Ewoks ewoks = Ewoks.getInstance();
        for(int i=0;i<numOfEwoks;i++)
            ewoks.addEwok(new Ewok(i));
    }
    @Override
    protected void initialize() {
        try {
            Thread.sleep(100);
        }catch (InterruptedException e){}

        diary.setStartTime(System.currentTimeMillis());

        for (int i=0; i<attacks.length;i++) {
            Future f = super.sendEvent(new AttackEvent(attacks[i]));
            futures[i] = f;
        }
        for (Future f:futures) {
            f.get();
        }
        Future deactivationFuture = super.sendEvent(new DeactivationEvent());
        deactivationFuture.get();
    }

    @Override
    protected void setTerminateInDiary() {
        diary.setLeiaTerminate(System.currentTimeMillis());
    }
}
