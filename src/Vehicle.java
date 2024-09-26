public class Vehicle extends Item {

  enum VehicleType {
    OffRoadCar,
    CityCar,
    Amphibious,
    Boat,
    Motorcycle
  }

  VehicleType vehicleType;

  public Vehicle(VehicleType vehicleType, String name, double volume) {
    super(Item.ItemType.Vehicle, name, volume);
    this.vehicleType = vehicleType;
  }

  public VehicleType getVehicleType() {
    return vehicleType;
  }

  public static Vehicle generate() {
    Vehicle vehicle = null;
    VehicleType vehicleType = VehicleType.values()[Utils.generateNum(VehicleType.OffRoadCar.ordinal(), VehicleType.Motorcycle.ordinal())];

    switch (vehicleType) {
      case OffRoadCar:
        vehicle = OffRoadCar.generate();
        break;
      case CityCar:
        vehicle = CityCar.generate();
        break;
      case Amphibious:
        vehicle = Amphibious.generate();
        break;
      case Boat:
        vehicle = Boat.generate();
        break;
      case Motorcycle:
        vehicle = Motorcycle.generate();
        break;
    }

    return vehicle;
  }
}