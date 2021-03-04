/**
 * A class for filter movie by a time.
 *
 * @author Pavlo Kislov
 * @version 22 Feb 2021
 */
package filters;

import database.MovieDatabase;

public class MinutesFilter implements Filter {
    private int min;
    private int max;

    public MinutesFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean satisfies(String id) {
        if (MovieDatabase.getMinutes(id) >= min && MovieDatabase.getMinutes(id) <= max) {
            return true;
        }
        return false;
    }
}
