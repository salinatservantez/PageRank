/**
 *Program Description:  This program as a whole uses the Page Rank Algorithm to successfully rank each page in descending order based on incoming links.
 * It does this by prompting the user, reading in the files recursively line by line until it gets to the end of the file, storing and getting the data and references, searching for
 * the keyword, calculating the rank, ordering them, and printing them out to the user.
 *
 * @author: Salina Servantez
 * @edu.uwp.cs.340.section001
 * @edu.uwp.cs.340.assignment1-Page Rank
 * @bugs none
 */

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PageRank {

    static public HashMap<String, PageOrder> webpageRank = new HashMap<String, PageOrder>();

    /**
     * This is the main method that runs the page rank program and prints out the user prompts.
     * @param args
     */

    public static void main(String args[]) {
        try {
            //BufferedReader bufferdReader = Files.newBufferedReader(Paths.get("Simple.txt"));
            Scanner input = new Scanner(System.in);
            System.out.print("Please enter in the input: ");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(input.next())));

            PageRank r = new PageRank();

            r.readFile(bufferedReader);
            calculateRank();

            System.out.print("Enter a keyword: " );
            ArrayList<PageOrder> hits = search(input.next());

            Collections.sort(hits, Collections.reverseOrder());

            for (int i = 0; i < hits.size(); i++) {
                hits.get(i).printInfo();
            }

        } catch (IOException exception) {
            System.out.println("File not found!");
        }


    }

    /**
     * This portion read in the files and stores them recursivley line by line. In the while loop it checks if the first line is
     * not not AND reads page, it then checks if the next line contains the url and so on until it reaches the end of the file.
     * @param bufferedReader bufferedreader reads in the file
     * @return boolean value
     * @throws IOException File not found Exception is thrown to handle the error nicely
     */
    static public boolean readFile(BufferedReader bufferedReader ) throws IOException {

        if ( bufferedReader.readLine().equals("PAGE"))
        {
            readsection(bufferedReader);
        }
        return true;
    }


    static public boolean readsection(BufferedReader bufferedReader ) throws IOException {

        String url = bufferedReader.readLine();
        //System.out.println(url);
        String content = bufferedReader.readLine();
        //System.out.println(content);
        PageOrder thisPage = (webpageRank.containsKey(url)) ? webpageRank.get(url) : new PageOrder();
        thisPage.name = url;
        thisPage.content = content.toLowerCase();
        thisPage.links = new ArrayList<String>();

        String tmp = bufferedReader.readLine();
        while (tmp != null && !tmp.equals("PAGE")) {
            if (!thisPage.links.contains(tmp))
                thisPage.links.add(tmp);
            //System.out.println(tmp);
            tmp = bufferedReader.readLine();
        }

            webpageRank.put(url, thisPage);

        if (tmp == null)
            return true;

        return readsection(bufferedReader);
    }

    /**
     * This method gets the references of each url so they are able to be ranked.
     * @param a
     * @return returns the references list
     */
    static private List<PageOrder> reference (PageOrder a) {

        List<PageOrder> references = new ArrayList<PageOrder>();

        for (String key: webpageRank.keySet()) {

            if(webpageRank.get(key).links.contains(a.name)){
                references.add(webpageRank.get(key));
            }
        }

        return references;
    }

    /**
     *This portion of code uses the page rank algorithm to calculate the rank of each page.
     */
   static public void calculateRank()
    {
        double constant = 0.25;

        for (String key: webpageRank.keySet()) {
            List<PageOrder> references = reference(webpageRank.get(key));
            double sum = 0.0;
            for(int r = 0; r < references.size(); r++)
            {
                double rank = references.get(r).getPageRank();
                double pageReference = references.get(r).links.size();

                sum += (rank /( pageReference));
            }
            webpageRank.get(key).setPageRank(( (1.0 - constant) /webpageRank.size()) + (constant * sum));
            //System.out.println(webpageRank.get(n).getPageRank());
        }
    }

    /**
     * This method searches for the keyword entered by the user.
     * @param find
     * @return returns the array searchfind
     */
    public static ArrayList<PageOrder> search(String find){

        ArrayList<PageOrder> searchFind = new ArrayList();

        for (String key: webpageRank.keySet()) {

            if(webpageRank.get(key).content.contains(find)){
                searchFind.add(webpageRank.get(key));
            }
        }
            return searchFind;

    }

    /**
     *This portion of code compares the rank using comparator to ensure it is in descending order.
     */
    public class pageCompare implements Comparator<PageOrder>{
        @Override
        public int compare(PageOrder p1, PageOrder p2){
            return p1.compareTo(p2);
        }
    }
}



