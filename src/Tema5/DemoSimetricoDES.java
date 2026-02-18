package Tema5;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Clase <strong>DemoSimetricoDES</strong> que actúa como ejemplo práctico del uso del
 * cifrado simétrico mediante el algoritmo DES en modo CBC con padding PKCS5.
 * Su objetivo es mostrar de forma clara el proceso completo de generación de
 * claves, creación del vector de inicialización, cifrado y descifrado de un
 * mensaje utilizando la API de criptografía de Java (JCA/JCE).
 *
 * Esta clase permite:
 * <ul>
 *     <li>Generar una clave DES de 56 bits mediante <code>KeyGenerator</code>.</li>
 *     <li>Crear un vector de inicialización (IV) de 8 bytes, requerido por DES.</li>
 *     <li>Cifrar un mensaje utilizando el algoritmo DES/CBC/PKCS5Padding.</li>
 *     <li>Descifrar el mensaje cifrado empleando la misma clave e IV.</li>
 *     <li>Mostrar los resultados en Base64 para facilitar su lectura.</li>
 * </ul>
 *
 * Aunque DES es un algoritmo histórico y actualmente considerado inseguro,
 * este ejemplo resulta útil con fines didácticos para comprender el
 * funcionamiento del cifrado simétrico por bloques.
 *
 * @author Guillermo Martín Chippirraz
 * @version v5.0.1
 */
public class DemoSimetricoDES {

    /**
     * Convierte un array de bytes en una cadena codificada en Base64.
     * Se utiliza para mostrar claves, IV y datos cifrados de forma legible.
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
     *         <strong>Generación de la clave DES:</strong>
     *         <ul>
     *             <li>Se crea un generador de claves mediante <code>KeyGenerator</code>.</li>
     *             <li>Se inicializa para producir claves de 56 bits.</li>
     *             <li>Se obtiene una clave simétrica válida para DES.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Creación del vector de inicialización (IV):</strong>
     *         <ul>
     *             <li>Se generan 8 bytes aleatorios mediante <code>SecureRandom</code>.</li>
     *             <li>Se encapsulan en un objeto <code>IvParameterSpec</code>.</li>
     *             <li>El IV evita que dos cifrados del mismo mensaje produzcan resultados idénticos.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Cifrado del mensaje:</strong>
     *         <ul>
     *             <li>Se configura un objeto <code>Cipher</code> en modo ENCRYPT_MODE.</li>
     *             <li>Se aplica el algoritmo DES/CBC/PKCS5Padding.</li>
     *             <li>El resultado se almacena como un array de bytes cifrados.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Descifrado del mensaje:</strong>
     *         <ul>
     *             <li>Se inicializa el mismo objeto <code>Cipher</code> en modo DECRYPT_MODE.</li>
     *             <li>Se utiliza la misma clave DES y el mismo IV.</li>
     *             <li>Se obtiene el texto original, verificando la correcta ejecución del proceso.</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     * Este método no devuelve ningún valor y finaliza tras mostrar por consola
     * el mensaje original, la clave, el IV, el texto cifrado y el texto descifrado.
     *
     * @since v5.0
     * @throws Exception Si ocurre algún error durante las operaciones criptográficas.
     */
    public static void main(String[] args) throws Exception {

        String mensaje = "Mensaje secretérrimo";

        System.out.println("=== CIFRADO SIMÉTRICO DES ===");

        // 1. Generación de clave DES
        KeyGenerator kgDES = KeyGenerator.getInstance("DES");
        kgDES.init(56); // DES utiliza una clave efectiva de 56 bits
        SecretKey claveDES = kgDES.generateKey();

        // 2. Generación del vector de inicialización (IV)
        SecureRandom random = new SecureRandom();
        byte[] ivBytes = new byte[8]; // DES usa bloques de 8 bytes
        random.nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        // 3. Cifrado con DES/CBC/PKCS5Padding
        Cipher cifrador = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cifrador.init(Cipher.ENCRYPT_MODE, claveDES, iv);
        byte[] cifrado = cifrador.doFinal(mensaje.getBytes("UTF-8"));

        System.out.println("Mensaje original: " + mensaje);
        System.out.println("Clave DES (Base64): " + b64(claveDES.getEncoded()));
        System.out.println("IV DES (Base64): " + b64(ivBytes));
        System.out.println("Cifrado DES (Base64): " + b64(cifrado));

        // 4. Descifrado
        Cipher descifrador = Cipher.getInstance("DES/CBC/PKCS5Padding");
        descifrador.init(Cipher.DECRYPT_MODE, claveDES, iv);
        byte[] descifrado = descifrador.doFinal(cifrado);

        System.out.println("Descifrado DES: " + new String(descifrado, "UTF-8"));
    }
}
