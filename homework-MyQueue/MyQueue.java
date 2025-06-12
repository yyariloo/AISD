import java.util.Stack;

public class MyQueue<T> {
    Stack<T> stack1;
    Stack<T> stack2;

    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void add(T x) {
        stack1.push(x);
    }

    public T remove() {
        if (!stack2.isEmpty()) {
            return stack2.pop();
        } else if (!stack1.isEmpty()) {
            reverse();
        } else if (isEmpty()){
            throw new RuntimeException();
        }
        return stack2.pop();
    }

    public boolean isEmpty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }

    private void reverse() {
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
    }
}

