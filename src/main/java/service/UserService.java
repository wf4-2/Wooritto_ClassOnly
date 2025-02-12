package service;

import domain.User;
import repository.UserRepository;
import util.SimpleHasher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Pattern;

public class UserService {
    private final UserRepository userRepository;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean signup(String email, String password, String name) {
        if (!isValidEmail(email)) {
            System.out.println("유효하지 않은 이메일 형식입니다. 다시 입력해주세요.");
            return false;
        }

        if (userRepository.findByEmail(email) != null) {
            System.out.println("이미 사용 중인 이메일입니다.");
            return false;
        }

        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

        User user = new User(email, hashedPassword, name, salt);
        userRepository.save(user);

        return true;
    }

    public boolean login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
            return false;
        }

        String hashedPassword = hashPassword(password, user.getSalt());
        return hashedPassword.equals(user.getHashedPassword());
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        StringBuilder hex = new StringBuilder();
        for (byte b : salt) {
            hex.append(String.format("%02x", b));
        }

        return hex.toString();
    }

    private String hashPassword(String password, String salt) {
        String passwordWithSalt = password + salt;

        return SimpleHasher.hash(passwordWithSalt);
    }
}
