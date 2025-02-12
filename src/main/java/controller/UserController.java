package controller;

import repository.RoomRepository;
import repository.UserRepository;
import service.RoomService;
import service.UserService;

import java.util.Scanner;

public class UserController {
    private final UserService userService;
    private final RoomRepository roomRepository = new RoomRepository();
    private final RoomService roomService = new RoomService(roomRepository);
    private final RoomController roomController = new RoomController(roomService);
    private final Scanner scanner = new Scanner(System.in);

    public UserController() {
        UserRepository userRepository = new UserRepository();
        this.userService = new UserService(userRepository);
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

    private void handleLogin() {
        System.out.print("\nEMAIL: ");
        String email = scanner.nextLine();
        System.out.print("PW: ");
        String password = scanner.nextLine();

        if (userService.login(email, password)) {
            roomController.start();
        } else {
            run();
        }
    }

    private void handleSignup() {
        System.out.print("\nNAME: ");
        String name = scanner.nextLine();
        System.out.print("EMAIL: ");
        String email = scanner.nextLine();
        System.out.print("PW: ");
        String password = scanner.nextLine();
        System.out.print("Confirm PW: ");
        String confirmPassword = scanner.nextLine();

        if (!password.equals(confirmPassword)) {
            System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
            handleSignup();
        }

        if (userService.signup(email, password, name)) {
            System.out.println("\n회원가입이 완료되었습니다. 로그인해주세요.");
        } else {
            handleSignup();
        }
    }

}