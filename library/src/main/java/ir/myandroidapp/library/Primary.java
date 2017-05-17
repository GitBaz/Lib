package ir.myandroidapp.library;

import okhttp3.OkHttpClient;

/**
 * Created by kam.amir on 4/13/17.
 */

public class Primary {

    private static String brand = "";
    private static int colorPrimary = 0;
    private static int colorPrimaryLight = 0;
    private static int colorPrimaryDark = 0;
    private static String link = "";

    public void init(String brnd, String uploadLink, int primary, int primaryLight, int primaryDark) {
        brand = brnd;
        colorPrimary = primary;
        colorPrimaryLight = primaryLight;
        colorPrimaryDark = primaryDark;
        link = uploadLink;
    }

    public String getBrand() {
        return brand;
    }

    public int getPrimary() {
        return colorPrimary;
    }

    public int getPrimaryLight() {
        return colorPrimaryLight;
    }

    public int getPrimaryDark() {
        return colorPrimaryDark;
    }

    public String getUploadLink(){
        return link;
    }

}