import java.util.Scanner;

public class Main {
    /*
   args[0] = file path -> "C:/Users/arzup/IdeaProjects/JavaExamples/src/market_data.csv"
   args[1] = Amount of Debt -> 1000
   */
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Please enter all parameters completely.");
            return;
        }

        try {

            String filePath = args[0];
            int amountofdept = Integer.parseInt((args[1]));

            if(amountofdept < 1000 || amountofdept > 15000 || amountofdept%100 != 0){
                System.out.println("The amount of dept must be between 1000 and 1500 and a multiple of 100");
                return;
            }

            Debt debt = new Debt(filePath, amountofdept);
            debt.calculateDebt();

            //Keyboard input
            Scanner scanner = new Scanner(System.in);
            String inputString = scanner.nextLine();

            if (inputString.equals("1")) { // csv update
                debt.update();
                System.out.println("Dept process complete.");
                System.exit(0);

            }
            if (inputString.equals("0")) { // exit
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("Please enter all parameters completely");
        }
    }

}
