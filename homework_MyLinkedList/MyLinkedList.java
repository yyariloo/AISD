package homework_MyLinkedList;

public class MyLinkedList<T> implements MyList<T> {
    class MyNode<T> {
        private T value;
        private MyNode<T> next;

        private MyNode(T value) {
            this.value = value;
        }
    }

    private MyNode<T> head;
    private MyNode<T> tail;
    private int length;

    public MyLinkedList() {
        head = null;
        tail = null;
        length = 0;
    }

    @Override
    public void add(T element) {
        MyNode<T> newNode = new MyNode<>(element);
        if(length == 0) {
            head = newNode;
            tail = newNode;
            length = 1;
            newNode.next = null;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    @Override
    public T get(int index) {
        MyNode<T> current = head;
        int i = 0;
        while (current != null) {
            if(i == index) {
                return current.value;
            }
            i++;
            current = current.next;
        }
        return null;
    }

    @Override
    public void remove(T element) {
        MyNode<T> previous = null;
        MyNode<T> current = head;
        while (current != null) {
            if(current.value.equals(element)) {
                if(previous != null) {
                    previous.next = current.next;
                } else {
                    head = current.next;
                }
            }
            previous = current;
            current = current.next;
        }
    }
}
