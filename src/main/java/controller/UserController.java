package controller;

import repository.RoomRepository;
import repository.UserRepository;
import service.RoomService;
import service.UserService;

import java.util.Scanner;

public class UserController {
    private final UserService userService;
    private final RoomController roomController;
    private Scanner scanner;

    // 🔥 생성자에서 외부에서 주입받도록 변경
    public UserController(UserService userService, RoomController roomController, Scanner scanner) {
        this.userService = userService;
        this.roomController = roomController;
        this.scanner = scanner;
    }

    // 🔥 Scanner를 변경할 수 있도록 메서드 추가 (테스트용)
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println("\n1. 로그인  2. 회원가입");

            int loginOrSignup = Integer.parseInt(scanner.nextLine());

            if (loginOrSignup == 1) {
                handleLogin();
                break;
            } else if (loginOrSignup == 2) {
                handleSignup();
            } else {
                System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
        }
    }

    public void handleLogin() {
        System.out.print("\nEMAIL: ");
        String email = scanner.nextLine();
        System.out.print("PW: ");
        String password = scanner.nextLine();

        if (userService.login(email, password)) {
            roomController.run();
        } else {
            System.out.println("로그인 실패!");
            // 🔥 `run()`을 호출하지 않고 종료되도록 변경
        }
    }

    public void handleSignup() {
        String name;
        String email;
        String password;
        String confirmPassword;

        while (true) {
            System.out.print("\nNAME: ");
            name = scanner.nextLine();

            System.out.print("EMAIL: ");
            email = scanner.nextLine();
            if (!userService.isValidEmail(email)) { // 🔥 isValidEmail() 조건 수정
                System.out.println("유효하지 않은 이메일 형식입니다. 다시 입력해주세요.");
                continue;
            }

            while (true) {
                System.out.print("PW: ");
                password = scanner.nextLine();
                System.out.print("Confirm PW: ");
                confirmPassword = scanner.nextLine();

                if (!password.equals(confirmPassword)) {
                    System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
                    continue;
                }
                break;
            }

            if (userService.signup(email, password, name)) {
                System.out.println("\n회원가입이 완료되었습니다. 로그인해주세요.");
                break;
            } else {
                System.out.println("회원가입 실패! 다시 시도해주세요.");
            }
        }
    }
}
