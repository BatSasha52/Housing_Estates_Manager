import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HousingEstatesManager {
  private List<HousingEstate> estates = new ArrayList<>();

  public HousingEstatesManager() {
  }

  public List<HousingEstate> getEstates() {
    return estates;
  }

  public void addEstate(HousingEstate estate) {
    this.estates.add(estate);
  }

  public void removeEstate(HousingEstate estate) {
    this.estates.remove(estate);
  }

  public void setCurrDate(Date currDate){
      HousingEstate.setCurrDate(currDate);
  }

  public void checkStatus(Date currDate) {
   // System.out.println(currDate);

    for (HousingEstate housingEstate : estates) {
      housingEstate.checkStatus(currDate);
    } 

  }
}