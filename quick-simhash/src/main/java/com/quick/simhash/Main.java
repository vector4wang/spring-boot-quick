package com.quick.simhash;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/6/22
 * Time: 11:13
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        String str1 = "this is string1";
        String str2 = "this is string2";

        SimHash simHash = new SimHash();
//    simHash
//    //compare 6 and 8 for test.
		simHash.compareString(String.valueOf(6), String.valueOf(8));
		System.out.println();
//
//    //compare str1 and str2 for test.
		simHash.compareString(str1, str2);
    }
}
