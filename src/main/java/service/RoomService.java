package service;

import domain.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.RoomRepository;
import util.Hasher;
import util.RoomCodeGenerator;

import java.util.Optional;

public class RoomService {
    private final RoomRepository roomRepository;
    private static final Logger logger = LogManager.getLogger(RoomService.class);

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom(String name, int maxParticipants) {
        String roomCode = RoomCodeGenerator.generateRoomCode();
        String encryptedCode = Hasher.hash(roomCode);

        Room newRoom = new Room(name, maxParticipants, encryptedCode);
        try {
            roomRepository.saveRoom(newRoom);
            logger.info("\n방 생성 완료! 참가 코드: {}", roomCode);
        } catch (RuntimeException e) {
            logger.error("방 생성 중 오류 발생", e);
            throw new RuntimeException(e);
        }

        return newRoom;
    }

    public boolean joinRoom(String roomCode) {
        String encryptedCode = Hasher.hash(roomCode);
        Optional<Room> roomOpt = Optional.ofNullable(roomRepository.findRoomByCode(encryptedCode));

        if (roomOpt.isEmpty()) {
            logger.warn("\n⚠유효하지 않은 방 코드입니다.");
            return false;
        }

        Room room = roomOpt.get();
        String anonymousUsername = room.generateAnonymousName();

        if (room.addParticipant(anonymousUsername)) {
            return true;
        } else {
            logger.warn("\n방이 가득 찼습니다! 다른 방을 시도해 주세요.");
            return false;
        }
    }
}
