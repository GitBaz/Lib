package ir.myandroidapp.library.backend;

import com.backtory.java.internal.BacktoryCallBack;
import com.backtory.java.internal.BacktoryFile;
import com.backtory.java.internal.BacktoryObject;
import com.backtory.java.internal.BacktoryQuery;
import com.backtory.java.internal.BacktoryResponse;
import com.backtory.java.internal.BacktoryUser;
import com.backtory.java.internal.BulkOperation;
import com.backtory.java.internal.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 4/8/17.
 */
public class BackendData {

    Core core;

    public BackendData(Core cre) {
        core = cre;
    }

    public void uploadImage(String path, final SimpleResponse response) {
        new BacktoryFile().beginUpload(new File(path), "usersPics/").commitInBackground(new BacktoryCallBack<String>() {
            @Override
            public void onResponse(BacktoryResponse<String> backtoryResponse) {
                if (backtoryResponse.isSuccessful())
                    response.onSuccess();
                else {
                    response.onFailure();
                    core.toast(core.getString(R.string.upload_error));
                }
            }
        });
    }

    public void uploadImages(final String[] paths, final SimpleResponse response) {
        List<Request.UploadRequest> uploadRequests = new ArrayList<>();
        for (String address : paths)
            uploadRequests.add(new BacktoryFile().beginUpload(new File(address), "usersPics/"));

        new BulkOperation.BulkUpload(uploadRequests).commitInBackground(new BacktoryCallBack<List<String>>() {
            @Override
            public void onResponse(BacktoryResponse<List<String>> backtoryResponse) {
                if (backtoryResponse.isSuccessful())
                    response.onSuccess();
                else {
                    response.onFailure();
                }
            }
        });

    }

    public void put(String table, BackendObject obj, final SimpleResponse response) {
        BacktoryObject object = new BacktoryObject(table);
        object.put("name", obj.getName());
        object.put("pics", obj.getPics());
        object.put("pp", obj.getPrimaryPrice());
        object.put("sp", obj.getSecondaryPrice());
        object.put("info", obj.getInfo());
        object.put("details", obj.getDetails());
        object.put("cat", obj.getCat());
        object.put("place", obj.getPlace());
        object.put("user", BacktoryUser.getCurrentUser().getUserId());
        object.put("page", obj.getPage());
        object.put("permission", "0");
        object.saveInBackground(new BacktoryCallBack<Void>() {
            @Override
            public void onResponse(BacktoryResponse<Void> backtoryResponse) {
                if (backtoryResponse.isSuccessful()) {
                    response.onSuccess();
                } else {
                    response.onFailure();
                    core.toast(core.getString(R.string.upload_error));
                }
            }
        });
    }

    public void put(String table, BackendPage page, final SimpleResponse response) {
        BacktoryObject object = new BacktoryObject(table);
        object.put("brand", page.getBrand());
        object.put("logo", page.getLogo());
        object.put("info", page.getInfo());
        object.put("number", page.getNumber());
        object.put("telegramLink", page.getTelegramLink());
        object.put("webLink", page.getWebLink());
        object.put("user", BacktoryUser.getCurrentUser().getUserId());
        object.saveInBackground(new BacktoryCallBack<Void>() {
            @Override
            public void onResponse(BacktoryResponse<Void> backtoryResponse) {
                if (backtoryResponse.isSuccessful()) {
                    response.onSuccess();
                } else {
                    response.onFailure();
                    core.toast(core.getString(R.string.upload_error));
                }
            }
        });
    }

    public void getUserPage(final GetUserPage response) {
        BacktoryQuery.getQuery("Pages").whereMatches("user", BacktoryUser.getCurrentUser().getUserId()).
                findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
                    @Override
                    public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                        if (backtoryResponse.isSuccessful()) {

                            if (backtoryResponse.body().isEmpty()) {
                                response.onNotExists();
                            } else {
                                BackendPage page = new BackendPage();
                                BacktoryObject obj = backtoryResponse.body().get(0);
                                page.setBrand(obj.get("brand").toString());
                                page.setLogo(obj.get("logo").toString());
                                page.setInfo(obj.get("info").toString());
                                page.setNumber(obj.get("number").toString());
                                page.setWebLink(obj.get("webLink").toString());
                                page.setTelegramLink(obj.get("telegramLink").toString());
                                response.onExists(page);
                            }
                        } else {
                            response.onFailure();
                            core.toast(core.getString(R.string.connection_error));
                        }
                    }
                });

    }

    public void createUser(final SimpleResponse response) {
        BacktoryObject object = new BacktoryObject("user");
        object.put("userId", BacktoryUser.getCurrentUser().getUserId());
        object.saveInBackground(new BacktoryCallBack<Void>() {
            @Override
            public void onResponse(BacktoryResponse<Void> backtoryResponse) {
                if (backtoryResponse.isSuccessful())
                    response.onSuccess();
                else
                    response.onFailure();
            }
        });

    }

    public void likePost(final String postId, final boolean like) {
        new BacktoryQuery("user").whereMatches("userId", BacktoryUser.getCurrentUser().getUserId())
                .findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
                    @Override
                    public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                        if (backtoryResponse.isSuccessful()) {
                            if (backtoryResponse.body().size() == 0)
                                createUser(new SimpleResponse() {
                                    @Override
                                    public void onSuccess() {
                                        likePost(postId, like);
                                    }

                                    @Override
                                    public void onFailure() {
                                        core.toast(core.getString(R.string.connection_error));
                                    }
                                });
                            else {

                                String postLike = "";

                                if (backtoryResponse.body().get(0).get("postLike") != null)
                                    postLike = backtoryResponse.body().get(0).get("postLike").toString();


                                BacktoryObject object = backtoryResponse.body().get(0);

                                if (!postLike.contains(postId) && like)
                                    postLike += postId + ":1|";
                                else if (!postLike.contains(postId) && !like)
                                    postLike += postId + ":0|";
                                else if (postLike.contains(postId + ":0|") && like)
                                    postLike = postLike.replace(postId + ":0|", postId + ":1|");
                                else if (postLike.contains(postId + ":1|") && like)
                                    postLike = postLike.replace(postId + ":1|", "");
                                else if (postLike.contains(postId + ":0|") && !like)
                                    postLike = postLike.replace(postId + ":0|", "");
                                else if (postLike.contains(postId + ":1|") && !like)
                                    postLike = postLike.replace(postId + ":1|", postId + ":0|");

                                object.put("postLike", postLike);
                                object.saveInBackground(new BacktoryCallBack<Void>() {
                                    @Override
                                    public void onResponse(BacktoryResponse<Void> backtoryResponse) {

                                    }
                                });
                            }

                        } else {
                            core.toast(core.getString(R.string.connection_error));
                        }

                    }

                });

    }

    public void getSearch(String name, final GetObject object) {
        BacktoryQuery.getQuery("Products").whereContains("name", name).findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
            @Override
            public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                if (backtoryResponse.isSuccessful()) {
                    List<BacktoryObject> objects = backtoryResponse.body();
                    BackendObject[] bo = new BackendObject[objects.size()];

                    for (int i = 0; i < objects.size(); i++) {
                        bo[i] = new BackendObject();
                        bo[i].setName(objects.get(i).get("name").toString());
                        bo[i].setPics(objects.get(i).get("pics").toString());
                        bo[i].setPrimaryPrice(objects.get(i).get("pp").toString());
                        bo[i].setSecondaryPrice(objects.get(i).get("sp").toString());
                        bo[i].setInfo(objects.get(i).get("info").toString());
                        bo[i].setDetails(objects.get(i).get("details").toString());
                        bo[i].setCat(objects.get(i).get("cat").toString());
                        bo[i].setPlace(objects.get(i).get("place").toString());
                        bo[i].setUser(objects.get(i).get("user").toString());
                        bo[i].setPage(objects.get(i).get("page").toString());
                        bo[i].setPermission(objects.get(i).get("permission").toString());
                    }

                    object.onSuccess(bo);
                } else {
                    object.onFailure();
                    core.toast(backtoryResponse.message());
                }
            }
        });
    }

    public interface GetUserPage {
        void onExists(BackendPage page);
        void onNotExists();
        void onFailure();
    }

    public interface GetObject {
        void onSuccess(BackendObject[] obj);
        void onFailure();
    }

}