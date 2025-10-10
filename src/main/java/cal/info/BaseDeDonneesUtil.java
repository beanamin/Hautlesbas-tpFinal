package cal.info;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDeDonneesUtil {
    private static final String URL = "jdbc:h2:./data/hackathonDB"; // Mode fichier
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        try {

            // Charger le driver H2
            Class.forName("org.h2.Driver");

            // Initialiser la base de données en créant la table si elle n'existe pas
            initialiserBase();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
    }

    private static void initialiserBase() {
        String creationTableSQLHackathon = "CREATE TABLE IF NOT EXISTS Hackathon ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nomHackathon VARCHAR(255) NOT NULL,"
                + "dateHackathon VARCHAR(50) NOT NULL,"
                + "lieuHackathon VARCHAR(255) NOT NULL,"
                + "urlHackathon VARCHAR(255) NOT NULL"
                + ");";

        String creationTableSQLEtudiant = "CREATE TABLE IF NOT EXISTS Etudiant ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nomEtudiant VARCHAR(255) NOT NULL,"
                + "ageEtudiant INT NOT NULL,"
                + "noteEtudiant INT NOT NULL"
                + ");";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(creationTableSQLHackathon);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(creationTableSQLEtudiant);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}