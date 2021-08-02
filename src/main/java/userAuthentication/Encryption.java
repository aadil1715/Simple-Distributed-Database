package userAuthentication;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

//Referred https://mkyong.com/java/java-aes-encryption-and-decryption/ for encryption and decryption.
public class Encryption {

    private static final int SALT_NUM_BYTES = 16;
    private static final int IV_NUM_BYTES = 12;
    private static final int T_LEN = 128;
    private static final String AlGO = "AES/GCM/NoPadding";
    static String commonString = "5408 - Group 3 Encryption";

    public static String encrypt(String password) {
        try {
            byte[] salt = getRandomNonce(SALT_NUM_BYTES);
            byte[] iv = getRandomNonce(IV_NUM_BYTES);

            SecretKey secretKey = getSecretKey(commonString.toCharArray(), salt);
            Cipher cipher = Cipher.getInstance(AlGO);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(T_LEN, iv));
            byte[] cipherText = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
                    .put(iv)
                    .put(salt)
                    .put(cipherText)
                    .array();
            return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encrypted) {
        try {

            byte[] decode = Base64.getDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8));
            ByteBuffer byteBuffer = ByteBuffer.wrap(decode);
            byte[] iv = new byte[IV_NUM_BYTES];
            byteBuffer.get(iv);
            byte[] salt = new byte[SALT_NUM_BYTES];
            byteBuffer.get(salt);
            byte[] cipherText = new byte[byteBuffer.remaining()];
            byteBuffer.get(cipherText);

            SecretKey secretKey = getSecretKey(commonString.toCharArray(), salt);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(T_LEN, iv));
            byte[] plainText = cipher.doFinal(cipherText);
            return new String(plainText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SecretKey getSecretKey(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    private static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }
}