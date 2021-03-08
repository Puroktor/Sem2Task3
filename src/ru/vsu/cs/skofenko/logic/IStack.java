package ru.vsu.cs.skofenko.logic;

interface IStack<T> {
    T peek();
    T pop();
    T push(T element);
    boolean empty();
    void clear();
}
