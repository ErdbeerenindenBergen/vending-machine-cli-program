package vendingmachine.view;

import java.io.*;
import java.time.LocalDateTime;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

public class Log {

    //variables below used for formatting dates and currency in writeToLog method
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss");
    NumberFormat currency = NumberFormat.getCurrencyInstance();

    public String writeToLog(double givenSpentOrDeposited, String methodOrItemPurchasedWithSlot, double updatedBalance, File destinationFile) {

        //established printMessage for unit testing
        String printMessage;

        try (PrintWriter myWriter = new PrintWriter(new FileOutputStream(destinationFile.getAbsoluteFile(), true))) {
            //this formula was designed flexibly so that it may be called throughout menu for all interactions with vending machine that alter funds balance in vending machine (funds added, Item purchased, change given)
            myWriter.println(LocalDateTime.now().format(myFormatObj) + " " + methodOrItemPurchasedWithSlot + ": " + currency.format(givenSpentOrDeposited) + " " + currency.format(updatedBalance));
            printMessage = "The log printed successfully.";
        } catch (IOException e) {
            // Could not find the file at the specified path.
            printMessage = "The file was not found.";
            System.out.println(printMessage);
        } return printMessage;
    }

}
