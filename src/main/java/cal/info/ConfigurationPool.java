package cal.info;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationPool {

    private static HikariDataSource dataSource;

    /**
     * Initialise le pool de connexion (à appeler UNE SEULE FOIS au démarrage)
     */
    public static void initialiser() {
        HikariConfig config = new HikariConfig();

        // Configuration de la base de données H2
        config.setJdbcUrl("jdbc:h2:./data/hautLesBas");
        config.setUsername("sa");
        config.setPassword("");
        config.setDriverClassName("org.h2.Driver");

        // Configuration du pool
        config.setMaximumPoolSize(10);        // Maximum 10 connexions
        config.setMinimumIdle(2);             // Minimum 2 connexions en attente
        config.setConnectionTimeout(30000);    // Timeout de 30 secondes
        config.setIdleTimeout(600000);         // Fermer après 10 minutes d'inactivité
        config.setMaxLifetime(1800000);        // Durée de vie maximale : 30 minutes

        // Nom du pool (pour le debugging)
        config.setPoolName("PoolH2Principal");

        // Requête de test de connexion
        config.setConnectionTestQuery("SELECT 1");

        // Auto-commit désactivé par défaut (bonne pratique)
        config.setAutoCommit(true);

        // Créer le pool
        dataSource = new HikariDataSource(config);

        System.out.println("✓ Pool de connexion initialisé");
    }

    public static void initialiserBase() {
        String creationTableSQLInventaire = "CREATE TABLE IF NOT EXISTS Inventaire ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "couleur VARCHAR(255) NOT NULL,"
                + "taille VARCHAR(50) NOT NULL,"
                + "typeTissu VARCHAR(255) NOT NULL,"
                + "prix INT NOT NULL,"
                + "idVendu INT DEFAULT 0 NOT NULL"
                + ");";

        /*private int identifiant;
        private Date dateVente;
        private double total;
        private List<Chausette> chausettes;*/
        String creationTableSQLVentes = "CREATE TABLE IF NOT EXISTS Ventes ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "dateVente VARCHAR(50) NOT NULL,"
                + "total DOUBLE NOT NULL);";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(creationTableSQLInventaire);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(creationTableSQLVentes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void creerEntresInit(){
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE Inventaire");
            stmt.execute("DROP TABLE Ventes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialiserBase();
        try {
            Vente v = new Vente();
            List<Chausette> chausettes = new ArrayList<>();
            chausettes.add(new Chausette(1, "Mauve", "M4", "Polyestre", 19));
            v.setChausettes(chausettes);
            v.calculerTotal();
            ServiceVente serviceVente = new ServiceVente();
            serviceVente.creerVente(v);
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {
            ServiceInventaire serviceInventaire = new ServiceInventaire();
            Chausette c1 = new Chausette();
            c1.setPrix(19);
            c1.setTaille("M4");
            c1.setCouleur("Mauve");
            c1.setTypeTissu("Polyestre");
            serviceInventaire.ajouterChausette(c1);
            Chausette c2 = new Chausette();
            c2.setPrix(22);
            c2.setTaille("F12");
            c2.setCouleur("Orange");
            c2.setTypeTissu("Cotton");
            serviceInventaire.ajouterChausette(c2);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Obtenir le DataSource (pool de connexion)
     */
    public static DataSource obtenirDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException(
                    "Le pool n'est pas initialisé. Appelez initialiser() d'abord."
            );
        }
        return dataSource;
    }

    /**
     * Fermer le pool (à appeler à la fin de l'application)
     */
    public static void fermer() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("✓ Pool de connexion fermé");
        }
    }
}
