package clases;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

/**
 *
 * @author ignac
 * Esta es la clase que se encarga de realizar la conexión con la base de datos mysql
 * pretendan usar.
 */
/**
 * Clase ConexionSQL
 * Esta clase maneja la conexión a una base de datos MySQL.
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
                System.out.println("conectado");  // Imprime un mensaje indicando que la conexión fue exitosa
            }
        } catch (SQLException e) {
            // Captura cualquier excepción SQL y la imprime
            System.out.println(e.getMessage());
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
            System.out.println("Conexion terminada");  // Imprime un mensaje indicando que la conexión ha terminado
        }
    }
    
    public void limpirInputs() {
        
    }
    
    public void insertarDatos(int id, String nombre, String direccion, String fechaAlta) {
        String sql = "INSERT INTO clientes (id, nombre, direccion, fechaAlta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, nombre);
            pstmt.setString(3, direccion);
            pstmt.setString(4, fechaAlta);
            int sqlResponse = pstmt.executeUpdate();
            if (sqlResponse > 0) {
                JOptionPane.showMessageDialog(null, "Persona Guardada");
                System.out.println("Datos insertados correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al Guardar persona");
                System.out.println("No se pudo insertar el dato correctamente");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
     public void actualizarDatos(int id, String nombre, String direccion, String fechaAlta) {
        String sql = "UPDATE clientes SET nombre = ?, direccion = ?, fechaAlta = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, direccion);
            pstmt.setString(3, fechaAlta);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Datos actualizados correctamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
     
     public ResultSet buscarDatosPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

