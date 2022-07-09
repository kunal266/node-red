import java.math.*;
import java.util.*;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import javax.crypto.Cipher;
import java.util.Base64;
import java.util.*;


interface signature{
  public void test(String bruh);
}
class ecdsa implements signature{
  
  public void encrypt(String bruh) throws Exception{
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

    keyGen.initialize(256, random);

    KeyPair pair = keyGen.generateKeyPair();
    PrivateKey priv = pair.getPrivate();
    PublicKey pub = pair.getPublic();
    Signature dsa = Signature.getInstance("SHA1withECDSA");

    dsa.initSign(priv);

    String str = bruh;
    byte[] strByte = str.getBytes("UTF-8");
    dsa.update(strByte);

    byte[] realSig = dsa.sign();
    System.out.println("ECDSA Encryption : " + new BigInteger(1, realSig).toString(16));
}
  
  public void test(String bruh) {
    ecdsa ehd = new ecdsa();
    try{
      ehd.encrypt(bruh);
  }catch (Exception ingored){}
}}


class RSA implements signature{

  private PrivateKey privateKey;
  private PublicKey publicKey;

  public RSA() {
      try {
          KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
          generator.initialize(1024);
          KeyPair pair = generator.generateKeyPair();
          privateKey = pair.getPrivate();
          publicKey = pair.getPublic();
      } catch (Exception ignored) {
      }
  }

  public String encrypt(String message) throws Exception{
      byte[] messageToBytes = message.getBytes();
      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      cipher.init(Cipher.ENCRYPT_MODE,publicKey);
      byte[] encryptedBytes = cipher.doFinal(messageToBytes);
      return encode(encryptedBytes);
  }
  private String encode(byte[] data){
      return Base64.getEncoder().encodeToString(data);
  }

  
  public void test(String bruh) {
      RSA rsa = new RSA();
      try{
          String encryptedMessage = rsa.encrypt(bruh);

          System.err.println("RSA Encryption: "+encryptedMessage);
      }catch (Exception ingored){}
  }
}


public class Encrypt{
  public static void main(String args[]) throws Exception
  {
    Scanner myObj = new Scanner(System.in);  // Create a Scanner object
    System.out.println("Enter Message to be Encrypted");
    String userName = myObj.nextLine(); 
    ecdsa Secdsa = new ecdsa();
    Secdsa.test(userName);
    RSA Srsa = new RSA();
    Srsa.test(userName);

  }
}