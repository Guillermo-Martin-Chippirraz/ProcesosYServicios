package Tema5;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * Clase <strong>DemoAsimetrico</strong> que demuestra el uso del cifrado
 * asimétrico mediante el algoritmo RSA. Su finalidad es ilustrar el proceso
 * completo de generación de un par de claves, cifrado de un mensaje con la
 * clave pública y posterior descifrado con la clave privada utilizando la API
 * de criptografía de Java (JCA/JCE).
 *
 * Esta clase permite:
 * <ul>
 *     <li>Generar un par de claves RSA de 2048 bits.</li>
 *     <li>Cifrar un mensaje utilizando la clave pública.</li>
 *     <li>Descifrar el mensaje cifrado empleando la clave privada.</li>
 *     <li>Mostrar claves y datos cifrados en formato Base64 para facilitar su lectura.</li>
 * </ul>
 *
 * Aunque RSA no es adecuado para cifrar grandes volúmenes de datos, este
 * ejemplo resulta útil para comprender el funcionamiento básico del cifrado
 * asimétrico y su uso en operaciones de pequeño tamaño.
 *
 * @author Guillermo Martín Chippirraz
 * @version v5.0.1
 */
public class DemoAsimetrico {

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
     *         Se establece un texto que será cifrado y posteriormente descifrado.
     *     </li>
     *
     *     <li>
     *         <strong>Generación del par de claves RSA:</strong>
     *         <ul>
     *             <li>Se crea un generador de claves mediante <code>KeyPairGenerator</code>.</li>
     *             <li>Se inicializa para producir claves de 2048 bits.</li>
     *             <li>Se obtiene un par formado por clave pública y clave privada.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Cifrado del mensaje:</strong>
     *         <ul>
     *             <li>Se configura un objeto <code>Cipher</code> en modo ENCRYPT_MODE.</li>
     *             <li>Se utiliza la clave pública para cifrar el mensaje.</li>
     *             <li>El resultado se almacena como un array de bytes cifrados.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Descifrado del mensaje:</strong>
     *         <ul>
     *             <li>Se inicializa el mismo objeto <code>Cipher</code> en modo DECRYPT_MODE.</li>
     *             <li>Se utiliza la clave privada para recuperar el texto original.</li>
     *             <li>Se muestra el mensaje descifrado para verificar la operación.</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * Este método no devuelve ningún valor y finaliza tras mostrar por consola
     * el mensaje original, las claves generadas, el texto cifrado y el texto
     * descifrado.
     *
     * @since v5.0
     * @throws Exception Si ocurre algún error durante las operaciones criptográficas.
     */
    public static void main(String[] args) throws Exception {

        String mensaje = "Mensaje absurdamente secreto: https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://www.youtube.com/watch%3Fv%3DdQw4w9WgXcQ&ved=2ahUKEwieg9K7mOGSAxW5VqQEHfB5BC8QwqsBegQIHhAB&usg=AOvVaw0aHtehaphMhOCAkCydRLZU";

        System.out.println("=== CIFRADO ASIMÉTRICO (RSA) ===");

        // 1. Generación del par de claves RSA
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair parRSA = kpg.generateKeyPair();
        PublicKey clavePublica = parRSA.getPublic();
        PrivateKey clavePrivada = parRSA.getPrivate();

        System.out.println("Clave pública RSA (Base64): " + b64(clavePublica.getEncoded()));
        System.out.println("Clave privada RSA (Base64): " + b64(clavePrivada.getEncoded()));

        // 2. Cifrado del mensaje con la clave pública
        Cipher cipherRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipherRSA.init(Cipher.ENCRYPT_MODE, clavePublica);
        byte[] cifradoRSA = cipherRSA.doFinal(mensaje.getBytes("UTF-8"));
        System.out.println("Cifrado RSA (Base64): " + b64(cifradoRSA));

        // 3. Descifrado del mensaje con la clave privada
        Cipher descifrarRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        descifrarRSA.init(Cipher.DECRYPT_MODE, clavePrivada);
        byte[] descifradoRSA = descifrarRSA.doFinal(cifradoRSA);
        System.out.println("Descifrado RSA: " + new String(descifradoRSA, "UTF-8"));
        System.out.println();
    }
}
