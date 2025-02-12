import controller.UserController;
import util.AsciiArtUtil;

public class ManittoMain {
	private static final UserController userController = new UserController();

	public static void main(String[] args) {
		AsciiArtUtil.printWoorittoLogo();
		System.out.println("WOORITTO에 오신 것을 환영합니다.");
		userController.run();
	}
}