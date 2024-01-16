package pages;

public class Page {
    private String type;

    public Page(final String type) {
        this.type = type;
    }


    /**
     * This method prints the page
     */
    public String printPage() {
        return "";
    }

    /**
     * This method sets the type of the page
     * @param type for the type of the page
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * This method gets the type of the page
     * @return type
     */
    public String getType() {
        return this.type;
    }
}
