package service;

import domain.User;
import exception.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import repository.UserRepository;
import util.Hasher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 회원가입_성공() {
        String email = "test@example.com";
        String password = "password123";
        String name = "Test User";
        String salt = "fixedSalt1234"; // 테스트용 고정된 salt
        String expectedHashedPassword = Hasher.hash(password + salt);

        when(userRepository.findByEmail(email)).thenReturn(null);
        doNothing().when(userRepository).save(any(User.class));

        // `generateSalt()`가 항상 같은 값을 반환하도록 Mocking
        UserService userServiceSpy = spy(userService);
        doReturn(salt).when(userServiceSpy).generateSalt();

        assertDoesNotThrow(() -> userServiceSpy.signup(email, password, name));
    }


    @Test
    void 회원가입_실패_이메일_중복() {
        String email = "test@example.com";
        String password = "password123";
        String name = "Test User";

        when(userRepository.findByEmail(email)).thenReturn(new User(email, name, "hashedPassword", "salt"));

        Exception exception = assertThrows(UserException.class, () -> userService.signup(email, password, name));
        assertEquals("이미 사용 중인 이메일입니다.", exception.getMessage());
    }

    @Test
    void 로그인_성공() {
        String email = "test@example.com";
        String password = "password123";
        String salt = "fixedSalt1234";
        String hashedPassword = "mockedHashedPassword";

        // `User` 객체를 실제로 생성하여 사용
        User user = new User(email, "Test User", hashedPassword, salt);

        // Mock 설정 변경: 실제 User 객체를 반환
        when(userRepository.findByEmail(email)).thenReturn(user);

        System.out.println("Before login: " + user.getHashedPassword());

        try (MockedStatic<Hasher> mockedHasher = Mockito.mockStatic(Hasher.class)) {
            mockedHasher.when(() -> Hasher.checkPassword(password, hashedPassword)).thenReturn(true);

            System.out.println("Hasher.checkPassword Result: " + Hasher.checkPassword(password, hashedPassword));

            assertTrue(userService.login(email, password));
        }
    }





    @Test
    void 로그인_실패_잘못된_비밀번호() {
        String email = "test@example.com";
        String password = "wrongPassword";
        String salt = "fixedSalt1234";
        String hashedPassword = "mockedHashedPassword"; // Mocking된 해시 값

        User user = new User(email, "Test User", hashedPassword, salt);

        when(userRepository.findByEmail(email)).thenReturn(user);

        // 확인용 출력 추가
        System.out.println("Before login: " + userRepository.findByEmail(email).getHashedPassword());

        try (MockedStatic<Hasher> mockedHasher = Mockito.mockStatic(Hasher.class)) {
            mockedHasher.when(() -> Hasher.checkPassword(password, hashedPassword)).thenReturn(false);
            assertFalse(userService.login(email, password));
        }
    }



    @Test
    void 로그인_실패_존재하지_않는_사용자() {
        String email = "notfound@example.com";
        String password = "password123";

        when(userRepository.findByEmail(email)).thenReturn(null);

        assertFalse(userService.login(email, password));
    }
}
