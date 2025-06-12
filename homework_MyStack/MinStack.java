package homework_MyStack;

import java.util.EmptyStackException;

class MinStack {
    private final Stack mainStack;
    private final Stack minStack;

    public MinStack(int size) {
        this.mainStack = new Stack(size);
        this.minStack = new Stack(size);
    }

    public boolean push(int value) {
        if (!mainStack.push(value)) {
            return false;
        }

        if (minStack.isEmpty() || value <= minStack.peek()) {
            minStack.push(value);
        } else {
            minStack.push(minStack.peek());
        }
        return true;
    }

    public int pop() {
        minStack.pop();
        return mainStack.pop();
    }

    public boolean isEmpty() {
        return mainStack.isEmpty();
    }

    public int getMin() {
        if (minStack.isEmpty()) throw new EmptyStackException();
        return minStack.peek();
    }

    public int peek() {
        return mainStack.peek();
    }
}