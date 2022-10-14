package vendingmachine.view;

public class Gum extends Item {

    public Gum(String name, String price, String vendingSlot) {
        super(name, price, vendingSlot);
    }

    //Gum overrides parent abstract class, Item, with unique printed sound when purchased
    @Override
    public String getPrintedSound() {
        return "Chew Chew, Yum!";
    }
}
