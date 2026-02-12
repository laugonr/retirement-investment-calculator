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
    
    public static void runSimulation(
            int currentAge,
            int retirementAge,
            double startingBalance,
            double annualContribution,
            double aprPercent,
            int compChoice,
            double contributionIncreasePercent
    ) {
        // 1) Convert compChoice to periods per year
        int periodsPerYear;
        if (compChoice == 1) {
            periodsPerYear = 1;      // annually
        } else if (compChoice == 2) {
            periodsPerYear = 12;     // monthly
        } else {
            periodsPerYear = 365;    // daily
        }

        // 2) Convert APR percent to rate per period
        double ratePerPeriod = (aprPercent / 100.0) / periodsPerYear;

        // 3) Track values
        double balance = startingBalance;
        double yearlyContribution = annualContribution;
        double totalContributed = 0;

        // 4) Print table header
        System.out.println();
        System.out.println("Year | Age | Contribution | End Balance");
        System.out.println("----------------------------------------");

        // 5) Run yearly simulation
        for (int age = currentAge; age < retirementAge; age++) {

            double contributionThisYear = 0;
            double contributionPerPeriod = yearlyContribution / periodsPerYear;

            for (int p = 0; p < periodsPerYear; p++) {
                balance += contributionPerPeriod;
                totalContributed += contributionPerPeriod;
                contributionThisYear += contributionPerPeriod;

                balance = balance * (1 + ratePerPeriod);
            }

            System.out.printf("%4d | %3d | %12.2f | %11.2f%n",
                    (age - currentAge + 1),
                    age,
                    contributionThisYear,
                    balance
            );

            // increase contribution for next year
            yearlyContribution = yearlyContribution * (1 + (contributionIncreasePercent / 100.0));
        }

        double totalInterestEarned = balance - startingBalance - totalContributed;

        System.out.println("----------------------------------------");
        System.out.printf("Total contributed: %.2f%n", totalContributed);
        System.out.printf("Total interest:    %.2f%n", totalInterestEarned);
        System.out.printf("Final balance:     %.2f%n", balance);
        System.out.println();
    }


}

