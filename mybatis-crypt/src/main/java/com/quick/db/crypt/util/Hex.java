package com.quick.db.crypt.util;

import org.springframework.util.StringUtils;

public class Hex {
    /**
     * char 转 byte的适配器
     */
    private static final String BYTE_CONVERTE="0123456789ABCDEF";

    /**
     * 将byte转化为16进制的字符串
     * @param src byte[] 数组
     * @return 返回16进制字符串
     */
    public static String bytesToHexString(byte[] src){

        if (src == null || src.length <= 0) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder("");
        for (int j:src) {
            int v = j & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 将16进制的字符串转化为byte[] 数组
     * @param hexString 16进制字符串
     * @return byte[] 数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (StringUtils.isEmpty(hexString)) {
            return new byte[0];
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 将char转化为byte数组
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) BYTE_CONVERTE.indexOf(c);
    }
}
