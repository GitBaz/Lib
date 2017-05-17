package ir.myandroidapp.library.backend;

/**
 * Created by kam.amir on 4/24/17.
 */
public class BackendObject {

    private String name = "";
    private String pics = "";
    private String pp = "";
    private String sp = "";
    private String info = "";
    private String details = "";
    private String cat = "";
    private String checked = "";
    private String place = "";
    private String page = "";

    public BackendObject() {
    }

    public void setName(String s) {
        name = s;
    }

    public void setPics(String s) {
        pics = s;
    }

    public void setPrimaryPrice(String s) {
        pp = s;
    }

    public void setSecondaryPrice(String s) {
        sp = s;
    }

    public void setInfo(String s) {
        info = s;
    }

    public void setDetails(String s) {
        details = s;
    }

    public void setCat(String s) {
        cat = s;
    }

    public void setChecked(String s) {
        checked = s;
    }

    public void setPlace(String s) {
        place = s;
    }

    public void setPage(String s) {
        page = s;
    }


    public String getName() {
        return name;
    }

    public String getPics() {
        return pics;
    }

    public String getPrimaryPrice() {
        return pp;
    }

    public String getSecondaryPrice() {
        return sp;
    }

    public String getInfo() {
        return info;
    }

    public String getDetails() {
        return details;
    }

    public String getCat() {
        return cat;
    }

    public String getChecked() {
        return checked;
    }

    public String getPlace() {
        return place;
    }

    public String getPage() {
        return page;
    }

}