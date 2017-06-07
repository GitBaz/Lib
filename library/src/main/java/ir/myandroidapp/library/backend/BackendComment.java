package ir.myandroidapp.library.backend;

/**
 * Created by kam.amir on 5/8/17.
 */

public class BackendComment {

    private String Id = "";
    private String comment = "";
    private String itemId = "";
    private String username = "";

    public BackendComment() {
    }

    public String getComment() {
        return comment;
    }

    public String getItemId() {
        return itemId;
    }

    public String getId(){
        return Id;
    }

    public String getUserName() {
        return username;
    }

    public void create(String cmnt, String item, String un, String id) {
        comment = cmnt;
        itemId = item;
        username = un;
        Id = id;
    }

}