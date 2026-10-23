# Gestión de drones

Aplicación de escritorio desarrollada con JavaFX y organizada con arquitectura
hexagonal. Permite administrar drones, consultar el clima de una ciudad y
notificar a los drones suscritos si las condiciones son aptas para el vuelo.

## Requisitos

- JDK 26
- Maven 3.9 o posterior
- MySQL
- Conexión a Internet para consultar las API de Open-Meteo

## Configuración

La aplicación lee la conexión a MySQL desde un archivo `.env` en la raíz del
proyecto:

```dotenv
DB_URL=jdbc:mysql://localhost:3306/hexagonal
DB_USER=tu_usuario
DB_PASSWORD=tu_contraseña
```

Configura `DB_URL`, `DB_USER` y `DB_PASSWORD` con los datos de tu entorno. La
base de datos debe incluir una tabla `drone` con las columnas `id`, `serial`,
`modelo` y `peso`.

## Compilar y probar

Desde la raíz del proyecto, ejecuta:

```bash
mvn clean test
```

## Ejecutar

Abre el proyecto en tu IDE y ejecuta la clase principal
`co.edu.poli.vista.App`. La interfaz JavaFX se carga desde
`src/main/resources/co/edu/poli/vista/vista.fxml`.

## Funcionalidades

- Crear, listar, editar y eliminar drones almacenados en MySQL.
- Suscribir y dar de baja drones para recibir notificaciones meteorológicas.
- Seleccionar una ciudad y usar **Notificar y evaluar vuelo** para consultar
  el clima actual una sola vez.
- Mostrar a cada drone suscrito las condiciones meteorológicas y el resultado
  de viabilidad: apto o no apto para el vuelo, con el motivo del rechazo
  cuando corresponda.

La búsqueda de ciudades y los datos meteorológicos se obtienen de las API de
geocodificación y pronóstico de Open-Meteo.

## Patrones y arquitectura

- **Observer:** `WeatherNotificationService` administra las suscripciones y
  notifica a cada drone suscrito con el clima y el resultado de viabilidad.
- **Cadena de responsabilidades:** los manejadores meteorológicos evalúan
  secuencialmente las condiciones para determinar si el vuelo es apto.
- **Arquitectura hexagonal:** los casos de uso y puertos están separados de
  los adaptadores de interfaz, persistencia y servicios meteorológicos.

## Estructura del proyecto

```text
src/main/java/co/edu/poli/
  aplication/
    domain/model/       Modelo de dominio y manejadores de clima
    port/               Contratos de entrada y salida
    service/            Casos de uso
    weather/            Notificaciones a drones suscritos
  infrastructure/
    persistence/        Adaptador de repositorio MySQL
    ui/                 Controlador JavaFX
    weather/            Adaptadores de Open-Meteo
  vista/                Aplicación JavaFX
src/main/resources/     Interfaz FXML
src/test/java/          Pruebas unitarias
```
