import java.util.Scanner;

public class InputHelper {
    private Scanner sc;

    public InputHelper(Scanner sc) {
        this.sc = sc;
    }

    public int readIntInRange(String prompt, int min, int max) {
        int value = 0;
        boolean valid = false;

        do {
            System.out.print(prompt);

            try {
                value = sc.nextInt();
                sc.nextLine();

                if (value >= min && value <= max) {
                    valid = true;
                } else {
                    System.out.println("Input must be between " + min + " and " + max + ".");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a whole number.");
                sc.nextLine();
            }
        } while (!valid);

        return value;
    }

    public double readDoubleInRange(String prompt, double min, double max) {
        double value = 0;
        boolean valid = false;

        do {
            System.out.print(prompt);

            try {
                value = sc.nextDouble();
                sc.nextLine();

                if (value >= min && value <= max) {
                    valid = true;
                } else {
                    System.out.println("Input must be between " + min + " and " + max + ".");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        } while (!valid);

        return value;
    }

    public CompoundingPeriod readCompoundingPeriod() {
        while (true) {
            System.out.println("Compounding frequency:");
            System.out.println("1 = Annually");
            System.out.println("2 = Monthly");
            System.out.println("3 = Daily (365)");
            System.out.print("Enter choice (1-3): ");

            String input = sc.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        return CompoundingPeriod.ANNUAL;
                    case 2:
                        return CompoundingPeriod.MONTHLY;
                    case 3:
                        return CompoundingPeriod.DAILY;
                    default:
                        System.out.println("Invalid selection. Please enter 1, 2, or 3.");
                }
            } catch (Exception e) {
                System.out.println("Invalid selection. Please enter 1, 2, or 3.");
            }
        }
    }

    public boolean askRunAgain() {
        while (true) {
            System.out.print("Run another simulation? (Y/N): ");
            String answer = sc.nextLine().trim().toLowerCase();

            if (answer.equals("y") || answer.equals("yes")) {
                return true;
            }

            if (answer.equals("n") || answer.equals("no")) {
                return false;
            }

            System.out.println("Please enter Y or N.");
        }
    }
}