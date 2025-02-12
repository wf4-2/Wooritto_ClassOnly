package service;

import domain.Room;
import repository.RoomRepository;
import util.RoomCodeGenerator;
import util.SimpleHasher;

import java.util.Optional;

public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom(String name, int maxParticipants) {
        String roomCode = RoomCodeGenerator.generateRoomCode();
        String encryptedCode = SimpleHasher.hash(roomCode);

        Room newRoom = new Room(name, maxParticipants, encryptedCode);
        try {
            roomRepository.saveRoom(newRoom);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n방 생성 완료! 참가 코드: " + roomCode);
        return newRoom;
    }

    public boolean joinRoom(String roomCode) {
        String encryptedCode = SimpleHasher.hash(roomCode);
        Optional<Room> roomOpt = Optional.ofNullable(roomRepository.findRoomByCode(encryptedCode));

        if (roomOpt.isEmpty()) {
            System.out.println("\n⚠유효하지 않은 방 코드입니다.");
            return false;
        }

        Room room = roomOpt.get();
        String anonymousUsername = room.generateAnonymousName(); // 자동 생성된 닉네임 사용

        if (room.addParticipant(anonymousUsername)) {
            System.out.println("\n방 참가 성공! " + room.getName() + "에 '"
                    + anonymousUsername + "'(으)로 입장했습니다.");
            return true;
        } else {
            System.out.println("\n방이 가득 찼습니다! 다른 방을 시도해 주세요.");
            return false;
        }
    }
}
