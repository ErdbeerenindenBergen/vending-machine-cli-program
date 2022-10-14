package vendingmachine.view;

public class Drink extends Item {

    public Drink(String name, String price, String vendingSlot) {
        super(name, price, vendingSlot);
    }

    //Drink overrides parent abstract class, Item, with unique printed sound when purchased
    @Override
    public String getPrintedSound() {
        return "Glug Glug, Yum!";
    }
}