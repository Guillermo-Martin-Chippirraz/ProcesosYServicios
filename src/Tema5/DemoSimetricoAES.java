package Tema5;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class DemoSimetricoAES {
    private static String b64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static void main(String[] args) throws Exception {
        String mensaje = "Mensaje ultrahipersupermegasecreto: https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://skavenger.byethost8.com/homerswebpage/&ved=2ahUKEwj32OaKlOGSAxUAUaQEHUC7FKYQFnoECCIQAQ&usg=AOvVaw3iJMHdAyeBtoKPnfaCXPDv";

        System.out.println("=== CIFRADO SIMÉTRICO (AES) ===");

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

        System.out.println("Mensaje original: " + mensaje);
        System.out.println("Clave AES (Base64): " + b64(claveAES.getEncoded()));
        System.out.println("IV AES( (Base64): " + b64(ivBytes));
        System.out.println("Cifrado AES (Base64): " + b64(cifradoAES));

        // Descifrar con AES
        Cipher descifrarAES = Cipher.getInstance("AES/CBC/PKCS5Padding");
        descifrarAES.init(Cipher.DECRYPT_MODE, claveAES, iv);
        byte[] descifradoAES = descifrarAES.doFinal(cifradoAES);
        System.out.println("Descifrado AES: " + new String(descifradoAES, "UTF-8"));
        System.out.println();
    }
}
