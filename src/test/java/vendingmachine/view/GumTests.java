package vendingmachine.view;

import org.junit.Test;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class GumTests {
    Gum gums;

    @Before
    public void setup() {
        gums = new Gum("Chicklets", "0.75", "D3");
    }


    @Test
    public void PrintedSound_ExpectedSound(){
        assertEquals("Chew Chew, Yum!", gums.getPrintedSound());
    }

}
