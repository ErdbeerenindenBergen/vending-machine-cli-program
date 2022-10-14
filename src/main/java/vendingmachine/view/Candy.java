package vendingmachine.view;

public class Candy extends Item{

    public Candy(String name, String price, String vendingSlot) {
        super(name, price, vendingSlot);
    }

    //Candy overrides parent abstract class, Item, with unique printed sound when purchased
    @Override
    public String getPrintedSound() {
        return "Munch Munch, Yum!";
    }
}
