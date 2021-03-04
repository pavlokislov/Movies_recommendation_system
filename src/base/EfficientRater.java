/**
 * Class EfficientRater stores information about raters in HashMap<String, ArrayList<Rating>>,
 * where the key is RaterID, and the value is an Arraylist of ratings in the format (movieID as item, rating as value).
 * EfficientRater uses binary search to get rating by item(movieId) in an Arraylist.
 *
 * @author Pavlo Kislov
 * @version 1.0 03 Mart 2021
 */
package base;

import search.BinarySearch;

import java.util.*;

public class EfficientRater implements Rater {
    private String id;
    private HashMap<String, ArrayList<Rating>> myRatings;

    public EfficientRater(String id) {
        this.id = id;
        this.myRatings = new HashMap<String, ArrayList<Rating>>();
    }

    public void addRating(String item, double rating) {
        ArrayList<Rating> list;
        if (myRatings.isEmpty()) {              /* If a rater doesn't have a rating, then add a new rating to new ArrayList*/
            list = new ArrayList<>();
            list.add(new Rating(item, rating));
        } else {
            list = myRatings.get(this.id);     /*If a rater has ratings, then add a new rating to the current ArrayList.*/
            list.add(new Rating(item, rating));
        }
        myRatings.putIfAbsent(this.id, list);
    }

    public boolean hasRating(String item) {
        BinarySearch search = new BinarySearch();
        int index = search.binarySearchRating(item, new ArrayList<>(myRatings.values()).get(0));
        return index != -1 ? true : false;
    }

    public String getID() {
        return id;
    }

    public double getRating(String item) {
        BinarySearch search = new BinarySearch();
        ArrayList<Rating> ratings = new ArrayList<>(myRatings.values()).get(0);
        int index = search.binarySearchRating(item, ratings);
        if (index != -1) {
            return ratings.get(index).getValue();
        }
        return -1.0;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for (Rating rating : myRatings.get(this.id)) {
            list.add(rating.getItem());
        }
        return list;
    }
}

