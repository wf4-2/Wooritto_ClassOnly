package domain;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final String name;
    private final int maxParticipants;
    private final String joinCode;
    private final List<String> participants;

    public Room(String name, int maxParticipants, String joinCode) {
        this.name = name;
        this.maxParticipants = maxParticipants;
        this.joinCode = joinCode;
        this.participants = new ArrayList<>();
    }

    public String getName() { return name; }
    public int getMaxParticipants() { return maxParticipants; }
    public String getJoinCode() { return joinCode; }
    public List<String> getParticipants() { return participants; }

    public boolean addParticipant(String username) {
        if (participants.size() < maxParticipants) {
            participants.add(username);
            return true;
        }
        return false;
    }

    public String generateAnonymousName() {
        return "익명" + (participants.size() + 1);
    }
}
