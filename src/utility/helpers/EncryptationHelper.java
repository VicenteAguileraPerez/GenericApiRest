package utility.helpers;

/**
 * @author vicenteaguilera
 * @author salvadormorado
 * @author antonio pulido
 * @author samuelbenitez
 * hola
 * hsjhd
 * shdjh
 */
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;



public class EncryptationHelper 
{
	
	public String encryptBase64(String data)
	{
		byte encodedData[] = Base64.encodeBase64(data.getBytes());
		return new String(encodedData);
	}
	public String decryptBase64(String data)
	{
		byte decodedData[] = Base64.decodeBase64(data.getBytes());
		return new String(decodedData);
	}
	/**
	 * aes encripto
	 * multiplos de 16
	 */
	private static String key ="D$L)neUnvj";
    
	public static byte keyAES[]= /*"A4$Gr@ADRQ/(&$jQA4$Gr@ADRQ/(&$jQ"*/key.getBytes(StandardCharsets.UTF_8);
	
	
	public final String algorithm="AES";
	
	public String encryptAES(String line)
	{
		
		
		byte dataToSent [] = line.getBytes();
		byte encryptedData[] = null;
		byte encryptedByteValue[] =null;
		
		
		//se autogenera un key
		SecretKeySpec secretKey = new SecretKeySpec(keyAES,algorithm);
		Cipher  c = null;
		
		try
		{
			c = Cipher.getInstance(algorithm);// se inicia con AES

			c.init(Cipher.ENCRYPT_MODE,secretKey);//encriptacion y le envi la clave que se generó
		
			// se manda esa por que en algunas operaciones hay un limite especifico para hacer esa operacion,
			// entonces se utiliza secretKey que es autogenerado y que necesita mi función init y esta llave es la que
			// se usa pata el cierre y el desencript
			//encriptacion AES no lo usamos directo por que podemos perder info
			// para ello nos refugiamos en base 64, esa no cambiará a la hora de decodificar
			encryptedData = c.doFinal(dataToSent);//guarda los datos encriptados
			System.out.println(3);
			//encriptar en base 64
			encryptedByteValue = new Base64().encode(encryptedData);
			System.out.println(4);
		}
		catch(Exception ex)
		{
			System.out.println("An error occur");
		}
		
		return new String(encryptedByteValue);
	}
	public String decryptAES(String line)
	{
		//Decrypt de BASE64
		byte dataToReceivedEncrypted [] = new Base64().decode(line);
		
		byte decryptedData[] =null;
	
		
		
		//se autogenera un key
		SecretKeySpec secretKey = new SecretKeySpec(keyAES,algorithm);
		Cipher  c = null;
		
		try
		{
			c = Cipher.getInstance(algorithm);// se inicia con AES
			c.init(Cipher.DECRYPT_MODE,secretKey);//encriptacion y le envi la clave que se generó
			// se manda esa por que en algunas operaciones hay un limite especifico para hacer esa operacion,
			// entonces se utiliza secretKey que es autogenerado y que necesita mi función init y esta llave es la que
			// se usa pata el cierre y el desencript
			//encriptacion AES no lo usamos directo por que podemos perder info
			// para ello nos refugiamos en base 64, esa no cambiará a la hora de decodificar
			//Decrypt de AES
			decryptedData = c.doFinal(dataToReceivedEncrypted);//guarda los datos encriptados
		
			
		}
		catch(Exception ex)
		{
			System.out.println("An error occur");
		}
		
		
		
		
		
		return new String(decryptedData);
	}
	

}
