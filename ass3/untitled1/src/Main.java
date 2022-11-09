public class Main {
    public static void main(String [] args){
        InputReader subject = new InputReader();
        subject.addObserver(i-> System.out.println(Integer.parseInt(i)));
        subject.getUserInput("Done");
    }
}
