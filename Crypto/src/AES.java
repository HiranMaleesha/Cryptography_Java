import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private SecretKey key;
    private int KEY_SIZE = 128;
    private int T_LEN = 128; 
    private byte[] IV;
    //private Cipher encryptionCipher;


    public void init() throws Exception{
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(KEY_SIZE);
        key = generator.generateKey();
    }

    public void initFromStrings(String secretKey,String IV) {
    key = new SecretKeySpec(decode(secretKey),"AES");
    this.IV = decode(IV);
    };
    

    public String encryptOld(String message) throws Exception{
        byte[]  messageInBytes = message.getBytes();
        Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE,key);
        IV = encryptionCipher.getIV();
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }

    public String encrypt(String message) throws Exception{
        byte[]  messageInBytes = message.getBytes();
        Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
        encryptionCipher.init(Cipher.ENCRYPT_MODE,key,spec);
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
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

    public void exportKeys(){
        System.err.println("Secret key :"+encode(key.getEncoded()));
        System.err.println("IV : "+encode(IV));
    } 


    public static void main(String[] args)  {
        try{
            AES aes = new AES();
            aes.initFromStrings( "DjrA03mQbx8uKOX41SmLsA==","PLkgZ6c4H9lKXLUO");
            String encryptedMessage = aes.encrypt("HI friend");
            System.out.println("Encrypted Message = "+encryptedMessage );

        }catch(Exception ignored){}
    }

}

