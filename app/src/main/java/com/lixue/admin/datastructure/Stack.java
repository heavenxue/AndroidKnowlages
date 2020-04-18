package com.lixue.admin.datastructure;

import java.util.LinkedList;

/* Java program to implement basic stack
operations */
public class Stack {

    static final int MAX = 1000;
    int top;
    int a[] = new int[MAX]; // Maximum size of Stack

    boolean isEmpty(){
        return (top < 0);
    }
    Stack(){
        top = -1;
    }

    boolean push(int x){
        if (top >= (MAX-1)){
            System.out.println("Stack Overflow");
            return false;
        }
        else{
            a[++top] = x;//++top是先自增后赋值
            System.out.println(x + " pushed into stack");
            return true;
        }
    }

    int pop(){
        if (top < 0){
            System.out.println("Stack Underflow");
            return 0;
        }
        else{
            int x = a[top--];
            return x;
        }
    }
}

// Driver code
class Main{
    public static void main(String args[]){
        Stack s = new Stack();
        s.push(10);
        s.push(20);
        s.push(30);
        System.out.println(s.pop() + " Popped from stack");

        //使用链表实现栈效果
        StackL stack = new StackL();
        for (int i = 0; i < 10; i++)
            stack.push(i);
        System.out.println(stack.top());
        System.out.println(stack.top());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
class StackL {
    private LinkedList list = new LinkedList();
    public void push(Object v) {
        list.addFirst(v);
    }
    public Object top() {
        return list.getFirst();
    }
    public Object pop() {
        return list.removeFirst();
    }
}

