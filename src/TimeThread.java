import java.util.Calendar;

public class TimeThread extends Thread {

    private static final int timePeriod = 5;
    private static final int checkPeriod = 10;

    private HousingEstatesManager estatesMgr;

    public TimeThread(HousingEstatesManager estatesMgr) {
        this.estatesMgr = estatesMgr;
    }

    @Override
    public void run() {

        int  i = 0;
        Calendar calendar = Calendar.getInstance();

        while (true) {
            try {
                
                if (i % timePeriod == 0){ 
                    calendar.add(Calendar.DATE, 1);
                    estatesMgr.setCurrDate(calendar.getTime());
                }
                if (i % checkPeriod == 0 ) estatesMgr.checkStatus(calendar.getTime());
                
                ++ i;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}