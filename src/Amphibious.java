import java.io.PrintStream;

public class Amphibious extends Vehicle {
  private String propulsionType;

  public Amphibious(String name, double volume, String propulsionType) {
    super(VehicleType.Amphibious, name, volume);
    this.propulsionType = propulsionType;
  }

  public String getPropulsionType() {
    return propulsionType;
  }

  public static Amphibious generate() {
    String propulsionType = Utils.generateString(new String[] { "Gas", "Electric", "Hybrid" });
    return new Amphibious(generateName(), Utils.generateNum(4.0, 15.0), propulsionType);
  }

  public static String generateName() {
    String[] names = {
        "Stridsvagn",
        "Sherman DD",
        "PT-76",
        "Conestoga",
        "Orukter Amphibolos",
        "Alligator",
        "Alvis Stalwart",
        "M3 Amphibious Rig",
        "Phibian",
        "Humdinga",
        "DUKW",
        "LARC-V",
        "Dampervan",
        "Triumph Herald",
        "Nissank"
    };

    return names[Utils.generateNum(0, names.length)];
  }

  @Override
  public void Dump(PrintStream stream) {
    super.Dump(stream);
    stream.print(this.propulsionType + " Propulsion\n");
    }

}