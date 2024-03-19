package be.ylorthioir.dal;

import be.ylorthioir.entities.Javanais;
import be.ylorthioir.entities.Voiture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JavanaisRepository implements JavanaisDAO{

    public void create(Javanais p) {
        String requete = "INSERT INTO javanais (nom, prenom) VALUES (?, ?)";

        try (Connection connection = ConnectionFactory.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setString(1, p.getNom());
            preparedStatement.setString(2, p.getPrenom());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("create error", e);
        }
    }

    public Optional<Javanais> getOne(Long id) {
        String requete = "SELECT * FROM javanais WHERE id = ?";

        try (Connection connection = ConnectionFactory.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Javanais p = new Javanais();
                    p.setId(rs.getLong("id"));
                    p.setNom(rs.getString("nom"));
                    p.setPrenom(rs.getString("prenom"));
                    return Optional.of(p);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("getOne error", e);
        }
    }

    public void update(Javanais p, Long id) {
        String requete = "UPDATE javanais SET nom = ?, prenom = ? WHERE id = ?";

        try (Connection connection = ConnectionFactory.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setString(1, p.getNom());
            preparedStatement.setString(2, p.getPrenom());
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("update error", e);
        }
    }

    public void delete(Long id) {
        String requete = "DELETE FROM javanais WHERE id = ?";

        try (Connection connection = ConnectionFactory.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(requete)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("delete error", e);
        }
    }

    public List<Javanais> getAll() {
        String requete = "SELECT * FROM javanais";

        try (Connection connection = ConnectionFactory.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(requete);
             ResultSet rs = preparedStatement.executeQuery()) {

            List<Javanais> liste = new ArrayList<>();

            while (rs.next()) {
                Javanais p = new Javanais();
                p.setId(rs.getLong("id"));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setNumeroRoueInfortune(rs.getInt("numeroRoueInfortune"));

                liste.add(p);
            }

            return liste;
        } catch (SQLException e) {
            throw new RuntimeException("getAll error", e);
        }
    }

    public List<Javanais> getAllWithCar(){
        String requete = "SELECT * FROM javanais j LEFT JOIN voiture v ON j.id_voiture = v.id ";

        try (Connection connection = ConnectionFactory.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(requete);
             ResultSet rs = preparedStatement.executeQuery()) {

            List<Javanais> liste = new ArrayList<>();

            while (rs.next()) {
                Voiture v = new Voiture();
                if(rs.getString("marque")!=null){
                    v.setId(rs.getLong("id_voiture"));
                    v.setModele(rs.getString("modele"));
                    v.setMarque(rs.getString("marque"));
                } else
                    v =null;

                Javanais p = new Javanais();
                p.setId(rs.getLong("id"));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setNumeroRoueInfortune(rs.getInt("numeroRoueInfortune"));
                p.setVoiture(v);

                liste.add(p);
            }

            return liste;
        } catch (SQLException e) {
            throw new RuntimeException("getAll error", e);
        }
    }

}
