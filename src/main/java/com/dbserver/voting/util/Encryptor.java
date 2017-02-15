package com.dbserver.voting.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

// Reference: http://stackoverflow.com/a/22445878/7085076
public class Encryptor {
	private static final String SALT = "-my-salt-string-"; // 128 bits key

	public String encrypt(String value) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(SALT.getBytes("UTF-8"), "AES");

            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, skeySpec);

            byte[] encrypted = aesCipher.doFinal(value.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String decrypt(String encrypted) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(SALT.getBytes("UTF-8"), "AES");

            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, skeySpec);

            byte[] original = aesCipher.doFinal(Base64.getDecoder().decode(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
