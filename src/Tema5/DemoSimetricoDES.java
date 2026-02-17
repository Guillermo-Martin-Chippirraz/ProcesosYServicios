package Tema5;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class DemoSimetricoDES {

    private static String b64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static void main(String[] args) throws Exception {

        String mensaje = "Mensaje secretérrimo";

        System.out.println("=== CIFRADO SIMÉTRICO DES ===");

        // Generar clave DES
        KeyGenerator kgDES = KeyGenerator.getInstance("DES");
        kgDES.init(56);
        SecretKey claveDES = kgDES.generateKey();

        // Generar IV (DES usa bloques de 8 bytes)
        SecureRandom random = new SecureRandom();
        byte[] ivBytes = new byte[8];
        random.nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        // Cifrar con DES/CBC(PKCS5Padding
        Cipher cifrador = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cifrador.init(Cipher.ENCRYPT_MODE, claveDES, iv);
        byte[] cifrado = cifrador.doFinal(mensaje.getBytes("UTF-8"));

        System.out.println("Mensaje original: " + mensaje);
        System.out.println("Clave DES (Base64): " + b64(claveDES.getEncoded()));
        System.out.println("IV DES (Base64): " + b64(ivBytes));
        System.out.println("Cifrado DES (Base64): " + b64(cifrado));

        //Descifrar
        Cipher descifrador = Cipher.getInstance("DES/CBC/PKCS5Padding");
        descifrador.init(Cipher.DECRYPT_MODE, claveDES, iv);
        byte[] descifrado = descifrador.doFinal(cifrado);

        System.out.println("Descifrado DES: " + new String(descifrado, "UTF-8"));
    }
}
