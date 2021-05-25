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
 * Sorts numerically DESCENDING based on the PageRank of the WebPage.
 */
public class RankComparator implements Comparator {
    /**
     * Compares 2 WebPage objects to determine, which has the lower rank
     * (higher standing).
     * @param o1 - Main WebPage object.
     * @param o2 - WebPage object being compared with.
     * @return 1 if the Page Rank of w1 < Page Rank of w2, -1 if w1 has a
     * rank > w2 and 0 if w1 and w2 have the same rank.
     */
    public int compare(Object o1, Object o2) {
        WebPage w1 = (WebPage) o1;
        WebPage w2 = (WebPage) o2;
        if(w1.getRank() == w2.getRank()) return 0;
        else if(w1.getRank() > w2.getRank()) return -1;
        else return 1;
    }
}
