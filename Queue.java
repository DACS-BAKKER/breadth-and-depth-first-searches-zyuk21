/*
 File: Queue.java
 Name: Alex Yuk
 Date: 10/22/2019
 */

import java.util.Iterator;

public class Queue<T> implements Iterable<T> {
    Node head;
    Node tail;
    int size;
    
    public Queue () {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    public void enqueue(T element) {
        this.size++;

        Node temp = this.tail;
        this.tail = new Node();
        this.tail.element = element;
        this.tail.next = null;
        
        if (isEmpty()) 
            this.head = this.tail;
        else
            temp.next = this.tail;
    }

    public T dequeue() {
        this.size--;

        T head_element = this.head.element;
        this.head = this.head.next;
        if (isEmpty())
            this.tail = null;
        return head_element;
    }

    public T peek() {
        if (this.head != null)
            return this.head.element;
        return null;
    }

    public int size() {
        return this.size;
    } 

    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<T> {
        private Node temp = head;

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
        Node previous;
    }

    public String toString() {
        String returned = "";
        Node temp = this.head;
        while (temp != null) {
            returned += temp.element + " ";
            temp = temp.next;
        }
        return returned;
    }
    
}