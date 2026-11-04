# Diagrama de clases: estados operativos del dron

El contexto del patrón State es `DroneStateService`: implementa el puerto de
entrada y mantiene el estado operativo por ID, sin añadir atributos ni cambiar
el modelo `Drone`.

```mermaid
classDiagram
    direction TB

    class Drone {
        -int id
        -String serial
        -String modelo
        -double peso
        +getId() int
        +getSerial() String
        +getModelo() String
        +getPeso() double
    }

    class ManageDroneStateUseCase {
        <<interface>>
        +registrarDrone(drone) void
        +eliminarDrone(drone) void
        +encender(drone) void
        +apagar(drone) void
        +despegar(drone) void
        +iniciarAterrizaje(drone) void
        +finalizarAterrizaje(drone) void
        +obtenerEstado(drone) String
    }

    class DroneStateService {
        <<Context>>
        -Map~Integer, DroneState~ estados
        +registrarDrone(drone) void
        +eliminarDrone(drone) void
        +encender(drone) void
        +apagar(drone) void
        +despegar(drone) void
        +iniciarAterrizaje(drone) void
        +finalizarAterrizaje(drone) void
        +obtenerEstado(drone) String
    }

    class DroneState {
        <<interface>>
        +encender() DroneState
        +apagar() DroneState
        +despegar() DroneState
        +iniciarAterrizaje() DroneState
        +finalizarAterrizaje() DroneState
        +nombre() String
    }

    class OffState {
        +encender() DroneState
        +nombre() String
    }

    class ReadyState {
        +apagar() DroneState
        +despegar() DroneState
        +nombre() String
    }

    class FlyingState {
        +iniciarAterrizaje() DroneState
        +nombre() String
    }

    class LandingState {
        +finalizarAterrizaje() DroneState
        +nombre() String
    }
    ManageDroneStateUseCase <|.. DroneStateService : implementa
    DroneStateService ..> Drone : utiliza
    DroneStateService o--> DroneState : mantiene y delega
    DroneState <|.. OffState : implementa
    DroneState <|.. ReadyState : implementa
    DroneState <|.. FlyingState : implementa
    DroneState <|.. LandingState : implementa

    OffState ..> ReadyState : encender
    ReadyState ..> FlyingState : despegar
    ReadyState ..> OffState : apagar
    FlyingState ..> LandingState : iniciar aterrizaje
    LandingState ..> ReadyState : finalizar aterrizaje
```

Las operaciones no mostradas en una clase de estado se rechazan. Las transiciones
permitidas producen el estado siguiente; por eso el servicio no necesita
condicionales para decidir qué transición es válida.
