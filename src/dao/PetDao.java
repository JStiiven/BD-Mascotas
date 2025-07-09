package dao;

import conexion.Conexion;
import vo.PetVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PetDao {

    private final Conexion connection;

    public PetDao() {
        this.connection = Conexion.getInstance();
    }

    public void registerPet(PetVo pet) {
        String sql = "INSERT INTO mascotas (nombre, id_dueno, raza, sexo) VALUES (?, ?, ?, ?)";
        Connection con = connection.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pet.getName());
            ps.setString(2, pet.getOwnerId());
            ps.setString(3, pet.getBreed());
            ps.setString(4, pet.getGender());
            ps.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("foreign key constraint fails")) {
                throw new RuntimeException("No se puede registrar la mascota, el dueño aún no existe");
            }else {
                e.printStackTrace();
            }

        }
    }

    public PetVo query(String name) {
        String sql = "SELECT * FROM mascotas WHERE nombre = ?";
        Connection con = connection.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PetVo pet = new PetVo();
                pet.setName(rs.getString("nombre"));
                pet.setOwnerId(rs.getString("id_dueno"));
                pet.setBreed(rs.getString("raza"));
                pet.setGender(rs.getString("sexo"));
                return pet;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void delete(String name) {
        String sql = "DELETE FROM mascotas WHERE nombre = ?";
        Connection con = connection.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(PetVo pet) {
        String sql = "UPDATE mascotas SET raza = ?, sexo = ?, id_dueno = ? WHERE nombre = ?";
        Connection con = connection.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pet.getBreed());
            ps.setString(2, pet.getGender());
            ps.setString(3, pet.getOwnerId());
            ps.setString(4, pet.getName());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String list() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM mascotas";
        Connection con = connection.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                sb.append("Name: ").append(rs.getString("nombre")).append("\n");
                sb.append("Owner ID: ").append(rs.getString("id_dueno")).append("\n");
                sb.append("Breed: ").append(rs.getString("raza")).append("\n");
                sb.append("Gender: ").append(rs.getString("sexo")).append("\n\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

} 