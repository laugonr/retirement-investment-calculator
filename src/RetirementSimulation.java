public class RetirementSimulation {

    private int currentAge;
    private int retirementAge;
    private double startingBalance;
    private double annualContribution;
    private double aprPercent;
    private double contributionIncreasePercent;
    private CompoundingPeriod compoundingPeriod;

    public RetirementSimulation(
            int currentAge,
            int retirementAge,
            double startingBalance,
            double annualContribution,
            double aprPercent,
            double contributionIncreasePercent,
            CompoundingPeriod compoundingPeriod) {

        this.currentAge = currentAge;
        this.retirementAge = retirementAge;
        this.startingBalance = startingBalance;
        this.annualContribution = annualContribution;
        this.aprPercent = aprPercent;
        this.contributionIncreasePercent = contributionIncreasePercent;
        this.compoundingPeriod = compoundingPeriod;
    }

    public SimulationResult runSimulation() {

        int periodsPerYear = compoundingPeriod.getPeriodsPerYear();
        double ratePerPeriod = (aprPercent / 100.0) / periodsPerYear;

        System.out.println();
        System.out.println("Year-by-Year Projection");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Age | Start Balance     | Contributions     | Interest Earned   | End Balance");
        System.out.println("--------------------------------------------------------------------------");

        double balance = startingBalance;
        double yearlyContribution = annualContribution;

        double totalContributed = 0.0;
        double totalInterest = 0.0;

        for (int age = currentAge; age < retirementAge; age++) {

            double startBalance = balance;
            double contributionsThisYear = 0.0;

            double contributionPerPeriod = yearlyContribution / periodsPerYear;

            for (int p = 0; p < periodsPerYear; p++) {
                balance += contributionPerPeriod;
                contributionsThisYear += contributionPerPeriod;
                balance = balance * (1.0 + ratePerPeriod);
            }

            double endBalance = balance;
            double interestEarnedThisYear = endBalance - startBalance - contributionsThisYear;

            totalContributed += contributionsThisYear;
            totalInterest += interestEarnedThisYear;

            System.out.printf(
                    "%3d | $%14.2f | $%15.2f | $%15.2f | $%11.2f%n",
                    (age + 1),
                    startBalance,
                    contributionsThisYear,
                    interestEarnedThisYear,
                    endBalance
            );

            yearlyContribution = yearlyContribution * (1.0 + (contributionIncreasePercent / 100.0));
        }

        System.out.println("--------------------------------------------------------------------------");

        return new SimulationResult(totalContributed, totalInterest, balance);
    }
}