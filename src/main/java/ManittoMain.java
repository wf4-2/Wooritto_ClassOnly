import controller.UserController;
import util.AsciiArtUtil;

public class ManittoMain {
	private static final UserController userController = new UserController();

	public static void main(String[] args) {
		AsciiArtUtil.printWoorittoLogo(); // 로고 출력
		userController.run(); // 로그인 또는 회원가입 선택 시작
	}
}