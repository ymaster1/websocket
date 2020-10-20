package ym.chat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: ym
 * @Description:
 * @Date: 2020/3/22 12:25 下午
 * @Version:
 */
public class MD5 {
    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String md5(String source) {
        StringBuilder sb = new StringBuilder();
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(source.getBytes());
            byte[] md = md5.digest();
            //for (byte b : digest) {
            //    // 10进制转16进制，X 表示以十六进制形式输出，02 表示不足两位前面补0输出
            //    sb.append(String.format("%02X", b));
            //}
            //return sb.toString();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String hello = md5("hello");
        System.out.println(hello.equals("5D41402ABC4B2A76B9719D911017C592"));

        System.out.println(hello.length());
    }
}
