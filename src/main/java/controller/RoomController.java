package controller;

import service.ChatService;
import service.RoomService;
import java.util.Scanner;

public class RoomController {
    private final RoomService roomService;
    private final Scanner scanner = new Scanner(System.in);

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    public void start() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n원하시는 작업을 선택하세요.");
            System.out.println("1. 방 생성 | 2. 방 참가 | 3. 종료");
            int choice = scanner.nextInt();
            scanner.nextLine();  // 개행 문자 제거

            if (choice == 1) {
                createRoomUI();
            } else if (choice == 2) {
                if (joinRoomUI()) {
                    isRunning = false; // 참가 후 루프 종료
                }
            } else {
                System.out.println("\n프로그램을 종료합니다. 감사합니다!\n");
                isRunning = false;
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

    private boolean joinRoomUI() {
        System.out.print("\n참가할 방의 코드를 입력하세요: ");
        String roomCode = scanner.nextLine();

        boolean success = roomService.joinRoom(roomCode);
        if (success) {
            ChatService.startChatSimulation();
            return true; // 성공 시 루프 종료
        } else {
            System.out.println("\n방 참가 실패! 다시 시도해 주세요.");
            return false;
        }
    }
}
