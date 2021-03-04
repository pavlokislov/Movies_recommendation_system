/**
 * A class for filter movie by a director.
 *
 * @author Pavlo Kislov
 * @version 22 Feb 2021
 */
package filters;

import database.MovieDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectorsFilter implements Filter {
    List<String> directors;

    public DirectorsFilter(String directors) {
        this.directors = Arrays.asList(directors.split(","));
    }

    @Override
    public boolean satisfies(String id) {
        for (String director : directors) {
            if (MovieDatabase.getDirector(id).contains(director.trim())) {
                return true;
            }
        }
        return false;
    }
}
