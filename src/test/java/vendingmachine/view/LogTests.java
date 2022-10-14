package vendingmachine.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class LogTests {

    private Log TestLog;

    @Before
    public void setup() {
        TestLog = new Log();
    }

    @Test
    public void checkingTheLog_given_correctFilePath_expect_logToBeWritten() {
        File destinationFile = new File("Log.txt");
        TestLog.writeToLog(3.50, "pretend method for test", 4.50, destinationFile);
        TestLog.writeToLog(5.0, "Another pretender", 0.00, destinationFile);
    }

    @Test
    public void logTryCatch_given_incorrectFilePathGiven_throwsAnException() {
        File destinationFile = new File("C/hasd ");
        TestLog.writeToLog(5.50, "pretend method for test", 8.50, destinationFile);
        TestLog.writeToLog(8.0, "Another pretender", 3.00, destinationFile);
        Assert.assertEquals("The file was not found.", TestLog.writeToLog(5.50, "pretend method for test", 8.50, destinationFile));
    }
}
