package be.ylorthioir.dal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class InitDB {

    public static void initialisation(){
        String requete = """
            DROP TABLE IF EXISTS javanais;
            DROP TABLE IF EXISTS voiture;
            
            CREATE TABLE voiture (id serial PRIMARY KEY, marque varchar(50), modele varchar(50));
            INSERT INTO voiture VALUES (DEFAULT, 'Honda', 'Jazz');
            INSERT INTO voiture VALUES (DEFAULT, 'Mazda', '2');
            INSERT INTO voiture VALUES (DEFAULT, 'Ford', 'Fiesta');
            INSERT INTO voiture VALUES (DEFAULT, 'Peugeot', '308');
            INSERT INTO voiture VALUES (DEFAULT, 'Fiat', 'Punto');
            INSERT INTO voiture VALUES (DEFAULT, 'Toyota', 'Aygo');
            INSERT INTO voiture VALUES (DEFAULT, 'Opel', 'Corsa');
            

            
            CREATE TABLE javanais (id serial PRIMARY KEY, prenom varchar(50), nom varchar(50), numeroRoueInfortune int, id_voiture bigint REFERENCES voiture);
            INSERT INTO javanais VALUES (DEFAULT, 'Elise', 'Dirheimer',1,null);
            INSERT INTO javanais VALUES (DEFAULT, 'Laurent', 'Valenne',2,1);
            INSERT INTO javanais VALUES (DEFAULT, 'Lucas', 'Balon',3,2);
            INSERT INTO javanais VALUES (DEFAULT, 'Ronny', 'Dermine',4,null);
            INSERT INTO javanais VALUES (DEFAULT, 'Gaetan', 'Beukens',5,null);
            INSERT INTO javanais VALUES (DEFAULT, 'Ismail', 'Jacoby',6,3);
            INSERT INTO javanais VALUES (DEFAULT, 'Geoffrey', 'Beyer de ryke',7,null);
            INSERT INTO javanais VALUES (DEFAULT, 'Samuel', 'Depierreux',8,4);
            INSERT INTO javanais VALUES (DEFAULT, 'Pascal', 'Decok',9,5);
            INSERT INTO javanais VALUES (DEFAULT, 'Aline', 'Daems',10,7);
            INSERT INTO javanais VALUES (DEFAULT, 'Nathan', 'Geisbush',11,6);
            INSERT INTO javanais VALUES (DEFAULT, 'Juan', 'Barbera',12,null);

            """;

        System.out.println("Init...");

        try (Connection connection = ConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {
            try{

                connection.setAutoCommit(false);
                System.out.println("Connection ok...");
                statement.execute(requete);
                System.out.println("Init done!");
                connection.commit();
            }catch(SQLException e){
                connection.rollback();
                throw new RuntimeException("error",e);
            }
        }catch (SQLException e){
            throw new RuntimeException("error",e);
        }
        
    }
}
