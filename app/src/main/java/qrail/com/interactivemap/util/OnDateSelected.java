package qrail.com.interactivemap.util;

/**
 * Created by emp236 on 7/5/2017.
 */

public interface OnDateSelected {
    void onDateSelected(int year, int month, int day, String dayOfweek);

    /**
     * singletone Util class to obtain FareDetails.
     *
     */
    class FareUtils {

        private static FareUtils sInstance;
        public static FareUtils getInstance() {
            if (sInstance == null) {
                sInstance = new FareUtils();
            }
            return sInstance;
        }

        /**
         * return fare of trip conditionally.
         * including discount during sunday & national holidays.
         *
         * @param tripDistance
         * @param isHoliday
         * @return
         */
        public String getDMRCFare(String tripDistance,boolean isHoliday){
            int farePrice = 0;
            float distance = 0;
            try {
                distance = Float.parseFloat(tripDistance);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            if (distance >= 0 && distance < 2) {
                farePrice=10;
            }
            else if (distance >= 2 && distance < 5){
                farePrice = isHoliday ? 10 : 20;
            }
            else if (distance >= 5 && distance < 12){
                farePrice = isHoliday ? 20 : 30;

            }
            else if (distance >= 12 && distance < 21){
                farePrice = isHoliday ? 30 : 40;
            }
            else if (distance >= 21 && distance <= 32){
                farePrice = isHoliday ? 40 : 50;
            }
            else if (distance >32){
                farePrice = isHoliday ? 50 : 60;
            }

            return String.valueOf(farePrice);
        }
    }
}
