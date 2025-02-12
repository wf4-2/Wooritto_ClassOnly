package repository;

import domain.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DatabaseUtil;
import java.sql.*;

public class RoomRepository {
    private static final Logger logger = LogManager.getLogger(UserRepository.class);

    public void saveRoom(Room room) {
        String sql = "INSERT INTO rooms (name, max_participants, join_code) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, room.getName());
            stmt.setInt(2, room.getMaxParticipants());
            stmt.setString(3, room.getJoinCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("방 저장 중 오류 발생", e);
        }
    }

    public Room findRoomByCode(String encryptedCode) {
        String sql = "SELECT * FROM rooms WHERE join_code = ?";
        try (Connection conn = DatabaseUtil.getConnection();
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
            logger.error("방 조회 중 오류 발생", e);
        }
        return null;
    }
}
