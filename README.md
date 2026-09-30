# Aplicacion hexagonal con Java 26

Proyecto de ejemplo de una aplicacion de tareas con arquitectura hexagonal. El dominio y los casos de uso no dependen de la consola ni del almacenamiento.

## Requisitos

- JDK 26
- Maven 3.9 o posterior

## Ejecutar

```bash
mvn clean test package
java -cp target/classes com.example.hexagonal.view.App
```

En la consola puedes usar `crear <titulo>`, `listar` y `salir`. El adaptador de almacenamiento es en memoria, por lo que las tareas viven solo durante esa ejecucion.

## Estructura

```text
application/domain/     Modelo e invariantes del dominio
application/port/       Casos de uso y contratos requeridos
application/service/    Implementacion de los casos de uso
infrastructure/persistence/ Almacenamiento en memoria
infrastructure/ui/     Entrada por consola
view/App.java           Composicion y punto de entrada
```