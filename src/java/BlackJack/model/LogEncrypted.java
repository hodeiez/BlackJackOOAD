package BlackJack.model;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Created by Hodei Eceiza
 * Date: 12/12/2020
 * Time: 16:58
 * Project: BlackJackOOAD
 * Copyright: MIT
 */
public class LogEncrypted implements IlogObserver {
    String KEY = "pasahitza";

    @Override
    public void update(String text) {


        try {
            FileWriter file=new FileWriter("log.txt",true);
            file.write(encryptText(text)+"\n");
            file.close();
           // System.out.println(encryptText(text));
           // System.out.println("decrypted-> " + decryptText(encryptText(text)));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String encryptText(String toEncrypt) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        DESKeySpec dks = new DESKeySpec(KEY.getBytes());
        SecretKey desKey = SecretKeyFactory.getInstance("DES").generateSecret(dks);
        Cipher encrypter = Cipher.getInstance("DES");
        encrypter.init(Cipher.ENCRYPT_MODE, desKey);
        byte[] utf8 = toEncrypt.getBytes("UTF8");
        byte[] enc = encrypter.doFinal(utf8);
        byte[]enc64= Base64.getEncoder().encode(enc);
        return new String(enc64);

    }

    private String decryptText(String toDecrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException, UnsupportedEncodingException {
        DESKeySpec dks = new DESKeySpec(KEY.getBytes());
        SecretKey desKey = SecretKeyFactory.getInstance("DES").generateSecret(dks);
        Cipher decrypter = Cipher.getInstance("DES");
        decrypter.init(Cipher.DECRYPT_MODE, desKey);

        byte[] dec = Base64.getDecoder().decode(toDecrypt.getBytes());
        byte[] unencrypted = decrypter.doFinal(dec);
        //byte[]dec64=Base64.getDecoder().decode(utf8);
        return new String(unencrypted,"UTF8");

    }
}
