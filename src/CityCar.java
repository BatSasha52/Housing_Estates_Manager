import java.io.PrintStream;

public class CityCar extends Vehicle {
    private String fuelType;

    public CityCar(String name, double volume, String fuelType) {
        super(VehicleType.CityCar, name, volume);
        this.fuelType = fuelType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public static CityCar generate() {
        String fuelType = Utils.generateString(new String[] {"Gasoline", "Diesel", "Electricity"});
        return new CityCar(generateName(), Utils.generateNum(4.0, 15.0), fuelType);
      }

    public static String generateName() {
        String[] names = {
        "Honda Fit",
        "Toyota Yaris",
        "Ford Fiesta",
        "Chevrolet Spark",
        "Nissan Micra",
        "Volkswagen Polo",
        "Hyundai Accent",
        "Kia Rio",
        "Porsche 911"
        };      

        return names[Utils.generateNum(0, names.length)];
   }

   @Override
  public void Dump(PrintStream stream) {
    super.Dump(stream);
    stream.print("on " + this.fuelType + " Fuel\n");
    }

}