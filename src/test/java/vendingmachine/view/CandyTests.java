package vendingmachine.view;

import org.junit.Test;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class CandyTests {
    //reference class lecture notes for @Before
    Candy candies;

    @Before
    public void setup() {
        candies = new Candy("Cowtales", "1.50", "B2");
    }


    @Test
    public void PrintedSound_ExpectedSound(){
        assertEquals("Munch Munch, Yum!", candies.getPrintedSound());
    }

}
