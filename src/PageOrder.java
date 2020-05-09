import java.util.ArrayList;

/**
 * This class PageOrder contains getters and setters for the needed variables. It implements comparable in order to compare ranks of the Urls
 * allowing it to properly order the data.
 */

public class PageOrder implements Comparable<PageOrder> {

public String name;
public String content;
public ArrayList<String> links;
private double pageRank;
private double updatedRank;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPageRank() {
        return pageRank;
    }

    public void setPageRank(double pageRank) {
        this.pageRank = pageRank;
    }

    public double getUpdatedRank() {
        return updatedRank;
    }

    public void setUpdatedRank(double updatedRank) {
        this.updatedRank = updatedRank;
    }

    public void printInfo() {
        System.out.print("PAGE RANK: " + pageRank + " ");
        System.out.println("URL: " + name);
    }

    /**
     * This method overrides to compare one rank with another and put them into descending order
     * @param p2
     * @return returns 0 if it meets neither condition
     */
    @Override
    public int compareTo(PageOrder p2){
        double result = this.pageRank - p2.pageRank;

        if(result > 0)
            return 1;
        else if(result < 0)
            return -1;
        else
            return 0;
    }
}
