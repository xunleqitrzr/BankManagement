import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class UserManager {
    private static final String FILE_NAME = "users.dat";
    private int lastId;
    private List<User> users;


    /**
     * Initialization
     */
    public UserManager() {
        this.users = new ArrayList<>();
        loadUsers();
    }

    /**
     * @param name
     * @param initial_balance
     * @param overdraft
     * @return User
     */
    public User createUser(String name, int initial_balance, int overdraft) {
        lastId++;
        User user = new User(name, lastId, initial_balance, overdraft);
        users.add(user);
        saveUsers();
        return user;
    }

    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            lastId = ois.readInt();  // Read last assigned ID
            int userCount = ois.readInt();  // Read number of users
            users.clear();

            for (int i = 0; i < userCount; i++) {
                users.add((User) ois.readObject());
            }
            System.out.println("Loaded " + userCount + " users from file. Last ID: " + lastId);

        } catch (FileNotFoundException e) {
            System.out.println("No existing user file found. Starting fresh.");
            lastId = 0;  // Start IDs from 1
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeInt(lastId);  // Save last used ID
            oos.writeInt(users.size());  // Save user count
            for (User user : users) {
                oos.writeObject(user);
            }
            System.out.println("Users saved successfully. Last ID: " + lastId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @return Returns a List of Users
     */
    public List<User> getUsers() {
        return users;
    }
}
