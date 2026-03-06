import java.util.Scanner;

	public class RetirementCalculator{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InputHelper input = new InputHelper(sc);

        System.out.println("Retirement Growth Simulator");
        System.out.println("...........................");

        boolean runAgain = true;

        while (runAgain) {
            int currentAge = input.readIntInRange("Enter current age (18-100): ", 18, 100);
            int retirementAge = input.readIntInRange("Enter retirement age: ", currentAge + 1, 100);
            double currentBalance = input.readDoubleInRange("Enter current balance (>= 0): ", 0, Double.MAX_VALUE);
            double annualContribution = input.readDoubleInRange("Enter annual contribution (>= 0): ", 0, Double.MAX_VALUE);
            double aprPercent = input.readDoubleInRange("Enter annual interest rate (0-30) (APR %): ", 0, 30);
            double contributionIncreasePercent = input.readDoubleInRange("Enter annual contribution increase (0-20) (%): ", 0, 20);
            CompoundingPeriod compoundingPeriod = input.readCompoundingPeriod();

            RetirementSimulation simulation = new RetirementSimulation(
                    currentAge,
                    retirementAge,
                    currentBalance,
                    annualContribution,
                    aprPercent,
                    contributionIncreasePercent,
                    compoundingPeriod
            );

            SimulationResult result = simulation.runSimulation();

            System.out.println();
            System.out.println("Summary");
            System.out.printf("Total Contributions: $%.2f%n", result.getTotalContributed());
            System.out.printf("Total Interest Earned: $%.2f%n", result.getTotalInterest());
            System.out.printf("Ending Balance at Age %d: $%.2f%n", retirementAge, result.getFinalBalance());
            System.out.println();

            runAgain = input.askRunAgain();
            System.out.println();
        }

        sc.close();
    }
}