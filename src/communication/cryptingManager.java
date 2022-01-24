package communication;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class cryptingManager {
	
	
private PublicKey publicKey;
private PrivateKey privateKey;

private Cipher cipher;
	
	
	public cryptingManager() {
		
	}
	
	
	public void createKey() {
		
		KeyPairGenerator keyPairGenerator;
		try {
			
			
			keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//			keyPairGenerator.initialize(4096);
			keyPairGenerator.initialize(1024);
			
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			
			publicKey = keyPair.getPublic();
	        privateKey = keyPair.getPrivate();
			
//	        System.out.println("Pup Key Client : " + publicKey);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		 try {
			 
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public byte[] encryptPacket(byte[] packetData, PublicKey clientKey) { //verschlüsseln with client public key
		
		byte[] cryptData = new byte[0];
		
		try {
			
			cipher.init(Cipher.ENCRYPT_MODE, clientKey);
			
			byte[] enCryptedData = cipher.doFinal(packetData);
			
			return enCryptedData;
			
			/*
			int count=0;
			int pos = 0;
			int size = packetData.length;
			
			byte[] toCrypt = new byte[1024];
			
			while(pos < size) {
				
				if(count == 0) {
					
//					System.out.println("Size - pos:" + (size - pos));
					
					if((size - pos)>= 1024) {
						toCrypt = new byte[1024];
					}else {
						toCrypt = new byte[size - pos];
					}
					
				}
				
//				System.out.println("toCryptLength:" + toCrypt.length);
				
				while(count < toCrypt.length) {
					
					toCrypt[count] = packetData[pos];
					
					count++;
					pos++;
				}
				
//				System.out.println("Count:" + count);
//				System.out.println("pos:" + pos);
//				System.out.println("size:" + size);
				
//				System.out.println("toCryptOriginalData:" + toCrypt.length);
				

				System.out.println("output size:" + cipher.getOutputSize(size));
				
				toCrypt  = cipher.doFinal(toCrypt) ;
				
//				System.out.println("toCryptenCryptedData:" + toCrypt.length);
				
				byte[] dataBuffer = cryptData;
				
				cryptData = new byte[dataBuffer.length + toCrypt.length];
				
				int Pos = 0;
				
				for(byte B : dataBuffer) {
					
					cryptData[Pos] = B;
					Pos++;
				
				}
				
				for(byte B : toCrypt) {
					
					cryptData[Pos] = B;
					Pos++;
					
				}
				
				count = 0;
				
			}
			*/
			
			
			
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		System.out.println("originalData:" + packetData.length);
		System.out.println("enCryptedData:" + cryptData.length);
		
		return cryptData;
		
	}
	
	
	public byte[] decryptPacket(byte[] packetData) { //entschlüßeln with server private key
		
		byte[] cleanData = new byte[0];
		
		try {
			
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			cleanData = cipher.doFinal(packetData);
			
			/*
			int count=0;
			int pos = 0;
			int size = packetData.length;
			
			byte[] toCrypt = new byte[512];
			
			while(pos < size) {
				
				if(count == 0) {
					
					if((size - pos)>= 512) {
						toCrypt = new byte[512];
					}else {
						toCrypt = new byte[size - pos];
					}
					
				}
				
				
				while(count < toCrypt.length) {
					
					toCrypt[count] = packetData[pos];
					
					count++;
					pos++;
				}
				
				toCrypt  = cipher.doFinal(toCrypt);
				
				
				byte[] dataBuffer = cleanData;
				
				cleanData = new byte[dataBuffer.length + toCrypt.length];
				
				int Pos = 0;
				
				for(byte B : dataBuffer) {
					
					cleanData[Pos] = B;
					Pos++;
				
				}
				
				for(byte B : toCrypt) {
					
					cleanData[Pos] = B;
					Pos++;
					
				}
				
				count = 0;
				
			}
			*/
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
        
		return cleanData;
		
	}
	
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
	
	
}
