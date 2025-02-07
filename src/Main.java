public class Main {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();

        // create persistent users
        User user1 = userManager.createUser("Persistent", 20000, 5000);

        // create temporary usres
        User user2 = new User("Temporary", 1, 20000, 5000);

        for (User user : userManager.getUsers()) {
            System.out.println(user);
        }
    }
}