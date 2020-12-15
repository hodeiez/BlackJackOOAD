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
public class LogEncrypted extends EncryptDecrypt implements IlogObserver  {


    @Override
    public void update(String text) {

        try {
            FileWriter file=new FileWriter("log.txt",true);
            file.write(encryptText(text)+"\n");
            file.close();
           // System.out.println(encryptText(text));
           // System.out.println("decrypted-> " + decryptText(encryptText(text)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
