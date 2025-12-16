package cal.info;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControleurInventaire implements HttpHandler {

    public ControleurInventaire(){

    }
    ServiceInventaire ServiceInventaire = new ServiceInventaire();
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Bienvenue au controle de l'inventaire!";
        String typeRequete = exchange.getRequestMethod();
        System.out.println("Requete de type : " + typeRequete);
        String query = exchange.getRequestURI().getQuery();
        switch (typeRequete){
            case "GET":
                try{
                    rechercherChausettes(exchange, query);
                }
                catch (SQLException e){
                    System.out.println(e);
                }
                break;
            case "POST":
                try{
                    ajouterChausette(exchange);
                }
                catch (IOException | SQLException e){
                    System.out.println(e);
                }
                break;
            case "PUT":
                try {
                    modifierChausette(exchange);
                }catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            case "DELETE":
                try {
                    supprimerChausette(exchange, query);
                }
                catch (SQLException e){
                    System.out.println(e);
                }
                break;
            default:
                exchange.sendResponseHeaders(405,-1);
                System.out.println("Error type request");
                break;


        }

        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    public void rechercherChausettes(HttpExchange exchange, String query) throws SQLException{
        List<Chausette> chausettesList;
        String taille = null;
        String couleur = null;
        if (query != null) {
            String[] exp = query.split("[&=]");
            for (int i = 0; i < exp.length; i++) {
                if (exp[i].equals("taille")) {
                    taille = exp[i + 1];
                    System.out.println(taille);
                }
                if (exp[i].equals("couleur")) {
                    couleur = exp[i + 1];
                    System.out.println(couleur);
                }
            }
        }
        chausettesList = ServiceInventaire.rechercherChausettes(taille, couleur);

        String response = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            response = mapper.writeValueAsString(chausettesList);

            byte[] reponseEncode = response.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, reponseEncode.length);

            OutputStream os = exchange.getResponseBody();
            os.write(reponseEncode);
            os.close();
        }catch (IOException e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public void ajouterChausette(HttpExchange exchange) throws IOException, SQLException{
        InputStream corpRequete = exchange.getRequestBody();
        InputStreamReader lecteur = new InputStreamReader(corpRequete, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        Chausette chausette = mapper.readValue(lecteur, Chausette.class);

        ServiceInventaire.ajouterChausette(chausette);

        String reponse = "La chausette a bien été ajouté";
        byte[] reponseEncodee = reponse.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(201, reponseEncodee.length);

        OutputStream os = exchange.getResponseBody();
        os.write(reponseEncodee);
        os.close();
    }
    public void modifierChausette(HttpExchange exchange) throws IOException, SQLException{
        InputStream corpRequete = exchange.getRequestBody();
        InputStreamReader lecteur = new InputStreamReader(corpRequete, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        Chausette chausette = mapper.readValue(lecteur, Chausette.class);

        ServiceInventaire.modifierChausette(chausette);

        String reponse = "La chausette a bien été modifiée^¸";
        byte[] reponseEncodee = reponse.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(201, reponseEncodee.length);

        OutputStream os = exchange.getResponseBody();
        os.write(reponseEncodee);
        os.close();
    }
    public void supprimerChausette(HttpExchange exchange, String query) throws IOException, SQLException {
        String[] exp = query.split("[&=]");
        int id = 0;
        for (int i = 0; i < exp.length; i++){
            if (exp[i].equals("id")){
                id = Integer.parseInt(exp[i + 1]);
            }
        }
        if (ServiceInventaire.supprimerChausette(id)){

        String reponse = "La chausette a bien été supprimée";
        byte[] reponseEncodee = reponse.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(204, reponseEncodee.length);
        OutputStream os = exchange.getResponseBody();
        os.write(reponseEncodee);
        os.close();
        }else {
            String reponse = "La chausette n,a pas été trouvée";
            byte[] reponseEncodee = reponse.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            exchange.sendResponseHeaders(404, reponseEncodee.length);
            OutputStream os = exchange.getResponseBody();
            os.write(reponseEncodee);
            os.close();
        }



    }

}
