package com.epam.esm.service;

public class Test {

    public static void main(String[] args) {
        System.out.println(polendromTwo("aaabaa"));
    }

    public static boolean polendrom(String s) {
        if (s != null) {
            StringBuilder stringBuilder = new StringBuilder(s);
            String s1 = stringBuilder.reverse().toString();
            return s.equals(s1);
        }
        return false;
    }

    public static boolean polendromTwo(String s) {
        char[] chars = s.toCharArray();

        for (int i = 0; i < chars.length/2; i++) {
            if (chars[i]!=chars[chars.length-1-i]){
                return false;
            }
        }
        return true;
    }
}
