package co.edu.poli.aplication.mediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.mediator.components.BatterySystem;
import co.edu.poli.aplication.mediator.components.CommunicationSystem;
import co.edu.poli.aplication.mediator.components.NavigationSystem;
import co.edu.poli.aplication.mediator.components.SensorSystem;

/**
 * Mediador concreto: coordina la secuencia de inicio de una misión.
 * Secuencia: ruta -> sensores -> batería -> comunicación.
 */
public class MissionCoordinator implements DroneMediator {

    private final NavigationSystem navigation;
    private final SensorSystem sensors;
    private final BatterySystem battery;
    private final CommunicationSystem communication;
    private final List<String> bitacora = new ArrayList<>();

    private boolean misionLista;
    private boolean abortada;

    public MissionCoordinator() {
        this(100);
    }

    /**
     * @param nivelBateria nivel de batería simulado (0 a 100)
     */
    public MissionCoordinator(int nivelBateria) {
        this.navigation = new NavigationSystem(this);
        this.sensors = new SensorSystem(this);
        this.battery = new BatterySystem(this, nivelBateria);
        this.communication = new CommunicationSystem(this);
    }

    @Override
    public String iniciarOperacion(Drone drone) {
        Objects.requireNonNull(drone, "El dron no puede ser nulo.");

        bitacora.clear();
        misionLista = false;
        abortada = false;

        bitacora.add("Iniciando operación de vigilancia del dron " + drone.getSerial());
        bitacora.add(navigation.prepararRuta());

        return String.join("\n", bitacora);
    }

    @Override
    public void notificar(Object sender, String evento) {
        bitacora.add("Evento recibido: " + evento);

        if (abortada) {
            return;
        }

        if (sender == navigation && "RUTA_PREPARADA".equals(evento)) {
            bitacora.add(sensors.activarSensores());

        } else if (sender == sensors && "SENSORES_ACTIVADOS".equals(evento)) {
            bitacora.add(battery.verificarBateria());

        } else if (sender == battery && "BATERIA_OK".equals(evento)) {
            bitacora.add(communication.establecerEnlace());

        } else if (sender == battery && "BATERIA_BAJA".equals(evento)) {
            abortada = true;
            bitacora.add("Misión abortada: batería insuficiente.");

        } else if (sender == communication && "ENLACE_ESTABLECIDO".equals(evento)) {
            misionLista = true;
            bitacora.add("Misión lista: todas las condiciones se cumplen.");
        }
    }

    public boolean isMisionLista() {
        return misionLista;
    }
}