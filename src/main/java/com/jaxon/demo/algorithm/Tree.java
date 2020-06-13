package com.jaxon.demo.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Tree {

    int value;
    List<Tree> children;

    public Tree(List<Tree> children,int value){
        this.value = value;
        this.children = children;
    }

    public void egodicTree(){
        System.out.println(value);
        if(children != null){

            for(Tree tree :children){
                tree.egodicTree();
            }
        }
    }

    public static void main(String[] args) {
        Tree b41 = new Tree(null,41);
        Tree b42 = new Tree(null,42);
        List<Tree> childree31 = new ArrayList<>();
        childree31.add(b41);
        childree31.add(b42);


        Tree b43 = new Tree(null,43);
        Tree b44 = new Tree(null,44);
        List<Tree> childree33 = new ArrayList<>();
        childree31.add(b43);
        childree31.add(b44);


        Tree b31 = new Tree(childree31,31);
        Tree b32 = new Tree(null,32);
        Tree b33 = new Tree(childree33,33);
        Tree b34 = new Tree(null,34);

        List<Tree> childree21 = new ArrayList<>();
        childree21.add(b31);
        childree21.add(b32);

        Tree b21 = new Tree(childree21,21);

        List<Tree> childree22 = new ArrayList<>();
        childree22.add(b33);
        childree22.add(b34);
        Tree b22 = new Tree(childree22,22);

        List<Tree> childree11 = new ArrayList<>();
        childree11.add(b21);
        childree11.add(b22);
        Tree b11 = new Tree(childree11,11);
        b11.egodicTree();
    }

}
