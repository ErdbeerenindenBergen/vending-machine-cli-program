package vendingmachine;

import vendingmachine.view.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class VendingMachineCLI {

    //See Menu Class for user input/output manipulation and most main methods.
    //See Inventory Class for vending machine information parsing from input file and stocking of vending machine.
    //See Money Class for manipulation of currency and balance.
    //See Log Class for log printing method.
    //See SalesReport Class for SalesReport code.
    //The application has been streamlined so that nearly all methods are located in other classes and only called here to run application.
    //See Item and its child classes Candy, Chip, Drink, and Gum for code concerning purchasable Items in vending machine.

    //All Main Menu options are declared and instantiated below, including an extra hidden menu option for the Main Menu

    //Main Menu includes 4 options: 3 are displayed, 4 are available to the user to choose
    //Said differently: option 4: MAIN_MENU_OPTION_HIDDEN_MENU will never be printed, but a user who knows about the option may enter "4" and generate a printed sales report.
    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String MAIN_MENU_OPTION_HIDDEN_MENU = "Secret Menu Item: Generate Sales Report";

    //Two main menu String arrays are declared and instantiated below:
    //MAIN_MENU_OPTIONS is used to contain the menu options that will be displayed to user
    //MAIN_MENU_OPTIONS_WITH_HIDDEN_MENU is used to contain all options available for user to choose from (including hidden menu item)
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
    private static final String[] MAIN_MENU_OPTIONS_WITH_HIDDEN_MENU = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_HIDDEN_MENU};

    //All Purchase Menu options are declared and instantiated below
    //Purchase Menu includes 3 choices
    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Add money to the machine";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select product for purchase";
    private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish transaction";

    //Unlike Main Menu, there is no hidden menu item in Purchase Menu, so there is only a need for one String array
    //In methods that are used by both Main Menu and Purchase Menu and require two parameters, PURCHASE_MENU_OPTIONS will be used as parameter twice
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

    //variables
    private Menu menu;
    private Inventory inventoryMyList;
    private SalesReport salesReport;
    private List<Item> vendingMachineInventory;
    private String filePath;

    public VendingMachineCLI(Menu menu) {
        this.menu = menu;
    }

    public void run() {

        //variables instantiated
        //Because Classes are stored in a different folder, Class Objects are instantiated below so methods from those classes can be used
        inventoryMyList = new Inventory();
        filePath = "vendingmachine.csv";
        inventoryMyList.inventoryMethod(filePath);
        salesReport = new SalesReport();
        vendingMachineInventory = new ArrayList<Item>(inventoryMyList.inventoryMethod(filePath));

        while (true) {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS_WITH_HIDDEN_MENU, MAIN_MENU_OPTIONS);

            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                // display vending machine items
                menu.getAllMenuOptions(vendingMachineInventory);

            } else if (choice.equals(MAIN_MENU_OPTION_HIDDEN_MENU)) {
                try {
                    salesReport.generateSalesReport(menu.getShoppingCart());
                } catch (IOException e) {
                    System.out.println("Your sales report did not print correctly.");
                }
                //A "break" can be uncommented below so that program auto-exits when user selects "4" and prints Sales Report. Otherwise, report will print when user selects "Exit"
                //break;

            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {

                while(true) {
                    String choiceFromPurchaseMenu = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS, PURCHASE_MENU_OPTIONS);
                    if (choiceFromPurchaseMenu.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                        menu.addingMoneyFromUser();
                    } else if (choiceFromPurchaseMenu.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
                        //displays all menu options
                        menu.getAllMenuOptions(vendingMachineInventory);
                        //select product method enables purchase
                        menu.selectProduct(vendingMachineInventory);
                    } else if (choiceFromPurchaseMenu.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)){
                        //finish transaction method
                        //change is given to user in quarters, dimes, and nickels; balance reset to zero
                        menu.finishTransaction();
                        break;

                    }
                }
            } else {
                break;
            }

        }

    }

    public static void main(String[] args) {

        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();

    }


}
