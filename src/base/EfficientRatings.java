/**
 *Class EfficientRating calculates average weighted rating based on the algorithm.
 * Idea of algorithm based on similarity rate. The raters that have more similar taste, they have bigger weight for recommendation.
 * Step 1. Center the ratings by subtracting the middle rating of five from each one.
 * Step 2. Calculate dot product between meRater and other raters. Dot product is a measure of closeness between two raters.
 * Step 3. Calculate similarity of meRater and others.
 * Step 4. Calculate list based on average weighted rating with a rule
 * that the movie has to be rated at least minimal number of raters and minimal number of raters from similarities list.
 * Example can be found at README.md.
 * @author Pavlo Kislov
 * @version 03 Mart 2021
 */
package base;

import database.MovieDatabase;
import database.RaterDatabase;
import search.BinarySearch;
import filters.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EfficientRatings {
    public EfficientRatings() {
        new MovieDatabase();
        new RaterDatabase();
    }

    public EfficientRatings(String movieFile, String ratingsFile) {
        new MovieDatabase(movieFile);
        new RaterDatabase(ratingsFile);
    }

    /*The method calculates dot product of meRater and another rater.
     *Dot product is a measure of closeness between two raters. */
    private double dotProduct(Rater me, Rater other) {
        double dotProduc = 0;
        ArrayList<String> otherItems = other.getItemsRated();
        BinarySearch search = new BinarySearch();
        for (String itemMe : me.getItemsRated()) {
            int index = search.binarySearchRater(itemMe, other);
            if (index != -1) {
                dotProduc += (me.getRating(itemMe) - 5.0) * (other.getRating(otherItems.get(index)) - 5.0);
            }
        }
        return dotProduc;
    }

    /*The method calculates similarities of meRater with all other raters in the database.
     * Similarities are measure of closeness raters.
     * The raters that more similarity to meRater, they have greater weight. */
    private ArrayList<Rating> getSimilarities(String id) {
        Rater meRater = RaterDatabase.getRater(id);
        ArrayList<Rating> similaritiesRatings = new ArrayList<Rating>();
        for (Rater otherRater : RaterDatabase.getRaters()) {
            if (otherRater.equals(meRater)) { /*Don't calculate meRater with themself */
                continue;
            } else {
                double dotProduct = dotProduct(meRater, otherRater);
                if (dotProduct > 0) { /*Don't add rating if closeness lesser than 0, meRater and another rater have very different tastes*/
                    similaritiesRatings.add(new Rating(otherRater.getID(), dotProduct));
                }
            }
        }
        Collections.sort(similaritiesRatings, Collections.reverseOrder()); /*Sort list as the largest similarity at top and the smallest at bottom.*/
        return similaritiesRatings; /*List in format as raterID and value of similarity*/
    }

    /*Calculate average weighted rating for movie with a rule
    that the movie has to be rated at least minimal number of raters and raters from similarities list.*/
    public Double getAverageByID(String movieID, Integer minimalRaters, Integer numSimilarRaters, ArrayList<Rating> similarRatings, String meRaterId) {
        ArrayList<Double> allRatings = new ArrayList<>(); /*list for calculate weighted average rating*/
        for (Rater rater : RaterDatabase.getRaters()) {
            if (rater.getID().equals(meRaterId)) { /*not include rating mady by yourself*/
                continue;
            }
            double rating = rater.getRating(movieID);
            if (rating != -1) {
                for (int i = 0; i < similarRatings.size(); i++) {
                    if (rater.getID().equals(similarRatings.get(i).getItem())) {
                        allRatings.add(rating * similarRatings.get(i).getValue());
                        --minimalRaters;
                        break;
                    }
                    if (i == similarRatings.size() - 1) {
                        allRatings.add(rating);
                    }
                }
            }
        }
        if ((minimalRaters <= 0) && allRatings.size() >= numSimilarRaters) { //minimalRaters<=0
            double averageRating = 0.0;
            for (double rating : allRatings) {
                averageRating += rating;
            }
            averageRating = averageRating / allRatings.size();
            return averageRating;
        }
        return 0.0;
    }

    /*Calculate average weighted rating for all movies */
    public ArrayList<Rating> getSimilarRatings(String raterId, Integer numSimilarRaters, Integer minimalRaters, Filter... filters) {
        ArrayList<Rating> weightedRatings = new ArrayList<>();
        List<Rating> cutSimilarRatings = getSimilarities(raterId).subList(0, numSimilarRaters);
        AllFilters allFilters = new AllFilters();
        for (Filter filter : filters) {
            allFilters.addFilter(filter);
        }
        ArrayList<String> movies = MovieDatabase.filterBy(allFilters);
        for (String movieId : movies) {
            Double averageByID = getAverageByID(movieId, minimalRaters, numSimilarRaters, new ArrayList<>(cutSimilarRatings), raterId);
            if (averageByID > 0) {
                weightedRatings.add(new Rating(movieId, averageByID));
            }
        }
        Collections.sort(weightedRatings, Collections.reverseOrder());
        return weightedRatings;
    }


    public int getRaterSize() {
        return RaterDatabase.size();
    }

}

