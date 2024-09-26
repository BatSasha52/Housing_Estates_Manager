import java.io.PrintStream;

public class Boat extends Vehicle {
  private String hullType;

  public Boat(String name, double volume, String hullType) {
    super(VehicleType.Boat, name, volume);
    this.hullType = hullType;
  }

  public String getHullType() {
    return hullType;
  }

  public static Boat generate() {
    String hullType = Utils.generateString(new String[] { "Displacement", "Flat-Bottomed", "Round-Bottomed" });
    return new Boat(generateName(), Utils.generateNum(4.0, 15.0), hullType);
  }

  public static String generateName() {
    String[] names = {
        "Aqua Vanguard",
        "Sea Serpent",
        "Neptune's Chariot",
        "Mariner's Dream",
        "Storm Surfer",
        "Sea Phoenix",
        "Nautical Nomad",
        "Harbor Hero",
        "Tidal Titan"
    };

    return names[Utils.generateNum(0, names.length)];
  }

  @Override
  public void Dump(PrintStream stream) {
    super.Dump(stream);
    stream.print(this.hullType + " Hull\n");
    }

}