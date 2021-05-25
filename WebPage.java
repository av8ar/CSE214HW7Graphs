/**
 * @author Victor Dai
 * @authorID 113206638
 * @email victor.dai.1@stonybrook.edu
 * @HWNumber 7
 * @course CSE 214
 * @recitation R04
 * @TA James Finn, Matthew Shinder
 */
import java.util.*; //For ArrayList API
/**
 * Represents a hyperlinked document.
 * public WebPage() - constructor (you may include a constructor with parameters)
 *
 * A String member variable:
 * private String url
 *
 * Two int member variables:
 * private int index
 * private int rank
 *
 * A Collection<String> member variable:
 * private Collection<String> keywords
 *
 * public toString() - returns string of data members in tabular form.
 * Note 1: Since we cannot determine the "Links" portion of this WebPage, we
 * will substitute it for a dummy String. For example, we can use "***"
 * (or anything that is unique). A sample result would be:
 *
 *    0   | google.com         |***| search, knowledge, tech
 *
 * In the WebGraph class, when we want to print, we will determine all the Links
 * for the URL "google.com" and can use the String.replace() method to replace
 * your unique String (e.g. ***) with the correct values.
 */
public class WebPage {
    //Member Variables
    private String url;
    private int index;
    private int rank;
    private ArrayList<String> keywords;
    private String links;
    //Default Constructor
    public WebPage(){}

    //Overloaded Constructor
    public WebPage(String url, int index, ArrayList<String> keywords){
        this.url = url;
        this.index = index;
        this.keywords = keywords;
        links = "***";
    }

    //Gets the url of the WebPage
    public String getUrl() {
        return url;
    }
    //Gets the index of WebPage
    public int getIndex() {
        return index;
    }
    //Gets the rank of WebPage
    public int getRank() {
        return rank;
    }
    //Gets the keywords of WebPage
    public ArrayList<String> getKeywords() {
        return keywords;
    }
    //Get the links of WebPage
    public String getLinks() {
        return links;
    }

    //Sets the url of WebPage
    public void setUrl(String url) {
        this.url = url;
    }
    //Sets the index of WebPage
    public void setIndex(int index) {
        this.index = index;
    }
    //Sets the rank of WebPage
    public void setRank(int rank) {
        this.rank = rank;
    }
    //Sets the keywords of WebPage
    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }
    //Sets the links of WebPage
    public void setLinks(String links) {
        this.links = links;
    }

    /**
     * Returns string of data members in tabular form.
     *
     * @return a String of the index, url, rank, and keywords of WebPage
     */
    public String toString() {
        String keywords = "";
        for(int i = 0; i < getKeywords().size(); i++) {
            if(i == getKeywords().size() - 1) keywords += getKeywords().get(i);
            else keywords += getKeywords().get(i) + ", ";
        }

        return String.format("%-4d%3s%-20s%3s%-5d%3s%-20s%3s%-30s",
                index, "|  ",
                url, "|    ", rank, "|  ", links, "|  ", keywords);
    } //end of toString method

} //end of WebPage class
