/**
 * @author Victor Dai
 * @authorID 113206638
 * @email victor.dai.1@stonybrook.edu
 * @HWNumber 7
 * @course CSE 214
 * @recitation R04
 * @TA James Finn, Matthew Shinder
 */
import java.util.*;
/**
 * Sorts alphabetically ASCENDING based the URL of the WebPage.
 */
public class URLComparator implements Comparator {
    /**
     * Compares 2 WebPage objects and determines which has the URL that comes
     * first alphabetically.
     * @param o1 - Main WebPage object.
     * @param o2 - WebPage object being compared with.
     * @return a positive value if w1 has a URL closer to 'a' in the alphabet,
     * a negative value if w2 has a
     * URL closer to 'a' in the alphabet. (returns the difference in ASCII
     * values)
     */
    public int compare(Object o1, Object o2) {
        WebPage w1 = (WebPage) o1;
        WebPage w2 = (WebPage) o2;
        return (w1.getUrl().compareTo(w2.getUrl()));
    }
}
