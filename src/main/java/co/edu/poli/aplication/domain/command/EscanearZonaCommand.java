package co.edu.poli.aplication.domain.command;

import java.util.Objects;

import co.edu.poli.aplication.service.DroneOperacionService;

public class EscanearZonaCommand implements DroneCommand {

    private final DroneOperacionService receiver;

    public EscanearZonaCommand(DroneOperacionService receiver) {
        this.receiver = Objects.requireNonNull(receiver, "El receptor no puede ser nulo.");
    }

    @Override
    public String ejecutar() {
        return receiver.escanearZona();   // MedirTemperatura: medirTemperatura(), TomarFotografia: tomarFotografia()
    }
    
}
