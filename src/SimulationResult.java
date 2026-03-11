public class SimulationResult {
    private double totalContributed;
    private double totalInterest;
    private double finalBalance;

    public SimulationResult(double totalContributed, double totalInterest, double finalBalance) {
        this.totalContributed = totalContributed;
        this.totalInterest = totalInterest;
        this.finalBalance = finalBalance;
    }

    public double getTotalContributed() {
        return totalContributed;
    }

    public double getTotalInterest() {
        return totalInterest;
    }

    public double getFinalBalance() {
        return finalBalance;
    }
}	

   