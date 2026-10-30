package co.edu.poli.aplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.edu.poli.aplication.domain.command.DroneCommand;

public class ProgramadorTareas {

    private final List<DroneCommand> tareas = new ArrayList<>();

    public void programar(DroneCommand comando) {
        tareas.add(Objects.requireNonNull(comando, "El comando no puede ser nulo."));
    }

    public List<String> ejecutarTodas() {
        if (tareas.isEmpty()) {
            throw new IllegalStateException("No hay tareas programadas.");
        }
        List<String> resultados = new ArrayList<>();
        for (DroneCommand comando : tareas) {
            resultados.add(comando.ejecutar());
        }
        return resultados;
    }

    public void limpiar() {
        tareas.clear();
    }
}