package Tema5;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

/**
 * Clase <strong>DemoHibrido</strong> que implementa un ejemplo completo de cifrado
 * híbrido combinando los algoritmos AES (simétrico) y RSA (asimétrico).
 * Este esquema es ampliamente utilizado en sistemas reales, ya que combina
 * la eficiencia del cifrado simétrico con la seguridad del cifrado asimétrico.
 *
 * Esta clase permite:
 * <ul>
 *     <li>Generar una clave simétrica AES de 128 bits.</li>
 *     <li>Crear un vector de inicialización (IV) para el modo CBC.</li>
 *     <li>Cifrar un mensaje con AES/CBC/PKCS5Padding.</li>
 *     <li>Generar un par de claves RSA de 2048 bits.</li>
 *     <li>Cifrar la clave AES utilizando la clave pública RSA.</li>
 *     <li>Descifrar la clave AES con la clave privada RSA.</li>
 *     <li>Descifrar el mensaje original utilizando la clave AES recuperada.</li>
 * </ul>
 *
 * Este ejemplo reproduce el funcionamiento de los sistemas híbridos reales,
 * como los utilizados en HTTPS, mensajería segura o intercambio de claves.
 *
 * @author Guillermo Martín Chippirraz
 * @version v5.0.1
 */
public class DemoHibrido {

    /**
     * Convierte un array de bytes en una cadena codificada en Base64.
     * Se utiliza para mostrar claves y datos cifrados de forma legible.
     *
     * @since v5.0
     * @param data Array de bytes a codificar.
     * @return Cadena en formato Base64.
     */
    private static String b64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * Método principal del programa.
     *
     * La ejecución sigue la siguiente secuencia:
     *
     * <ol>
     *     <li>
     *         <strong>Definición del mensaje:</strong>
     *         Se establece un texto que será cifrado mediante un esquema híbrido.
     *     </li>
     *
     *     <li>
     *         <strong>Generación de la clave AES:</strong>
     *         <ul>
     *             <li>Se crea un generador de claves para AES.</li>
     *             <li>Se inicializa para producir claves de 128 bits.</li>
     *             <li>Se obtiene una clave simétrica válida para cifrar el mensaje.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Creación del vector de inicialización (IV):</strong>
     *         <ul>
     *             <li>Se generan 16 bytes aleatorios mediante <code>SecureRandom</code>.</li>
     *             <li>Se encapsulan en un objeto <code>IvParameterSpec</code>.</li>
     *             <li>El IV garantiza que dos cifrados del mismo mensaje produzcan resultados distintos.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Cifrado del mensaje con AES:</strong>
     *         <ul>
     *             <li>Se configura un objeto <code>Cipher</code> en modo ENCRYPT_MODE.</li>
     *             <li>Se aplica el algoritmo AES/CBC/PKCS5Padding.</li>
     *             <li>El resultado se almacena como un array de bytes cifrados.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Generación del par de claves RSA:</strong>
     *         <ul>
     *             <li>Se crea un generador de claves RSA de 2048 bits.</li>
     *             <li>Se obtiene un par formado por clave pública y clave privada.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Cifrado de la clave AES con RSA:</strong>
     *         <ul>
     *             <li>Se utiliza la clave pública RSA para cifrar la clave simétrica.</li>
     *             <li>Esto permite enviar la clave AES de forma segura al receptor.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Descifrado de la clave AES:</strong>
     *         <ul>
     *             <li>El receptor utiliza su clave privada RSA para recuperar la clave AES.</li>
     *             <li>Se reconstruye un objeto <code>SecretKey</code> a partir de los bytes descifrados.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Descifrado del mensaje con AES:</strong>
     *         <ul>
     *             <li>Se utiliza la clave AES recuperada junto con el IV original.</li>
     *             <li>Se obtiene el mensaje original, verificando la validez del proceso híbrido.</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * Este método no devuelve ningún valor y finaliza tras mostrar por consola
     * la clave AES cifrada y el mensaje recuperado correctamente.
     *
     * @since v5.0
     * @throws Exception Si ocurre algún error durante las operaciones criptográficas.
     */
    public static void main(String[] args) throws Exception {

        String mensaje = "Mensaje secretitos jijiji";

        System.out.println("=== ESQUEMA HÍBRIDO (RSA + AES) ===");

        // 1. Generación de clave AES
        KeyGenerator kgAES = KeyGenerator.getInstance("AES");
        kgAES.init(128);
        SecretKey claveAES = kgAES.generateKey();

        // 2. Generación del vector de inicialización (IV)
        SecureRandom random = new SecureRandom();
        byte[] ivBytes = new byte[16];
        random.nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        // 3. Cifrado del mensaje con AES
        Cipher cipherAES = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherAES.init(Cipher.ENCRYPT_MODE, claveAES, iv);
        byte[] cifradoAES = cipherAES.doFinal(mensaje.getBytes("UTF-8"));

        // 4. Generación del par de claves RSA
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair parRSA = kpg.generateKeyPair();
        PublicKey clavePublica = parRSA.getPublic();
        PrivateKey clavePrivada = parRSA.getPrivate();

        // 5. Cifrado de la clave AES con RSA
        Cipher cipherRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipherRSA.init(Cipher.ENCRYPT_MODE, clavePublica);
        byte[] claveAESCifradaConRSA = cipherRSA.doFinal(claveAES.getEncoded());

        System.out.println("Clave AES cifrada con RSA (Base64): " + b64(claveAESCifradaConRSA));

        // 6. Descifrado de la clave AES con RSA
        Cipher descifrarRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        descifrarRSA.init(Cipher.DECRYPT_MODE, clavePrivada);
        byte[] claveAESRecuperadaBytes = descifrarRSA.doFinal(claveAESCifradaConRSA);
        SecretKey claveAESRecuperada = new SecretKeySpec(claveAESRecuperadaBytes, "AES");

        // 7. Descifrado del mensaje con la clave AES recuperada
        Cipher descifrarAESHibrido = Cipher.getInstance("AES/CBC/PKCS5Padding");
        descifrarAESHibrido.init(Cipher.DECRYPT_MODE, claveAESRecuperada, iv);
        byte[] mensajeRecuperado = descifrarAESHibrido.doFinal(cifradoAES);

        System.out.println("Mensaje recuperado con esquema híbrido: " +
                new String(mensajeRecuperado, "UTF-8"));
    }
}
