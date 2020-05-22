package com.lixue.admin.datastructure;


public class ListNode<Integer> {

    Node<Integer> head;//头部节点
    int size = 0;//节点数
    public class Node<T>{
        T vaule;//data值
        Node<T> next;//指向下一个节点
        public Node(T vaule, Node<T> next) {//构造方法
            this.vaule = vaule;
            this.next = next;
        }
    }
    public ListNode() {}

    /**
     * 链表添加方法
     * @param data
     */
    public void add(Integer data){

        if(head==null){
            head=new Node<Integer>(data,null);
            size++;
        }else{
            Node<Integer> temp = head;
            while(temp.next != null){
                temp = temp.next;
            }
            temp.next= new Node<Integer>(data,null);
            size++;
        }
    }

    public int size(){
        if(head == null){
            return 0;
        }else{
            int i = 1;
            Node<Integer> temp = head;
            while(temp.next != null){
                temp = temp.next;
                i ++;
            }
            return i;
        }
    }

    /**
     * 节点索引
     * @param index
     * @return
     */
    public Node<Integer> get(int index){
        if(index > size || index < 0){
            throw new IndexOutOfBoundsException();
        }else{
            int i = 0;
            Node<Integer> temp=head;
            if(index == i){
                return temp;
            }
            while(temp.next != null){
                temp = temp.next;
                if(index == ++i){
                    return temp;
                }
            }
            return null;
        }
    }

    /**
     * 删除第n个节点
     * @param n
     */
    public void deleteNode(int n){
        if(n > size || n<0){
            throw new IndexOutOfBoundsException();
        }
        Node faster = head;//指针
        while(faster != null){
            if(--n == 1){//被删除节点之前的一个节点
                faster.next = faster.next.next;//让指针的next指向next的next
                break;
            }
            faster = faster.next;
        }
    }

    /**
     * 移除 根据value值删除
     * @param o
     * @return
     */
    public boolean remove(Integer o) {
        Node<Integer> temp = head;
        if(temp.vaule == o){
            head = null;
            head.next = null;
            size--;
            return true;
        }
        Node<Integer> tempBefore;//记录要删除节点前的一个节点
        while(temp.next != null){
            tempBefore = temp;
            temp = temp.next;
            if(temp.vaule == o){
                if(temp.next != null){
                    tempBefore.next = temp.next;
                }
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * 反转链表
     */
    public void reverseNode(){
        Node now;//当前节点
        Node newNode = null;//新节点
        while(head != null){
            now = head;
            head = head.next;
            now.next = newNode;
            newNode = now;
            if(head == null){//保证头部还存在
                head = now;
                break;
            }
        }
    }
}