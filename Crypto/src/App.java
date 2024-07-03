public class App {
    public static void main(String[] args)  {
        try{
            AES aes = new AES();
            aes.init();
            String encryptedMessage = aes.encrypt("Cryptography");
            String decryptedMEssage = aes.decrypt(encryptedMessage);

            System.out.println("Encrypted Message = "+encryptedMessage );
            System.out.println("Decrypted Message = "+decryptedMEssage );
        }catch(Exception ignored){}
    }
}
