package vendingmachine.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InventoryAndItemTests {

    private Inventory InventoryTest;
    private Chip chip;
    private Drink drink;
    private Gum gum;
    private Candy candy;
    private String filePath;
    private String filePathThatShouldNotWork;


    @Before
    public void setup() {
        InventoryTest = new Inventory();
        chip = new Chip("Doritos", "4.50", "M89");
        drink = new Drink("Yoohoo", "3.00", "Z23");
        gum = new Gum("Bubbleyum", "1.00", "Y2K");
        candy = new Candy("Nerds", "1.50", "G2");
        filePath = "vendingmachine.csv";
        filePathThatShouldNotWork = "abnskjb a:::: asjkdb kahjs";
    }

    @Test
    public void name_Item_test() {
        Assert.assertEquals("Potato Crisps", InventoryTest.inventoryMethod(filePath).get(0).getName());
        Assert.assertEquals("Triplemint", InventoryTest.inventoryMethod(filePath).get(15).getName());
        Assert.assertEquals("Crunchie", InventoryTest.inventoryMethod(filePath).get(7).getName());
    }

    @Test
    public void stock_Item_test() {
        Assert.assertEquals(5, InventoryTest.inventoryMethod(filePath).get(0).getStock());
        Assert.assertEquals(5, InventoryTest.inventoryMethod(filePath).get(15).getStock());
        Assert.assertEquals(5, InventoryTest.inventoryMethod(filePath).get(7).getStock());
    }

    @Test
    public void Class_Item_test() {
        Assert.assertEquals(chip.getClass(), InventoryTest.inventoryMethod(filePath).get(0).getClass());
        Assert.assertEquals(drink.getClass(), InventoryTest.inventoryMethod(filePath).get(9).getClass());
        Assert.assertEquals(gum.getClass(), InventoryTest.inventoryMethod(filePath).get(14).getClass());
        Assert.assertEquals(candy.getClass(), InventoryTest.inventoryMethod(filePath).get(6).getClass());
    }

    @Test
    public void vendingSlot_Item_test() {
        Assert.assertEquals("A1", InventoryTest.inventoryMethod(filePath).get(0).getVendingSlot());
        Assert.assertEquals("C2", InventoryTest.inventoryMethod(filePath).get(9).getVendingSlot());
        Assert.assertEquals("D3", InventoryTest.inventoryMethod(filePath).get(14).getVendingSlot());
    }


    @Test
    public void updateStock_Item_test() {
        chip.updateStock(); Assert.assertEquals(4, chip.getStock());
        drink.updateStock(); Assert.assertEquals(4, drink.getStock());
        candy.updateStock(); Assert.assertEquals(4, candy.getStock());
    }

    @Test
    public void price_Item_test() {
        Assert.assertEquals("1.50", InventoryTest.inventoryMethod(filePath).get(6).getPrice());
        Assert.assertEquals("1.75", InventoryTest.inventoryMethod(filePath).get(7).getPrice());
        Assert.assertEquals("1.25", InventoryTest.inventoryMethod(filePath).get(8).getPrice());
    }

    @Test
    public void InventoryTryCatch_give_incorrectFilePath_throwsException() {
        InventoryTest.inventoryMethod(filePathThatShouldNotWork);
        Assert.assertEquals("Your file was not found.", InventoryTest.getPrintMessage());
    }

}
