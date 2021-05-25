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
import java.io.*; //For File Reader
/**
 * Organizes the WebPage objects as a directed graph.
 */
public class WebGraph {
    //Member Variables
    public static final int MAX_PAGES = 40; //Max Number of Pages
    private ArrayList<WebPage> pages;
    private int[][] edges;
    private ArrayList<String> urlList;
    //Default Constructor
    public WebGraph(){
        pages = new ArrayList<WebPage>();
        edges = new int[MAX_PAGES][MAX_PAGES];
        urlList = new ArrayList<String>();
    }
    //Gets pages of WebGraph
    public ArrayList<WebPage> getPages() {
        return pages;
    }
    //Gets edges of WebGraph
    public int[][] getEdges() {
        return edges;
    }
    //Sets pages of WebGraph
    public void setPages(ArrayList<WebPage> pages) {
        this.pages = pages;
    }
    //Sets edges of WebGraph
    public void setEdges(int[][] edges) {
        this.edges = edges;
    }
    //Gets the all of the URL in an ordered index list
    public ArrayList<String> getUrlList() {
        return urlList;
    }
    //Sets the list of URLs
    public void setUrlList(ArrayList<String> urlList) {
        this.urlList = urlList;
    }

    /**
     * Constructs a WebGraph object using the indicated files as the source
     * for pages and edges.
     *
     * <dt> Preconditions:
     * Both parameters reference text files which exist.
     * The files follow proper format as outlined in the "Reading Graph from
     * File" section below. </dt>
     *
     * <dt> Postconditions:
     * A WebGraph has been constructed and initialized based on the text
     * files. </dt>
     *
     * @param pagesFile - String of the relative path to the file containing
     *                  the page information.
     * @param linksFile - String of the relative path to the file containing
     *                  the link information
     *
     * @return The WebGraph constructed from the text files.
     *
     * @throws IllegalArgumentException: Thrown if either of the files does
     * not reference a valid text file, or if the files are not formatted
     * correctly.
     *
     */
    public static WebGraph buildFromFiles(String pagesFile, String
     linksFile) throws IllegalArgumentException {
        Scanner pageReader; //Initialization of page reader
        Scanner linkReader; //Initialization of link reader
        try {
            pageReader = new Scanner(new File(pagesFile));
            linkReader = new Scanner(new File(linksFile));
        }
        catch(FileNotFoundException f) { //For Invalid Files
            throw new IllegalArgumentException("Error: Files are invalid or " +
                    "incorrectly formatted.");
        }

        WebGraph graph = new WebGraph();
        int webPageIndex = 0;

        while(pageReader.hasNextLine()) {
            String line = pageReader.nextLine().trim();
            String url = line.substring(0,line.indexOf(" "));
            ArrayList<String> keywords = new ArrayList<String>();
            String[] keys = line.substring(line.indexOf(" ")+1).split(" ");
            for(int i = 0; i < keys.length; i++) {
                keywords.add(keys[i]);
            }
            graph.getPages().add(new WebPage(url,webPageIndex++,keywords));
        }

        int[][] links = new int[MAX_PAGES][MAX_PAGES];

        while(linkReader.hasNextLine()) {
            String line = linkReader.nextLine().trim();
            String src = line.substring(0,line.indexOf(" "));
            String dest = line.substring(line.indexOf(" ") + 1);
            int indexOfSrc = 0, indexOfDest = 0;
            for(WebPage w : graph.getPages()) {
                if(w.getUrl().equals(src)) indexOfSrc = w.getIndex();
                if(w.getUrl().equals(dest)) indexOfDest = w.getIndex();
            }
            links[indexOfSrc][indexOfDest] = 1;
        }
        graph.setEdges(links);

        for(WebPage w : graph.getPages()) {
            graph.getUrlList().add(w.getUrl());
        }

        return graph;
    } //end of buildFromFiles method

    /**
     * Adds a page to the WebGraph
     *
     * <dt> Preconditions:
     * url is unique and does not exist as the URL of a WebPage already in the
     * graph.
     * url and keywords are not null. </dt>
     *
     * <dt> Postconditions:
     * The page has been added to pages at index 'i' and links has been
     * logically extended to include the new row and column indexed by 'i'.
     * </dt>
     *
     * @param url - The URL of the webpage (must not already exist in the
     *            WebGraph).
     * @param keywords - The keywords associated with the WebPage.
     *
     * @throws IllegalArgumentException: If url is not unique and already
     * exists in the graph, or if either argument is null.
     */
    public void addPage(String url, ArrayList<String> keywords)
            throws IllegalArgumentException{
        if(url.isEmpty() || keywords.isEmpty())
            throw new IllegalArgumentException("Null value for url or " +
                    "keywords.");
        for(WebPage w : pages) {
            if(w.getUrl().equals(url))
                throw new IllegalArgumentException("Error: " + url + " " +
                        "already exists in the WebGraph. Could not add new " +
                        "WebPage.");
        }
        WebPage newPage = new WebPage(url, pages.size(), keywords);
        pages.add(newPage);
        System.out.println("\n" + url + " successfully added " +
                "to the WebGraph!");
        urlList.add(url);
        updatePageRanks();
    } //end of addPage method

    /**
     * Adds a link from the WebPage with the URL indicated by source to the
     * WebPage with the URL indicated by destination.
     *
     * <dt> Preconditions:
     * Both parameters reference WebPages which exist in the graph. </dt>
     *
     * @param source - the URL of the page which contains the hyperlink to
     *               destination.
     * @param destination - the URL of the page which the hyperlink points to.
     *
     * @throws IllegalArgumentException: If either of the URLs are null or
     * could not be found in pages.
     */
    public void addLink(String source, String destination)
            throws IllegalArgumentException {
        if(source.isEmpty() || destination.isEmpty())
            throw new IllegalArgumentException("\nNull value for source or " +
                    "destination links.");
        int indexOfSrc = urlList.indexOf(source), indexOfDest =
                urlList.indexOf(destination);

        if(indexOfSrc < 0 && indexOfDest < 0)
            throw new IllegalArgumentException("\nError: " + source + " and " +
                    destination + " could not be found in the WebGraph.");
        else if(indexOfSrc < 0)
            throw new IllegalArgumentException("\nError: " + source + " could" +
                    " " +
                    "not be found in the WebGraph.");
        else if(indexOfDest < 0)
            throw new IllegalArgumentException("\nError: " + destination + " " +
                    "could not be found in the WebGraph.");
        else { //Adding Link
            edges[indexOfSrc][indexOfDest] = 1;
            System.out.println("\nLink successfully added from " + source
                    + " to " + destination + "!");
        }
        updatePageRanks();
    } //end of addLink method

    /**
     * Removes the WebPage from the graph with the given URL.
     *
     * <dt> Postconditions:
     * The WebPage with the indicated URL has been removed from the graph, and
     * it's corresponding row and column has been removed from the adjacency
     * matrix.
     * All pages that has an index greater than the index that was removed
     * should decrease their index value by 1.
     * If url is null or could not be found in pages, the method ignores the
     * input and does nothing. </dt>
     *
     * @implNote When the page is removed, it's corresponding row and column
     * must be removed from the adjacency matrix. This can be accomplished by
     * copying links[k][j+1] to links[k][j] and links[j+1][k] to links[j][k] for
     * 0 ≤ k < size(pages) and i ≤ j < size(pages)-1
     *
     * @param url - The URL of the page to remove from the graph.
     */
    public void removePage(String url) {
        //Remove WebPage with url from pages
        int index = urlList.indexOf(url);
        pages.remove(index);
        urlList.remove(index);
        for(int i = 0; i < pages.size(); i++) {
            WebPage w = pages.get(i);
            if(w.getIndex() > index) {
                w.setIndex(w.getIndex() - 1);
            }
        }
        //Modify edges for this change by removing the index-th column and row
        // of edges
        //Remove row
        for(int i = index; i < pages.size() + 1; i++) {
            for(int j = 0; j < pages.size(); j++) {
                edges[i][j] = edges[i+1][j];
            }
        }
        //Remove column
        for(int i = 0; i < pages.size(); i++) {
            for(int j = index; j < pages.size() + 1; j++) {
                edges[i][j] = edges[i][j+1];
            }
        }
        System.out.println("\n" + url + " has been removed " +
                "from the graph!");
        updatePageRanks();
    } //end of removePage method

    /**
     * Removes the link from WebPage with the URL indicated by source to the
     * WebPage with the URL indicated by destination.
     *
     * <dt> Postconditions:
     * The entry in links for the specified hyperlink has been set to 0 (no link).
     * If either of the URLs could not be found, the input is ignored and the
     * method does nothing. </dt>
     *
     * @param source - The URL of the WebPage to remove the link.
     * @param destination - The URL of the link to be removed.
     */
    public void removeLink(String source, String destination) {
        int sourceIndex = urlList.indexOf(source), destinationIndex =
                urlList.indexOf(destination);
        if(sourceIndex >= 0 && destinationIndex >= 0) {
            edges[sourceIndex][destinationIndex] = 0;
            System.out.println("\nLink removed from " + source + " to " +
                    destination + "!");
            updatePageRanks();
        }
    } //end of removeLink method

    /**
     * Calculates and assigns the PageRank for every page in the WebGraph (see
     * the PageRank Algorithm section for further detail).
     *
     * <dt> Postconditions:
     * All WebPages in the graph have been assigned their proper PageRank. </dt>
     *
     * @implNote This operation should be performed after ANY alteration of the
     * graph structure (adding/removing a link, adding/removing a page).
     */
    public void updatePageRanks() {
        int pageRank = 0;
        for(int i = 0; i < pages.size(); i++) {
            pageRank = 0;
            for(int j = 0; j < pages.size(); j++) {
                if(edges[j][i] == 1) pageRank += 1;
            }
            pages.get(i).setRank(pageRank);
        }
    } //end of updatePageRanks method

    /**
     * Prints the WebGraph in tabular form.
     */
    public void printTable() {
        String header = String.format("%4s%3s%-20s%1s%-7s%3s%-20s%3s%-30s",
                "Index", " ", "URL", " ", "PageRank", " ", "Links", " ",
                "Keywords\n" + ("-").repeat(110) + "\n");
        String list = "";
        for(int i = 0; i < pages.size(); i++) {
            WebPage w = pages.get(i);
            String links = "";
            for(int j = 0; j < pages.size(); j++) {
                if(edges[w.getIndex()][j] == 1) links += j + ", ";
            }
            links = links.trim();
            if(!links.isEmpty() && links.substring(links.length() - 1).equals(
                    ",")) links = links.substring(0, links.length() - 1);
            w.setLinks(links);
            list += w.toString() + "\n";
        }
        System.out.print(header + list);
    } //end of printTable method

    /** Prints visual of edges 2D array- for testing use
    public void printEdges() {
        String row = "";
        for(int i = 0; i < pages.size(); i++) {
            row = "";
            for(int j = 0; j < pages.size(); j++) {
                row += edges[i][j] + " ";
            }
            System.out.println(row);
        }
    }
    */
} //end of WebGraph Class
