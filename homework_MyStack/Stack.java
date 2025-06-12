package homework_MyStack;

import java.util.EmptyStackException;

class Stack {
    private int[] stack;
    private int top;

    public Stack(int capacity) {
        stack = new int[capacity];
        top = -1;
    }

    public boolean push(int x) {
        if (top < stack.length - 1) {
            stack[++top] = x;
            return true;
        }
        return false;
    }

    public int pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack[top--];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack[top];
    }
}
