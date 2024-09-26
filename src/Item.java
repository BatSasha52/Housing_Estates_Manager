import java.io.PrintStream;
import java.util.Random;

public abstract class Item {

    enum ItemType {
      Object,
      Vehicle
    }
  
    private String name;
    private double volume;
    private ItemType itemType;

    public Item(ItemType itemType, String name, double volume) {
        this.name = name;
        this.itemType = itemType;
        this.volume = volume;
    }

    public Item(ItemType itemType, String name, double length, double width, double height) {
        this.name = name;
        this.itemType = itemType;
        this.volume = Utils.getVolume(length, width, height);;
    }

    public String getName() {
        return name;
    }

    public double getVolume() {
        return volume;
    }

    public ItemType getItemType() {
      return itemType;
    }

    public static Item generate() {

      Item item = null;
      Random random = new Random();
      ItemType itemType = ItemType.values()[random.nextInt(2)]; // 0, 1

      switch(itemType) {
        case Vehicle: item = Vehicle.generate();
        break;
        case Object: item = ObjectItem.generate();   
        break;
      }
      
      return item;      
    }

    public void Dump(PrintStream stream) {
      if(itemType == ItemType.Object) 
      stream.println(getName() + ": " + getVolume());
      else
      stream.print(getItemType() + " " + getName() + ": " + getVolume() + "m^3  ");
    }

    @Override
    public String toString() {
      if(itemType == ItemType.Object) 
        return getName() + ": " + getVolume();
      else
        return getItemType() + " " + getName() + ": " + getVolume();
    }

}