package cal.info;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

public class ControleurVente implements HttpHandler {

    public ControleurVente(){}
    ServiceVente ServiceVente = new ServiceVente();
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Bienvenue au controle des Ventes!";
        String typeRequete = exchange.getRequestMethod();
        System.out.println("Requete de type : " + typeRequete);
        String query = exchange.getRequestURI().getQuery();
        System.out.println(query);
        switch (typeRequete){
            case "GET":

                try{
                    if (query == null){
                        afficherVentes(exchange);
                    }else {
                        if (query.contains("date=")) {
                            rechercherVenteParDate(exchange, query);
                        }else{
                            if (query.contains("id=")){
                                rechercherVente(exchange, query);
                            }
                        }
                    }
                }
                catch (IOException e){
                    System.out.println(e);
                }
                break;
            case "POST":
                try{
                    ajouterVente(exchange);
                }
                catch (IOException e){
                    System.out.println(e);
                }
                break;
            case "DELETE":

                supprimerVente(exchange, query);

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
        public void afficherVentes(HttpExchange exchange) throws IOException{
            List<Vente> VenteList = ServiceVente.listerVentes();
            String response = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                response = mapper.writeValueAsString(VenteList);

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

        public void rechercherVente(HttpExchange exchange, String query) throws IOException{
            String[] exp = query.split("[&=]");
            int id = 0;
            for (int i = 0; i < exp.length; i++){
                if (exp[i].equals("id")){
                    id = Integer.parseInt(exp[i + 1]);
                    System.out.println(id);
                }
            }
            Vente VenteList = ServiceVente.rechercherVente(id);
            String response = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                response = mapper.writeValueAsString(VenteList);

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
        public void rechercherVenteParDate(HttpExchange exchange, String query) throws IOException{
            String[] exp = query.split("[&=]");
            Date date = new Date("0");
            for (int i = 0; i < exp.length; i++){
                if (exp[i].equals("date")){
                    date = new Date(exp[i+1]);
                    System.out.println(date);
                }
            }
            Vente VenteList = ServiceVente.rechercherVenteParDate(date);
            String response = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                response = mapper.writeValueAsString(VenteList);

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
        public void ajouterVente(HttpExchange exchange) throws IOException{
            InputStream corpRequete = exchange.getRequestBody();
            InputStreamReader lecteur = new InputStreamReader(corpRequete, StandardCharsets.UTF_8);

            ObjectMapper mapper = new ObjectMapper();
            Vente vente = mapper.readValue(lecteur, Vente.class);
            System.out.println(vente);
            ServiceVente.creerVente(vente);

            String reponse = "La vente a bien été ajouté";
            byte[] reponseEncodee = reponse.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            exchange.sendResponseHeaders(201, reponseEncodee.length);

            OutputStream os = exchange.getResponseBody();
            os.write(reponseEncodee);
            os.close();
        }
        public void supprimerVente(HttpExchange exchange, String query) throws IOException{
            String[] exp = query.split("[&=]");
            int id = 0;
            for (int i = 0; i < exp.length; i++){
                if (exp[i].equals("id")){
                    id = Integer.parseInt(exp[i + 1]);
                    System.out.println(id);
                }
            }
            ServiceVente.annulerVente(id);

            String reponse = "La vente a bien été supprimée";
            byte[] reponseEncodee = reponse.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            exchange.sendResponseHeaders(201, reponseEncodee.length);

            OutputStream os = exchange.getResponseBody();
            os.write(reponseEncodee);
            os.close();
        }

    }
