package repository;

import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private static final Logger logger = LogManager.getLogger(UserRepository.class);

    public void save(User user) {
        String sql = "INSERT INTO users (email, name, hashed_password, salt) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getHashedPassword());
            stmt.setString(4, user.getSalt());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("사용자 저장 중 오류 발생", e);
        }
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("hashed_password"),
                        rs.getString("salt")
                );
            }
        } catch (SQLException e) {
            logger.error("사용자 조회 중 오류 발생", e);
        }
        return null;
    }
}
