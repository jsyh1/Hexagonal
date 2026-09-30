package co.edu.poli.infrastructure.persistence;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.port.out.DroneRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MysqlDroneRepository implements DroneRepository {

    private final conexionDB conexion;

    public MysqlDroneRepository() {
        this(conexionDB.getInstance());
    }

    MysqlDroneRepository(conexionDB conexion) {
        this.conexion = conexion;
    }

    @Override
    public Drone guardar(Drone drone) {
        String sql = "INSERT INTO drone (serial , modelo, peso) VALUES ( ?, ?, ?)";

        try {
            Connection connection = conexion.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, drone.getSerial());
                statement.setString(2, drone.getModelo());
                statement.setDouble(3, drone.getPeso());
                statement.executeUpdate();

                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        return new Drone(keys.getInt(1), drone.getSerial(), drone.getModelo(),
                                 drone.getPeso());
                    }
                }
                throw new SQLException("MySQL no retornó el ID del drone creado.");
            }
        } catch (SQLException exception) {
            throw persistenceError("crear el drone", exception);
        }
    }

    @Override
    public Drone buscarPorId(int id) {
        String sql = "SELECT id, serial, modelo, peso FROM drone WHERE id = ?";

        try {
            Connection connection = conexion.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet result = statement.executeQuery()) {
                    return result.next() ? mapDrone(result) : null;
                }
            }
        } catch (SQLException exception) {
            throw persistenceError("buscar el drone por ID", exception);
        }
    }

    @Override
    public List<Drone> buscarTodos() {
        String sql = "SELECT id, serial, modelo, peso FROM drone";
        List<Drone> drones = new ArrayList<>();

        try {
            Connection connection = conexion.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    drones.add(mapDrone(result));
                }
            }
            return drones;
        } catch (SQLException exception) {
            throw persistenceError("listar los drones", exception);
        }
    }

    @Override
    public Drone modificar(int id, Drone drone) {
        String sql = "UPDATE drone SET serial = ?, modelo = ?,  peso = ? WHERE id = ?";

        try {
            Connection connection = conexion.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, drone.getSerial());
                statement.setString(2, drone.getModelo());
                statement.setDouble(3, drone.getPeso());
                statement.setInt(4, id);

                if (statement.executeUpdate() == 0) {
                    return null;
                }
                return new Drone(id, drone.getSerial(), drone.getModelo(), drone.getPeso());
            }
        } catch (SQLException exception) {
            throw persistenceError("actualizar el drone", exception);
        }
    }

    @Override
    public void eliminarPorId(int id) {
        String sql = "DELETE FROM drone WHERE id = ?";

        try {
            Connection connection = conexion.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw persistenceError("eliminar el drone", exception);
        }
    }

    private Drone mapDrone(ResultSet result) throws SQLException {
        return new Drone(result.getInt("id"), result.getString("serial"),
                result.getString("modelo"), result.getDouble("peso"));
    }

    private IllegalStateException persistenceError(String operation, SQLException cause) {
        return new IllegalStateException("No se pudo " + operation + " en la base de datos.", cause);
    }
}
