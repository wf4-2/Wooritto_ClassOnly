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
        try{
            roomRepository.saveRoom(newRoom);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n✅ 방 생성 완료! 참가 코드: " + roomCode);
        return newRoom;
    }

    public boolean joinRoom(String roomCode, String username) {
        String encryptedCode = SimpleHasher.hash(roomCode);
        Optional<Room> roomOpt = Optional.ofNullable(roomRepository.findRoomByCode(encryptedCode));

        if (roomOpt.isEmpty()) {
            System.out.println("\n⚠️ 유효하지 않은 방 코드입니다.");
            return false;
        }

        Room room = roomOpt.get();
        if (room.addParticipant(username)) {
            System.out.println("\n🎉 방 참가 성공! " + room.getName() + "에 입장했습니다.");
            return true;
        } else {
            System.out.println("\n🚫 방이 가득 찼습니다! 다른 방을 시도해 주세요.");
            return false;
        }
    }
}
