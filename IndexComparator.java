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
 * Sorts numerically ASCENDING based on index of the WebPage.
 */
public class IndexComparator implements Comparator {
    /**
     * Compares the indices of 2 WebPage objects and determines which WebPage
     * object has a higher index.
     * @param o1 - The main WebPage object
     * @param o2 - The WebPage object being compared with.
     * @return 1 if o1 (w1) has a higher index, -1 if o1 (w1) has a lower
     * index, and 0 if o1 (w1) has the same index as o2 (w2)
     */
    public int compare(Object o1, Object o2) {
        WebPage w1 = (WebPage) o1;
        WebPage w2 = (WebPage) o2;
        if(w1.getIndex() == w2.getIndex()) return 0;
        else if(w1.getIndex() > w2.getIndex()) return 1;
        else return -1;
    }
}
