public interface Observable {
    public void addObserver (Observer o);
    public void notifyObserver(String i1) throws Exception;
}