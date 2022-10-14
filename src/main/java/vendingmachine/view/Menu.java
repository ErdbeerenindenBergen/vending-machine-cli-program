package vendingmachine.view;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.NumberFormat;

//hold most methods that are called from the application (excepting money, file reading and writing, and the Item classes)
public class Menu {

//*********************************************************************************************************

    //variables
    private PrintWriter out;
    private Scanner in;

    NumberFormat currency = NumberFormat.getCurrencyInstance();
    List<Item> shoppingCart = new ArrayList<>();
    Money money = new Money();
    Log log = new Log();
    File destinationFile = new File("Log.txt");

    //variables below used for unit testing
    List<String> displayMenu = new ArrayList<>();
    double balanceAfterAddingMoney;
    double balanceAfterFinishingTransaction;
    String productNotPurchasedMessage = "";
    String addingMoneyFromUserMessage = "";

    //getters below
    //shopping cart holds all Items that are purchased
    public List<Item> getShoppingCart() {
        return shoppingCart;
    }

    //all getters below are used for unit testing
    public String getAddingMoneyFromUserMessage() {
        return addingMoneyFromUserMessage;
    }

    public List<String> getDisplayMenu() {
        return displayMenu;
    }

    public double getBalanceAfterAddingMoney() {
        return balanceAfterAddingMoney;
    }

    public double getBalanceAfterFinishingTransaction() {
        return balanceAfterFinishingTransaction;
    }

    public String getProductNotPurchasedMessage() {
        return productNotPurchasedMessage;
    }

    //*********************************************************************************************************
    //constructor for Menu
    public Menu(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }

    //loops through entire loaded list of vending machine inventory to print menu items
    public void getAllMenuOptions(List<Item> vendingMachineInventory) {
        for (Item inventoryItem : vendingMachineInventory) {
            System.out.println(inventoryItem.getVendingSlot() + " | $" + inventoryItem.getPrice() + " | " + inventoryItem.getName() + " | Quantity available: " + inventoryItem.getStock());
            displayMenu.add(inventoryItem.getVendingSlot() + " | $" + inventoryItem.getPrice() + " | " + inventoryItem.getName() + " | Quantity available: " + inventoryItem.getStock());
        }
    }

//*********************************************************************************************************

    // methods below are used to get selections from user in the menu and purchase menus

    //getChoiceFromOptions accepts as parameter two arrays: one array to be displayed and one array from which the user can choose
    //returns choice
    public Object getChoiceFromOptions(Object[] options, Object[] displayOptions) {
        Object choice = null;
        while (choice == null) {
            displayMenuOptions(displayOptions);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    //collects choice input from the user
    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.valueOf(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch (NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }
        if (choice == null) {
            out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
        }
        return choice;
    }

    //displays options for user in numbered list
    private void displayMenuOptions(Object[] displayOptions) {
        out.println();
        for (int i = 0; i < displayOptions.length; i++) {
            int optionNum = i + 1;
            out.println(optionNum + ") " + displayOptions[i]);
        }
        out.print(System.lineSeparator() + "Please choose an option >>> ");
        out.flush();
    }

//*********************************************************************************************************

    //addingMoneyFromUser allows user to input a string number, number is parsed for integer and casted as double for calculation
    //feedMoney adds user input integer to current balance in the machine available for use
    public void addingMoneyFromUser() {
        System.out.println("You have a balance of " + currency.format(money.getBalance()) + " available for use." + " Please enter money into the vending machine in whole dollar amounts ($1, $5, $10, etc.): ");

        String userAddsThisMoney = in.nextLine();

        //added try/catch to ensure integer is entered by user
        try {
            int addedMoney = Integer.valueOf(userAddsThisMoney);
            if (addedMoney > 0) {
                Double newMoney = (double) addedMoney;
                money.feedMoney(newMoney);
                balanceAfterAddingMoney = money.getBalance();
                System.out.println("You now have a balance of " + currency.format(money.getBalance()) + " available for use.");
                //successful addingMoneyFromUser methods are written to the log
                log.writeToLog(addedMoney, "FEED MONEY", money.getBalance(), destinationFile);
            }
        } catch (NumberFormatException e) {
            addingMoneyFromUserMessage = "You have not entered a whole dollar amount. You have a balance of " + currency.format(money.getBalance()) + " available for use.";
            out.println(addingMoneyFromUserMessage);
        }
    }

//*********************************************************************************************************

    //when user selects "Purchase" from the main purchase menu in the application, selectProductMethod will run enabling user to purchase product
    //if user enters valid product location, has enough funds, and product is not sold out, selected Item will be purchased
    public String selectProduct(List<Item> vendingMachineInventory) {
        System.out.println("Please select your product from the menu by entering the correct product location (A4, B2, D1, etc.)");
        //collect user input
        String userProductChoice = in.nextLine();

        for (Item inventoryItem : vendingMachineInventory) {
            if (userProductChoice.equals(inventoryItem.getVendingSlot())) {
                if (inventoryItem.getStock() > 0) {
                    if (money.getBalance() >= Double.parseDouble(inventoryItem.getPrice())) {
                        //product purchase is successful!
                        //purchased Item information stored in shoppingCart as Item
                        shoppingCart.add(inventoryItem);
                        //purchase price deducted from balance
                        money.deductPurchasePrice(Double.parseDouble(inventoryItem.getPrice()));
                        //stock of Item is updated
                        inventoryItem.updateStock();
                        //successful purchase message is displayed to user with Item name, purchase price, and remaining balance
                        String productPurchased = "You've purchased " + inventoryItem.getName() + " for " + currency.format(Double.parseDouble(inventoryItem.getPrice())) + "! " + inventoryItem.getPrintedSound() + " You still have " + currency.format(money.getBalance()) + " available for use.";
                        System.out.println(productPurchased);
                        //successful purchases are written to the log
                        log.writeToLog(Double.parseDouble(inventoryItem.getPrice()), inventoryItem.getName() + " " + inventoryItem.getVendingSlot(), money.getBalance(), destinationFile);
                        return productPurchased;
                    } else {
                        //if user does not have available funds (regardless of whether Item is in stock), the error message below is printed
                        productNotPurchasedMessage = "You do not have enough available funds.";
                        break; }
                } else {
                    //if user has enough funds, but the item is sold out, the error message below will print
                    productNotPurchasedMessage = "This item is sold out.";
                    break; }
            } else if (shoppingCart.isEmpty()) {
                //if user has funds but enters incorrect vendingSlot or Item location, the error message below will print
                productNotPurchasedMessage = "You have entered an invalid product location."; } }
        System.out.println(productNotPurchasedMessage);
        return productNotPurchasedMessage;
    }

//***********************************************************************************************************

    //finish transaction method below calls the .getChange method from money to identify proper change in quarters, dimes, nickels and to cash out machine
    public void finishTransaction() {
        double totalDispensed = money.getBalance();
        money.getChange();
        balanceAfterFinishingTransaction = money.getBalance();
        //successful finishTransaction methods are written to the log
        log.writeToLog(totalDispensed, "GIVE CHANGE", money.getBalance(), destinationFile);
    }

//***********************************************************************************************************

}
