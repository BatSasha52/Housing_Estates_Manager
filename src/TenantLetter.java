public class TenantLetter {

    private Room room;
    private String message;

    public TenantLetter(Room room, String message) {
        this.room = room;
        this.message = message;
    }

    public Room getRoom() {
        return room;
    }

    public String getMessage() {
        return message;
    }

    public static TenantLetter generateLetter(Room room, long daysExpired, String tenantName) {
        String message = "Dear " + tenantName + ",\n" +
                "Your rent for room " + room.getId() + " has expired " + daysExpired + " days ago.\n" +
                "Please make your payment as soon as possible.\n" +
                "Thank you.";
        return new TenantLetter(room, message);
    }
}