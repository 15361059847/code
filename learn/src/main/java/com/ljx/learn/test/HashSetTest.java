package com.ljx.learn.test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashSetTest {

     public static void main(String args[]) {
        String s1 = new String("aaa");
        String s2 = new String("aaa");
        String s3 = s2;

        System.out.println(s1 == s2);
        System.out.println(s3 == s2);
         System.out.println(s3.equals(s2));
        System.out.println(s1.equals(s2));
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
         Set hashset = new HashSet();
         hashset.add(s1);
         hashset.add(s2);
        Iterator it = hashset.iterator();
         while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
 }