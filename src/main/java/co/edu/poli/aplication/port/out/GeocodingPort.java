package co.edu.poli.aplication.port.out;

/** Puerto de salida para resolver nombres de ciudades en coordenadas. */
public interface GeocodingPort {

    /**
     * Busca las coordenadas geográficas de una ciudad.
     *
     * @param ciudad nombre de la ciudad
     * @return coordenadas geográficas encontradas
     * @throws Exception si la búsqueda falla o no puede completarse
     */
    Coordenadas buscarCiudad(String ciudad) throws Exception;

    /** Coordenadas geográficas expresadas en latitud y longitud. */
    class Coordenadas {

        private final double latitud;
        private final double longitud;

        /**
         * Crea un par de coordenadas.
         *
         * @param latitud latitud geográfica
         * @param longitud longitud geográfica
         */
        public Coordenadas(double latitud, double longitud) {
            this.latitud = latitud;
            this.longitud = longitud;
        }

        /**
         * Devuelve la latitud geográfica.
         *
         * @return latitud
         */
        public double getLatitud() {
            return latitud;
        }

        /**
         * Devuelve la longitud geográfica.
         *
         * @return longitud
         */
        public double getLongitud() {
            return longitud;
        }
    }
}