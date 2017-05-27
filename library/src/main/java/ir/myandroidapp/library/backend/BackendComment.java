package ir.myandroidapp.library.backend;

import android.widget.LinearLayout;

import com.backtory.java.internal.BacktoryCallBack;
import com.backtory.java.internal.BacktoryObject;
import com.backtory.java.internal.BacktoryQuery;
import com.backtory.java.internal.BacktoryResponse;
import com.backtory.java.internal.BacktoryUser;

import java.util.List;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.ImagePicker;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 5/8/17.
 */

public class BackendComment {

    private String Id = "";
    private String comment = "";
    private String itemId = "";
    private String username = "";

    boolean exist = false;

    Core core;

    public BackendComment(Core cre) {
        core = cre;
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

    public void create(String cmnt, String item) {
        comment = cmnt;
        itemId = item;
    }

    private void create(String cmnt, String item, String un, String id) {
        comment = cmnt;
        itemId = item;
        username = un;
        Id = id;
    }

    public void writeComment(final SimpleResponse response) {
        BacktoryObject object = new BacktoryObject("Comments");
        object.put("user", BacktoryUser.getCurrentUser().getUserId());
        object.put("comment", comment);
        object.put("itemId", itemId);
        object.put("permission", "0");
        object.put("username", BacktoryUser.getCurrentUser().getFirstName());
        object.saveInBackground(new BacktoryCallBack<Void>() {
            @Override
            public void onResponse(BacktoryResponse<Void> backtoryResponse) {
                if (backtoryResponse.isSuccessful())
                    response.onSuccess();
                else {
                    core.toast(core.getString(R.string.connection_error));
                    response.onFailure();
                }
            }
        });
    }

    public void readComment(String id, final CommentResponse response) {
        new BacktoryQuery("Comments").whereMatches("itemId", id).whereMatches("permission", "1")
                .findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
                    @Override
                    public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                        if (backtoryResponse.isSuccessful()) {

                            int count = backtoryResponse.body().size();
                            BackendComment[] bcmt = new BackendComment[count];

                            for (int i = 0; i < count; i++) {
                                bcmt[i] = new BackendComment(core);
                                BacktoryObject obj = backtoryResponse.body().get(i);
                                bcmt[i].create(obj.get("comment").toString(),
                                        obj.get("itemId").toString(), obj.get("username").toString(),
                                        obj.getObjectId().toString());
                            }

                            exist = true;
                            response.onSuccess(bcmt);

                        } else {
                            core.toast(core.getString(R.string.connection_error));
                        }

                    }
                });
    }

    public interface CommentResponse {
        void onSuccess(BackendComment[] cmt);
    }

    public boolean exists() {
        return exist;
    }


}