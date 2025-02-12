package domain;

public class User {
    private String email;
    private String name;
    private String hashedPassword;
    private String salt;

    public User(String email, String name, String hashedPassword, String salt) {
        this.email = email;
        this.name = name;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getSalt() {
        return salt;
    }
}
