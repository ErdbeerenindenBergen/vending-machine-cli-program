package vendingmachine.view;

import org.junit.Test;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class DrinkTests {
    Drink drinks;

    @Before
    public void setup() {
        drinks = new Drink("Dr. Salt", "1.50", "C2");
    }

    @Test
    public void PrintedSound_ExpectedSound(){
        assertEquals("Glug Glug, Yum!", drinks.getPrintedSound());
    }
}
