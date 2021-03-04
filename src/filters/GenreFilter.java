/**
 * A class for filter movie by a genre.
 *
 * @author Pavlo Kislov
 * @version 22 Feb 2021
 */
package filters;

import database.MovieDatabase;

public class GenreFilter implements Filter {
    private String genre;

    public GenreFilter(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean satisfies(String id) {
        if (MovieDatabase.getGenres(id).contains(genre)) {
            return true;
        }
        return false;
    }
}
