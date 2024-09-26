public class ObjectItem extends Item {
  
  public ObjectItem(String name, double volume) {
      super(Item.ItemType.Object, name, volume);
  }

  public ObjectItem(String name, double length, double width, double height) {
      super(Item.ItemType.Object, name, Utils.getVolume(length, width, height));
  }

  public static ObjectItem generate() {
    return new ObjectItem(generateName(), Utils.generateNum(1.0, 5.0));
  }

  public static String generateName() {

    String[] names = {
      "desk",
      "box",
      "shelve",
      "chair"
    };      

    return names[Utils.generateNum(0, names.length)];      
  }
  
}