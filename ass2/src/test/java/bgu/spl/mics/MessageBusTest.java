package bgu.spl.mics;

import bgu.spl.mics.application.services.C3POMicroservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


import static org.junit.jupiter.api.Assertions.*;

public class MessageBusTest {
    private MessageBusImpl messageBus;
    private MicroService m1,m2;

    @BeforeEach
    public void setUp(){
        messageBus = MessageBusImpl.getInstance();

        //Creates two anonymous classes of Microservice
        m1 = new MicroService("m1") {
            @Override
            protected void initialize() { }

            @Override
            protected void setTerminateInDiary() {

            }
        };
        m2 = new MicroService("m2") {
            @Override
            protected void initialize() { }

            @Override
            protected void setTerminateInDiary() {

            }
        };
        //Registers the two microservices to the messageBus
        messageBus.register(m1);
        messageBus.register(m2);
    }

    @AfterEach
    public void tearDown(){
        //Unregisters the two microservices from the messageBus after each test
        messageBus.unregister(m1);
        messageBus.unregister(m2);
    }

    @Test
    public void testSubscribeAndSendBroadcast(){
        BroadcastExample b = new BroadcastExample();

        //Subscribes the two microservices to broadcast from type 'ExampleBroadcast'
        messageBus.subscribeBroadcast(b.getClass(),m1);
        messageBus.subscribeBroadcast(b.getClass(),m2);
        //Sends broadcast from type 'ExampleBroadcast'
        messageBus.sendBroadcast(b);
        Message ms1 = null;
        Message ms2 = null;
        try{
            ms1 = messageBus.awaitMessage(m1);
            ms2 = messageBus.awaitMessage(m2);
        }
        catch(InterruptedException exception){}

        assertEquals(b,ms1); // m1 should get the broadcast because he is registered
        assertEquals(b,ms2); // m2 should get the broadcast because he is registered
    }

    @Test
    public void testSubscribeAndSendEvent(){
        EventExample e1 = new EventExample();
        EventExample e2 = new EventExample();

        messageBus.subscribeEvent(e1.getClass(),m1);
        messageBus.subscribeEvent(e1.getClass(),m2);

        messageBus.sendEvent(e1);
        messageBus.sendEvent(e2);
        Message ms1 = null;
        Message ms2 = null;
        try{
            ms1 = messageBus.awaitMessage(m1);
            ms2 = messageBus.awaitMessage(m2);
        }
        catch(InterruptedException exception){}

        assertEquals(e1,ms1); // m1 should get the event because, he is the first one registered to the event
        assertNotEquals(e1,ms2); // m2 should'nt get the event, because he is *not* the first one registered to the event
    }

    @Test
    public void testRegisterAndUnregister(){
        BroadcastExample b = new BroadcastExample();

        //Subscribes the two microservices to broadcast from type 'ExampleBroadcast'
        messageBus.subscribeBroadcast(b.getClass(),m1);
        messageBus.subscribeBroadcast(b.getClass(),m2);
        //Unregisters m2 from the messageBus
        messageBus.unregister(m2);
        //Sends the broadcast from type 'BroadcastExample'
        messageBus.sendBroadcast(b);

        Message ms1 = null;
        Message ms2 = null;
        try{
            ms1 = messageBus.awaitMessage(m1);
            ms2 = messageBus.awaitMessage(m2);
        }
        catch(IllegalStateException | InterruptedException exception1){}

        assertEquals(b,ms1); // m1 should get the broadcast because he is registered
        assertNull(ms2); // m2 shouldn't get the broadcast because he is not registered to the messageBus
    }

    @Test
    public void testComplete(){
        EventExample e = new EventExample();
        messageBus.subscribeEvent(e.getClass(),m1);
        Future<Boolean> f = messageBus.sendEvent(e);
        assertFalse(f.isDone());
        messageBus.complete(e,true);
        assertTrue(f.isDone());
        assertTrue(f.get());
    }
}

class BroadcastExample implements Broadcast{ }
class EventExample implements Event<Boolean> { }
