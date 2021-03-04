package search;

import base.Rater;
import base.Rating;

import java.util.ArrayList;

public class BinarySearch {
    //Binary search of the item in Ratings, return the index of searched rating
    public int binarySearchRating(String item, ArrayList<Rating> ratings) {
        String a =ratings.get(0).getItem();
        int low = 0;
        int high = ratings.size();
        int searchedItem = Integer.valueOf(item);
        while (low <= high) {
            int mid = (low + high) / 2;
            if (mid >= ratings.size()) {
                break;
            }
            int currentItem = Integer.valueOf(ratings.get(mid).getItem());
            if (currentItem < searchedItem) {
                low = mid + 1;
            } else if (currentItem > searchedItem) {
                high = mid - 1;
            } else if (currentItem == searchedItem) {
                return mid; //index of searched rating
            }
        }
        return -1;
    }
    //Binary search of the item in Rater, return the index of searched rater
    public int binarySearchRater(String item, Rater raters) {
        ArrayList<String> othersRatings = new ArrayList<>(raters.getItemsRated());
        int low = 0;
        int high = othersRatings.size();
        int searchedItem = Integer.valueOf(item);
        while (low <= high) {
            int mid = (low + high) / 2;
            if (mid >= othersRatings.size()) {
                break;
            }
            int currentItem = Integer.valueOf(othersRatings.get(mid));
            if (currentItem < searchedItem) {
                low = mid+1;
            } else if (currentItem > searchedItem) {
                high=mid-1;
            } else if (currentItem == searchedItem) {
                return mid;
            }
        }
        return -1;
    }
}
