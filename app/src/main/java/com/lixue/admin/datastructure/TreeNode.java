package com.lixue.admin.datastructure;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    int value;
    List descendents;
    public TreeNode(int value){
        this.value = value;
        this.descendents = new ArrayList();
    }
    static class Test{
        public static void main(String[] args){
            TreeNode root = new TreeNode('a');
            TreeNode bNode = new TreeNode('b');
            TreeNode cNode = new TreeNode('c');
            TreeNode dNode = new TreeNode('d');
            TreeNode eNode = new TreeNode('e');

            //将bNode设为root的子节点
            root.descendents.add(bNode);
            //将c,d,e设置为bNode的子节点
            bNode.descendents.add(cNode);
            bNode.descendents.add(dNode);
            bNode.descendents.add(eNode);
        }

    }
}

