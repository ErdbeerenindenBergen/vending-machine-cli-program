package vendingmachine.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inventory {

    //variable and getter below established for unit testing
    String printMessage;

    public String getPrintMessage() {
        return printMessage;
    }

    //finds file containing information, collects information from file, sorts information into appropriate Item Objects (Candy, Chip, Drink, or Gum) and stores in List (inventoryList)
    public List<Item> inventoryMethod(String filePath) {
        //locates file

        File inventoryInfoSource = new File(filePath);

        //creates list to hold all inventory as Candy, Chip, Drink, or Gum (Items)
        List<Item> inventoryList = new ArrayList<Item>();

        //parse information in file, sort information into appropriate Item-child objects (Candy, Chip, Drink, Gum), store in list
        try(Scanner fileOutput = new Scanner(inventoryInfoSource)) {
            while(fileOutput.hasNextLine()) {
                String inventoryItemInfo = fileOutput.nextLine();
                //each line is stored as an array of Strings
                String[] lineByLine = inventoryItemInfo.split("\\|");

                //this array of strings (lineByLine) will be sorted according to its labeled type: Candy, Chip, Drink, or Gum
                //once string array is sorted by appropriate type (child class), the corresponding constructor will be used
                //each Item's name, price, and vendingSlot (location in machine) is stored via the constructor
                //default total stock is automatically set to 5 via the parent Item class
                if (lineByLine[3].equals("Candy")) {
                    inventoryList.add(new Candy(lineByLine[1], lineByLine[2], lineByLine[0]));
                } else if (lineByLine[3].equals("Chip")) {
                    inventoryList.add(new Chip(lineByLine[1], lineByLine[2], lineByLine[0]));
                } else if (lineByLine[3].equals("Drink")) {
                    inventoryList.add(new Drink(lineByLine[1], lineByLine[2], lineByLine[0]));
                } else {
                    inventoryList.add(new Gum(lineByLine[1], lineByLine[2], lineByLine[0]));
                }
            }
        } catch (FileNotFoundException e) {
            //The system.out.println within the catch is not tested via current unit tests
            printMessage = "Your file was not found.";
            System.out.println(printMessage);
        } return inventoryList;
    }

}
