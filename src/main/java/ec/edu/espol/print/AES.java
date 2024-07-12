/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.print;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Alejandro Diez
 */
public class AES {
    private SecretKey key;
    private byte[] IV;
    private int T_LEN = 128;
    
    public void init() throws Exception{
        key = saveKey();
        IV = saveIV();
    }
    
    public String encrypt(String Message) throws Exception{
        byte[] messageInBytes = Message.getBytes();
        Cipher encryptedCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec  spec = new GCMParameterSpec(T_LEN, IV);
        encryptedCipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encryptedBytes = encryptedCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }
    
    public String decrypt(String encryptedMessage) throws Exception{
        byte[] messageInBytes = decode(encryptedMessage);
        Cipher decryptedCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec  spec = new GCMParameterSpec(T_LEN, IV);
        decryptedCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptedCipher.doFinal(messageInBytes);
        return new String(decryptedBytes);
    }
    
    public String encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }
    
    public byte[] decode(String data){
        return Base64.getDecoder().decode(data);
    }
    
    public static SecretKey saveKey() throws Exception{
            byte[] keyBytes = Files.readAllBytes(Paths.get("src/main/resources/bin/SecretKey.key"));
            return new SecretKeySpec(keyBytes, "AES");
    }
    
    public static byte[] saveIV() throws Exception{
        return Files.readAllBytes(Paths.get("src/main/resources/bin/IV.bin"));
    }
    
}
