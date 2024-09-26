import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HousingEstate {

    private static final int MAX_RENT_ROOMS_NUMBER = 5;

    private static Date currDate = new Date();

    private List<Person> persons = new ArrayList<>();
    private List<Apartment> apartments = new ArrayList<>();
    private List<ParkingPlace> ParkingPlaces = new ArrayList<>();

    public HousingEstate() {
        this.apartments = new ArrayList<>();
        this.ParkingPlaces = new ArrayList<>();
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void addApartment(Apartment apartment) {
        this.apartments.add(apartment);
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }

    public List<ParkingPlace> getParkingPlaces() {
        return ParkingPlaces;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public int getFreeApartmentsAmmount() {
        int count = 0;
        for (Apartment apartment : apartments) {
            if (!apartment.isRented()) {
                ++count;
            }
        }
        return count;
    }

    public int getTenantsWithLettersAmmount() {
        int count = 0;
        for (Person person : persons) {
            if (person.getLetters() != null) {
                count++;
            }
        }
        return count;
    }

    public void addParkingPlace(ParkingPlace ParkingPlace) {
        this.ParkingPlaces.add(ParkingPlace);
    }

    public void startRentApartment(Apartment apartament, Person person, ParkingPlace parkingPlace, Date endDate)
            throws Exception {
        Person existingPerson = findPerson(person);

        if (person.getRentedRoomsNumber() > MAX_RENT_ROOMS_NUMBER) {
            throw new Exception("Too many rented rooms.");
        }

        if (existingPerson == null) {
            persons.add(person);
        } else
            person = existingPerson;

        apartament.startRent(person, parkingPlace, endDate);

    }

    public void stopRentApartment(Apartment apartament, Person person) {
        Person existingPerson = findPerson(person);

        if (existingPerson == null)
            return;

        apartament.endRent(existingPerson);

        if (existingPerson.getRentedRoomsNumber() == 0) {
            persons.remove(existingPerson);
        }
        if (!apartament.isRented()) {
            removeItemsAndPersons(apartament);
        }
    }

    private void removeItemsAndPersons(Room room) {
        Apartment apartment = (Apartment) room;
        apartment.removePersons();
        if (apartment.getLinkedParkingPlace() != null) {
            ParkingPlace parkingPlace = apartment.getLinkedParkingPlace();
            parkingPlace.removeItems();
        }
    }

    private Person findPerson(Person personToFind) {
        for (Person person : persons) {
            if (person.isSame(personToFind))
                return person;
        }

        return null;
    }

    public static void setCurrDate(Date currDate) {
        HousingEstate.currDate = currDate;
    }

    public static Date getCurrDate() {
        return currDate;
    }

    public static Date generateEndDate() {
        long millisInOneDay = 24 * 60 * 60 * 1000; // milliseconds in one day
        long millisInRandomDays = Utils.generateNum(15, 45) * millisInOneDay;
        //long millisInRandomDays =2 * millisInOneDay;
        long currDateMillis = currDate.getTime();
        long endDate = currDateMillis + millisInRandomDays;
        return new Date(endDate);
    }

    public static HousingEstate generate() throws TooManyThingsException {

        HousingEstate estate = new HousingEstate();

        int apartmentsNumber = Utils.generateNum(5, 10);
        int tenantsNumber = Utils.generateNum(5, (apartmentsNumber));

        for (int i = 0; i < apartmentsNumber; i++) {

            Apartment newApartment = Apartment.generate();
            ParkingPlace newParkingPlace = ParkingPlace.generate();

            estate.addApartment(newApartment);
            estate.addParkingPlace(newParkingPlace);

        }

        for (int i = 0; i < tenantsNumber; i++) {
            try {
                for (Apartment apartment : estate.getApartments()) {
                    if (!apartment.isRented()) {
                        Date endDate = generateEndDate();
                        Person newPerson = Person.generate();
                        ParkingPlace parkingPlace = null;

                        if (Utils.generateNum(1, 3) == 2) {
                            parkingPlace = estate.ParkingPlaces.stream()
                                    .filter(x -> !x.isRented())
                                    .findFirst()
                                    .get();
                        }

                        estate.startRentApartment(apartment, newPerson, parkingPlace, endDate);

                        //generation of roommates
                        int numberOfRoommates = Utils.generateNum(1, 6);
                        for (int j = 0; j < numberOfRoommates; j++) {
                            Person newRoommate = Person.generate();
                            apartment.checkIn(newRoommate);
                        }

                        //generation of items
                        if (parkingPlace != null) {
                            Item item = Item.generate();
                                while(parkingPlace.hasSpace(item.getVolume())) {
                                    parkingPlace.addStoredItem(item);
                                    item = Item.generate();
                                }
                            }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return estate;
    }

    public void checkStatus(Date currDate) {
        for (Person person : persons) {
            person.checkStatus(currDate);
        }
    }
}