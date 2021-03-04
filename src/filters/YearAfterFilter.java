/**
 * A class for filter movie by a year.
 *
 * @author Pavlo Kislov
 * @version 22 Feb 2021
 */
package filters;

import database.MovieDatabase;

public class YearAfterFilter implements Filter {
    private int year;

    public YearAfterFilter(int year) {
        this.year = year;
    }

    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getYear(id) >= year;
    }

}
