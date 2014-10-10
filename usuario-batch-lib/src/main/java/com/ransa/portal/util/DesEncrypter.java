package com.ransa.portal.util;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.ransa.portal.exception.EjecucionException;

/**
 * utilizado para encriptar textos mediante el algorimo DES
 * 
 * @author César Bardález Vela
 */
public class DesEncrypter {
	private Cipher ecipher;
	private Cipher dcipher;

	/**
	 * Crea una clave de 56 bits segun el estandar DES, la cual puede ser usada
	 * como llave unica de encriptación
	 */
	public static SecretKey createSecretKey() {
		KeyGenerator kgen = null;
		try {
			kgen = KeyGenerator.getInstance("DES");
			kgen.init(56);

		} catch (NoSuchAlgorithmException e) {
			throw new EjecucionException(e.getLocalizedMessage(), e);
		}
		return kgen.generateKey();
	}

	/**
	 * Construye el objeto de encriptacion DES
	 * 
	 * @param key
	 *            Llave de encriptacion
	 */
	public DesEncrypter(SecretKey key) {
		try {
			ecipher = Cipher.getInstance("DES");
			dcipher = Cipher.getInstance("DES");
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);

		} catch (Exception e) {
			throw new EjecucionException(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * Metodo de encriptacion de textos
	 */
	public String encrypt(String str) {
		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");

			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);

			// Encode bytes to base64 to get a string
			return new sun.misc.BASE64Encoder().encode(enc);
		} catch (Exception e) {
			throw new EjecucionException(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * Metodo para desencriptacion de textos
	 */
	public String decrypt(String str) {
		try {
			// Decode base64 to get bytes
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (Exception e) {
			throw new EjecucionException(e.getLocalizedMessage(), e);
		}
	}
}
