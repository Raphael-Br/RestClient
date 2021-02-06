import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.json.JSONArray;


public class Restful {

    static ArrayList<Movie> movies = new ArrayList<Movie>();

    public static void main(String[] args) throws IOException {

        boolean run = true;

        while (run) {
            System.out.print("\nTitle >> ");
            Scanner ks = new Scanner(System.in);
            String s = ks.nextLine();
            if (s.equals("exit")) {
                run = false;
                System.out.println("Bye!");
            } else {
                getData(s);
            }
        }


    } // main

    static void addMovie(String title, String year, String imdb, String type, String poster) {
        movies.add(new Movie(title, year, imdb, type, poster));
    }

    static void getData(String s) throws IOException, UnknownHostException {
        try {
            // Request to Server
            String url = "http://www.omdbapi.com/?apikey=58ed8f90";
            url += "&s=" + s;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            String responseStr = response.body().string();

            // handle JSON
            JSONObject jsonObj = new JSONObject(responseStr); // JSON Objekt erstellen
            String responseServer = jsonObj.getString("Response");

            if (responseServer.equals("False")) {
                String errorRequest = jsonObj.getString("Error");
                System.out.println("Fehler: " + errorRequest);
            } else if (responseServer.equals("True")) {
                movies.clear(); // Array leeren
                JSONArray jsonArray = jsonObj.getJSONArray("Search"); // Objekt in Array
                for (int i = 0; i < jsonArray.length(); i++) {
                    String title = jsonArray.getJSONObject(i).getString("Title");
                    String year = jsonArray.getJSONObject(i).getString("Year");
                    String imdb = jsonArray.getJSONObject(i).getString("imdbID");
                    String type = jsonArray.getJSONObject(i).getString("Type");
                    String poster = jsonArray.getJSONObject(i).getString("Poster");
                    addMovie(title, year, imdb, type, poster);
                }

                // Sortieren nach Jahr
                Collections.sort(movies, Movie.sortByYear);

                // Ausgabe Resultate
                System.out.println("\nResults:");

                for (Movie m : movies) {
                    System.out.println(m.title + " (" + m.year + ") [" + m.type + "]");
                }

            } else {
                System.out.println("Unknown Status");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    } // getData()

}
