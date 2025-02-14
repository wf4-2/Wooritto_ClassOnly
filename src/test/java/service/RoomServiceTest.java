package service;

import domain.Room;
import exception.RoomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.RoomRepository;
import util.Hasher;
import util.RoomCodeGenerator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 방_생성_성공() {
        String roomName = "테스트방";
        int maxParticipants = 10;
        String roomCode = RoomCodeGenerator.generateRoomCode();
        Room room = new Room(roomName, maxParticipants, roomCode);

        doNothing().when(roomRepository).saveRoom(any(Room.class));

        assertDoesNotThrow(() -> roomService.createRoom(roomName, maxParticipants));
    }

    @Test
    void 방_참가_성공() {
        String roomCode = "A1B2C3";
        String encryptedCode = Hasher.hash(roomCode); // 암호화된 방 코드 생성
        Room room = new Room("테스트방", 10, encryptedCode);

        when(roomRepository.findRoomByCode(encryptedCode)).thenReturn(room);

        // 디버깅용 출력
        System.out.println("방 찾기 결과: " + roomRepository.findRoomByCode(roomCode));

        assertTrue(roomService.joinRoom(roomCode));
    }


    @Test
    void 방_참가_실패_잘못된_코드() {
        String invalidCode = "INVALID123";

        when(roomRepository.findRoomByCode(invalidCode)).thenReturn(null);

        assertFalse(roomService.joinRoom(invalidCode));
    }
}
