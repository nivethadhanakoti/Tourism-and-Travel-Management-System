package Management;

public class Login {
    private long userID;
    private String password;

    public Login(long userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public boolean validLoginCheck(long enteredUserID, String enteredPassword) {
        return userID == enteredUserID && password.equals(enteredPassword);
    }

    public long getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
