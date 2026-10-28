package co.edu.poli.infrastructure.util;

import co.edu.poli.aplication.domain.strategy.PuntoGeografico;

/**
 * Calcula la distancia geográfica entre dos puntos.
 */
public final class HaversineUtil {

    private static final double RADIO_TIERRA_KM = 6371.0088;

    /**
     * Constructor privado para impedir la instanciación.
     */
    private HaversineUtil() {
    }

    /**
     * Calcula la distancia entre dos puntos mediante Haversine.
     *
     * @param origen punto inicial
     * @param destino punto final
     * @return distancia en kilómetros
     */
    public static double calcularDistanciaKm(
            PuntoGeografico origen,
            PuntoGeografico destino) {

        validarPunto(origen);
        validarPunto(destino);

        double latitudOrigen =
                Math.toRadians(origen.getLatitud());

        double latitudDestino =
                Math.toRadians(destino.getLatitud());

        double diferenciaLatitud =
                Math.toRadians(
                        destino.getLatitud() - origen.getLatitud());

        double diferenciaLongitud =
                Math.toRadians(
                        destino.getLongitud() - origen.getLongitud());

        double a =
                Math.pow(Math.sin(diferenciaLatitud / 2), 2)
                + Math.cos(latitudOrigen)
                * Math.cos(latitudDestino)
                * Math.pow(Math.sin(diferenciaLongitud / 2), 2);

        // Evita errores numéricos por precisión de punto flotante.
        a = Math.max(0.0, Math.min(1.0, a));

        double c = 2 * Math.atan2(
                Math.sqrt(a),
                Math.sqrt(1 - a));

        return RADIO_TIERRA_KM * c;
    }

    /**
     * Comprueba que el punto tenga coordenadas válidas.
     *
     * @param punto punto que se va a validar
     */
    private static void validarPunto(PuntoGeografico punto) {

        if (punto == null
                || punto.getLatitud() == null
                || punto.getLongitud() == null) {

            throw new IllegalArgumentException(
                    "El punto debe contener latitud y longitud.");
        }

        double latitud = punto.getLatitud();
        double longitud = punto.getLongitud();

        if (!Double.isFinite(latitud)
                || latitud < -90
                || latitud > 90) {

            throw new IllegalArgumentException(
                    "La latitud debe estar entre -90 y 90.");
        }

        if (!Double.isFinite(longitud)
                || longitud < -180
                || longitud > 180) {

            throw new IllegalArgumentException(
                    "La longitud debe estar entre -180 y 180.");
        }
    }
}