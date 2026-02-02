package Tema4.Practica.Actividad3;

/**
 * Record CountryData para la Actividad 3 de la práctica del tema 4 que representa una estructura
 * inmutable destinada a almacenar la información filtrada de un país obtenida a través del servicio
 * web RESTCountries.
 *
 * Este record actúa como un contenedor de datos (DTO), permitiendo al servidor transmitir al cliente
 * únicamente la información relevante y ya procesada, evitando así exponer directamente la salida
 * completa y compleja de la API externa.
 *
 * Los campos almacenados corresponden a los atributos solicitables por el cliente:
 * <ul>
 *     <li><strong>codigo</strong>: Código ISO del país.</li>
 *     <li><strong>lenguaje</strong>: Lenguaje principal del país.</li>
 *     <li><strong>moneda</strong>: Moneda oficial, incluyendo su nombre y código.</li>
 *     <li><strong>capital</strong>: Ciudad capital del país.</li>
 *     <li><strong>telefono</strong>: Código telefónico internacional.</li>
 * </ul>
 *
 * Su naturaleza inmutable garantiza consistencia en los datos transmitidos y facilita su uso en el
 * intercambio entre servidor y cliente.
 *
 * @author Guillermo Martín Chippirraz
 * @version v4.4
 */
public record CountryData(
        String codigo,
        String lenguaje,
        String moneda,
        String capital,
        String telefono
) {}
