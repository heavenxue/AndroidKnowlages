package com.lixue.admin.datastructure;

import android.content.Context;
import android.content.Intent;
import android.text.style.AlignmentSpan;

import org.w3c.dom.Node;

import java.util.LinkedList;

/**
 * 二叉树
 * 定义：子节点最多有两个
 * 完全二叉树：如果在一棵二叉树中，除了叶子结点，其余层的结点都是满的，并且最后一层的结点都是靠左排列，则此二叉树为完全二叉树
 *
 * 二叉树有两种存储方式：
 * 链式存储：基于指针或引用,如下代码所示
 * 数组存储：数组存储必须按照2 * i + 1的规律存储
 *
 * 二叉树的遍历：
 * 前序遍历：对于树中的任意节点，先打印这个节点的，再打印左节点data，再打印右节点
 * 中序遍历：对于树中的任意节点，先打印左节点，再打印这个节点data，再打印右节点
 * 后序遍历：对于树中的任意节点，先打印左节点，再打印右节点，再打印这个节点data
 * 二叉树的这三个遍历就是一个递归过程，用伪代码表示如下所示
 *
 * 层级遍历：一层一层的遍历
 * 1、先打印处于第一层的根节点
 * 2、再打印根节点左子节点和右子节点。也就是第二层的数据
 * 3、以此类推，再打印第三层等等
 * 伪代码实现如下
 *
 */
public class BinaryNode {
    //data保存节点自身数据
    private Object data;
    //left和right指向左节点和右节点
    private BinaryNode left,right;

    public BinaryNode(){
        data = left = right = null;
    }

    public BinaryNode(Object data){
        this.data = data;
        left = right = null;
    }
    static class Test{
        public static void main(String[] args){

        }

        /**
         * 中序遍历
         * @param node
         */
        private void traverseInOrder(BinaryNode node){
            if (node != null){
                traverseInOrder(node.left);
                System.out.println(" " + node.data);
                traverseInOrder(node.right);
            }
        }
        /**
         * 后序遍历
         * @param node
         */
        private void traversePosOrder(BinaryNode node){
            if (node != null){
                traversePosOrder(node.left);
                traversePosOrder(node.right);
                System.out.println(" " + node.data);
            }
        }
        /**
         * 前序遍历
         * @param node
         */
        private void traversePreOrder(BinaryNode node){
            if (node != null){
                System.out.println(" " + node.data);
                traversePreOrder(node.left);
                traversePreOrder(node.right);
            }
        }

        /**
         * 层级遍历
         * @param root
         */
        private void traverLevelOrder(BinaryNode root){
            if (root == null){
                return;
            }
            LinkedList<BinaryNode> nodes = new LinkedList<>();
            nodes.add(root);
            BinaryNode currentNode;
            while (!nodes.isEmpty()){
                //poll使用方法,获取并删除列表的第一个元素
                //peek使用方法,获取并不删除列表的第一个元素
                //pop取堆栈中取出元素，并出栈
                currentNode = nodes.poll();
                System.out.println(currentNode.data);
                if (currentNode.left != null){
                    nodes.add(currentNode.left);
                }
                if (currentNode.right != null){
                    nodes.add(currentNode.right);
                }
            }
        }
    }
}

