
package cz.muni.fi.pa165;

import java.time.LocalDateTime;


/**
 *
 * @author Martin Miškeje
 */
public interface DateTimeProvider {
    LocalDateTime provideNow();
}
