package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatService {
    private static final Logger logger = LogManager.getLogger(ChatService.class);

    private static final String[] USERS = {"익명1", "익명2"};
    private static final String[] MESSAGES = {
        "안녕!", "오늘 기분 어때?", "뭐 어때 안좋지",
        "좋은 하루 보내!", "너도", "ㅋㅋㅋ 우리 회식 언제함?",
        "그니까 ㅋㅋ", "뭐 먹을래?", "먹먹(다 먹는다는 뜻)"
    };

    private static final Random random = new Random();

    public static void startChatSimulation() {
        ExecutorService executor = Executors.newFixedThreadPool(USERS.length);

        for (String user : USERS) {
            executor.execute(() -> simulateChat(user));
        }

        executor.shutdown();
    }

    private static void simulateChat(String user) {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(random.nextInt(3000) + 1000);
                String message = MESSAGES[random.nextInt(MESSAGES.length)];
                System.out.println(user + ": " + message);
            }
        } catch (InterruptedException e) {
            logger.error("채팅 시뮬레이션 중 오류 발생: {}", user, e);
        }
    }
}