package vendingmachine.view;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesReport {
    //A sales report is generated showing all Items sold, quantity of Items sold, and total sales
    public void generateSalesReport(List<Item> shoppingCart) throws IOException {

        //the time format below had to be altered in order to save the new file as ":" is not allowed in a file name
        //the time format in generated SalesReports uses "." in place of ":"
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy hh.mm.ss");
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        Map<String, Integer> itemsPurchased = new HashMap<>();
        //file is named with date and time to make each SalesReport unique and avoid overwriting old SalesReports
        String fileName = LocalDateTime.now().format(myFormatObj);
        File file = new File(fileName + " Sales Report.txt");

        //variables declared and instantiated
        double totalSales = 0.00;
        Integer numberPurchased = 0;
        PrintWriter out = new PrintWriter(file);

        //items from the shopping cart list are added to a map below in order to facilitate proper counting/printing in the sales report
        //Map was used to update total of each Item sold and avoid later duplicate printing of item in Sales Report
        for (Item item : shoppingCart) {
            if (!itemsPurchased.containsKey(item.getName())) {
                itemsPurchased.put(item.getName(), 1);
            } else {
                numberPurchased = itemsPurchased.get(item.getName());
                itemsPurchased.put(item.getName(), numberPurchased + 1);
            }
        }

        //loop through shoppingCart list to aggregate total sales
        for(int i = 0; i < shoppingCart.size(); i++) {
            totalSales += Double.parseDouble(shoppingCart.get(i).getPrice());
        }

        //loop through newly generated map to collect total item stock sold and print in expected format in SalesReport
        for (Map.Entry<String, Integer> purchase : itemsPurchased.entrySet()) {
            out.println(purchase.getKey() + "|" + purchase.getValue());
        }
        //total sales is printed at the bottom of the SalesReport
        out.println("**TOTAL SALES** " + currency.format(totalSales));
        out.flush();
        out.close();

    }

}
