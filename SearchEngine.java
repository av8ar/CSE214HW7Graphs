/**
 * @author Victor Dai
 * @authorID 113206638
 * @email victor.dai.1@stonybrook.edu
 * @HWNumber 7
 * @course CSE 214
 * @recitation R04
 * @TA James Finn, Matthew Shinder
 */
import java.lang.reflect.Array;
import java.util.*;
/**
 * Initializes a WebGraph from the appropriate text files and allow the user
 * to search for keywords in the graph.
 */
public class SearchEngine {
    public static final String PAGES_FILE = "pages.txt";
    public static final String LINKS_FILE = "links.txt";
    private WebGraph web;

    //default constructor
    public SearchEngine() {
        web = new WebGraph();
    }

    /**
     * Provide a menu prompt and implement the following menu options:
     * (AP) - Add a new page to the graph.
     * (RP) - Remove a page from the graph.
     * (AL) - Add a link between pages in the graph.
     * (RL) - Remove a link between pages in the graph.
     * (P) - Print the graph.
     *      (I) Sort based on index (ASC)
     *      (U) Sort based on URL (ASC)
     *      (R) Sort based on rank (DSC)
     * (S) - Search for pages with a keyword.
     * (Q) - Quit.
     *
     * Note 1: You should make sure that the graph is sorted by index before
     * making any changes to the structure (failing to do so will lead to
     * unintended bugs). Also, be sure to update all of the PageRanks for pages
     * after any alterations by using the updatePageRanks() method for the graph.
     *
     * Note 2: After choosing the print option, the user should be given another
     * set of menu options. Display the results based on the secondary menu
     * option selected.
     */
    public static void main(String[] args) {
        SearchEngine se = new SearchEngine();
        try {
            se.web = se.web.buildFromFiles(PAGES_FILE, LINKS_FILE);
        }
        catch(IllegalArgumentException i) {
            System.out.println(i.getMessage());
        }
        System.out.println("Loading WebGraph data...\nSuccess!");
        se.web.updatePageRanks();
        //Variables for menu
        Scanner in = new Scanner(System.in);
        boolean quit = false;
        String option = "";
        String url = "";
        String keywords = "";
        String srcURL = "";
        String destURL = "";

        while(!quit) {
           System.out.print("\nMenu:\n\t(AP) - Add a new page to the graph" +
                   ".\n\t(RP) - Remove a page from the graph.\n\t(AL) - Add a" +
                   " link between pages in the graph.\n\t(RL) - Remove a link" +
                   " between pages in the graph.\n\t(P) - Print the graph" +
                   ".\n\t(S) - Search for pages with a keyword.\n\t(Q) - " +
                   "Quit.\n\nPlease select an option: ");
           option = in.nextLine();
           try {
               switch (option.toUpperCase()) {
                   case "AP": //addPage() - adds new WebPage to WebGraph
                       System.out.print("Enter a URL: ");
                       url = in.nextLine();
                       System.out.print("Enter keywords (space-separated): ");
                       keywords = in.nextLine();
                       String[] keys = keywords.split(" ");
                       ArrayList<String> keyList = new ArrayList<String>();
                       for(String k : keys) {
                           keyList.add(k);
                       }
                       se.web.addPage(url, keyList);
                       break;
                   case "RP": //removePage() - removes WebPage with the URL
                       // entered by the user
                       System.out.print("Enter a URL: ");
                       url = in.nextLine();
                       if(se.web.getUrlList().contains(url)) {
                           se.web.removePage(url);
                       }
                       else System.out.println("URL does not exist in the " +
                               "graph.");
                       break;
                   case "AL": //addLink() - adds a link in the edges
                       // adjacency matrix for the WebGraph
                       System.out.print("Enter a source URL: ");
                       srcURL = in.nextLine();
                       System.out.print("Enter a destination URL: ");
                       destURL = in.nextLine();
                       se.web.addLink(srcURL, destURL);
                       break;
                   case "RL": //removeLink() - removes a link in the edges
                       // adjacency matrix for the WebGraph
                       System.out.print("Enter a source URL: ");
                       srcURL = in.nextLine();
                       System.out.print("Enter a destination URL: ");
                       destURL = in.nextLine();
                       se.web.removeLink(srcURL, destURL);
                       break;
                   case "P": //Sorts WebGraph based on user choice and prints
                       // the WebGraph in table format
                       boolean invalid = false;
                       while(!invalid) {
                           System.out.print("\n\t(I) Sort based on index (ASC)" +
                                   "\n\t(U) Sort based on URL (ASC)\n\t(R) Sort " +
                                   "based on rank (DSC)\n\nPlease select an " +
                                   "option: ");
                           option = in.nextLine().toUpperCase();

                           if (option.equals("I")) { //IndexComparator sorts
                               // by Index in ascending order
                               Collections.sort(se.web.getPages(),
                                       new IndexComparator());
                               invalid = true;
                           } else if (option.equals("U")) { //URLComparator
                               // sorts by alphabetical order of URL for each
                               // WebPage in ascending order
                               Collections.sort(se.web.getPages(),
                                       new URLComparator());
                               invalid = true;
                           } else if (option.equals("R")) { //RankComparator
                               // sorts by page rank of WebPage in descending
                               // order
                               Collections.sort(se.web.getPages(),
                                       new RankComparator());
                               invalid = true;
                           }
                           else System.out.println("Not a valid input option. " +
                                       "Try again.");
                       }
                       //accommodating the url list to have the correct order
                       ArrayList<String> modifiedURLList =
                               new ArrayList<String>();
                       for(int i = 0; i < se.web.getPages().size(); i++) {
                           String copyUrl = se.web.getPages().get(i).getUrl();
                           modifiedURLList.add(copyUrl);
                       }
                       se.web.setUrlList(modifiedURLList);
                       System.out.println();
                       //printing sorted table
                       se.web.printTable();
                       break;
                   case "S":
                       System.out.print("Search keyword: ");
                       keywords = in.nextLine().trim(); //One keyword
                       ArrayList<WebPage> filterKey = new ArrayList<WebPage>();
                       String filterTable = String.format("%6s%-2s%7s%-2s%-13s",
                               "Rank", " ", " PageRank", " ", " URL") + "\n" +
                               ("-").repeat(50) + "\n";
                       String table = "";
                       for(WebPage w : se.web.getPages()) {
                           if(w.getKeywords().contains(keywords)) {
                               filterKey.add(w);
                           }
                       }
                       if(filterKey.isEmpty()) System.out.println("\nNo " +
                                       "search results found for the keyword "
                               + keywords);
                       else {
                           Collections.sort(filterKey, new RankComparator());
                           for(int i = 0; i < filterKey.size(); i++) {
                               WebPage w = filterKey.get(i);
                               table += String.format("%3s%-4d%2s%-5d%2s%-13s",
                                       " ", i + 1, " |   ", w.getRank(), "| ",
                                       w.getUrl()) + "\n";
                           }
                           System.out.println("\n" + filterTable + table);
                       }
                       break;
                   case "Q":
                       quit = true;
                       System.out.println("\nGoodbye.");
                       break;
                   default:
                       System.out.println("Not a valid input option. Try " +
                               "again.");
               } //end of switch
           } //end of try block
           catch(IllegalArgumentException i) {
               System.out.println(i.getMessage());
           } //end of catch block
        } //end of loop
    } //end of main method
} //end of SearchEngine Class
