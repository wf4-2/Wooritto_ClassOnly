package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import controller.RoomController;
import controller.UserController;
import service.UserService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RoomController roomController;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 회원가입_흐름_테스트() {
        // 🛠 테스트용 입력 설정 (사용자 입력값 시뮬레이션)
        String input = "Test User\n" +
                "test@example.com\n" +
                "password123\n" +
                "password123\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        userController.setScanner(scanner); // 🔥 Scanner 주입

        // 🔥 직접 handleSignup() 호출
        when(userService.isValidEmail(anyString())).thenReturn(true); // 🔥 올바른 이메일이라고 가정
        when(userService.signup(anyString(), anyString(), anyString())).thenReturn(true);

        userController.handleSignup(); // ✅ run() 대신 직접 호출

        verify(userService, times(1)).signup("test@example.com", "password123", "Test User");
    }

    @Test
    void 로그인_흐름_테스트() {
        // 🛠 테스트용 입력 설정
        String input = "test@example.com\npassword123\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        userController.setScanner(scanner); // 🔥 Scanner 주입

        // 🔥 직접 handleLogin() 호출
        when(userService.login("test@example.com", "password123")).thenReturn(true);

        userController.handleLogin(); // ✅ run() 대신 직접 호출

        verify(userService, times(1)).login("test@example.com", "password123");
        verify(roomController, times(1)).run(); // 🔥 방 입장 시 roomController.run() 호출 검증
    }
}
