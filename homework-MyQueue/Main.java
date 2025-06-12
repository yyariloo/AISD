public class Main {
    public static void main(String[] args) {
        MyQueue<Integer> myQueue = new MyQueue();
        myQueue.add(1);
        myQueue.add(2);
        myQueue.add(3);
        myQueue.add(4);
        myQueue.add(5);

        myQueue.remove();
        System.out.println(myQueue.remove());
        System.out.println(myQueue.remove());
        myQueue.add(500);
        System.out.println(myQueue.remove());
    }
}