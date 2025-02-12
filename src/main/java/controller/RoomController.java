package controller;

import service.RoomService;
import java.util.Scanner;

public class RoomController {
    private final RoomService roomService;
    private final Scanner scanner = new Scanner(System.in);

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    public void start() {
        while (true) {
            System.out.println("\n원하시는 작업을 선택하세요.");
            System.out.println("1. 방 생성 | 2. 방 참가 | 3. 종료");
            int choice = scanner.nextInt();
            scanner.nextLine();  // 개행 문자 제거

            if (choice == 1) {
                createRoomUI();
            } else if (choice == 2) {
                joinRoomUI();
            } else {
                System.out.println("\n프로그램을 종료합니다. 감사합니다!\n");
                break;
            }
        }
    }

    private void createRoomUI() {
        System.out.print("\n생성할 방 이름을 입력하세요: ");
        String roomName = scanner.nextLine();
        System.out.print("최대 인원 수를 입력하세요: ");
        int maxParticipants = scanner.nextInt();
        scanner.nextLine();  // 개행 문자 제거

        roomService.createRoom(roomName, maxParticipants);
    }

    private void joinRoomUI() {
        System.out.print("\n 참가할 방의 코드를 입력하세요: ");
        String roomCode = scanner.nextLine();

        boolean success = roomService.joinRoom(roomCode);
        if (success) {
            System.out.println("\n방 참가 성공!");
        } else {
            System.out.println("\n방 참가 실패! 다시 시도해 주세요.");
        }
    }
}
