import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        HousingEstatesManager estatesMgr = new HousingEstatesManager();

        try {
            estatesMgr.addEstate(HousingEstate.generate());
        } catch (Exception e) {
            System.out.println("Failure: " + e.getMessage());
        }

        TimeThread th = new TimeThread(estatesMgr);

        th.start();

        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.println("\n1. View Estates\n2. View Rooms\n3. View Persons\n4. View Roommates\n" +
                    "5. View Items\n6. Create Person\n7. Create Apartment\n" +
                    "8. Create Parking Place\n9. Create Estate\n10. Show Tenant Letters\n" +
                    "11. Check in a Person\n12. Check Out a Person\n13. Save Status\n14. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.next();

            switch (choice) {
                case "1":
                    viewEstates(estatesMgr);
                    break;
                case "2":
                    viewRooms(estatesMgr);
                    break;
                case "3":
                    viewPersons(estatesMgr);
                    break;
                case "4":
                    viewRoommates(estatesMgr);
                case "5":
                    viewItems(estatesMgr);
                    break;
                case "6":
                    createPerson(estatesMgr, scanner);
                    break;
                case "7":
                    createApartment(estatesMgr, scanner);
                    break;
                case "8":
                    createParkingPlace(estatesMgr, scanner);
                    break;
                case "9":
                    createEstate(estatesMgr, scanner);
                    break;
                case "10":
                    showTenantLetters(estatesMgr);
                    break;
                case "11":
                    checkInPersonToRoom(estatesMgr, scanner);
                    break;
                case "12":
                    checkOutPersonFromRoom(estatesMgr, scanner);
                    break;
                case "13":
                    outputStatus(estatesMgr);
                    System.out.println("Status is saved in output.txt");
                break;
                case "14":
                    System.out.println("\nExiting the program");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (!choice.equals("14"));
    }

    private static void viewEstates(HousingEstatesManager estatesMgr) {
        System.out.println("\nEstates:");
        for (HousingEstate estate : estatesMgr.getEstates()) {
            System.out.println("- " + estate);
        }
    }

    private static void viewRooms(HousingEstatesManager estatesMgr) {
        for (HousingEstate housingEstate : estatesMgr.getEstates()) {
            System.out.println("\nApartments:");
            for (Apartment apartment : housingEstate.getApartments()) {
                System.out.println("- " + apartment);
            }
            System.out.println("\nParking places:");
            for (ParkingPlace parkingPlace : housingEstate.getParkingPlaces()) {
                System.out.println("- " + parkingPlace);
            }

        }
    }

    private static void viewPersons(HousingEstatesManager estatesMgr) {
        System.out.println("\nPeople:");
        int i = 1;
        for (HousingEstate estate : estatesMgr.getEstates()) {
            System.out.println("Estate: " + estate);
            for (Person person : estate.getPersons()) {
                System.out.println(i + " - " + person);
                ++i;
            }
        }
    }

    private static void viewRoommates(HousingEstatesManager estatesMgr) {
        System.out.println("\nRoommates: ");
        int i = 1;
        for (HousingEstate estate : estatesMgr.getEstates()) {
            for (Apartment apartment : estate.getApartments()) {
                for (Person person : apartment.getPersons()) {
                    if (!person.isTenant())
                        System.out.println(i + " - " + person);
                    ++i;
                }
            }
        }
    }

    private static void viewItems(HousingEstatesManager estatesMgr) {
        System.out.println("\nItems:");
        for (HousingEstate estate : estatesMgr.getEstates()) {
            for (ParkingPlace parkingPlace : estate.getParkingPlaces()) {
                for (Item item : parkingPlace.getStoredItems()) {
                    System.out.println("- " + item);
                }
            }
        }
    }

    private static void createPerson(HousingEstatesManager estatesMgr, Scanner scanner) {
        System.out.println("\nEnter a name, surname, address");
        String name = scanner.next();
        String surname = scanner.next();
        String address = scanner.next();

        Person newPerson = new Person(name, surname, address);

        System.out.println("\nSelect an estate to add the person:");
        for (int i = 0; i < estatesMgr.getEstates().size(); i++) {
            System.out.println((i + 1) + ". " + estatesMgr.getEstates().get(i));
        }

        int estateChoice = scanner.nextInt();
        if (estateChoice >= 1 && estateChoice <= estatesMgr.getEstates().size()) {
            HousingEstate selectedEstate = estatesMgr.getEstates().get(estateChoice - 1);

            if (selectedEstate.getFreeApartmentsAmmount() < 1) {
                selectedEstate.addPerson(newPerson);
                System.out.println("There are no available rooms in this estate. You can add a Person to a room later");
            } else
                try {
                    System.out.println("\nSelect an apartment to add the person:");
                    List<Apartment> apartments = selectedEstate.getApartments();

                    for (int i = 0; i < apartments.size(); i++) {
                        if (!apartments.get(i).isRented())
                            System.out.println((i + 1) + ". " + apartments.get(i));
                    }

                    int apartmentChoice = scanner.nextInt();
                    if (apartmentChoice >= 1 && apartmentChoice <= apartments.size()) {
                        Apartment selectedApartment = apartments.get(apartmentChoice - 1);
                        selectedEstate.startRentApartment(selectedApartment, newPerson, null,
                                HousingEstate.generateEndDate());
                        System.out.println("New Person created and added to the estate.");
                    } else {
                        System.out.println("Invalid apartment choice.");
                    }
                } catch (Exception e) {
                    System.out.println("Failed to start rent for the new person: " + e.getMessage());
                }
        } else {
            System.out.println("Invalid estate choice.");
        }
    }

    private static void createApartment(HousingEstatesManager estatesMgr, Scanner scanner) {
        Room.RoomType roomType = Room.RoomType.Apartment;

        System.out.println("\nEnter 1 for volume or 2 for dimensions (length, width, height):");
        int inputChoice = scanner.nextInt();

        if (inputChoice == 1) {
            System.out.println("\nEnter volume:");
            double volume = scanner.nextDouble();
            Apartment newApartment = new Apartment(volume);
            addRoomToEstate(estatesMgr, roomType, newApartment, scanner);
        } else if (inputChoice == 2) {
            System.out.println("\nEnter length, width, and height:");
            double length = scanner.nextDouble();
            double width = scanner.nextDouble();
            double height = scanner.nextDouble();
            Apartment newApartment = new Apartment(length, width, height);
            addRoomToEstate(estatesMgr, roomType, newApartment, scanner);
        } else {
            System.out.println("\nInvalid input choice.");
        }
    }

    private static void createParkingPlace(HousingEstatesManager estatesMgr, Scanner scanner) {
        Room.RoomType roomType = Room.RoomType.ParkingPlace;

        System.out.println("\nEnter 1 for volume or 2 for dimensions (length, width, height):");
        int inputChoice = scanner.nextInt();

        if (inputChoice == 1) {
            System.out.println("\nEnter volume:");
            double volume = scanner.nextDouble();
            ParkingPlace newParkingPlace = new ParkingPlace(volume);
            addRoomToEstate(estatesMgr, roomType, newParkingPlace, scanner);

            // Check if there are available apartments without linked parking places
            List<Apartment> availableApartments = getApartmentsWithoutParking(estatesMgr);
            if (!availableApartments.isEmpty()) {
                linkParkingPlaceToApartment(scanner, newParkingPlace, availableApartments);
            } else {
                System.out.println("No available apartments to link.");
            }
        } else if (inputChoice == 2) {
            System.out.println("\nEnter length, width, and height:");
            double length = scanner.nextDouble();
            double width = scanner.nextDouble();
            double height = scanner.nextDouble();
            ParkingPlace newParkingPlace = new ParkingPlace(length, width, height);
            addRoomToEstate(estatesMgr, roomType, newParkingPlace, scanner);

            List<Apartment> availableApartments = getApartmentsWithoutParking(estatesMgr);
            if (!availableApartments.isEmpty()) {
                linkParkingPlaceToApartment(scanner, newParkingPlace, availableApartments);
            } else {
                System.out.println("No available apartments to link. You can link a parking place later.");
            }

        } else {
            System.out.println("\nInvalid input choice.");
        }
    }

    private static void createEstate(HousingEstatesManager estatesMgr, Scanner scanner) {
        try {
            estatesMgr.addEstate(HousingEstate.generate());
            System.out.println("New Estate added.");
        } catch (Exception e) {
            System.out.println("Failure: " + e.getMessage());
        }
    }

    private static void checkInPersonToRoom(HousingEstatesManager estatesMgr, Scanner scanner) {
        System.out.println("\nCheck in a person to the apartment:");

        System.out.println("\nSelect an estate:");
        for (int i = 0; i < estatesMgr.getEstates().size(); i++) {
            System.out.println((i + 1) + ". " + estatesMgr.getEstates().get(i));
        }

        int estateChoice = scanner.nextInt();
        if (estateChoice >= 1 && estateChoice <= estatesMgr.getEstates().size()) {
            HousingEstate selectedEstate = estatesMgr.getEstates().get(estateChoice - 1);

            System.out.println("Select a person:");
            viewPersons(estatesMgr);
            System.out.print("Enter person index: ");
            int personChoice = scanner.nextInt();

            if (personChoice >= 1 && personChoice <= selectedEstate.getPersons().size()) {
                Person selectedPerson = selectedEstate.getPersons().get(personChoice - 1);

                System.out.println("Select an apartment:");
                viewApartments(selectedEstate);
                System.out.print("Enter apartment index: ");
                int apartmentChoice = scanner.nextInt();

                if (apartmentChoice >= 1 && apartmentChoice <= selectedEstate.getApartments().size()) {
                    Apartment selectedApartment = selectedEstate.getApartments().get(apartmentChoice - 1);

                    try {
                        if (selectedApartment.isRented()) {
                            selectedApartment.checkIn(selectedPerson);
                            System.out.println("Person checked in to the apartment.");
                        } else {
                            selectedApartment.startRent(selectedPerson, null, HousingEstate.generateEndDate());
                            ;
                            System.out.println("Person is now the tenant of the apartment.");
                        }
                    } catch (Exception e) {
                        System.out.println("Failed to check in person to the apartment: " + e.getMessage());
                    }
                } else {
                    System.out.println("Invalid apartment choice.");
                }
            } else {
                System.out.println("Invalid person choice.");
            }
        } else {
            System.out.println("Invalid estate choice.");
        }
    }

    private static void checkOutPersonFromRoom(HousingEstatesManager estatesMgr, Scanner scanner) {
        System.out.println("\nCheck out a person from the apartment:");

        System.out.println("\nSelect an estate:");
        for (int i = 0; i < estatesMgr.getEstates().size(); i++) {
            System.out.println((i + 1) + ". " + estatesMgr.getEstates().get(i));
        }

        int estateChoice = scanner.nextInt();
        if (estateChoice >= 1 && estateChoice <= estatesMgr.getEstates().size()) {
            HousingEstate selectedEstate = estatesMgr.getEstates().get(estateChoice - 1);

            System.out.println("Select a person:");
            viewPersons(estatesMgr);
            System.out.print("Enter person index: ");
            int personChoice = scanner.nextInt();

            if (personChoice >= 1 && personChoice <= selectedEstate.getPersons().size()) {
                Person selectedPerson = selectedEstate.getPersons().get(personChoice - 1);

                System.out.println("Select an apartment:");
                viewApartments(selectedEstate);
                System.out.print("Enter apartment index: ");
                int apartmentChoice = scanner.nextInt();

                if (apartmentChoice >= 1 && apartmentChoice <= selectedEstate.getApartments().size()) {
                    Apartment selectedApartment = selectedEstate.getApartments().get(apartmentChoice - 1);

                    try {
                        selectedApartment.checkOut(selectedPerson);
                        System.out.println("Person checked out from the apartment.");
                    } catch (Exception e) {
                        System.out.println("Failed to check out person from the apartment: " + e.getMessage());
                    }
                } else {
                    System.out.println("Invalid apartment choice.");
                }
            } else {
                System.out.println("Invalid person choice.");
            }
        } else {
            System.out.println("Invalid estate choice.");
        }
    }

    private static List<Apartment> getApartmentsWithoutParking(HousingEstatesManager estatesMgr) {
        List<Apartment> availableApartments = new ArrayList<>();
        for (HousingEstate estate : estatesMgr.getEstates()) {
            for (Apartment apartment : estate.getApartments()) {
                if (!apartment.hasParkingPlace()) {
                    availableApartments.add(apartment);
                }
            }
        }
        return availableApartments;
    }

    private static void showTenantLetters(HousingEstatesManager estatesMgr) {
        boolean hasPersonsWithLetters = false;

        for (HousingEstate estate : estatesMgr.getEstates()) {
            for (Person person : estate.getPersons()) {
                if (person.getLetters() != null) {
                    if (hasPersonsWithLetters) {
                        System.out.println("\nPersons with Tenant Letters:");
                        hasPersonsWithLetters = true;
                    }
                    System.out.println("- " + person + " (Letters: " + person.getLetters().size() + ")");
                }
            }
        }
    }

    private static void outputStatus(HousingEstatesManager estatesMgr){
        PrintStream stream = null;

        File outputFile = new File("output.txt");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(outputFile);
            stream = new PrintStream(fileOutputStream);
            
            for (Person person : estatesMgr.getEstates().get(0).getPersons()) {
                person.Dump(stream);
            }

            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void linkParkingPlaceToApartment(Scanner scanner, ParkingPlace parkingPlace, List<Apartment> availableApartments) {
        System.out.println("\nSelect an apartment to link the parking place:");
        for (int i = 0; i < availableApartments.size(); i++) {
            System.out.println((i + 1) + ". " + availableApartments.get(i));
        }

        int apartmentChoice = scanner.nextInt();
        if (apartmentChoice >= 1 && apartmentChoice <= availableApartments.size()) {
            Apartment selectedApartment = availableApartments.get(apartmentChoice - 1);
            selectedApartment.setLinkedParkingPlace(parkingPlace);
            System.out.println("Parking place linked to the selected apartment.");
        } else {
            System.out.println("Invalid apartment choice.");
        }
    }

    private static void addRoomToEstate(HousingEstatesManager estatesMgr, Room.RoomType roomType, Room newRoom,
            Scanner scanner) {
        System.out.println("\nSelect an estate to add the room:");
        for (int i = 0; i < estatesMgr.getEstates().size(); i++) {
            System.out.println((i + 1) + ". " + estatesMgr.getEstates().get(i));
        }

        int estateChoice = scanner.nextInt();
        if (estateChoice >= 1 && estateChoice <= estatesMgr.getEstates().size()) {
            HousingEstate selectedEstate = estatesMgr.getEstates().get(estateChoice - 1);
            if (roomType == Room.RoomType.Apartment) {
                selectedEstate.addApartment((Apartment) newRoom);
            } else if (roomType == Room.RoomType.ParkingPlace) {
                selectedEstate.addParkingPlace((ParkingPlace) newRoom);
            }
            System.out.println("New " + roomType + " created and added to the estate.");
        } else {
            System.out.println("Invalid estate choice.");
        }
    }

    private static void viewApartments(HousingEstate selectedEstate) {
        System.out.println("\nApartments:");
        List<Apartment> apartments = selectedEstate.getApartments();

        for (int i = 0; i < apartments.size(); i++) {
            Apartment apartment = apartments.get(i);
            String status = apartment.isRented() ? "Occupied" : "Available";
            System.out.println((i + 1) + ". " + apartment + " - Status: " + status);
        }
    }
}