package com.quick.db.crypt.util;

import com.quick.db.crypt.encrypt.AesDesDefaultEncrypt;

import java.security.NoSuchAlgorithmException;

public class TestMain {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        AesDesDefaultEncrypt aesDesDefaultEncrypt = new AesDesDefaultEncrypt();
//        System.out.println(aesDesDefaultEncrypt.decrypt("0e22e227f48d13e17baa500f68e72024"));
        System.out.println(aesDesDefaultEncrypt.encrypt("17727826853"));
    }
}