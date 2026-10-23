package co.edu.poli.infrastructure.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Administra la conexión compartida con MySQL y lee sus credenciales del
 * archivo de entorno.
 */
public final class conexionDB {

    private static conexionDB instance;

    private final Dotenv dotenv;
    private Connection connection;

    /** Obtiene la instancia única del administrador de conexión. */
    private conexionDB() {
        dotenv = Dotenv.configure().ignoreIfMissing().load();
    }

    /**
     * Devuelve la instancia única del administrador de conexión.
     *
     * @return instancia compartida
     */
    public static synchronized conexionDB getInstance() {
        if (instance == null) {
            instance = new conexionDB();
        }
        return instance;
    }

    /**
     * Abre o devuelve la conexión activa con la base de datos.
     *
     * @return conexión JDBC activa
     * @throws SQLException si faltan parámetros de conexión o no es posible
     *         conectarse
     */
    public synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = dotenv.get("DB_URL");
            String user = dotenv.get("DB_USER");
            String password = dotenv.get("DB_PASSWORD");

            if (url == null || url.isBlank()) {
                throw new SQLException("La variable DB_URL no está configurada.");
            }
            if (user == null || user.isBlank()) {
                throw new SQLException("La variable DB_USER no está configurada.");
            }

            connection = DriverManager.getConnection(url, user, password == null ? "" : password);
        }
        return connection;
    }

    /**
     * Cierra la conexión activa y permite abrir una nueva posteriormente.
     *
     * @throws SQLException si ocurre un error al cerrar la conexión
     */
    public synchronized void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        connection = null;
    }
}
