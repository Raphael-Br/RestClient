import java.util.Comparator;

public class Movie {

    public String title;
    public String year;
    public String imdbNr;
    public String type;
    public String poster;

    public Movie (String title, String year, String imdbNr, String type, String poster) {
        this.title = title;
        this.year = year;
        this.imdbNr = imdbNr;
        this.type = type;
        this.poster = poster;
    }

    //sort by year
    public static Comparator<Movie> sortByYear = new Comparator<Movie>() {
        @Override
        public int compare(Movie obj1, Movie obj2) {
            return obj1.year.compareTo(obj2.year);
        }
    };

}
