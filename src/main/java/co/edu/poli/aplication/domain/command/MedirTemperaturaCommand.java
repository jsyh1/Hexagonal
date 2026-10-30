package co.edu.poli.aplication.domain.command;

import java.util.Objects;

import co.edu.poli.aplication.service.DroneOperacionService;

public class MedirTemperaturaCommand implements DroneCommand {

    private final DroneOperacionService receiver;

    public MedirTemperaturaCommand(DroneOperacionService receiver) {
        this.receiver = Objects.requireNonNull(receiver, "El receptor no puede ser nulo.");
    }

    @Override
    public String ejecutar() {
        return receiver.medirTemperatura();   // EscanearZona: escanearZona(), TomarFotografia: tomarFotografia()
    }
    
}
