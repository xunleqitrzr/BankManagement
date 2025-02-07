import java.util.List;
import java.util.Scanner;

public class CLI {
    private final UserManager userManager;
    private final Scanner scanner;


    /**
     * Sets up the work environment for the CLI
     */
    public CLI() {
        this.userManager = new UserManager();
        this.scanner = new Scanner(System.in);
    }


    /**
     * Main Loop
     */
    public void start() {
        while (true) {
            System.out.println("\n=== Bank Management System ===");
            System.out.println("1. Create User");
            System.out.println("2. List Users");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Transfer Money");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    createUser();
                    break;
                case "2":
                    listUsers();
                    break;
                case "3":
                    depositMoney();
                    break;
                case "4":
                    withdrawMoney();
                    break;
                case "5":
                    transferMoney();
                    break;
                case "6":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice please try again");
            }
        }
    }

    private void createUser() {
        System.out.print("Enter username: ");
        String username = this.scanner.nextLine();

        System.out.println("Enter initial balance (in cents): ");
        int initialBalance = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter overdraft limit (in cents): ");
        int overdraftLimit = Integer.parseInt(scanner.nextLine());
        if (overdraftLimit < 0) {overdraftLimit = -overdraftLimit;}

        User user = userManager.createUser(username, initialBalance, overdraftLimit);
        System.out.println("User created successfully: " + user);
    }

    private void listUsers() {
        List<User> users = userManager.getUsers();
        if (users.isEmpty()) {
            System.out.println("No users found");
        } else {
            System.out.println("\n--- Users ---");
            for (User user : users) {
                System.out.println(user);
            }
        }
    }

    private void depositMoney() {
        User user = findUserById();
        if (user == null) return;

        System.out.println("Enter amount to deposit (in cents): ");
        int depositAmount = Integer.parseInt(scanner.nextLine());

        if (user.check_and_deposit(depositAmount)) {
            System.out.println("Deposit successful");
        } else {
            System.out.println("Deposit failed");
        }
    }

    private void withdrawMoney() {
        System.out.println("Enter sender user ID: ");
        User user = findUserById();
        if (user == null) return;

        System.out.print("Enter withdrawal amount (in cents): ");
        int amount = Integer.parseInt(scanner.nextLine());

        if (user.check_and_withdraw(amount)) {
            System.out.println("Withdrawal successful!");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    private void transferMoney() {
        System.out.print("Enter sender user ID: ");
        User sender = findUserById();
        if (sender == null) return;

        System.out.println("Enter receiver user ID: ");
        User receiver = findUserById();
        if (receiver == null) return;

        System.out.println("Enter transfer amount (in cents): ");
        int amount = Integer.parseInt(scanner.nextLine());

        if (sender.check_and_transfer(receiver, amount)) {
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed. Check balance and details.");
        }
    }

    private User findUserById() {
        System.out.print("Enter user ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        for (User user : userManager.getUsers()) {
            if (user.getId() == id) {
                return user;
            }
        }
        System.out.println("User not found!");
        return null;
    }
}
