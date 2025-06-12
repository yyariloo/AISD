package homework_MyStack;

import java.util.EmptyStackException;

public class Main {
    public static void main(String[] args) {
        Stack stack = new Stack(3);

        stack.push(10);
        stack.push(15);
        stack.push(20);

        boolean result = stack.push(40);
        System.out.println(result);

        System.out.println("Вершина стека: " + stack.peek());
        System.out.println("Удален: " + stack.pop());
        System.out.println("Удален: " + stack.pop());
        System.out.println("Удален: " + stack.pop());

        try {
            stack.pop();
        } catch (EmptyStackException e) {
            System.out.println("Cтек пуст");
        }

        MinStack minStack = new MinStack(4);

        minStack.push(6);
        minStack.push(8);
        minStack.push(1);
        minStack.push(3);

        System.out.println("Минимум: " + minStack.getMin());
        System.out.println("Удален: " + minStack.pop());
        System.out.println("Минимум: " + minStack.getMin());
        System.out.println("Удален: " + minStack.pop());
        System.out.println("Минимум: " + minStack.getMin());
    }
}




