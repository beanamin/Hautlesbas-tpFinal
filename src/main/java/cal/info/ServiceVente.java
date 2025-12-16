package cal.info;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ServiceVente {
    ServiceInventaire si = new ServiceInventaire();

    public ServiceVente(){

    }

    public boolean creerVente(Vente v) throws SQLException{
        if(!si.rechercherChausettes(null, null).containsAll(v.getChausettes())){
            return false;
        }
        String insertSQL = "INSERT INTO Ventes (dateVente, total) VALUES (?, ?)";
        try (Connection conn = ConfigurationPool.obtenirDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setDate(1, new Date(v.getDateVente().getTime()));
            pstmt.setDouble(2, v.calculerTotal());
            pstmt.executeUpdate();
            ResultSet test = conn.createStatement().executeQuery("SELECT MAX(id) AS id FROM Ventes");
            if (test.first()){
                int idVente = test.getInt("id");
                System.out.println(idVente);
                for (Chausette chausette : v.getChausettes()){
                    conn.createStatement().executeUpdate("UPDATE Inventaire SET idVendu=" + idVente + "WHERE id=" + chausette.getId());
                }
                return true;
            }else {
                System.out.println("¯\\(ツ)/¯");
            }

        }
        return false;
    }
    public void annulerVente(int id) throws SQLException{
        String removeVenteSQL = "DELETE FROM Ventes WHERE id=?";
        String chausettesSQL = "UPDATE Inventaire SET idVendu=0 WHERE idVendu=?";
        try (Connection conn = ConfigurationPool.obtenirDataSource().getConnection();
             PreparedStatement pstmtChausettes = conn.prepareStatement(chausettesSQL);
             PreparedStatement pstmtVente = conn.prepareStatement(removeVenteSQL)) {
            pstmtVente.setInt(1, id);
            pstmtVente.executeUpdate();
            pstmtChausettes.setInt(1, id);
            pstmtChausettes.executeUpdate();
        }
    }
    public List<Vente> listerVentes() throws SQLException{
        List<Vente> ventes = new ArrayList<>();
        String selectSQL = "SELECT * FROM Ventes";
        try (Connection conn = ConfigurationPool.obtenirDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                Vente vente = new Vente();
                vente.setId(rs.getInt("id"));
                vente.setDateVente(rs.getDate("dateVente"));
                vente.setTotal(rs.getDouble("total"));
                List<Chausette> chausettes = new ArrayList<>();
                String selectSQL2 = "SELECT * FROM Inventaire WHERE idVendu=" + rs.getInt("id");
                try (Connection conn2 = ConfigurationPool.obtenirDataSource().getConnection();
                     Statement stmt2 = conn2.createStatement();
                     ResultSet rs2 = stmt2.executeQuery(selectSQL2)) {
                    while (rs2.next()) {
                        Chausette chausette = new Chausette();
                        chausette.setId(rs2.getInt("id"));
                        chausette.setCouleur(rs2.getString("couleur"));
                        chausette.setTaille(rs2.getString("taille"));
                        chausette.setTypeTissu(rs2.getString("typeTissu"));
                        chausette.setPrix(rs2.getInt("prix"));
                        chausettes.add(chausette);
                    }
                }
                vente.setChausettes(chausettes);
                ventes.add(vente);
            }
        }
        return ventes;
    }

    // Police
    // Licks
    // Rat
    // Tail
    // Six
    // Poops
    // Ascend
    public Vente rechercherVente(int id) throws SQLException{
        Vente vente = new Vente();
        String selectSQL = "SELECT * FROM Ventes WHERE id=?";
        try (Connection conn = ConfigurationPool.obtenirDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                vente.setId(rs.getInt("id"));
                vente.setTotal(rs.getDouble("total"));
                vente.setDateVente(rs.getDate("dateVente"));
                List<Chausette> chausettes = new ArrayList<>();
                String selectSQL2 = "SELECT * FROM Inventaire WHERE idVendu=" + rs.getInt("id");
                try (Connection conn2 = ConfigurationPool.obtenirDataSource().getConnection();
                     Statement stmt2 = conn2.createStatement();
                     ResultSet rs2 = stmt2.executeQuery(selectSQL2)) {
                    while (rs2.next()) {
                        Chausette chausette = new Chausette();
                        chausette.setId(rs2.getInt("id"));
                        chausette.setCouleur(rs2.getString("couleur"));
                        chausette.setTaille(rs2.getString("taille"));
                        chausette.setTypeTissu(rs2.getString("typeTissu"));
                        chausette.setPrix(rs2.getInt("prix"));
                        chausettes.add(chausette);
                    }
                }
                vente.setChausettes(chausettes);
            }
        }
        return vente;
    }
    public Vente rechercherVenteParDate(Date d) throws SQLException{
        Vente vente = null;
        String selectSQL = "SELECT * FROM Ventes WHERE dateVente=?";
        try (Connection conn = ConfigurationPool.obtenirDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, d.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                vente = new Vente();
                vente.setId(rs.getInt("id"));
                vente.setTotal(rs.getDouble("total"));
                vente.setDateVente(rs.getDate("dateVente"));
                List<Chausette> chausettes = new ArrayList<>();
                String selectSQL2 = "SELECT * FROM Inventaire WHERE idVendu=" + rs.getInt("id");
                try (Connection conn2 = ConfigurationPool.obtenirDataSource().getConnection();
                     Statement stmt2 = conn2.createStatement();
                     ResultSet rs2 = stmt2.executeQuery(selectSQL2)) {
                    while (rs2.next()) {
                        Chausette chausette = new Chausette();
                        chausette.setId(rs2.getInt("id"));
                        chausette.setCouleur(rs2.getString("couleur"));
                        chausette.setTaille(rs2.getString("taille"));
                        chausette.setTypeTissu(rs2.getString("typeTissu"));
                        chausette.setPrix(rs2.getInt("prix"));
                        chausettes.add(chausette);
                    }
                }
                vente.setChausettes(chausettes);
            }
        }
        return vente;
    }
}
//public void ajouterHackathon(Hackathon hackathon) throws SQLException {
//    String insertSQL = "INSERT INTO Hackathon (nomHackathon, dateHackathon, lieuHackathon, urlHackathon) VALUES (?, ?, ?, ?)";
//    try (Connection conn = ConfigurationPool.obtenirDataSource().getConnection();
//         PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
//        pstmt.setString(1, hackathon.getNomHackathon());
//        pstmt.setString(2, hackathon.getDateHackathon());
//        pstmt.setString(3, hackathon.getLieuHackathon());
//        pstmt.setString(4, hackathon.getUrlHackathon());
//        pstmt.executeUpdate();
//    }
//}
//public void modifierHackathon(Hackathon h){
//    System.out.println("Modification en construction");
//}
//public void supprimerHackathon(String nomHackathon) throws SQLException{ //CHANGER POUR LE SQL
//    String deleteSQL = "DELETE FROM Hackathon WHERE nomHackathon=?";
//    try(Connection conn = ConfigurationPool.obtenirDataSource().getConnection();
//        PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
//        pstmt.setString(1, nomHackathon);
//        pstmt.executeUpdate();
//    }
//}
//// Récupérer tous les hackathons
//public List<Hackathon> obtenirHackathons() throws SQLException {
//    List<Hackathon> hackathons = new ArrayList<>();
//    String selectSQL = "SELECT * FROM Hackathon";
//    try (Connection conn = ConfigurationPool.obtenirDataSource().getConnection();
//         Statement stmt = conn.createStatement();
//         ResultSet rs = stmt.executeQuery(selectSQL)) {
//        while (rs.next()) {
//            Hackathon hackathon = new Hackathon();
//            hackathon.setId(rs.getInt("id"));
//            hackathon.setNomHackathon(rs.getString("nomHackathon"));
//            hackathon.setDateHackathon(rs.getString("dateHackathon"));
//            hackathon.setLieuHackathon(rs.getString("lieuHackathon"));
//            hackathon.setUrlHackathon(rs.getString("urlHackathon"));
//            hackathons.add(hackathon);
//        }
//    }
//    return hackathons;
//}
//public List<Hackathon> obtenirHackathons(String nomHackathon) throws SQLException {
//    List<Hackathon> hackathons = new ArrayList<>();
//    String selectSQL = "SELECT * FROM Hackathon WHERE nomHackathon=?";
//    try (Connection conn = ConfigurationPool.obtenirDataSource().getConnection();
//         PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
//        pstmt.setString(1, nomHackathon);
//        ResultSet rs = pstmt.executeQuery();
//        while (rs.next()) {
//            Hackathon hackathon = new Hackathon();
//            hackathon.setId(rs.getInt("id"));
//            hackathon.setNomHackathon(rs.getString("nomHackathon"));
//            hackathon.setDateHackathon(rs.getString("dateHackathon"));
//            hackathon.setLieuHackathon(rs.getString("lieuHackathon"));
//            hackathon.setUrlHackathon(rs.getString("urlHackathon"));
//            hackathons.add(hackathon);
//        }
//    }
//    return hackathons;
//}
