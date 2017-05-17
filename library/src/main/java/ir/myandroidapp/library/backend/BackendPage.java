package ir.myandroidapp.library.backend;

/**
 * Created by kam.amir on 5/3/17.
 */

public class BackendPage {

    private String logo;
    private String brand;
    private String info;
    private String number;
    private String telegramLink;
    private String webLink;

    public BackendPage(){

    }

    public void setLogo(String s){
        logo = s;
    }

    public void setBrand(String s){
        brand = s;
    }

    public void setInfo(String s){
        info = s;
    }

    public void setNumber(String s){
        number = s;
    }

    public void setTelegramLink(String s){
        telegramLink = s;
    }

    public void setWebLink(String s){
        webLink = s;
    }

    public String getLogo(){
        return logo;
    }

    public String getBrand(){
        return brand;
    }

    public String getInfo(){
        return info;
    }

    public String getNumber(){
        return number;
    }

    public String getTelegramLink(){
        return telegramLink;
    }

    public String getWebLink(){
        return webLink;
    }


}
