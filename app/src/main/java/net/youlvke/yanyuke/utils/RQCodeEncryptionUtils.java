package net.youlvke.yanyuke.utils;


import java.security.Security;


public class RQCodeEncryptionUtils {



    /**
     * 二维码加密
     *
     * @param encryptionText 加密内容
     * @return 加密结果
     */
    public static String RQCodeEncryption(String encryptionText) {
        byte[] enk = ThreeDes.hex("Ticket");
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        byte[] encoded = ThreeDes.encryptMode(enk, encryptionText.getBytes());
        return Base64.encode(encoded);
    }

    /**
     * 二维码解密
     *
     * @param DecryptionText 解密的文本
     * @return 解密的内容
     */
    public static String RQCodeDecryption(String DecryptionText) {
        byte[] enk = ThreeDes.hex("Ticket");
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        byte[] reqPassword = Base64.decode(DecryptionText);
        byte[] srcBytes = ThreeDes.decryptMode(enk, reqPassword);
        return new String(srcBytes);
    }
}
