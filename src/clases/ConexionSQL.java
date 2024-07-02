package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 * Clase ConexionSQL
 * Esta clase maneja la conexión a una base de datos MySQL y operaciones CRUD básicas.
 */
public class ConexionSQL {
    // Variable estática para mantener la conexión a la base de datos
    private static Connection conn = null;

    // Constantes para las credenciales de la base de datos y la URL de conexión
    private static final String USER = "root";
    private static final String PSWD = "root";
    private static final String DATABASE = "testjava";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE;

    /**
     * Método ConexionSQL
     * Este método intenta establecer una conexión con la base de datos MySQL
     * utilizando las credenciales y la URL proporcionadas.
     *
     * @return Connection conn - La conexión establecida con la base de datos.
     */
    public Connection ConexionSQL() {
        try {
            // Intenta establecer una conexión con la base de datos
            conn = DriverManager.getConnection(URL, USER, PSWD);
            // Verifica si la conexión fue exitosa
            if (conn != null) {
                System.out.println("Conectado a la base de datos");  // Imprime un mensaje indicando que la conexión fue exitosa
            }
        } catch (SQLException e) {
            // Captura cualquier excepción SQL y la imprime
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        // Retorna la conexión establecida (o null si falló)
        return conn;
    }

    /**
     * Método desconectar
     * Este método desconecta la conexión a la base de datos establecida,
     * estableciendo la conexión a null.
     */
    public void desconectar() {
        // Desconecta la conexión establecida
        conn = null;
        // Verifica si la conexión se ha establecido a null
        if (conn == null) {
            System.out.println("Conexión terminada");  // Imprime un mensaje indicando que la conexión ha terminado
        }
    }

    /**
     * Método insertarDatos
     * Este método inserta datos en la tabla 'clientes' de la base de datos.
     *
     * @param id         El ID del cliente a insertar.
     * @param nombre     El nombre del cliente a insertar.
     * @param direccion  La dirección del cliente a insertar.
     * @param fechaAlta  La fecha de alta del cliente a insertar.
     */
    public void insertarDatos(int id, String nombre, String direccion, String fechaAlta) {
        String sql = "INSERT INTO clientes (id, nombre, direccion, fechaAlta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, nombre);
            pstmt.setString(3, direccion);
            pstmt.setString(4, fechaAlta);
            int sqlResponse = pstmt.executeUpdate();
            if (sqlResponse > 0) {
                JOptionPane.showMessageDialog(null, "Persona guardada correctamente");
                System.out.println("Datos insertados correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar persona");
                System.out.println("No se pudo insertar el dato correctamente");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar datos: " + e.getMessage());
        }
    }

    /**
     * Método actualizarDatos
     * Este método actualiza los datos de un cliente en la tabla 'clientes' de la base de datos.
     *
     * @param id         El ID del cliente a actualizar.
     * @param nombre     El nuevo nombre del cliente.
     * @param direccion  La nueva dirección del cliente.
     * @param fechaAlta  La nueva fecha de alta del cliente.
     */
    public void actualizarDatos(int id, String nombre, String direccion, String fechaAlta) {
        String sql = "UPDATE clientes SET nombre = ?, direccion = ?, fechaAlta = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, direccion);
            pstmt.setString(3, fechaAlta);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Persona actualizada correctamente");
            System.out.println("Datos actualizados correctamente");
        } catch (SQLException e) {
            System.out.println("Error al actualizar datos: " + e.getMessage());
        }
    }

    /**
     * Método buscarDatosPorId
     * Este método busca un cliente por su ID en la tabla 'clientes' de la base de datos.
     *
     * @param id  El ID del cliente a buscar.
     * @return ResultSet con los datos del cliente encontrado, o null si no se encontró.
     */
    public ResultSet buscarDatosPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            JOptionPane.showMessageDialog(null, "Persona obtenida correctamente");
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error al buscar datos por ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Método eliminarDatos
     * Este método elimina un cliente por su ID de la tabla 'clientes' de la base de datos.
     *
     * @param id  El ID del cliente a eliminar.
     */
    public void eliminarDatos(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Persona eliminada correctamente");
            System.out.println("Datos eliminados correctamente");
        } catch (SQLException e) {
            System.out.println("Error al eliminar datos: " + e.getMessage());
        }
    }

    /**
     * Método obtenerDatos
     * Este método obtiene todos los clientes de la tabla 'clientes' de la base de datos.
     *
     * @return ResultSet con todos los datos de los clientes encontrados, o null si hubo un error.
     */
    public ResultSet obtenerDatos() {
        String sql = "SELECT * FROM clientes";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            JOptionPane.showMessageDialog(null, "Personas obtenidas correctamente");
            System.out.println("Datos obtenidos correctamente");
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error al obtener datos: " + e.getMessage());
            return null;
        }
    }
}
