package Tema4.Practica.Actividad3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clase RestCountriesAPI para la Actividad 3 de la práctica del tema 4 que actúa como módulo de acceso
 * al servicio web RESTCountries. Su función principal es realizar peticiones HTTP al servicio externo,
 * obtener la información en formato JSON y filtrar únicamente los datos relevantes para la aplicación.
 *
 * Esta clase encapsula la lógica de comunicación con la API, permitiendo:
 * <ul>
 *     <li>Construir dinámicamente la URL de consulta a partir del nombre del país.</li>
 *     <li>Realizar una petición HTTP GET al servicio RESTCountries.</li>
 *     <li>Comprobar el código de respuesta para validar la existencia del país.</li>
 *     <li>Extraer del JSON únicamente los campos necesarios (código, capital, lenguaje, moneda y teléfono).</li>
 *     <li>Devolver un objeto <code>CountryData</code> con la información filtrada.</li>
 * </ul>
 *
 * Su diseño simplifica la interacción entre el servidor y la API externa, evitando que el resto de clases
 * deban procesar directamente el JSON completo o gestionar la conexión HTTP.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.4
 */
public class RestCountriesAPI {

    /**
     * Método público encargado de buscar un país en el servicio RESTCountries.
     * La línea de ejecución sigue la siguiente secuencia:
     *
     * <ol>
     *     <li>Se construye la URL de consulta utilizando el nombre del país.</li>
     *     <li>Se abre una conexión HTTP mediante <code>HttpURLConnection</code>.</li>
     *     <li>Se envía una petición GET al servidor remoto.</li>
     *     <li>Se comprueba el código de respuesta:
     *         <ul>
     *             <li>Si es distinto de 200, se devuelve <code>null</code> indicando país no válido.</li>
     *         </ul>
     *     </li>
     *     <li>Se lee la primera línea del JSON devuelto por la API.</li>
     *     <li>Se extraen los campos relevantes mediante el método auxiliar <code>extraer()</code>.</li>
     *     <li>Se construye y devuelve un objeto <code>CountryData</code> con la información filtrada.</li>
     * </ol>
     *
     * En caso de producirse cualquier excepción (conexión fallida, formato inesperado, etc.), el método
     * devuelve <code>null</code> para indicar que la consulta no ha sido válida.
     *
     * @since v4.4
     * @see CountryData
     * @param nombre Nombre del país a consultar.
     * @return Objeto <code>CountryData</code> con la información filtrada, o <code>null</code> si el país no existe.
     */
    public static CountryData buscarPais(String nombre) {
        try {
            URL url = new URL("https://restcountries.com/v3.1/name/" + nombre);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() != 200) return null;

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json = br.readLine();

            String codigo = extraer(json, "\"cca3\":\"", "\"");
            String capital = extraer(json, "\"capital\":[\"", "\"");
            String lenguaje = extraer(json, "\"languages\":{", "}");
            String moneda = extraer(json, "\"currencies\":{", "}");
            String telefono = extraer(json, "\"root\":\"", "\"")
                    + extraer(json, "\"suffixes\":[\"", "\"");

            return new CountryData(codigo, lenguaje, moneda, capital, telefono);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método auxiliar encargado de extraer un fragmento de texto delimitado por dos cadenas dentro del JSON.
     * Este método realiza una búsqueda simple basada en índices, sin análisis estructural del JSON.
     *
     * <ul>
     *     <li>Si el delimitador inicial no se encuentra, se devuelve <code>"N/A"</code>.</li>
     *     <li>Si se encuentra, se devuelve la subcadena comprendida entre ambos delimitadores.</li>
     * </ul>
     *
     * @since v4.4
     * @param json Cadena JSON completa recibida desde la API.
     * @param inicio Delimitador inicial.
     * @param fin Delimitador final.
     * @return Subcadena extraída o <code>"N/A"</code> si no se encuentra el patrón.
     */
    private static String extraer(String json, String inicio, String fin) {
        int i = json.indexOf(inicio);
        if (i == -1) return "N/A";
        int j = json.indexOf(fin, i + inicio.length());
        return json.substring(i + inicio.length(), j);
    }
}