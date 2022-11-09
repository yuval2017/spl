package bgu.spl.mics;



import java.util.*;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

    private static MessageBusImpl instance;

    private HashMap<Event,Future> eventsFutures;
	private HashMap<Class<? extends Message>,Queue<MicroService>> messageHandlers;
	private HashMap<MicroService,Queue<Message>> MSQueues;

	private MessageBusImpl(){
		MSQueues = new HashMap<>();
		messageHandlers = new HashMap<>();
		eventsFutures = new HashMap<>();
	}

    public synchronized static MessageBusImpl getInstance(){
        if(instance == null)
            instance = new MessageBusImpl();
        return instance;
    }

	
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		subscribeMessage(type,m);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		subscribeMessage(type,m);
    }

    //Private method that subscribes to a certain type of message (Broadcast or Event)
	private void subscribeMessage(Class<? extends Message> type, MicroService m){
		if(MSQueues.containsKey(m)) {
			synchronized (this) {
				Queue<MicroService> handlers = messageHandlers.get(type);
				if (handlers == null) {
					messageHandlers.put(type, new LinkedList<>());
					handlers = messageHandlers.get(type);
				}

				handlers.add(m);
			}
		}
	}

	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
		if(eventsFutures.containsKey(e)){
			Future<T> f = eventsFutures.get(e);
			f.resolve(result);
		}
	}

	@Override
	public synchronized void sendBroadcast(Broadcast b) {
		if(messageHandlers.containsKey(b.getClass())){
			Queue<MicroService> q = messageHandlers.get(b.getClass());
			for (MicroService m: q) {
				Queue<Message> mQ = MSQueues.get(m);
				mQ.add(b);
				notifyAll();
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		Future<T> f = null;
		if (messageHandlers.containsKey(e.getClass())) {
			f = new Future<>();
			eventsFutures.put(e,f);
			Queue<MicroService> q = messageHandlers.get(e.getClass());
			synchronized (this) {
				MicroService m = q.remove();
				q.add(m);
				Queue<Message> mQ = MSQueues.get(m);
				mQ.add(e);
				notifyAll();
			}
		}
        return f;
	}

	@Override
	public void register(MicroService m) {
		if(!MSQueues.containsKey(m)){
			Queue<Message> q = new LinkedList<>();
			MSQueues.put(m,q);
		}
	}

	@Override
	public void unregister(MicroService m) {
		MSQueues.remove(m);
		for(Queue<MicroService> q : messageHandlers.values()){
			q.remove(m);
		}
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException, IllegalStateException {
		if(!MSQueues.containsKey(m))
			throw new IllegalStateException();
		Queue<Message> q = MSQueues.get(m);
		synchronized (this) {
			while (q.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException ex) { }
			}
			return q.remove();
		}
	}
}
