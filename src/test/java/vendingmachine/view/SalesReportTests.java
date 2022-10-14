package vendingmachine.view;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SalesReportTests {

    //using ByteArrayOutputStream to catch the string output
    private ByteArrayOutputStream output;
    private SalesReport SalesReportTest;
    private List<Item> shoppingCartTest;
    Inventory toLoadShoppingCart;
    Gum gum = new Gum("Bubbleyum", "1.00", "Y2K");
    Candy candy = new Candy("Nerds", "1.50", "G2");

    @Before
    public void setup() {
        output = new ByteArrayOutputStream();
        SalesReportTest = new SalesReport();
        toLoadShoppingCart = new Inventory();
        String filePath = "src/target/vendingmachine.csv";
        toLoadShoppingCart.inventoryMethod(filePath);
        toLoadShoppingCart.inventoryMethod(filePath);
        shoppingCartTest = new ArrayList<>(toLoadShoppingCart.inventoryMethod(filePath));
        shoppingCartTest.add(gum);
        shoppingCartTest.add(gum);
        shoppingCartTest.add(candy);
    }

    @Test
    public void generateSalesReport_Test() throws IOException {
        SalesReportTest.generateSalesReport(shoppingCartTest);
    }
}

