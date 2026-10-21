package co.edu.poli.aplication.port.out;

public interface GeocodingPort {

    Coordenadas buscarCiudad(String ciudad) throws Exception;

    class Coordenadas {

        private final double latitud;
        private final double longitud;

        public Coordenadas(double latitud, double longitud) {
            this.latitud = latitud;
            this.longitud = longitud;
        }

        public double getLatitud() {
            return latitud;
        }

        public double getLongitud() {
            return longitud;
        }
    }
}