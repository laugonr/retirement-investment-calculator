import java.util.Scanner;

public class RetirementCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Retirement Growth Simulator");
        System.out.println("...........................");

        boolean runAgain = true;
        while (runAgain) {
            int currentAge = readIntInRange(sc, "Enter current age (18-100): ", 18, 100);
            int retirementAge = readIntInRange(sc, "Enter retirement age: ", currentAge + 1, 100);
            double currentBalance = readDoubleInRange( sc,"Enter current balance (>= 0): ", 0, Double.MAX_VALUE);
            double annualContribution = readDoubleInRange(sc, "Enter annual contribution (>= 0): ", 0, Double.MAX_VALUE);
            double aprPercent = readDoubleInRange(sc, "Enter annual interest rate (0-30) (APR %): ", 0, 30);
            double contributionIncreasePercent =readDoubleInRange(sc, "Enter annual contribution increase (0-20) (%): ", 0, 20);
            int compChoice = readCompoundingChoice(sc);


            System.out.println("You entered current age: " + currentAge);
            System.out.println("You entered retirement age: " + retirementAge);
            System.out.println("Current balance: " + currentBalance);
            System.out.println("Annual contribution: " + annualContribution);
            System.out.println("APR (%): " + aprPercent);
            System.out.println("Contribution increase (%): " + contributionIncreasePercent);
            System.out.println("Compounding choice: " + compChoice);
            System.out.print("Run another simulation? (y/n): ");
            String answer = sc.next().trim().toLowerCase();
            runAgain = answer.equals("y") || answer.equals("yes");
        }

        sc.close();
    }

    public static boolean askRunAgain(Scanner sc) {
        while (true) {
            System.out.print("Run another simulation? (Y/N): ");
            String answer = sc.nextLine().trim().toLowerCase();

            if (answer.equals("y") || answer.equals("yes")) return true;
            if (answer.equals("n") || answer.equals("no")) return false;

            System.out.println("Please enter Y or N.");
        }
    }

    public static int readIntInRange(Scanner sc, String prompt, int min, int max) {
        int value = 0;
        boolean valid = false;

        do {
            System.out.print(prompt);

            try {
                value = sc.nextInt();
                sc.nextLine(); // consume leftover newline

                if (value >= min && value <= max) {
                    valid = true;
                } else {
                    System.out.println("Input must be between " + min + " and " + max + ".");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a whole number.");
                sc.nextLine(); // clear bad token/line
            }

        } while (!valid);

        return value;
    }

    public static double readDoubleInRange(Scanner sc, String prompt, double min, double max) {
        double value = 0;
        boolean valid = false;

        do {
            System.out.print(prompt);

            try {
                value = sc.nextDouble();
                sc.nextLine(); // consume leftover newline

                if (value >= min && value <= max) {
                    valid = true;
                } else {
                    System.out.println("Input must be between " + min + " and " + max + ".");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // clear bad token/line
            }

        } while (!valid);

        return value;
    }
    public static int readCompoundingChoice(Scanner sc) {
        while (true) {
            System.out.println("Compounding frequency:");
            System.out.println("1 = Annually");
            System.out.println("2 = Monthly");
            System.out.println("3 = Daily (365)");
            System.out.print("Enter choice (1-3): ");

            String input = sc.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 3) {
                    return choice;
                }
            } catch (Exception e) {
                // ignore, fall through to error message
            }

            System.out.println("Invalid selection. Please enter 1, 2, or 3.");
        }
    }

}
