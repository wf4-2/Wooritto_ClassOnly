import controller.RoomController;
import repository.RoomRepository;
import service.RoomService;
import util.AsciiArtUtil;

public class ManittoMain {
	public static void main(String[] args) {
		// 1. 프로그램 시작 메시지 출력
		System.out.println("\nWooritto Chat System을 시작합니다!\n");

		// 2. ASCII 로고 출력
		AsciiArtUtil.printWoorittoLogo();

		// 3. 데이터베이스 준비 및 서비스 초기화
		RoomRepository roomRepository = new RoomRepository();
		RoomService roomService = new RoomService(roomRepository);
		RoomController roomController = new RoomController(roomService);

		// 4. 방 생성 및 참가 UI 실행
		roomController.start();
	}
}
