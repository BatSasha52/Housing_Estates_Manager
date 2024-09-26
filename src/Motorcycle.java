import java.io.PrintStream;

public class Motorcycle extends Vehicle {
    private String engineType;

    public Motorcycle(String name, double volume, String engineType) {
        super(VehicleType.Motorcycle, name, volume);
        this.engineType = engineType;
    }

    public String getEngineType() {
        return engineType;
    }

    public static Motorcycle generate() {
        String engineType = Utils.generateString(new String[] {"Single-cylinder", "V-twin", "Inline-four"});
        return new Motorcycle(generateName(), Utils.generateNum(4.0, 15.0), engineType);
      }

    public static String generateName() {
        String[] names = {
        "Harley-Davidson Sportster",
        "Honda CBR600RR",
        "Yamaha MT-09",
        "Ducati Panigale V4",
        "Kawasaki Ninja ZX-10R",
        "BMW R1250GS"
        };      

        return names[Utils.generateNum(0, names.length)];
   }

   @Override
   public void Dump(PrintStream stream) {
     super.Dump(stream);
     stream.print(this.engineType + " Engine\n");
     }
}