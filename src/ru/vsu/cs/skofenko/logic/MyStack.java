package ru.vsu.cs.skofenko.logic;

import java.util.EmptyStackException;

public class MyStack<T> implements IStack<T> {
    private class LinkedNode<T> {
        T value;
        LinkedNode<T> next;

        public LinkedNode(T value, LinkedNode<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private LinkedNode<T> head = null;
    private int size =0;

    public int getSize() {
        return size;
    }

    @Override
    public T peek() {
        if(empty()) throw new EmptyStackException();
        return head.value;
    }

    @Override
    public T pop() {
        if(empty()) throw new EmptyStackException();
        T value = head.value;
        head=head.next;
        size--;
        return value;
    }

    @Override
    public T push(T element) {
        head=new LinkedNode<>(element,head);
        size++;
        return element;
    }

    @Override
    public boolean empty() {
        return size==0;
    }

    @Override
    public void clear() {
        head=null;
        size=0;
    }
}
