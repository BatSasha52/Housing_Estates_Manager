import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class Person {

    private String name;
    private String surname;
    private String address;
    private static int idCounter;
    private int idNumber;
    private boolean isTenant;

    private List<Apartment> rentedRooms;
    private List<TenantLetter> letters;

    public Person(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.idNumber = getNextId();
        this.address = address;
        this.rentedRooms = new ArrayList<>();
        this.letters = new ArrayList<>();
        this.isTenant = false;
    }

    public static Person generate() {
        return new Person(generateName(), generateSurname(), generateAddress());
    }

    public static String generateName() {
        String[] names = { "John", "Bruce", "Joe", "Jane", "Nathan", "Bob", "Jack", "Alice", "Mark", "Ihor", "Bart",
                "Eve", "Sergey", "Bruce", "Alla", "Cindy", "Hanna","Natasha", "Richard" };
        return names[Utils.generateNum(0, names.length)];
    }

    public static String generateSurname() {
        String[] surnames = { "Smith", "Drake", "Doe", "Chill", "Johnson", "Parker", "Brown", "Taylor", "Miller", "Grayson", "Milko", "Popiuk", "Feduk", "Wayne" };
        return surnames[Utils.generateNum(0, surnames.length)];
    }

    protected static int getNextId() {
        return ++idCounter;
    }

    public static String generateAddress() {
        String[] addresses = { "Street 1", "Street 2", "Street 3", "Street 4", "Street 5", "Street 6", "Street 7",
                "Street 8", "Street 9", "Street 10", "Street 11", "Street 12" };
        return addresses[Utils.generateNum(0, addresses.length - 1)];
    }

    public int getRentedRoomsNumber() {
        return rentedRooms.size();
    }

    public int getIdNumber() {
        return idNumber;
    }

    public boolean isSame(Person person) {
        return person.getIdNumber() == this.idNumber;
    }

    public boolean hasAparment() {
        for (Room room : rentedRooms) {
            if (room.getRoomType() == Room.RoomType.Apartment)
                return true;
        }

        return false;
    }

    public boolean isTenant() {
        return isTenant;
    }

    public void setTenant(boolean tenant) {
        isTenant = tenant;
    }

    public void rentApartment(Apartment apartment) {
        rentedRooms.add(apartment);
    }

    public void releaseApartment(Apartment apartment) {
        rentedRooms.remove(apartment);
    }

    public List<TenantLetter> getLetters() {
        return letters;
    }

    public List<Apartment> getRentedRooms() {
        return rentedRooms;
    }

    public void setLetters(List<TenantLetter> letters) {
        this.letters = letters;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void checkStatus(Date currDate) {
        ListIterator<Apartment> iter = rentedRooms.listIterator();

        while (iter.hasNext()) {
            Apartment room = iter.next();
            long daysExpired = room.daysExpired(currDate);
            if (daysExpired > 0) {
                TenantLetter letter = TenantLetter.generateLetter(room, daysExpired, this.name);
                letters.add(letter);

                if (daysExpired > 30) {
                    room.endRent(this);
                    iter.remove();
                }
            }
        }
    }

    public void Dump(PrintStream stream) {
        stream.println(
                "\nI am " + this.name + " " + this.surname + "\nID = " + this.idNumber + "; Address: " + this.address);

        for (Room room : rentedRooms.stream().sorted(Comparator.comparingDouble(x -> x.volume))
                .collect(Collectors.toList())) {
            room.Dump(stream);
        }
    }

    @Override
    public String toString() {
        return "ID = " + idNumber + "; " + name + " " + surname + ", " + address;
    }
}