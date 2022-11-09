package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;

import java.util.List;


/**
 * C3POMicroservices is in charge of the handling {@link AttackEvents}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvents}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {
    private Ewoks ewoks;

    public C3POMicroservice() {
        super("C3PO");
        ewoks = Ewoks.getInstance();
    }

    @Override
    protected void initialize() {
        super.subscribeEvent(AttackEvent.class,(AttackEvent a)->performAttack(a));
    }

    @Override
    protected void setTerminateInDiary() {
        diary.setC3POTerminate(System.currentTimeMillis());
    }

    private void performAttack(AttackEvent attack){
        //Get the list of the required ewoks for the attack
        List<Integer> serialNumbers = attack.getSerials();
        //Acquire the needed Ewoks for the attack
        for(int n: serialNumbers){
            ewoks.acquireEwok(n);
        }
        try{
            //Perform the attack by sleeping for the duration needed
            Thread.sleep(attack.getDuration());
        }
        catch(InterruptedException e){}
        //Release all the Ewoks used in the attack
        for(int n:serialNumbers){
            ewoks.releaseEwok(n);
        }
        super.complete(attack,true);
        diary.increaseTotalAttack();
        diary.setC3POFinish(System.currentTimeMillis());
    }
}
