 /*
 File: Stack.java
 Name: Alex Yuk
 Date: 10/22/2019
 */

import java.util.Iterator;

public class Stack<T> implements Iterable<T> {

    private Node top;
    private int size;

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    public void push(T element) {
        this.size++;

        Node temp = this.top;
        this.top = new Node();
        this.top.element = element;
        this.top.next = temp;
    }

    public T pop() {
        this.size--;
        T top_element = this.top.element;
        this.top = this.top.next;

        return top_element;
    }

    public T peek() {
        return this.top.element;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.top == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<T> {
        private Node temp = top;

        public boolean hasNext() {
            return temp != null;
        }

        public void remove() {
        }

        public T next() {
            T item = temp.element;
            temp = temp.next;
            return item;
        }
    }

    class Node {
        T element;
        Node next;
    }

    public String toString() {
        String returned = "";
        Node temp = top;
        while (temp != null) {
            returned += temp.element + " ";
            temp = temp.next;
        }
        return returned;
    }
}