package pagecontent;

public class Merch {
    private String name;
    private String description;
    private int price;

    public Merch() {
    }

    /**
     * getter for the name of the merch
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for the name of the merch
     * @param name for the name of the merch
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * getter for the description of the merch
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for the description of the merch
     * @param description for the description of the merch
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * getter for the price of the merch
     * @return price
     */
    public int getPrice() {
        return price;
    }

    /**
     * setter for the price of the merch
     * @param price for the price of the merch
     */
    public void setPrice(final int price) {
        this.price = price;
    }

}
