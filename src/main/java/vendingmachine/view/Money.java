package vendingmachine.view;

import java.text.NumberFormat;

public class Money {

    //Variables
    public static final int NICKEL_AMOUNT = 5;
    public static final int DIME_AMOUNT = 10;
    public static final int QUARTER_AMOUNT = 25;
    int totalNickelsBack = 0;
    int totalDimesBack = 0;
    int totalQuartersBack = 0;
    String getChangeMessage = "";

    double balance = 0.0;
    int integerBalance;
    NumberFormat currency = NumberFormat.getCurrencyInstance();

    //getters
    public double getBalance() {
        return balance;
    }

    //feedMoney adds user-added money to balance
    public void feedMoney(Double newMoney) {
        this.balance = balance + newMoney;
    }

    //deductPurchasePrice deducts the purchase price of an item when item is purchased
    public void deductPurchasePrice(Double purchasePrice) {
        this.balance = balance - purchasePrice;
    }

    //getChange returns change of remaining balance to users in the least number of quarters, nickels, and dimes possible
    public Object getChange() {
        integerBalance = (int) (balance*100);
        while (integerBalance > 0) {
            //calculating with integer to prevent computer math error
            if (integerBalance >= QUARTER_AMOUNT) {
                totalQuartersBack++;
                integerBalance -= QUARTER_AMOUNT;
            } else if (integerBalance >= DIME_AMOUNT) {
                totalDimesBack++;
                integerBalance -= DIME_AMOUNT;
            } else {
                totalNickelsBack++;
                integerBalance -= NICKEL_AMOUNT;
            }
        }
        getChangeMessage = "You will receive "+ totalQuartersBack + " quarters, " + totalDimesBack + " dimes, " + totalNickelsBack + " nickels.";
        this.balance = 0;
        System.out.println(getChangeMessage);
        System.out.println("Remaining balance in machine: " + currency.format(this.balance));

        //added this return to get MoneyTests.java (line 35) to pass. -- mikey
        return getChangeMessage;
    }
}
