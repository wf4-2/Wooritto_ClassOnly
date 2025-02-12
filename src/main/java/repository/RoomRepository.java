package repository;

import domain.Room;
import util.DatabaseUtil;
import java.sql.*;

public class RoomRepository {
    public void saveRoom(Room room) {
        String sql = "INSERT INTO rooms (name, max_participants, join_code) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
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
            e.printStackTrace();
        }
        return null;
    }
}
