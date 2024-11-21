import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
//geçerli
public class PiggyBankApp {
    private static final int MAX_BANKNOTES = 100;
    private static final int[] VALID_BANKNOTES = {1, 2, 5, 10, 20};
    private static int goal = 0;
    private static int totalMoney = 0;
    private static int totalBanknotes = 0;
    private static Map<Integer, Integer> banknoteCounts = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nPiggy Bank Menu:");
            System.out.println("1. Set Goal");
            System.out.println("2. Add Money");
            System.out.println("3. Take Out Money");
            System.out.println("4. Show Current Money");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> setGoal(scanner);
                case 2 -> addMoney(scanner);
                case 3 -> takeOutMoney(scanner);
                case 4 -> showCurrentMoney();
                case 0 -> {
                    System.out.println("Exiting program. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void setGoal(Scanner scanner) {
        System.out.print("Enter your saving goal in dollars: ");
        goal = scanner.nextInt();

        int minBanknotes = (int) Math.ceil((double) goal / VALID_BANKNOTES[VALID_BANKNOTES.length - 1]);
        if (minBanknotes > MAX_BANKNOTES) {
            System.out.println("Warning: Goal cannot be reached with the maximum capacity of the piggy bank.");
        } else {
            System.out.println("Goal set successfully!");
        }
    }

    private static void addMoney(Scanner scanner) {
        if (goal <= 0) {
            System.out.println("Please set a goal first.");
            return;
        }

        System.out.print("Enter the type of banknote to add (1, 2, 5, 10, 20): ");
        int type = scanner.nextInt();
        if (!isValidBanknote(type)) {
            System.out.println("Invalid banknote type. Please try again.");
            return;
        }

        System.out.print("Enter the number of banknotes to add: ");
        int count = scanner.nextInt();

        if (totalBanknotes + count > MAX_BANKNOTES) {
            System.out.println("Warning: Adding these banknotes will exceed the piggy bank capacity.");
            return;
        }

        banknoteCounts.put(type, banknoteCounts.getOrDefault(type, 0) + count);
        totalMoney += type * count;
        totalBanknotes += count;

        if (totalMoney >= goal) {
            System.out.println("Congratulations! You reached your goal.");
            resetPiggyBank();
        } else {
            System.out.println("Remaining amount to reach the goal: $" + (goal - totalMoney));
        }
    }

    private static void takeOutMoney(Scanner scanner) {
        System.out.print("Enter the type of banknote to take out (1, 2, 5, 10, 20): ");
        int type = scanner.nextInt();
        if (!isValidBanknote(type)) {
            System.out.println("Invalid banknote type. Please try again.");
            return;
        }

        System.out.print("Enter the number of banknotes to take out: ");
        int count = scanner.nextInt();

        int currentCount = banknoteCounts.getOrDefault(type, 0);
        if (count > currentCount) {
            System.out.println("Warning: Not enough banknotes of this type in the piggy bank.");
            return;
        }

        banknoteCounts.put(type, currentCount - count);
        totalMoney -= type * count;
        totalBanknotes -= count;

        System.out.println("Banknotes removed successfully!");
    }

    private static void showCurrentMoney() {
        System.out.println("Current money in the piggy bank:");
        banknoteCounts.forEach((type, count) ->
                System.out.println("Banknote $" + type + ": " + count + " pieces")
        );
        System.out.println("Total money: $" + totalMoney);
        System.out.println("Remaining amount to reach the goal: $" + (goal - totalMoney));
    }

    private static boolean isValidBanknote(int type) {
        for (int banknote : VALID_BANKNOTES) {
            if (banknote == type) return true;
        }
        return false;
    }

    private static void resetPiggyBank() {
        goal = 0;
        totalMoney = 0;
        totalBanknotes = 0;
        banknoteCounts.clear();
        System.out.println("The piggy bank has been reset. Set a new goal to start saving again.");
    }
}
