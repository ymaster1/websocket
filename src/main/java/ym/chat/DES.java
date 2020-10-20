package ym.chat;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;

/**
 * @Author: ym
 * @Description:
 * @Date: 2019/11/11 5:06 下午
 * @Version:
 */
public class DES {
    /**
     * 使用DES对字符串加密
     *
     * @param str
     *            utf8编码的字符串
     * @param key
     *            密钥（56位，7字节）
     *
     */
    public static byte[] desEncrypt(String str, String key) throws Exception {
        if (str == null || key == null) {
            return null;
        }
        /**
         * a. Cipher在使用时需以参数方式指定transformation
         * b. transformation的格式为algorithm/mode/padding，其中algorithm为必输项，如: AES/DES/CBC/PKCS5Padding
         * c. 缺省的mode为ECB，缺省的padding为PKCS5Padding
         * d. 在block算法与流加密模式组合时, 需在mode后面指定每次处理的bit数, 如DES/CFB8/NoPadding, 如未指定则使用缺省值,
         * SunJCE缺省值为64bits
         * e. Cipher有4种操作模式: ENCRYPT_MODE(加密), DECRYPT_MODE(解密), WRAP_MODE(导出Key), UNWRAP_MODE
         * (导入Key)，初始化时需指定某种操作模式
         */
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "DES"));
        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
        return bytes;
    }

    /**
     * 使用DES对数据解密
     *
     * @param bytes
     *            utf8编码的二进制数据
     * @param key
     *            密钥（16字节）
     * @return 解密结果
     * @throws Exception
     */
    public static String desDecrypt(byte[] bytes, String key) throws Exception {
        if (bytes == null || key == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "DES"));
        bytes = cipher.doFinal(bytes);
        return new String(bytes, "utf-8");
    }

    /**
     * 使用base64解决乱码
     *
     * @param secretKey
     *            加密后的字节码
     */
    public static String jdkBase64String(byte[] secretKey) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(secretKey);
    }

    /**
     * 使用jdk的base64 解密字符串 返回为null表示解密失败
     *
     * @throws IOException
     */
    public static byte[] jdkBase64Decoder(String str) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(str);
    }

    public static void main(String[] args) throws Exception {
//加密
        String result = jdkBase64String(desEncrypt("ymaster1", "12345678"));
        System.out.println(result);
//解密
        String desDecrypt = desDecrypt(jdkBase64Decoder(result), "12345678");
        System.out.println(desDecrypt);

        System.out.println("9vOPEChTi44=".length());
    }


}