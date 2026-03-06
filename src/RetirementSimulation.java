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

                balance = balance * (1 + ratePerPeriod);
            }

            double interestEarned = balance - startBalance - contributionsThisYear;

            totalContributed += contributionsThisYear;
            totalInterest += interestEarned;

            yearlyContribution = yearlyContribution * (1 + contributionIncreasePercent / 100.0);
        }

        return new SimulationResult(totalContributed, totalInterest, balance);
    }
}