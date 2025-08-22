package qrail.com.interactivemap.util;

import java.util.HashMap;

import qrail.com.interactivemap.R;

public class AppUtils {

   // public static HashMap<String, Integer> LINE_COLOR = fillLineColor();
    public static HashMap<String, Integer> COLOR = fillColor();
    public static HashMap<String, String> DIVERSING_LINE_TERMINALS = fillDiversingLieTermianls();
    public static HashMap<String, String> OLA_CATEGORIES = setOlaCategories();


    private static HashMap<String, Integer> fillColor() {

        COLOR = new HashMap<String, Integer>();
        COLOR.put("18", R.color.colorPrimary);
        COLOR.put("13", R.color.yellow);
        COLOR.put("16", R.color.green);
        return COLOR;
    }

    public static int getColor(String id) {
        // System.out.println(COLOR.get(id));
        return COLOR.get(id);
    }

    private static HashMap<String, String> fillDiversingLieTermianls() {

        DIVERSING_LINE_TERMINALS = new HashMap<String, String>();
        DIVERSING_LINE_TERMINALS.put("15", "Mundka");
        DIVERSING_LINE_TERMINALS.put("16", "Kirti Nagar");
        DIVERSING_LINE_TERMINALS.put("17", "Inderlok");
        DIVERSING_LINE_TERMINALS.put("18", "Dwarika Sector 21");
        DIVERSING_LINE_TERMINALS.put("19", "Noida City Center");
        DIVERSING_LINE_TERMINALS.put("20", "vaishali");
        DIVERSING_LINE_TERMINALS.put("22", "Botanical Garden");

        return DIVERSING_LINE_TERMINALS;
    }


    private static HashMap<String, String> setOlaCategories() {

        OLA_CATEGORIES = new HashMap<String, String>();
        OLA_CATEGORIES.put("Mini", "compact");
        OLA_CATEGORIES.put("Sedan", "economy_sedan");
        OLA_CATEGORIES.put("Prime", "luxury_sedan");
        OLA_CATEGORIES.put("AUTO", "local_auto");
        OLA_CATEGORIES.put("TFS", "tfs");
        OLA_CATEGORIES.put("Kaalipeeli", "local_taxi");

        return OLA_CATEGORIES;
    }
}
