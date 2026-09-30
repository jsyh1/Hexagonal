package co.edu.poli.infrastructure.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public final class conexionDB {

    private static conexionDB instance;

    private final Dotenv dotenv;
    private Connection connection;

    private conexionDB() {
        dotenv = Dotenv.configure().ignoreIfMissing().load();
    }

    public static synchronized conexionDB getInstance() {
        if (instance == null) {
            instance = new conexionDB();
        }
        return instance;
    }

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

    public synchronized void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        connection = null;
    }
}
