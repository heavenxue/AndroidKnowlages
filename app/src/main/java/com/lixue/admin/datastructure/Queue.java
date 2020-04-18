package com.lixue.admin.datastructure;

import java.util.LinkedList;

class Queue {
    int front, rear, size;//最前面的元素，最后面的元素，元素的个数
    int  capacity;//容量
    int array[];

    public Queue(int capacity) {
        this.capacity = capacity;
        front = this.size = 0;
        rear = capacity - 1;
        array = new int[this.capacity];
    }

    // Queue is full when size becomes equal to
    // the capacity
    boolean isFull(Queue queue) {
        return (queue.size == queue.capacity);
    }

    // Queue is empty when size is 0
    boolean isEmpty(Queue queue) {
        return (queue.size == 0); }

    // Method to add an item to the queue.入队
    // It changes rear and size
    void enqueue( int item) {
        if (isFull(this))
            return;
        this.rear = (this.rear + 1) % this.capacity;
        this.array[this.rear] = item;
        this.size = this.size + 1;
        System.out.println(item+ " enqueued to queue");
    }

    // Method to remove an item from queue.出队
    // It changes front and size
    int dequeue() {
        if (isEmpty(this))
            return Integer.MIN_VALUE;

        int item = this.array[this.front];
        this.front = (this.front + 1) % this.capacity;
        System.out.println("this.front is : " + this.front);
        this.size = this.size - 1;
        return item;
    }

    // Method to get front of queue
    int front() {
        if (isEmpty(this))
            return Integer.MIN_VALUE;

        return this.array[this.front];
    }

    // Method to get rear of queue
    int rear() {
        if (isEmpty(this))
            return Integer.MIN_VALUE;
        return this.array[this.rear];
    }
}


// Driver class
class Test {
    public static void main(String[] args) {
        Queue queue = new Queue(1000);

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        queue.enqueue(40);

        System.out.println(queue.dequeue() +" dequeued from queue\n");
        System.out.println(queue.dequeue() +" dequeued from queue\n");

        System.out.println("Front item is " + queue.front());

        System.out.println("Rear item is " + queue.rear());

        //使用链表来实现队列效果
        Queue1 queue1 = new Queue1();
        for (int i = 0; i < 10; i++)
            queue1.put(Integer.toString(i));
        while (!queue1.isEmpty())
            System.out.println(queue1.get());
    }

    static class Queue1 {
        private LinkedList list = new LinkedList();
        public void put(Object v) {
            list.addFirst(v);
        }
        public Object get() {
            return list.removeLast();
        }
        public boolean isEmpty() {
            return list.isEmpty();
        }
    }
}
