package recommendationRunner;

import filters.GenreFilter;

public class Main {
    public static void main(String[] args) {
        MovieRunnerSimilarRatings movieRunnerSimilarRatingsTest = new MovieRunnerSimilarRatings();
        movieRunnerSimilarRatingsTest.printSimilarRatingsByAnyFilter("65", new GenreFilter("Action"), new GenreFilter("Comedy"));
    }


}
