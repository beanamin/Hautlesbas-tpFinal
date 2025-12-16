package cal.info;

import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceInventaire {

    public ServiceInventaire() {

    }


    public void ajouterChausette(Chausette chausette) throws SQLException{
        String insertSQL = "INSERT INTO Inventaire (couleur, taille, typeTissu, prix) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConfigurationPool.obtenirDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, chausette.getCouleur());
            pstmt.setString(2, chausette.getTaille());
            pstmt.setString(3, chausette.getTypeTissu());
            pstmt.setDouble(4, chausette.getPrix());
            pstmt.executeUpdate();
        }
    }
    public void modifierChausette(Chausette c) throws SQLException{
        String updateSQL = "UPDATE Inventaire SET couleur=?, taille=?, typeTissu=?, prix=? WHERE id=?";
        try(Connection conn = ConfigurationPool.obtenirDataSource().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setInt(5, c.getId());
            pstmt.setString(1, c.getCouleur());
            pstmt.setString(2, c.getTaille());
            pstmt.setString(3, c.getTypeTissu());
            pstmt.setDouble(4, c.getPrix());
            pstmt.executeUpdate();
        }
    }
    public boolean supprimerChausette(int id) throws SQLException{
        String deleteSQL = "DELETE FROM Inventaire WHERE id=?";
        try(Connection conn = ConfigurationPool.obtenirDataSource().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            if(pstmt.executeUpdate() == 1){
                return true;
            }
        }
        return false;
    }

    public List<Chausette> rechercherChausettes(String taille, String couleur) throws SQLException{
        List<Chausette> chausettes = new ArrayList<>();
        try (Connection conn = ConfigurationPool.obtenirDataSource().getConnection()) {
            String selectSQL = "SELECT * FROM Inventaire";
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(selectSQL);
            if (taille != null && couleur != null) {
                selectSQL = "SELECT * FROM Inventaire WHERE couleur=? AND taille=?";
                pstmt = conn.prepareStatement(selectSQL);
                pstmt.setString(1, couleur);
                pstmt.setString(2, taille);
            } else if (taille != null) {
                selectSQL = "SELECT * FROM Inventaire WHERE taille=?";
                pstmt = conn.prepareStatement(selectSQL);
                pstmt.setString(1, taille);
            } else if (couleur != null){
                selectSQL = "SELECT * FROM Inventaire WHERE couleur=?";
                pstmt = conn.prepareStatement(selectSQL);
                pstmt.setString(1, couleur);
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                    Chausette chausette = new Chausette();
                    chausette.setId(rs.getInt("id"));
                    chausette.setCouleur(rs.getString("couleur"));
                    chausette.setTaille(rs.getString("taille"));
                    chausette.setTypeTissu(rs.getString("typeTissu"));
                    chausette.setPrix(rs.getInt("prix"));
                    chausettes.add(chausette);
            }
        }
        return chausettes;
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