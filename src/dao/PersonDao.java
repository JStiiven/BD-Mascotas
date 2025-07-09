package dao;

import conexion.Conexion;

import vo.PetVo;
import vo.PersonVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PersonDao {

    private final Conexion connection;

    public PersonDao() {
        this.connection = Conexion.getInstance();
    }

    public void registerPerson(PersonVo person) {
        try {
            java.sql.Connection conn = Conexion.getInstance().getConexion();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO personas (documento, nombre, telefono) VALUES (?, ?, ?)");
            ps.setString(1, person.getDocument());
            ps.setString(2, person.getName());
            ps.setString(3, person.getPhone());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PersonVo query(String document) {
        String sql = "SELECT * FROM personas WHERE documento = ?";
        Connection con = connection.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, document);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PersonVo person = new PersonVo();
                person.setDocument(rs.getString("documento"));
                person.setName(rs.getString("nombre"));
                person.setPhone(rs.getString("telefono"));
                return person;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void delete(String document) {
        String sql = "DELETE FROM personas WHERE documento = ?";
        Connection con = connection.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, document);
            ps.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("foreign key constraint fails")) {
                throw new RuntimeException("No se puede eliminar la persona porque tiene mascotas registradas");
                
            }else {
                e.printStackTrace();
            }

        }
    }

    public void update(PersonVo person) {
        String sql = "UPDATE personas SET nombre = ?, telefono = ? WHERE documento = ?";
        Connection con = connection.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, person.getName());
            ps.setString(2, person.getPhone());
            ps.setString(3, person.getDocument());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String list() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM personas";
        Connection con = connection.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                sb.append("Document: ").append(rs.getString("documento")).append("\n");
                sb.append("Name: ").append(rs.getString("nombre")).append("\n");
                sb.append("Phone: ").append(rs.getString("telefono")).append("\n\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public boolean documentExists(String document) {
        String sql = "SELECT 1 FROM personas WHERE documento = ?";
        Connection con = connection.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, document);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


} 