package vendingmachine.view;

import org.junit.Test;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class MoneyTests {
    Money monies;

    @Before
    public void setup() {
        monies = new Money();
    }


    @Test
    public void FeedingFive_ExpectedCorrect(){
        monies.feedMoney(5.0);
        assertEquals(5.0, monies.getBalance(), 0.0001);
    }
    @Test
    public void FeedingTen_ExpectedCorrect(){
        monies.feedMoney(10.0);
        assertEquals(10.0, monies.getBalance(), 0.0001);
    }

    @Test
    public void DeductingPurchasePrice_ExpectedCorrect(){
        monies.feedMoney(8.0);
        monies.deductPurchasePrice(4.50);
        assertEquals(3.50, monies.getBalance(), 0.0001);
    }

    @Test
    public void GetChangeReturnsThreeDollarsFiftyCents_ExpectedCorrect(){
        monies.feedMoney(8.0);
        monies.deductPurchasePrice(4.50);
        assertEquals("You will receive 14 quarters, 0 dimes, 0 nickels.", monies.getChange());
    }

    @Test
    public void GetChangeReturnsFifteenCents_ExpectedCorrect(){
        monies.feedMoney(1.0);
        monies.deductPurchasePrice(0.85);
        assertEquals("You will receive 0 quarters, 1 dimes, 1 nickels.", monies.getChange());
    }

    @Test
    public void GetChangeReturnsTwoDollarsFifteenCents_ExpectedCorrect(){
        monies.feedMoney(4.0);
        monies.deductPurchasePrice(1.85);
        assertEquals("You will receive 8 quarters, 1 dimes, 1 nickels.", monies.getChange());
    }

    @Test
    public void GetChangeReturnsTwoDollars_ResetBalance_ExpectedCorrect(){
        monies.feedMoney(4.0);
        monies.deductPurchasePrice(1.75);
        assertEquals("You will receive 9 quarters, 0 dimes, 0 nickels.", monies.getChange());
        assertEquals(0.0, monies.getBalance(), 0.0001);
    }

    @Test
    public void BalanceIsSetToZero(){
        assertEquals(0.0, monies.getBalance(), 0.0001);
    }

}
