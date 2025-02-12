package domain;

public class User {
    private String email;
    private String hashedPassword;
    private String name;
    private String salt;

    public User(String email, String hashedPassword, String name, String salt) {
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.name = name;
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getName() {
        return name;
    }

    public String getSalt() {
        return salt;
    }
}
