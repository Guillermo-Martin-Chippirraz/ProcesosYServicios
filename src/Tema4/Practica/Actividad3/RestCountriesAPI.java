package Tema4.Practica.Actividad3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestCountriesAPI {
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

    private static String extraer(String json, String inicio, String fin) {
        int i = json.indexOf(inicio);
        if (i == -1) return "N/A";
        int j = json.indexOf(fin, i + inicio.length());
        return json.substring(i + inicio.length(), j);
    }
}
