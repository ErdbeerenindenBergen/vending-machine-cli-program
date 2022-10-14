package vendingmachine.view;

public class Chip extends Item {

    public Chip(String name, String price, String vendingSlot){
        super(name, price, vendingSlot);
    }

    //Chip overrides parent abstract class, Item, with unique printed sound when purchased
    @Override
    public String getPrintedSound() {
        return "Crunch Crunch, Yum!";
    }
}
