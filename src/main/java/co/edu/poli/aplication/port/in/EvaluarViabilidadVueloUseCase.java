package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.weather.Weather;

/** Caso de uso para determinar si las condiciones permiten un vuelo. */
public interface EvaluarViabilidadVueloUseCase {

    /**
     * Evalúa el clima recibido mediante las reglas de operación.
     *
     * @param ciudad ciudad asociada al clima, usada para identificar el registro
     * @param clima condiciones meteorológicas que se evaluarán
     * @return resultado apto o no apto, con el motivo si incumple una regla
     */
    String evaluarViabilidad(String ciudad, Weather clima);
}