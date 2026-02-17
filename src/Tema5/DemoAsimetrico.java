package Tema5;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class DemoAsimetrico {
    private static String b64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static void main(String[] args) throws Exception{
        String mensaje = "Mensaje absurdamente secreto: https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://www.youtube.com/watch%3Fv%3DdQw4w9WgXcQ&ved=2ahUKEwieg9K7mOGSAxW5VqQEHfB5BC8QwqsBegQIHhAB&usg=AOvVaw0aHtehaphMhOCAkCydRLZU";

        System.out.println("=== CIFRADO ASIMÉTRICO (RSA) ===");

        // Generar par de claves RSA
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair parRSA = kpg.generateKeyPair();
        PublicKey clavePublica = parRSA.getPublic();
        PrivateKey clavePrivada = parRSA.getPrivate();

        System.out.println("Clave pública RSA (Base64): " + b64(clavePublica.getEncoded()));
        System.out.println("Clave privada RSA (Base64): " + b64(clavePrivada.getEncoded()));

        // Cifrar un pequeño mensaje directamente con RSA (no recomendable para datos grandes)
        Cipher cipherRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipherRSA.init(Cipher.ENCRYPT_MODE, clavePublica);
        byte[] cifradoRSA = cipherRSA.doFinal(mensaje.getBytes("UTF-8"));
        System.out.println("Cifrado RSA (Base64): " + b64(cifradoRSA));

        // Descifrar con RSA
        Cipher descifrarRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        descifrarRSA.init(Cipher.DECRYPT_MODE, clavePrivada);
        byte[] descifradoRSA = descifrarRSA.doFinal(cifradoRSA);
        System.out.println("Descifrado RSA: " + new String(descifradoRSA, "UTF-8"));
        System.out.println();

    }
}
