package ir.myandroidapp.library.backend;

import ir.myandroidapp.library.Core;

/**
 * Created by kam.amir on 4/24/17.
 */
public class BackendObject {

    private String id = "0";
    private String name = "0";
    private String pics = "0";
    private String pp = "0";
    private String sp = "0";
    private String info = "0";
    private String details = "0";
    private String cat = "0";
    private String place = "0";
    private String page = "0";
    private String user = "0";
    private String permission = "0";
    private String location = "0";
    private String number = "0";

    public BackendObject() {
    }

    public void setId(String s) {
        id = s;
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

    public void setPlace(String s) {
        place = s;
    }

    public void setPage(String s) {
        page = s;
    }

    public void setUser(String s) {
        user = s;
    }

    public void setPermission(String s) {
        permission = s;
    }

    public void setLocation(String s) {
        location = s;
    }

    public void setNumber(String s){
        number=s;
    }


    public String getId() {
        if (!id.equals(""))
            return id;
        else
            return "0";
    }

    public String getName() {
        if (!name.equals(""))
            return name;
        else
            return "0";
    }

    public String getPics() {
        if (!pics.equals(""))
            return pics;
        else
            return "https://storage.backtory.com/nokchefile/usersPics/gray.png|";
    }

    public String getPrimaryPrice() {
        if (!pp.equals(""))
            return pp;
        else
            return "0";
    }

    public String getSecondaryPrice() {
        if (!sp.equals(""))
            return sp;
        else
            return "0";
    }

    public String getInfo() {
        if (!info.equals(""))
            return info;
        else
            return "0";
    }

    public String getDetails() {
        if (!details.equals(""))
            return details;
        else
            return "0";
    }

    public String getCat() {
        if (!cat.equals(""))
            return cat;
        else
            return "0";
    }

    public String getPlace() {
        if (!place.equals(""))
            return place;
        else
            return "0";
    }

    public String getPage() {
        if (!page.equals(""))
            return page;
        else
            return "0";
    }

    public String getUser() {
        if (!user.equals(""))
            return user;
        else
            return "0";
    }

    public String getPermission() {
        if (!permission.equals(""))
            return permission;
        else
            return "0";
    }

    public String getLocation() {
        if (!location.equals(""))
            return location;
        else
            return "0";
    }

    public String getNumber(){
        if (!number.equals(""))
            return number;
        else
            return "0";
    }

}