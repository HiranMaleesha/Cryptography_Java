import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Client {
    private SecretKey key;
    private int KEY_SIZE = 128;
    private int T_LEN = 128; 
    private byte[] IV;

    public void initFromStrings(String secretKey,String IV) {
        key = new SecretKeySpec(decode(secretKey),"AES");
        this.IV = decode(IV);
        }

    public String decrypt(String encryptedMessage) throws Exception{
        byte[]  messageInBytes = decode(encryptedMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
        decryptionCipher.init(Cipher.DECRYPT_MODE,key,spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
        return new String(decryptedBytes);
    }

    private String encode(byte[] data)
     { 
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) 
    { 
        return Base64.getDecoder().decode(data);
    }

public static void main(String[] args){
    try{
        Client client = new Client();
    client.initFromStrings( "DjrA03mQbx8uKOX41SmLsA==","PLkgZ6c4H9lKXLUO");
    String decryptedMessage = client.decrypt("X6x6N4e/E8N1/sr29p90UBkkU1pmcxFjMw==");
    System.out.println(decryptedMessage);
    }catch (Exception ignored){}
  }
}




