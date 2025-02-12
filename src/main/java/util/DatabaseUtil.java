package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String JDBC_URL = "jdbc:h2:./database";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";

    static {
        try {
            Class.forName("org.h2.Driver");  // H2 드라이버 강제 로드
            initializeDatabase();  // 테이블 자동 생성
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("H2 드라이버를 찾을 수 없습니다!", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    private static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Users 테이블 생성
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "email VARCHAR(255) PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "hashed_password VARCHAR(255), " +
                    "salt VARCHAR(255))");

            // 방(Rooms) 테이블 생성
            stmt.execute("CREATE TABLE IF NOT EXISTS rooms (" +
                    "id IDENTITY PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "max_participants INT, " +
                    "join_code VARCHAR(255))");

            // 채팅(Chats) 테이블 생성
            stmt.execute("CREATE TABLE IF NOT EXISTS chats (" +
                    "id IDENTITY PRIMARY KEY, " +
                    "sender VARCHAR(255), " +
                    "message TEXT, " +
                    "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 초기화 중 오류 발생!", e);
        }
    }
}
