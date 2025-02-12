package repository;

import domain.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public User findByEmail(String email) {
        return users.get(email);
    }

    public void save(User user) {
        users.put(user.getEmail(), user);
    }
}
