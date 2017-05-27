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

    public void setLocation(String s){
        location = s;
    }


    public String getId() {
        return id;
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

    public String getPlace() {
        return place;
    }

    public String getPage() {
        return page;
    }

    public String getUser() {
        return user;
    }

    public String getPermission() {
        return permission;
    }

    public String getLocation(){
        return location;
    }


}