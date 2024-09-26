import java.io.PrintStream;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Apartment extends Room {

  private List<Person> persons = new ArrayList<>();

  private ParkingPlace linkedParkingPlace;

  private Person tenant;

  private Date rentEndDate;

  public Apartment(double volume) {
    super(RoomType.Apartment, volume);

  }

  public Apartment(double lenght, double witdth, double height) {
    super(RoomType.Apartment, lenght, witdth, height);
  }

  public void checkIn(Person person) {
    for (Person existingPerson : persons) {
      if (person.getIdNumber() == existingPerson.getIdNumber())
        throw new RuntimeException("Person is already in apartment");
    }

    persons.add(person);
  }

  public void checkOut(Person person) {
    if (!isTenantOfApartment(person))
      persons.removeIf(existingPerson -> person.getIdNumber() == existingPerson.getIdNumber());
  }

  public void removePersons() {
    persons.forEach(this::checkOut);
  }

  public boolean hasParkingPlace() {
    return linkedParkingPlace != null;
  }

  public ParkingPlace getLinkedParkingPlace() {
    return linkedParkingPlace;
  }

  public void setLinkedParkingPlace(ParkingPlace parkingPlace) {
    this.linkedParkingPlace = parkingPlace;
  }

  public static Apartment generate() {
    Apartment apartment = new Apartment(Utils.generateNum(20.0, 70.0));

    return apartment;
  }

  public boolean isTenantOfApartment(Person person) {
    return person.getRentedRooms().contains(this);
  }

  public void startRent(Person tenant, ParkingPlace parkingPlace, Date to) throws ProblematicTenantException {

    if (this.tenant != tenant && isRented())
      throw new RuntimeException("Room is already rented");
    if (!canRent(tenant))
      throw new RuntimeException("Can't rent.");

    if (tenant.getLetters() != null && tenant.getLetters().size() >= 3) {
      throw new ProblematicTenantException(tenant, tenant.getRentedRoomsNumber());
    }

    this.tenant = tenant;
    this.rentEndDate = to;

    if (parkingPlace != null) {
      this.linkedParkingPlace = parkingPlace;
      parkingPlace.startRent(this);
    }
    tenant.setTenant(true);
    tenant.rentApartment(this);
  }

  public void endRent(Person tenant) {

    if (this.tenant.getIdNumber() == tenant.getIdNumber()) {
      this.tenant = null;
      this.rentEndDate = null;
      if (this.linkedParkingPlace != null)
        this.linkedParkingPlace.endRent();

      // clean letters
      List<TenantLetter> tempLetters = new ArrayList<>();
      for (TenantLetter letter : tenant.getLetters()) {
        if (!letter.getRoom().equals(this))
          tempLetters.add(letter);
      }
      tenant.setLetters(tempLetters);

    }
  }

  public List<Person> getPersons() {
    return persons;
  }

  public boolean isRented() {
    return tenant != null;
  }

  public long daysExpired(Date currDate) {
    return ChronoUnit.DAYS.between(rentEndDate.toInstant(), currDate.toInstant());
  }

  protected boolean canRent(Person person) {
    return person.getRentedRoomsNumber() < MAX_RENT_ROOMS_NUMBER;
  }

  @Override
  public void Dump(PrintStream stream) {
    super.Dump(stream);
    stream.println("Persons checked in:");
    for (Person person : persons) {
      stream.println(person.getName() + " " + person.getSurname());
    }
    if (linkedParkingPlace != null) {
      linkedParkingPlace.Dump(stream);
    }

    // List<Room> allRooms = new ArrayList<>();
    // if (linkedParkingPlace != null) {
    // allRooms.add(linkedParkingPlace);
    // }
  }

  @Override
  public String toString() {
    if (tenant != null) {
      return "ID = " + id + ", volume = " + volume + "; (" + tenant.getName() + " " + tenant.getSurname() + ", ID = "
          + tenant.getIdNumber() + ") Rent ends on: " + rentEndDate;
    } else {
      return "ID = " + id + ", volume = " + volume;
    }
  }
}