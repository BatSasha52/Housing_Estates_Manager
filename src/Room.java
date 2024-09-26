import java.io.PrintStream;
import java.text.MessageFormat;

public abstract class Room {

  protected static final int MAX_RENT_ROOMS_NUMBER = 5;
  private static int idCounter = 0;

  protected double volume;
  
  public enum RoomType {
    Apartment,
    ParkingPlace
  }

  protected int id;
  private RoomType roomType;

  public Room(RoomType roomType, double volume) {
    this.volume = volume;
    this.roomType = roomType;

    this.id = getNextId();
  }

  public Room(RoomType roomType, double length, double width, double height) {
    this.roomType = roomType;
    this.volume = Utils.getVolume(length, width, height);

    this.id = getNextId();
  }

  public int getId() {
    return id;
  }

  public double getVolume() {
    return volume;
  }

  protected int getNextId() {
    return ++idCounter;
  }

  protected abstract boolean canRent(Person person);

  public RoomType getRoomType() {
    return roomType;
  }

  public void Dump(PrintStream stream) {
    stream.println(MessageFormat.format("Room id={0}, volume={1,number,#.##}, roomType={2}", id, volume, roomType));
  }

  public abstract boolean isRented();

  public abstract String toString();
}