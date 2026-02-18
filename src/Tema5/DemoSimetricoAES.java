package Tema5;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Clase <strong>DemoSimetricoAES</strong> que actúa como ejemplo práctico del uso de
 * cifrado simétrico mediante el algoritmo AES en Java. Su función consiste en
 * demostrar el proceso completo de generación de claves, creación del vector de
 * inicialización, cifrado y descifrado de un mensaje utilizando la API de
 * criptografía de Java (JCA/JCE).
 *
 * Esta clase permite:
 * <ul>
 *     <li>Generar una clave simétrica AES de 128 bits mediante <code>KeyGenerator</code>.</li>
 *     <li>Crear un vector de inicialización (IV) aleatorio para el modo CBC.</li>
 *     <li>Cifrar un mensaje utilizando el algoritmo AES/CBC/PKCS5Padding.</li>
 *     <li>Descifrar el mensaje cifrado empleando la misma clave e IV.</li>
 *     <li>Mostrar los resultados en formato Base64 para facilitar su lectura.</li>
 * </ul>
 *
 * Su diseño facilita la comprensión del funcionamiento del cifrado simétrico,
 * mostrando de forma clara y secuencial cada una de las fases del proceso.
 *
 * @author Guillermo Martín Chippirraz
 * @version v5.0.1
 */
public class DemoSimetricoAES {

    /**
     * Convierte un array de bytes en una cadena codificada en Base64.
     * Este método se utiliza para mostrar claves, IV y datos cifrados
     * de forma legible.
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
     *         <strong>Generación de la clave AES:</strong>
     *         <ul>
     *             <li>Se crea un generador de claves mediante <code>KeyGenerator</code>.</li>
     *             <li>Se inicializa para producir claves de 128 bits.</li>
     *             <li>Se obtiene una clave simétrica válida para AES.</li>
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
     *         <strong>Cifrado del mensaje:</strong>
     *         <ul>
     *             <li>Se configura un objeto <code>Cipher</code> en modo ENCRYPT_MODE.</li>
     *             <li>Se aplica el algoritmo AES/CBC/PKCS5Padding.</li>
     *             <li>El resultado se almacena como un array de bytes cifrados.</li>
     *         </ul>
     *     </li>
     *
     *     <li>
     *         <strong>Descifrado del mensaje:</strong>
     *         <ul>
     *             <li>Se inicializa el mismo objeto <code>Cipher</code> en modo DECRYPT_MODE.</li>
     *             <li>Se utiliza la misma clave AES y el mismo IV.</li>
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

        String mensaje = "Mensaje ultrahipersupermegasecreto: https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://skavenger.byethost8.com/homerswebpage/&ved=2ahUKEwj32OaKlOGSAxUAUaQEHUC7FKYQFnoECCIQAQ&usg=AOvVaw3iJMHdAyeBtoKPnfaCXPDv";

        System.out.println("=== CIFRADO SIMÉTRICO (AES) ===");

        // 1. Generación de clave AES
        KeyGenerator kgAES = KeyGenerator.getInstance("AES");
        kgAES.init(128); // 128, 192 o 256 bits según el entorno
        SecretKey claveAES = kgAES.generateKey();

        // 2. Creación del vector de inicialización (IV)
        SecureRandom random = new SecureRandom();
        byte[] ivBytes = new byte[16]; // 16 bytes para AES
        random.nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        // 3. Cifrado con AES/CBC/PKCS5Padding
        Cipher cipherAES = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherAES.init(Cipher.ENCRYPT_MODE, claveAES, iv);
        byte[] cifradoAES = cipherAES.doFinal(mensaje.getBytes("UTF-8"));

        System.out.println("Mensaje original: " + mensaje);
        System.out.println("Clave AES (Base64): " + b64(claveAES.getEncoded()));
        System.out.println("IV AES (Base64): " + b64(ivBytes));
        System.out.println("Cifrado AES (Base64): " + b64(cifradoAES));

        // 4. Descifrado con AES
        Cipher descifrarAES = Cipher.getInstance("AES/CBC/PKCS5Padding");
        descifrarAES.init(Cipher.DECRYPT_MODE, claveAES, iv);
        byte[] descifradoAES = descifrarAES.doFinal(cifradoAES);

        System.out.println("Descifrado AES: " + new String(descifradoAES, "UTF-8"));
        System.out.println();
    }
}
