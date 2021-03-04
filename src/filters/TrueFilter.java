/**
 * A class for show all movies without a filter.
 *
 * @author Pavlo Kislov
 * @version 22 Feb 2021
 */
package filters;

public class TrueFilter implements Filter {
    @Override
    public boolean satisfies(String id) {
        return true;
    }

}
