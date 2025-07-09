package conexion;

import controlador.Coordinador;
import properties.Credenciales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Conexion instance;
    private java.sql.Connection conn;
    private Coordinador coordinator;

    private Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    Credenciales.URL,
                    Credenciales.USER,
                    Credenciales.PASSWORD
            );
            System.out.println(" Successful connection to " + Credenciales.BD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(" Error connecting to database:");
            e.printStackTrace();
        }
    }


    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    public java.sql.Connection getConexion() {
        return conn;
    }

    public void setCoordinator(Coordinador coordinator) {
        this.coordinator = coordinator;
    }
}



