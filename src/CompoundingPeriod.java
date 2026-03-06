public enum CompoundingPeriod {
    ANNUAL(1, "Annually"),
    MONTHLY(12, "Monthly"),
    DAILY(365, "Daily");

    private final int periodsPerYear;
    private final String label;

    CompoundingPeriod(int periodsPerYear, String label) {
        this.periodsPerYear = periodsPerYear;
        this.label = label;
    }

    public int getPeriodsPerYear() {
        return periodsPerYear;
    }

    public String getLabel() {
        return label;
    }
}