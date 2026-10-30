package co.edu.poli.aplication.domain.command;

import java.util.Objects;

import co.edu.poli.aplication.service.DroneOperacionService;

public class TomarFotografiaCommand implements DroneCommand {

    private final DroneOperacionService receiver;

    public TomarFotografiaCommand(DroneOperacionService receiver) {
        this.receiver = Objects.requireNonNull(receiver, "El receptor no puede ser nulo.");
    }

    @Override
    public String ejecutar() {
        return receiver.tomarFotografia();   // EscanearZona: escanearZona(), MedirTemperatura: medirTemperatura()
    }
}