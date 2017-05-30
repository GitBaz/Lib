package ir.myandroidapp.library.backend;

/**
 * Created by kam.amir on 5/3/17.
 */

public class BackendPage {

    private String id = "0";
    private String logo = "0";
    private String brand = "0";
    private String info = "0";
    private String number = "0";
    private String detail = "0";
    private String user = "0";
    private String permission = "0";
    private String cat = "0";
    private String place = "0";
    private String location = "0";
    private String lat = "0";
    private String lng = "0";
    private String sliderPic = "0";
    private String sponserPic = "0";


    public BackendPage() {

    }

    public void setId(String s) {
        id = s;
    }

    public void setLogo(String s) {
        logo = s;
    }

    public void setBrand(String s) {
        brand = s;
    }

    public void setInfo(String s) {
        info = s;
    }

    public void setNumber(String s) {
        number = s;
    }

    public void setDetail(String s) {
        detail = s;
    }

    public void setUser(String s) {
        user = s;
    }

    public void setPermission(String s) {
        permission = s;
    }

    public void setCat(String s) {
        cat = s;
    }

    public void setPlace(String s) {
        place = s;
    }

    public void setLocation(String s) {
        location = s;
    }

    public void setLat(String s){
        lat = s;
    }

    public void setLng(String s){
        lng = s;
    }

    public void setSliderPic(String s) {
        sliderPic = s;
    }

    public void setSponserPic(String s){
        sponserPic = s;
    }


    public String getId() {
        if (!id.equals(""))
            return id;
        else
            return "0";
    }

    public String getLogo() {
        if (!logo.equals(""))
            return logo;
        else
            return "https://storage.backtory.com/nokchefile/usersPics/gray.png";    }

    public String getBrand() {
        if (!brand.equals(""))
            return brand;
        else
            return "0";    }

    public String getInfo() {
        if (!info.equals(""))
            return info;
        else
            return "0";    }

    public String getNumber() {
        if (!number.equals(""))
            return number;
        else
            return "0";    }

    public String getDetail() {
        if (!detail.equals(""))
            return detail;
        else
            return "0";    }

    public String getUser() {
        if (!user.equals(""))
            return user;
        else
            return "0";    }

    public String getPermission() {
        if (!permission.equals(""))
            return permission;
        else
            return "0";    }

    public String getCat() {
        if (!cat.equals(""))
            return cat;
        else
            return "0";    }

    public String getPlace() {
        if (!place.equals(""))
            return place;
        else
            return "0";    }

    public String getLocation() {
        if (!location.equals(""))
            return location;
        else
            return "0";    }

    public String getLat(){
        if (!lat.equals(""))
            return lat;
        else
            return "0";
    }

    public String getLng(){
        if (!lng.equals(""))
            return lng;
        else
            return "0";
    }

    public String getSliderPic(){
        if (!sliderPic.equals(""))
            return sliderPic;
        else
            return "0";
    }

    public String getSponserPic(){
        if (!sponserPic.equals(""))
            return sponserPic;
        else
            return "0";
    }

}