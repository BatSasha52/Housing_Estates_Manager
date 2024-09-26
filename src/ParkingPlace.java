import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingPlace extends Room {

    private Room parentRoom = null;
    private List<Item> storedItems = new ArrayList<>();

    public ParkingPlace(double volume) {
        super(RoomType.ParkingPlace, volume);
    }

    public ParkingPlace(double lenght, double witdth, double height) {
        super(RoomType.ParkingPlace, lenght, witdth, height);
    }

    public List<Item> getStoredItems() {
        return storedItems;
    }

    public void addStoredItem(Item item) throws TooManyThingsException {
        if (!hasSpace(item.getVolume()))
            throw new TooManyThingsException();
        storedItems.add(item);
    }

    public void removeStoredItem(Item item) {
        storedItems.remove(item);
    }

    public boolean hasSpace(double volume) {
        double itemSize = 0;

        for (Item item : storedItems)
            itemSize += item.getVolume();

        return (itemSize + volume) <= this.volume;
    }

    public static ParkingPlace generate() throws TooManyThingsException {
        ParkingPlace space = new ParkingPlace(Utils.generateNum(20.0, 30.0));
        return space;
    }

    public void removeItems() {
        storedItems.clear();
    }

    protected boolean canRent(Person person) {
        return !isRented();
    }

    public boolean isRented() {
        return parentRoom != null;
    }

    public void startRent(Apartment apartment) {
        this.parentRoom = apartment;
    }

    public void endRent() {
        this.parentRoom = null;
    }

    @Override
    public void Dump(PrintStream stream) {
        super.Dump(stream);
        stream.println("Items inside the parking place:");
        for (Item item : storedItems.stream().sorted(Comparator.comparingDouble(x -> x.getVolume())).collect(Collectors.toList())) {
            item.Dump(stream);
        }
      }

    @Override
    public String toString() {
        if (parentRoom != null) {
            return "ID = " + id + ", volume = " + volume + "; (" + "ID = " + parentRoom.getId() + ")";
        } else {
            return "ID = " + id + ", volume = " + volume;
        }
    }

    
}