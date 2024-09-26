import java.io.PrintStream;

public class OffRoadCar extends Vehicle {
    private String engineCapacity;

    public OffRoadCar(String name, double volume, String engineCapacity) {
        super(VehicleType.OffRoadCar, name, volume);
        this.engineCapacity = engineCapacity;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public static OffRoadCar generate() {
        String engineCapacity = Utils.generateString(new String[] {"2.0L", "2.5L", "3.0L"});
        return new OffRoadCar(generateName(), Utils.generateNum(4.0, 15.0), engineCapacity);
      }

    public static String generateName() {
        String[] names = {
            "Jeep Wrangler",
            "Toyota Land Cruiser",
            "Land Rover Defender",
            "Ford Bronco",
            "Chevrolet Colorado ZR2",
            "Nissan Patrol"
        };      

        return names[Utils.generateNum(0, names.length)];
   }

   @Override
  public void Dump(PrintStream stream) {
    super.Dump(stream);
    stream.print(this.engineCapacity + " Engine\n");
    }

}