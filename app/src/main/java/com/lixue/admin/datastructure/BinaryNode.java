package com.lixue.admin.datastructure;


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

    /**
     * 二叉搜索树（有序二叉树）
     * 二叉搜索树可以实现快速查找，插入和删除操作效率也很快，并且可以方便的实现数据的排序遍历
     * 二叉搜索树的特点：
     * 1、二叉搜索树的中的任意节点，其左子树中的每个结点的值都小于这个结点的值
     * 2、二叉搜索树的中的任意节点，其右子树中的每个结点都大于这个结点的值
     *
     * */
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
         * 1、先打印处于第一层的根结点，
         * 2、再打印根结点左子结点和再右子结点。也就是第二层的数据
         * 3、以此类推，再打印第三层等等。。
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

        /**
         * 二叉搜索树的查找操作
         * @param root
         * @param data
         * @return
         */
        private BinaryNode search(BinaryNode root,int data){
            if (root == null || (int)root.data == data){
                return root;
            }

            if ((int)root.data > data){
                return search(root.left,data);
            }

            return search(root.right,data);
        }

        /**
         * 二叉搜索树的插入操作
         * @param key
         */
        private void insert(int key){

        }

        private BinaryNode insertRec(BinaryNode root,int key){
            //如果根节点为null，则直接将其设置为根节点
            if (root == null){
                root = new BinaryNode(key);
                return root;
            }
            //被插入数据小于结点的值
            if (key < (int)root.data){
                root.left = insertRec(root.left,key);
            }
            //被插入数据大于结点的值
            else if(key > (int) root.data){
                root.right = insertRec(root.right,key);
            }
            return root;
        }

        /**
         * 二叉搜索树的删除操作
         * @param root 父节点
         * @param data 被删除结点的值
         * @return
         */
        private BinaryNode deleteRec(BinaryNode root,int data){
            if (root  == null) return root;
            //如果被删除结点的值小于root结点，则在父节点的左子树中继续遍历
            if (data < (int)root.data){
                root.left = deleteRec(root.left,data);
            }//如果被删除子节点的值大于root结点，则在父节点的右子树中继续遍历
            else if (data > (int) root.data){
                root.right = deleteRec(root.right,data);
            }else {//首先需要找到这个结点的右子树中的最小结点Min
                // 然后把它替换到被删除的结点上，然后递归的调用删除操作，删除这个最小结点Min
                if (root.left == null){
                    return root.right;
                }else if (root.right == null){
                    return root.left;
                }
                root.data = minValue(root.right);
                root.right = deleteRec(root.right,(int)root.data);
            }
            return root;
        }

        /**
         * 查找root结点子树中最小的值
         * @param node
         * @return
         */
        private int minValue(BinaryNode node){
            int minv = (int)node.data;
            while (node.left != null){
                minv = (int)node.left.data;
                node = node.left;
            }
            return minv;
        }
    }
}

