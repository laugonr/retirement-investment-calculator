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
            runSimulation(
            	    currentAge,
            	    retirementAge,
            	    currentBalance,
            	    annualContribution,
            	    aprPercent,
            	    compChoice,
            	    contributionIncreasePercent
            	);

           
            runAgain = askRunAgain(sc);

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
    
    public static void runSimulation(
            int currentAge,
            int retirementAge,
            double startingBalance,
            double annualContribution,
            double aprPercent,
            int compChoice,
            double contributionIncreasePercent
    ) {
        // Convert compChoice to periods per year + label
        int periodsPerYear;
        String compoundingLabel;

        if (compChoice == 1) {
            periodsPerYear = 1;
            compoundingLabel = "Annually";
        } else if (compChoice == 2) {
            periodsPerYear = 12;
            compoundingLabel = "Monthly";
        } else {
            periodsPerYear = 365;
            compoundingLabel = "Daily";
        }

        double ratePerPeriod = (aprPercent / 100.0) / periodsPerYear;

        // Print header block (matches professor style)
        System.out.println();
        System.out.println("Retirement Growth Simulator");
        System.out.println("----------------------------");
        System.out.println();
        System.out.println("Current Age: " + currentAge);
        System.out.println("Retirement Age: " + retirementAge);
        System.out.printf("Annual Rate: %.2f%%%n", aprPercent);
        System.out.println("Compounding: " + compoundingLabel);
        System.out.printf("Annual Contribution (Year 1): $%.2f%n", annualContribution);
        System.out.printf("Annual Contribution Increase: %.2f%%%n", contributionIncreasePercent);
        System.out.println();

        // Table header (matches example)
        System.out.println("Year-by-Year Projection");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Age | Start Balance     | Contributions     | Interest Earned   | End Balance");
        System.out.println("--------------------------------------------------------------------------");

        double balance = startingBalance;
        double yearlyContribution = annualContribution;

        double totalContributed = 0.0;
        double totalInterestEarned = 0.0;

        for (int age = currentAge; age < retirementAge; age++) {

            double startBalance = balance;
            double contributionsThisYear = 0.0;

            double contributionPerPeriod = yearlyContribution / periodsPerYear;

            for (int p = 0; p < periodsPerYear; p++) {
                // deposit
                balance += contributionPerPeriod;
                contributionsThisYear += contributionPerPeriod;

                // interest
                balance = balance * (1.0 + ratePerPeriod);
            }

            double endBalance = balance;

            double interestEarnedThisYear = endBalance - startBalance - contributionsThisYear;

            totalContributed += contributionsThisYear;
            totalInterestEarned += interestEarnedThisYear;

            // Age at end of year is age + 1 (matches example)
            System.out.printf(
                    "%3d | $%14.2f | $%15.2f | $%15.2f | $%11.2f%n",
                    (age + 1),
                    startBalance,
                    contributionsThisYear,
                    interestEarnedThisYear,
                    endBalance
            );

            // Increase annual contribution for next year
            yearlyContribution = yearlyContribution * (1.0 + (contributionIncreasePercent / 100.0));
        }

        System.out.println("--------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Summary");
        System.out.printf("Total Contributions: $%.2f%n", totalContributed);
        System.out.printf("Total Interest Earned: $%.2f%n", totalInterestEarned);
        System.out.printf("Ending Balance at Age %d: $%.2f%n", retirementAge, balance);
        System.out.println();
    }
    
}
