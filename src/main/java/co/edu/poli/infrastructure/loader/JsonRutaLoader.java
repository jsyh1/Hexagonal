package co.edu.poli.infrastructure.loader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.poli.aplication.domain.strategy.PuntoGeografico;

/**
 * Carga los puntos de origen y destino desde un archivo JSON.
 */
public class JsonRutaLoader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Lee y valida una ruta geográfica desde un archivo JSON.
     *
     * @param archivo archivo JSON seleccionado
     * @return lista con origen en la posición 0 y destino en la posición 1
     * @throws IOException si ocurre un error al leer el archivo
     * @throws IllegalArgumentException si los datos son inválidos
     */
    public List<PuntoGeografico> cargar(File archivo)
            throws IOException {

        if (archivo == null || !archivo.isFile()) {
            throw new IllegalArgumentException(
                    "Debes seleccionar un archivo válido.");
        }

        JsonNode raiz = objectMapper.readTree(archivo);

        if (raiz == null || !raiz.isObject()) {
            throw new IllegalArgumentException(
                    "El archivo no contiene una ruta JSON válida.");
        }

        PuntoGeografico origen = leerPunto(
                raiz.get("origen"), "origen");

        PuntoGeografico destino = leerPunto(
                raiz.get("destino"), "destino");

        return List.of(origen, destino);
    }

    /**
     * Lee y valida un punto geográfico del JSON.
     *
     * @param nodo nodo JSON que representa el punto
     * @param campo nombre del campo que se está leyendo
     * @return punto geográfico validado
     */
    private PuntoGeografico leerPunto(
            JsonNode nodo,
            String campo) {

        if (nodo == null || !nodo.isObject()) {
            throw new IllegalArgumentException(
                    "Falta el punto de " + campo + ".");
        }

        JsonNode nombreNodo = nodo.get("nombre");
        JsonNode latitudNodo = nodo.get("latitud");
        JsonNode longitudNodo = nodo.get("longitud");

        if (nombreNodo == null
                || !nombreNodo.isTextual()
                || nombreNodo.asText().isBlank()) {

            throw new IllegalArgumentException(
                    "El punto de " + campo + " necesita un nombre.");
        }

        if (latitudNodo == null
                || !latitudNodo.isNumber()
                || longitudNodo == null
                || !longitudNodo.isNumber()) {

            throw new IllegalArgumentException(
                    "El punto de " + campo
                            + " debe incluir latitud y longitud numéricas.");
        }

        double latitud = latitudNodo.asDouble();
        double longitud = longitudNodo.asDouble();

        if (!Double.isFinite(latitud)
                || latitud < -90
                || latitud > 90) {

            throw new IllegalArgumentException(
                    "La latitud del punto de " + campo
                            + " debe estar entre -90 y 90.");
        }

        if (!Double.isFinite(longitud)
                || longitud < -180
                || longitud > 180) {

            throw new IllegalArgumentException(
                    "La longitud del punto de " + campo
                            + " debe estar entre -180 y 180.");
        }

        return new PuntoGeografico(
                nombreNodo.asText(),
                latitud,
                longitud);
    }
}