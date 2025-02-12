package repository;

import domain.Room;
import java.sql.*;

public class RoomRepository {
    private static final String JDBC_URL = "jdbc:h2:./database";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";

    public RoomRepository() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS rooms (" +
                    "id IDENTITY PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "max_participants INT, " +
                    "join_code VARCHAR(255))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("org.h2.Driver");  // ✅ H2 드라이버 강제 로드
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("H2 드라이버를 찾을 수 없습니다! Gradle 설정을 확인하세요.", e);
        }
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }


    public void saveRoom(Room room) {
        String sql = "INSERT INTO rooms (name, max_participants, join_code) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, room.getName());
            stmt.setInt(2, room.getMaxParticipants());
            stmt.setString(3, room.getJoinCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Room findRoomByCode(String encryptedCode) {
        String sql = "SELECT * FROM rooms WHERE join_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, encryptedCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Room(
                        rs.getString("name"),
                        rs.getInt("max_participants"),
                        rs.getString("join_code")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
