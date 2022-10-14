package vendingmachine.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MenuTests {

    private ByteArrayOutputStream output;
    private Inventory InventoryTest;
    private List<Item> vendingMachineInventoryTest;
    private List<Item> shoppingCartTest;
    private Money money;
    private Log logTest;
    private Inventory toLoadShoppingCart;
    private File destinationFile;
    private String filePath;

    @Before
    public void setup() {
        output = new ByteArrayOutputStream();
        InventoryTest = new Inventory();
        filePath = "vendingmachine.csv";
        InventoryTest.inventoryMethod(filePath);
        money = new Money();
        logTest = new Log();
        toLoadShoppingCart = new Inventory();
        vendingMachineInventoryTest = new ArrayList<>(InventoryTest.inventoryMethod(filePath));
        destinationFile = new File("Log.txt");
    }

    private Menu getMenuForTestingWithUserInput(String userInput) {
        ByteArrayInputStream input = new ByteArrayInputStream(String.valueOf(userInput).getBytes());
        return new Menu(input, output);
    }

    private Menu getMenuForTesting() {
        return getMenuForTestingWithUserInput("1" + System.lineSeparator());
    }

    @Test
    public void addingMoneyFromUser() {
        Menu menu = getMenuForTestingWithUserInput("5" + System.lineSeparator());
        menu.addingMoneyFromUser();
        double expected = 5.0;

        Assert.assertEquals(expected, menu.getBalanceAfterAddingMoney(), 0.001);
    }

    @Test
    public void addingMoneyFromUser_TrippingTryCatchClause_Test() {
        Menu menu = getMenuForTestingWithUserInput("gh" + System.lineSeparator());
        menu.addingMoneyFromUser();

        Assert.assertEquals("You have not entered a whole dollar amount. You have a balance of $0.00 available for use.", menu.getAddingMoneyFromUserMessage());
    }

    @Test
    public void getAllMenuOptions_Test() {
        Menu menu = getMenuForTesting();

        menu.getAllMenuOptions(vendingMachineInventoryTest);

        List<String> displayMenuTest = Arrays.asList("A1 | $3.05 | Potato Crisps | Quantity available: 5",
                "A2 | $1.45 | Stackers | Quantity available: 5",
                "A3 | $2.75 | Grain Waves | Quantity available: 5",
                "A4 | $3.65 | Cloud Popcorn | Quantity available: 5",
                "B1 | $1.80 | Moonpie | Quantity available: 5",
                "B2 | $1.50 | Cowtales | Quantity available: 5",
                "B3 | $1.50 | Wonka Bar | Quantity available: 5",
                "B4 | $1.75 | Crunchie | Quantity available: 5",
                "C1 | $1.25 | Cola | Quantity available: 5",
                "C2 | $1.50 | Dr. Salt | Quantity available: 5",
                "C3 | $1.50 | Mountain Melter | Quantity available: 5",
                "C4 | $1.50 | Heavy | Quantity available: 5",
                "D1 | $0.85 | U-Chews | Quantity available: 5",
                "D2 | $0.95 | Little League Chew | Quantity available: 5",
                "D3 | $0.75 | Chiclets | Quantity available: 5",
                "D4 | $0.75 | Triplemint | Quantity available: 5");

        Assert.assertEquals(displayMenuTest, menu.getDisplayMenu());
    }

    @Test
    public void finishTransaction_Test() {
        Menu menu = getMenuForTesting();
        menu.finishTransaction();
        Assert.assertEquals("You will receive 0 quarters, 0 dimes, 0 nickels.", money.getChange());
        Assert.assertEquals(0.0, menu.getBalanceAfterFinishingTransaction(), 0.001);
    }

    @Test
    public void selectProductMethod_given_notEnoughFunds_expect_emptyList(){
        shoppingCartTest = new ArrayList<>();

        Menu menu = getMenuForTestingWithUserInput("A1" + System.lineSeparator());
        menu.selectProduct(vendingMachineInventoryTest);

        Assert.assertEquals(shoppingCartTest, menu.getShoppingCart());
        Assert.assertEquals("You do not have enough available funds.", menu.getProductNotPurchasedMessage());
    }

    @Test
    public void selectProductMethod_given_notEnoughStock_expect_emptyList() {
        Chip itemAtZ1 = new Chip("Potato Crisps", "0.0", "Z1");
        vendingMachineInventoryTest.add(itemAtZ1);
        vendingMachineInventoryTest.get(16).updateStock();
        vendingMachineInventoryTest.get(16).updateStock();
        vendingMachineInventoryTest.get(16).updateStock();
        vendingMachineInventoryTest.get(16).updateStock();
        vendingMachineInventoryTest.get(16).updateStock();
        shoppingCartTest = new ArrayList<>();

        Menu menu = getMenuForTestingWithUserInput("Z1" + System.lineSeparator());
        menu.selectProduct(vendingMachineInventoryTest);

        Assert.assertEquals(shoppingCartTest, menu.getShoppingCart());
        Assert.assertEquals("This item is sold out.", menu.getProductNotPurchasedMessage());
    }

    @Test
    public void selectProductMethod_given_incorrectLocationInput_expect_emptyList_Test() {
        shoppingCartTest = new ArrayList<>();

        Menu menu = getMenuForTestingWithUserInput("M1" + System.lineSeparator());
        menu.selectProduct(vendingMachineInventoryTest);

        Assert.assertEquals(shoppingCartTest, menu.getShoppingCart());
        Assert.assertEquals("You have entered an invalid product location.", menu.getProductNotPurchasedMessage());
    }

    @Test
    public void selectProductMethod_given_enoughFunds_itemInStock_correctItemLocationInput_expected_itemAddedToShoppingCart() {
        Chip itemAtZ1 = new Chip("Potato Crisps", "0.0", "Z1");
        vendingMachineInventoryTest.add(itemAtZ1);
        shoppingCartTest = new ArrayList<>();
        shoppingCartTest.add(itemAtZ1);

        Menu menu = getMenuForTestingWithUserInput("Z1" + System.lineSeparator());
        menu.selectProduct(vendingMachineInventoryTest);

        Assert.assertEquals(shoppingCartTest, menu.getShoppingCart());
    }

    @Test
    public void displays_a_list_of_menu_options_and_prompts_user_to_make_a_choice() {
        Object[] options = new Object[] { Integer.valueOf(4), "Blind", "Mice" , "Run"};
        Object[] displayOptions = new Object[] { Integer.valueOf(3), "Blind", "Mice" };
        Menu menu = getMenuForTesting();

        menu.getChoiceFromOptions(options, displayOptions);

        String expected = System.lineSeparator() + "1) " + displayOptions[0].toString() + System.lineSeparator() + "2) " + displayOptions[1].toString() + System.lineSeparator() + "3) "
                + displayOptions[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";
        Assert.assertEquals(expected, output.toString());
    }

    @Test
    public void returns_object_corresponding_to_user_choice() {
        Integer expected = Integer.valueOf(456);
        Integer[] options = new Integer[] { Integer.valueOf(123), expected, Integer.valueOf(789) };
        Object[] displayOptions = new Object[] { Integer.valueOf(3), "Blind", "Mice" };
        Menu menu = getMenuForTestingWithUserInput("2" + System.lineSeparator());

        Integer result = (Integer) menu.getChoiceFromOptions(options, displayOptions);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void redisplays_menu_if_user_does_not_choose_valid_option() {
        Object[] options = new Object[] {"Larry", "Curly", "Moe" , "Another"};
        Object[] displayOptions = new Object[] { "Larry", "Curly", "Moe" };
        Menu menu = getMenuForTestingWithUserInput("5" + System.lineSeparator() + "1" + System.lineSeparator());

        menu.getChoiceFromOptions(options, displayOptions);

        String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
                + options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

        String expected = menuDisplay + System.lineSeparator() + "*** 5 is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay;

        Assert.assertEquals(expected, output.toString());
    }

    @Test
    public void redisplays_menu_if_user_chooses_option_less_than_1() {
        Object[] options = new Object[] { "Larry", "Curly", "Moe", "Francesca"};
        Object[] displayOptions = new Object[] { "Larry", "Curly", "Moe" };
        Menu menu = getMenuForTestingWithUserInput("0" + System.lineSeparator() + "1" + System.lineSeparator());

        menu.getChoiceFromOptions(options, displayOptions);

        String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
                + options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

        String expected = menuDisplay + System.lineSeparator() + "*** 0 is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay;

        Assert.assertEquals(expected, output.toString());
    }

    @Test
    public void redisplays_menu_if_user_enters_garbage() {
        Object[] options = new Object[] { "Larry", "Curly", "Moe" };
        Object[] displayOptions = new Object[] { "Larry", "Curly", "Moe" };
        Menu menu = getMenuForTestingWithUserInput("Mickey Mouse" + System.lineSeparator() + "1" + System.lineSeparator());

        menu.getChoiceFromOptions(options, displayOptions);

        String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
                + options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

        String expected = menuDisplay + System.lineSeparator() + "*** Mickey Mouse is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay;

        Assert.assertEquals(expected, output.toString());
    }

}
