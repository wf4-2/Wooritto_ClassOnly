import controller.UserController;
import controller.RoomController;
import repository.RoomRepository;
import repository.UserRepository;
import service.RoomService;
import service.UserService;
import util.AsciiArtUtil;

import java.util.Scanner;

public class ManittoMain {
	public static void main(String[] args) {
		// 🔥 필요한 객체들 생성 후 UserController에 주입
		UserRepository userRepository = new UserRepository();
		UserService userService = new UserService(userRepository);

		RoomRepository roomRepository = new RoomRepository();
		RoomService roomService = new RoomService(roomRepository);
		RoomController roomController = new RoomController(roomService);

		Scanner scanner = new Scanner(System.in); // 🔥 Scanner 주입

		// 🔥 UserController를 올바르게 생성
		UserController userController = new UserController(userService, roomController, scanner);

		// 프로그램 시작
		AsciiArtUtil.printWoorittoLogo();
		System.out.println("WOORITTO에 오신 것을 환영합니다.");
		userController.run();
	}
}
