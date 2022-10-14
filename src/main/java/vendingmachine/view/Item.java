package vendingmachine.view;

public abstract class Item {

    //Variables
    private String name;
    private String price;
    private String vendingSlot;
    private int stock;

    //the abstract Item class is the parent class for Candy, Chip, Drink, and Gum
    //Candy, Chip, Drink, and Gum inherit an automatic initial stock value of 5
    public Item(String name, String price, String vendingSlot) {
        this.name = name;
        this.price = price;
        this.vendingSlot = vendingSlot;
        this.stock = 5;
    }

    //updateStock method is called when an Item is purchased to reduce that Item's stock by 1
    public void updateStock() {
        this.stock = stock - 1;
    }

    //getters below
    //getPrintedSound is overridden in all child Classes of Item with sound that is specific to child Class
    public abstract String getPrintedSound();

    public String getName() {return name; }

    public String getPrice() {
        return price;
    }

    public String getVendingSlot() { return vendingSlot; }

    public int getStock() {
        return stock;
    }

}
