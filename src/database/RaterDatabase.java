/**
 * A class for reading rater data from a file.
 *
 * @author Pavlo Kislov
 * @version 25 Feb 2021
 */

package database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.*;

import base.*;
import org.apache.commons.csv.*;

public class RaterDatabase {
    private static HashMap<String, Rater> ourRaters;

    public RaterDatabase() {
        initialize("ratings.csv");
    }

    public RaterDatabase(String fileName) {
        initialize(fileName);
    }

    private static void initialize() {
        /* this method called only in this class to initialize new HashMap*/
        if (ourRaters == null) {
            ourRaters = new HashMap<String, Rater>();
        }
    }

    private static void initialize(String fileName) {
        if (ourRaters == null) {
            ourRaters = new HashMap<String, Rater>();
            addRatings("data//" + fileName);
        }
    }


    public static void addRatings(String filename) {
        initialize();
        try {
            Reader in = new FileReader(filename);
            String HEADERS[] = {"rater_id", "movie_id", "rating", "time"};
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(HEADERS).withFirstRecordAsHeader()
                    .parse(in);
            for (CSVRecord rec : records) {
                String id = rec.get("rater_id");
                String item = rec.get("movie_id");
                String rating = rec.get("rating");
                addRaterRating(id, item, Double.parseDouble(rating));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found " + e);
        } catch (IOException e) {
            System.out.println("Exception: " + e);

        }
    }

    public static void addRaterRating(String raterID, String movieID, double rating) {
        initialize();
        Rater rater = null;
        if (ourRaters.containsKey(raterID)) {        /* if exist then get that rater*/
            rater = (Rater) ourRaters.get(raterID);
        } else {
            rater = new EfficientRater(raterID);     /* else initialize a new rater*/
        }
        rater.addRating(movieID, rating);
        ourRaters.put(raterID, rater);               /*put the rater to database*/
    }

    public static Rater getRater(String id) {
        initialize();
        return ourRaters.get(id);
    }

    public static ArrayList<Rater> getRaters() {
        initialize();
        return new ArrayList<Rater>(ourRaters.values());
    }

    public static int size() {
        return ourRaters.size();
    }
}