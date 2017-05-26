package ir.myandroidapp.library.backend;

/**
 * Created by kam.amir on 5/3/17.
 */

public class BackendPage {

    private String id = "";
    private String logo = "";
    private String brand = "";
    private String info = "";
    private String number = "";
    private String detail = "";
    private String user = "";
    private String permission = "";
    private String cat = "";
    private String place = "";
    private String location = "";

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
