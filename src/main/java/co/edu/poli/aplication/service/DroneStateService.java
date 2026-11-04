package co.edu.poli.aplication.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.Objects;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.domain.state.DroneState;
import co.edu.poli.aplication.domain.state.OffState;
import co.edu.poli.aplication.port.in.ManageDroneStateUseCase;

/** Administra el estado operativo de los drones sin modificar el modelo Drone. */
public class DroneStateService implements ManageDroneStateUseCase {

    private final ConcurrentMap<Integer, DroneState> estados =
            new ConcurrentHashMap<>();

    @Override
    public void registrarDrone(Drone drone) {
        estados.putIfAbsent(idDe(drone), new OffState());
    }

    @Override
    public void eliminarDrone(Drone drone) {
        estados.remove(idDe(drone));
    }

    @Override
    public void encender(Drone drone) {
        transicionar(drone, DroneState::encender);
    }

    @Override
    public void apagar(Drone drone) {
        transicionar(drone, DroneState::apagar);
    }

    @Override
    public void despegar(Drone drone) {
        transicionar(drone, DroneState::despegar);
    }

    @Override
    public void iniciarAterrizaje(Drone drone) {
        transicionar(drone, DroneState::iniciarAterrizaje);
    }

    @Override
    public void finalizarAterrizaje(Drone drone) {
        transicionar(drone, DroneState::finalizarAterrizaje);
    }

    @Override
    public String obtenerEstado(Drone drone) {
        return obtenerEstadoRegistrado(idDe(drone)).nombre();
    }

    private DroneState transicionar(
            Drone drone,
            Function<DroneState, DroneState> operacion) {
        int droneId = idDe(drone);
        return estados.compute(droneId, (id, estado) -> {
            if (estado == null) {
                throw new IllegalArgumentException(
                        "No existe un estado registrado para el dron " + id + ".");
            }
            return operacion.apply(estado);
        });
    }

    private DroneState obtenerEstadoRegistrado(int droneId) {
        validarId(droneId);
        DroneState estado = estados.get(droneId);
        if (estado == null) {
            throw new IllegalArgumentException(
                    "No existe un estado registrado para el dron " + droneId + ".");
        }
        return estado;
    }

    private int idDe(Drone drone) {
        Objects.requireNonNull(drone, "El dron no puede ser nulo.");
        validarId(drone.getId());
        return drone.getId();
    }

    private void validarId(int droneId) {
        if (droneId <= 0) {
            throw new IllegalArgumentException(
                    "El ID del dron debe ser mayor que cero.");
        }
    }
}
