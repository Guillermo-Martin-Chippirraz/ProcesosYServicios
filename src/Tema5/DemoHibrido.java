package Tema5;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

public class DemoHibrido {
    private static String b64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static void main(String[] args) throws Exception {
        String mensaje = "Mensaje secretitos jijiji";

        System.out.println("=== ESQUEMA HÍBRIDO (RSA + AES) ===");

        //Generar clave AES
        KeyGenerator kgAES = KeyGenerator.getInstance("AES");
        kgAES.init(128); // 128, 192 o 256 según el entorno
        SecretKey claveAES = kgAES.generateKey();

        // Vector de inicialización (IV) para modo CBC
        SecureRandom random = new SecureRandom();
        byte[] ivBytes = new byte[16]; // 16 bytes para AES
        random.nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        // Cifrar con AES/CBC/PKCS5Padding
        Cipher cipherAES = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherAES.init(Cipher.ENCRYPT_MODE, claveAES, iv);
        byte[] cifradoAES = cipherAES.doFinal(mensaje.getBytes("UTF-8"));

        // Generar par de claves RSA
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair parRSA = kpg.generateKeyPair();
        PublicKey clavePublica = parRSA.getPublic();
        PrivateKey clavePrivada = parRSA.getPrivate();

        // Cifrar la clave AES con RSA
        Cipher cipherRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipherRSA.init(Cipher.ENCRYPT_MODE, clavePublica);
        byte[] claveAESCifradaConRSA = cipherRSA.doFinal(claveAES.getEncoded());

        System.out.println("Clave AES cifrada con RSA (Base64): " + b64(claveAESCifradaConRSA));

        // El receptor descifra la clave AES con su clave privada RSA
        Cipher descifrarRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        descifrarRSA.init(Cipher.DECRYPT_MODE, clavePrivada);
        byte[] claveAESRecuperadaBytes = descifrarRSA.doFinal(claveAESCifradaConRSA);
        SecretKey claveAESRecuperada = new SecretKeySpec(claveAESRecuperadaBytes, "AES");

        // Con la clave AES recuperada, descifra el mensaje
        Cipher descifrarAESHibrido = Cipher.getInstance("AES/CBC/PKCS5Padding");
        descifrarAESHibrido.init(Cipher.DECRYPT_MODE, claveAESRecuperada, iv);
        byte [] mensajeRecuperado = descifrarAESHibrido.doFinal(cifradoAES);

        System.out.println("Mensaje recuperado con esquema híbrido: " +
                new String(mensajeRecuperado, "UTF-8"));
    }
}
