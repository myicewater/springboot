package com.example.demo.algorithm;

public class BinaryTree {

    BinaryTree left;

    BinaryTree right;

    int value;

    /**
     * 遍历二叉树
     */
    public void egodicBinaryTree(){
        System.out.println(value);
        if(this.left != null){
            left.egodicBinaryTree();
        }
        if(right !=null){
            right.egodicBinaryTree();
        }
    }

    public void egodicBinaryTree2(){
        System.out.println(value);
        if(left != null){
            System.out.println(left.value);
        }
        if(right != null){
            System.out.println(right.value);
        }

    }

    public BinaryTree(BinaryTree left,BinaryTree right,int value){
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public static void main(String[] args) {
        BinaryTree b41 = new BinaryTree(null,null,41);
        BinaryTree b42 = new BinaryTree(null,null,42);
        BinaryTree b43 = new BinaryTree(null,null,43);
        BinaryTree b44 = new BinaryTree(null,null,44);

        BinaryTree b31 = new BinaryTree(b41,b42,31);
        BinaryTree b32 = new BinaryTree(null,null,32);
        BinaryTree b33 = new BinaryTree(b43,b44,33);
        BinaryTree b34 = new BinaryTree(null,null,34);

        BinaryTree b21 = new BinaryTree(b31,b32,21);
        BinaryTree b22 = new BinaryTree(b33,b34,22);

        BinaryTree b11 = new BinaryTree(b21,b22,11);
        b11.egodicBinaryTree();

    }

    public BinaryTree getLeft() {
        return left;
    }

    public void setLeft(BinaryTree left) {
        this.left = left;
    }

    public BinaryTree getRight() {
        return right;
    }

    public void setRight(BinaryTree right) {
        this.right = right;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
