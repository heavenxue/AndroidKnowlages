package com.lixue.admin.asmlifecycledemo;

public class Test {
    public static void main(String[] args){
        ClassLoader c1 = Test.class.getClassLoader();
        System.out.println("classloader " + c1);

        ClassLoader parent = c1.getParent();
        System.out.println("parent is " + parent);

        ClassLoader boot_strap = parent.getParent();
        System.out.println("boot_strap is " + boot_strap);
    }
}
