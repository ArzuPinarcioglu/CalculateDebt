import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.List;

public class Debt {
    static final int lender_index = 0;
    static final int rate_index = 1;
    static final int available_index = 2;
    static final int maturityCount = 36;
    private String filePath;
    private int amountofDept;

    private List<String[]> csvBody;

    public Debt(String filePath, int amountofdept) {
        this.filePath = filePath;
        this.amountofDept = amountofdept;

    }

    void calculateDebt() {

        String resultText = "";

        float totalRate = 0, totalRepayment = 0, MonthlyPayment = 0;
        try {
            CSVReader reader = null;
            reader = new CSVReader(new FileReader(filePath));

            csvBody = reader.readAll();
            boolean isSwapOccured = true;

            while (isSwapOccured) {
                isSwapOccured = false;
                //We sort the data by interest rate using the bubble sort algorithm.
                for (int i = 1; i < csvBody.size() - 1; i++) {
                    if (Float.parseFloat(csvBody.get(i)[rate_index]) > Float.parseFloat(csvBody.get(i + 1)[rate_index])) {
                        String lender_temp = csvBody.get(i)[lender_index];
                        String rate_temp = csvBody.get(i)[rate_index];
                        String available_temp = csvBody.get(i)[available_index];
                        csvBody.get(i)[lender_index] = csvBody.get(i + 1)[lender_index];
                        csvBody.get(i)[rate_index] = csvBody.get(i + 1)[rate_index];
                        csvBody.get(i)[available_index] = csvBody.get(i + 1)[available_index];
                        csvBody.get(i + 1)[lender_index] = lender_temp;
                        csvBody.get(i + 1)[rate_index] = rate_temp;
                        csvBody.get(i + 1)[available_index] = available_temp;
                        isSwapOccured = true;
                    }

                }
            }

            for (int i = 1; i < csvBody.size(); i++) {
                //we complete the debt from user with the lowest interest rate
                if (Integer.parseInt(csvBody.get(i)[available_index]) >= amountofDept) {
                    totalRate = amountofDept * Float.parseFloat(csvBody.get(i)[rate_index]) * (maturityCount / 12);
                    totalRepayment = amountofDept + totalRate;
                    MonthlyPayment = totalRepayment / maturityCount;
                    csvBody.get(i)[available_index] = String.valueOf(Integer.parseInt(csvBody.get(i)[available_index]) - amountofDept);
                    resultText = "Amount of Dept = " + amountofDept + "\n" +
                            "Rate = %" + String.format("%.1f", Float.parseFloat(csvBody.get(i)[rate_index]) * 100) +   "\n" +
                            "Total Repayment = " + String.format("%.2f", totalRepayment) + "\n" +
                            "Monthly Payment = " + String.format("%.2f", MonthlyPayment) + "\n" +
                            "If you accept the offer, type 1 and press Enter.";
                    break;
                }
            }
            if (resultText.equals("")) {
                resultText = "There is no debt suitable for you in the system" + "\n" +
                        "press 0 to exit";
            }
            System.out.println(resultText);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void update() {
        try {
            // Write to CSV file which is open
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}