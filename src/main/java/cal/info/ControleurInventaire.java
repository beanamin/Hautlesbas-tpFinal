package cal.info;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ControleurInventaire implements HttpHandler {

    public ControleurInventaire(){

    }
    ServiceInventaire ServiceInventaire = cal.info.ServiceInventaire.getInstance();
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Bienvenue au controle de l'inventaire!";
        String typeRequete = exchange.getRequestMethod();
        System.out.println("Requete de type : " + typeRequete);
        String query = exchange.getRequestURI().getQuery();
        System.out.println(query);
        switch (typeRequete){
            case "GET":

                try{
                    if (query == null){
                        afficherChausettes(exchange);
                    }else {
                        if (query.contains("couleur=") && query.contains("taille=")){
                            rechercherChausettes(exchange, query);
                        }
                    }
                }
                catch (IOException e){
                    System.out.println(e);
                }
                break;
            case "POST":
                try{
                    ajouterChausette(exchange);
                }
                catch (IOException e){
                    System.out.println(e);
                }
                break;
            case "PUT":
                 modifierChausette(exchange);
                break;
            case "DELETE":
                    supprimerChausette(exchange);
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
    public void afficherChausettes(HttpExchange exchange) throws IOException{
        List<Chausette> chausettesList = ServiceInventaire.listerChausettes();
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
    public void rechercherChausettes(HttpExchange exchange, String query) throws IOException{
        String[] exp = query.split("[&=]");
        String taille = "";
        String couleur = "";
        for (int i = 0; i < exp.length; i++){
            if (exp[i].equals("taille")){
                taille = exp[i+1];
                System.out.println(taille);
            }
            if (exp[i].equals("couleur")){
                couleur = exp[i+1];
                System.out.println(couleur);
            }
        }
        List<Chausette> chausettesList = ServiceInventaire.rechercherChausette(couleur, taille);
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

    public void ajouterChausette(HttpExchange exchange) throws IOException{
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
    public void modifierChausette(HttpExchange exchange) throws IOException{
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
    public void supprimerChausette(HttpExchange exchange) throws IOException{
        InputStream corpRequete = exchange.getRequestBody();
        InputStreamReader lecteur = new InputStreamReader(corpRequete, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        Chausette chausette = mapper.readValue(lecteur, Chausette.class);

        ServiceInventaire.supprimerChausette(chausette.getIdentifiant());

        String reponse = "La chausette a bien été supprimée";
        byte[] reponseEncodee = reponse.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(201, reponseEncodee.length);

        OutputStream os = exchange.getResponseBody();
        os.write(reponseEncodee);
        os.close();
    }

}
