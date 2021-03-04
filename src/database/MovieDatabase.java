/**
 * A class for reading movie data from a file.
 *
 * @author Pavlo Kislov
 * @version 25 Feb 2021
 */
package database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import base.*;
import filters.Filter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class MovieDatabase {
    private static HashMap<String, Movie> ourMovies;

    public MovieDatabase() {
        initialize("ratedmoviesfull.csv");
    }

    public MovieDatabase(String movieFile) {
        initialize(movieFile);
    }

    private static void initialize(String movieFile) {
        if (ourMovies == null) {
            ourMovies = new HashMap<String, Movie>();
            loadMovies("data//" + movieFile);
        }
    }

    private static void initialize() {
        if (ourMovies == null) {
            ourMovies = new HashMap<String, Movie>();
            loadMovies("ratedmoviesfull.csv");
        }
    }


    private static void loadMovies(String filename) { //load movies in the database from the file cvs;
        try {
            Reader in = new FileReader(filename);
            String HEADERS[] = {"id", "title", "year", "country", "genre", "director", "minutes", "poster"};
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(HEADERS).withFirstRecordAsHeader()
                    .parse(in);
            for (CSVRecord rec : records) {
                String id = rec.get("id");
                String title = rec.get("title");
                String year = rec.get("year");
                String country = rec.get("country");
                String genre = rec.get("genre");
                String director = rec.get("director");
                int minuter = Integer.valueOf(rec.get("minutes"));
                String poster = rec.get("poster");
                ourMovies.put(id, new Movie(id, title, year, genre, director, country, poster, minuter)); // Parameters in a movie by order: ID, Title, Year, Genres,
                // Director, Country, Poster, Minutes.
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + e);
        } catch (
                IOException e) {
            System.out.println("Exception: " + e);
        }
    }

    public static boolean containsID(String id) {
        initialize();
        return ourMovies.containsKey(id);
    }

    public static int getYear(String id) {
        initialize();
        return ourMovies.get(id).getYear();
    }

    public static String getGenres(String id) {
        initialize();
        return ourMovies.get(id).getGenres();
    }

    public static String getTitle(String id) {
        initialize();
        return ourMovies.get(id).getTitle();
    }

    public static Movie getMovie(String id) {
        initialize();
        return ourMovies.get(id);
    }

    public static String getPoster(String id) {
        initialize();
        return ourMovies.get(id).getPoster();
    }

    public static int getMinutes(String id) {
        initialize();
        return ourMovies.get(id).getMinutes();
    }

    public static String getCountry(String id) {
        initialize();
        return ourMovies.get(id).getCountry();
    }

    public static String getDirector(String id) {
        initialize();
        return ourMovies.get(id).getDirector();
    }

    public static int size() {
        return ourMovies.size();
    }

    public static ArrayList<String> filterBy(Filter f) {
        initialize();
        ArrayList<String> list = new ArrayList<String>();
        for (String id : ourMovies.keySet()) {
            if (f.satisfies(id)) {
                list.add(id);
            }
        }
        return list;
    }
}
