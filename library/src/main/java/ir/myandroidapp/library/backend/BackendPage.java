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

    public BackendPage() {

    }

    public void setId(String s){
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

    public void setPermission(String s){
        permission = s;
    }

    public void setCat(String s){
        cat = s;
    }

    public void setPlace(String s){
        place = s;
    }

    public void setLocation(String s){
        location = s;
    }


    public String getId(){
        return id;
    }

    public String getLogo() {
        return logo;
    }

    public String getBrand() {
        return brand;
    }

    public String getInfo() {
        return info;
    }

    public String getNumber() {
        return number;
    }

    public String getDetail() {
        return detail;
    }

    public String getUser() {
        return user;
    }

    public String getPermission(){
        return permission;
    }

    public String getCat(){
        return cat;
    }

    public String getPlace(){
        return place;
    }

    public String getLocation(){
        return location;
    }

}
