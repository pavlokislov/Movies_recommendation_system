package recommendationRunner;

import base.*;
import database.*;
import filters.*;

import java.util.ArrayList;

public class MovieRunnerSimilarRatings {
    // default variables
    private int numSimilarRatings = 20;
    private int minimalRaters = 5;
    private EfficientRatings efficientRatings;

    MovieRunnerSimilarRatings() {
        efficientRatings = new EfficientRatings("ratedmoviesfull.csv", "ratings.csv");
    }

    // print recommendation list with zero or more filters
    public void printSimilarRatingsByAnyFilter(String raterId, Filter ... filters) {
        ArrayList<Rating> ratings = efficientRatings.getSimilarRatings(raterId, numSimilarRatings, minimalRaters, filters);
        printList(ratings);
    }

    private void printList(ArrayList<Rating> ratings) {
        System.out.println("Recommendation list:");
        int number = 1;
        for (Rating rating : ratings) {
            System.out.println(number++ + ". " + MovieDatabase.getTitle(rating.getItem()));
        }
    }
}
