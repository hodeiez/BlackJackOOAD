package BlackJack.model;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Created by Hodei Eceiza
 * Date: 12/15/2020
 * Time: 15:24
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class EncryptDecrypt {
    private static final String KEY = "pasahitza";
    protected String encryptText(String toEncrypt) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {

       DESKeySpec dks = new DESKeySpec(KEY.getBytes());
        SecretKey desKey = SecretKeyFactory.getInstance("DES").generateSecret(dks);
        Cipher encryptor = Cipher.getInstance("DES");
        encryptor.init(Cipher.ENCRYPT_MODE, desKey);
        byte[] utf8 = toEncrypt.getBytes("UTF8");
        byte[] enc = encryptor.doFinal(utf8);
        byte[]enc64= Base64.getEncoder().encode(enc);
        return new String(enc64);

    }

    protected String decryptText(String toDecrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException, UnsupportedEncodingException {
        DESKeySpec dks = new DESKeySpec(KEY.getBytes());
        SecretKey desKey = SecretKeyFactory.getInstance("DES").generateSecret(dks);
        Cipher decrypt = Cipher.getInstance("DES");
        decrypt.init(Cipher.DECRYPT_MODE, desKey);

        byte[] dec = Base64.getDecoder().decode(toDecrypt.getBytes());
        byte[] unencrypted = decrypt.doFinal(dec);
        //byte[]dec64=Base64.getDecoder().decode(utf8);
        return new String(unencrypted,"UTF8");

    }
}
