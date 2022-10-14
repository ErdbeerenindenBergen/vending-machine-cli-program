package vendingmachine.view;

import org.junit.Test;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ChipTests {
    Chip chips;

    @Before
    public void setup() {
        chips = new Chip("Cloud Popcorn", "3.65", "A4");
    }


    @Test
    public void PrintedSound_ExpectedSound(){
        assertEquals("Crunch Crunch, Yum!", chips.getPrintedSound());
    }

}