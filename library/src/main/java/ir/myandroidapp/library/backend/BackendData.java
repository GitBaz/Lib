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
    int getCounter = 0;

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
        object.put("comments", "0");
        object.put("location", obj.getLocation());
        object.put("number",obj.getNumber());
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
            object.put("detail", page.getDetail());
            object.put("user", BacktoryUser.getCurrentUser().getUserId());
            object.put("cat", page.getCat());
            object.put("permission", "0");
            object.put("place", "0");
            object.put("lat", page.getLat());
            object.put("lng", page.getLng());
            object.put("location", page.getLocation());
            object.put("sliderpic",page.getSliderPic());
            object.put("sponserpic",page.getSponserPic());
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

    public void get(String id, final Getobj objct) {
        BacktoryObject.getQuery("Products").whereMatches("permission", "1").getInBackground(id, new BacktoryCallBack<BacktoryObject>() {
            @Override
            public void onResponse(BacktoryResponse<BacktoryObject> backtoryResponse) {
                if (backtoryResponse.isSuccessful()) {
                    BacktoryObject object = backtoryResponse.body();
                    BackendObject bo = new BackendObject();

                    bo.setId(object.getObjectId().toString());
                    bo.setName(object.get("name").toString());
                    bo.setPics(object.get("pics").toString());
                    bo.setPrimaryPrice(object.get("pp").toString());
                    bo.setSecondaryPrice(object.get("sp").toString());
                    bo.setInfo(object.get("info").toString());
                    bo.setDetails(object.get("details").toString());
                    bo.setCat(object.get("cat").toString());
                    bo.setPlace(object.get("place").toString());
                    bo.setUser(object.get("user").toString());
                    bo.setPage(object.get("page").toString());
                    bo.setPermission(object.get("permission").toString());
                    bo.setLocation(object.get("location").toString());
                    bo.setNumber(object.get("number").toString());

                    objct.onSuccess(bo);

                } else {
                    objct.onFailure();
                }
            }

        });
    }

    public void get(String[] id, final GetObject object) {

        final BackendObject[] bo = new BackendObject[id.length];

        for (int i = 0; i < id.length; i++) {
            final int j = i;
            BacktoryQuery.getQuery("Products").whereMatches("permission", "1").orderByDescending("createdAt").getInBackground(id[i], new BacktoryCallBack<BacktoryObject>() {
                @Override
                public void onResponse(BacktoryResponse<BacktoryObject> backtoryResponse) {
                    if (backtoryResponse.isSuccessful()) {
                        BacktoryObject object = backtoryResponse.body();
                        bo[j] = new BackendObject();
                        bo[j].setId(object.getObjectId().toString());
                        bo[j].setName(object.get("name").toString());
                        bo[j].setPics(object.get("pics").toString());
                        bo[j].setPrimaryPrice(object.get("pp").toString());
                        bo[j].setSecondaryPrice(object.get("sp").toString());
                        bo[j].setInfo(object.get("info").toString());
                        bo[j].setDetails(object.get("details").toString());
                        bo[j].setCat(object.get("cat").toString());
                        bo[j].setPlace(object.get("place").toString());
                        bo[j].setUser(object.get("user").toString());
                        bo[j].setPage(object.get("page").toString());
                        bo[j].setPermission(object.get("permission").toString());
                        bo[j].setLocation(object.get("location").toString());
                        bo[j].setNumber(object.get("number").toString());
                        getCounter++;
                    }
                }
            });
        }

        object.onSuccess(bo);
    }

    public void getUserPage(final GetUserPage response) {
        BacktoryQuery.getQuery("Pages").whereMatches("permission", "1").whereMatches("user", BacktoryUser.getCurrentUser().getUserId()).
                findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
                    @Override
                    public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                        if (backtoryResponse.isSuccessful()) {
                            if (backtoryResponse.body().isEmpty()) {
                                response.onNotExists();
                            } else {
                                BackendPage page = new BackendPage();
                                BacktoryObject obj = backtoryResponse.body().get(0);
                                page.setId(obj.getObjectId().toString());
                                page.setBrand(obj.get("brand").toString());
                                page.setLogo(obj.get("logo").toString());
                                page.setInfo(obj.get("info").toString());
                                page.setNumber(obj.get("number").toString());
                                page.setDetail(obj.get("detail").toString());
                                page.setUser(obj.get("user").toString());
                                page.setPermission(obj.get("permission").toString());
                                page.setPlace(obj.get("place").toString());
                                page.setCat(obj.get("cat").toString());
                                page.setLocation(obj.get("location").toString());
                                page.setLat(obj.get("lat").toString());
                                page.setLng(obj.get("lng").toString());
                                page.setSliderPic(obj.get("sliderpic").toString());
                                page.setSponserPic(obj.get("sponserpic").toString());
                                response.onExists(page);
                            }
                        } else {
                            response.onFailure();
                            core.toast(core.getString(R.string.connection_error));
                        }
                    }
                });

    }

    public void getPageById(String id, final GetUserPage response) {
        BacktoryObject.getQuery("Pages").getInBackground(id, new BacktoryCallBack<BacktoryObject>() {
            @Override
            public void onResponse(BacktoryResponse<BacktoryObject> backtoryResponse) {
                if (backtoryResponse.isSuccessful()) {
                    if (backtoryResponse.body().toString()=="") {
                        response.onNotExists();
                    } else {
                        BackendPage page = new BackendPage();
                        BacktoryObject obj = backtoryResponse.body();
                        page.setId(obj.getObjectId().toString());
                        page.setBrand(obj.get("brand").toString());
                        page.setLogo(obj.get("logo").toString());
                        page.setInfo(obj.get("info").toString());
                        page.setNumber(obj.get("number").toString());
                        page.setDetail(obj.get("detail").toString());
                        page.setUser(obj.get("user").toString());
                        page.setPermission(obj.get("permission").toString());
                        page.setPlace(obj.get("place").toString());
                        page.setCat(obj.get("cat").toString());
                        page.setLocation(obj.get("location").toString());
                        page.setLat(obj.get("lat").toString());
                        page.setLng(obj.get("lng").toString());
                        page.setSliderPic(obj.get("sliderpic").toString());
                        page.setSponserPic(obj.get("sponserpic").toString());
                        response.onExists(page);
                    }
                } else {
                    response.onFailure();
                    core.toast(core.getString(R.string.connection_error));
                }
            }
        });
    }

    public void getUserPagePosts(String user, final GetUserPagePosts getPosts) {
        BacktoryQuery.getQuery("Products").whereMatches("permission", "1").orderByDescending("createdAt").whereMatches("user", user).whereMatches("page", "1").
                findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
                    @Override
                    public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                        if (backtoryResponse.isSuccessful()) {
                            if (backtoryResponse.body().isEmpty()) {
                                getPosts.onNotExists();
                            } else {
                                List<BacktoryObject> obj = backtoryResponse.body();
                                int count = obj.size();
                                BackendObject[] objects = new BackendObject[count];
                                for (int i = 0; i < count; i++) {
                                    BacktoryObject bo = obj.get(i);
                                    objects[i] = new BackendObject();
                                    objects[i].setId(bo.getObjectId().toString());
                                    objects[i].setName(bo.get("name").toString());
                                    objects[i].setPics(bo.get("pics").toString());
                                    objects[i].setPrimaryPrice(bo.get("pp").toString());
                                    objects[i].setSecondaryPrice(bo.get("sp").toString());
                                    objects[i].setInfo(bo.get("info").toString());
                                    objects[i].setDetails(bo.get("details").toString());
                                    objects[i].setCat(bo.get("cat").toString());
                                    objects[i].setPlace(bo.get("place").toString());
                                    objects[i].setUser(bo.get("user").toString());
                                    objects[i].setPage(bo.get("page").toString());
                                    objects[i].setPermission(bo.get("permission").toString());
                                    objects[i].setLocation(bo.get("location").toString());
                                    objects[i].setNumber(bo.get("number").toString());

                                }
                                getPosts.onExists(objects);
                            }

                        } else {
                            getPosts.onFailure();
                            core.toast(core.getString(R.string.connection_error));
                        }
                    }
                });

    }

    public void getSearch(String name, String cat, String loc, final GetObject object) {
        BacktoryQuery.getQuery("Products").whereMatches("permission", "1").orderByDescending("createdAt")
                .whereContains("name", name).whereContains("cat", cat).whereMatches("location", loc).
                findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
                    @Override
                    public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                        if (backtoryResponse.isSuccessful()) {
                            List<BacktoryObject> objects = backtoryResponse.body();
                            BackendObject[] bo = new BackendObject[objects.size()];

                            for (int i = 0; i < objects.size(); i++) {
                                bo[i] = new BackendObject();
                                bo[i].setId(objects.get(i).getObjectId().toString());
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
                                bo[i].setLocation(objects.get(i).get("location").toString());
                                bo[i].setNumber(objects.get(i).get("number").toString());

                            }

                            object.onSuccess(bo);
                        } else {
                            object.onFailure();
                            core.toast(backtoryResponse.message());
                        }
                    }
                });
    }

    public void getSearchPages(String name, String cat, String loc, final GetPag object) {
        BacktoryQuery.getQuery("Pages").whereMatches("permission", "1")
                .whereContains("name", name).whereMatches("cat", cat).orderByDescending("createdAt").whereMatches("location", loc).
                findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
                    @Override
                    public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                        if (backtoryResponse.isSuccessful()) {
                            List<BacktoryObject> objects = backtoryResponse.body();
                            BackendPage[] bo = new BackendPage[objects.size()];

                            for (int i = 0; i < objects.size(); i++) {
                                bo[i] = new BackendPage();
                                bo[i].setId(objects.get(i).getObjectId().toString());
                                bo[i].setBrand(objects.get(i).get("brand").toString());
                                bo[i].setLogo(objects.get(i).get("logo").toString());
                                bo[i].setInfo(objects.get(i).get("info").toString());
                                bo[i].setNumber(objects.get(i).get("number").toString());
                                bo[i].setDetail(objects.get(i).get("detail").toString());
                                bo[i].setUser(objects.get(i).get("user").toString());
                                bo[i].setPermission(objects.get(i).get("permission").toString());
                                bo[i].setPlace(objects.get(i).get("place").toString());
                                bo[i].setCat(objects.get(i).get("cat").toString());
                                bo[i].setLocation(objects.get(i).get("location").toString());
                                bo[i].setLat(objects.get(i).get("lat").toString());
                                bo[i].setLng(objects.get(i).get("lng").toString());
                                bo[i].setSliderPic(objects.get(i).get("sliderpic").toString());
                                bo[i].setSponserPic(objects.get(i).get("sponserpic").toString());

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

    public interface GetPages {
        void onExists(BackendPage[] page);

        void onNotExists();

        void onFailure();
    }

    public interface GetObject {
        void onSuccess(BackendObject[] obj);

        void onFailure();
    }

    public interface GetObjects {
        void onExists(BackendObject[] obj);

        void onNotExists();

        void onFailure();

    }

    public interface GetPag {
        void onSuccess(BackendPage[] obj);

        void onFailure();
    }

    public interface Getobj {
        void onSuccess(BackendObject obj);

        void onFailure();
    }

    public interface GetUserPagePosts {
        void onExists(BackendObject[] objects);

        void onNotExists();

        void onFailure();
    }

    public void getPageByPlace(String s, final GetPages obj) {
        BacktoryQuery.getQuery("Pages").whereMatches("permission", "1").orderByDescending("createdAt").whereMatches("place", s).findInBackground(
                new BacktoryCallBack<List<BacktoryObject>>() {
                    @Override
                    public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                        if (backtoryResponse.isSuccessful()) {
                            if (backtoryResponse.body().size() > 0) {
                                BackendPage[] objects = new BackendPage[backtoryResponse.body().size()];
                                for (int i = 0; i < backtoryResponse.body().size(); i++) {
                                    BacktoryObject bo = backtoryResponse.body().get(i);
                                    objects[i] = new BackendPage();
                                    objects[i].setId(bo.getObjectId().toString());
                                    objects[i].setBrand(bo.get("brand").toString());
                                    objects[i].setLogo(bo.get("logo").toString());
                                    objects[i].setInfo(bo.get("info").toString());
                                    objects[i].setNumber(bo.get("number").toString());
                                    objects[i].setDetail(bo.get("detail").toString());
                                    objects[i].setUser(bo.get("user").toString());
                                    objects[i].setPermission(bo.get("permission").toString());
                                    objects[i].setPlace(bo.get("place").toString());
                                    objects[i].setCat(bo.get("cat").toString());
                                    objects[i].setLocation(bo.get("location").toString());
                                    objects[i].setLat(bo.get("lat").toString());
                                    objects[i].setLng(bo.get("lng").toString());
                                    objects[i].setSliderPic(bo.get("sliderpic").toString());
                                    objects[i].setSponserPic(bo.get("sponserpic").toString());

                                }
                                obj.onExists(objects);
                            } else
                                obj.onNotExists();
                        } else {
                            obj.onFailure();
                        }
                    }
                });
    }

    public void getPageByCat(String s, final GetPages obj) {
        BacktoryQuery.getQuery("Pages").whereMatches("permission", "1").orderByDescending("createdAt").whereContains("cat", s).findInBackground(
                new BacktoryCallBack<List<BacktoryObject>>() {
                    @Override
                    public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                        if (backtoryResponse.isSuccessful()) {
                            if (backtoryResponse.body().size() > 0) {
                                BackendPage[] objects = new BackendPage[backtoryResponse.body().size()];
                                for (int i = 0; i < backtoryResponse.body().size(); i++) {
                                    BacktoryObject bo = backtoryResponse.body().get(i);
                                    objects[i] = new BackendPage();
                                    objects[i].setId(bo.getObjectId().toString());
                                    objects[i].setBrand(bo.get("brand").toString());
                                    objects[i].setLogo(bo.get("logo").toString());
                                    objects[i].setInfo(bo.get("info").toString());
                                    objects[i].setNumber(bo.get("number").toString());
                                    objects[i].setDetail(bo.get("detail").toString());
                                    objects[i].setUser(bo.get("user").toString());
                                    objects[i].setPermission(bo.get("permission").toString());
                                    objects[i].setPlace(bo.get("place").toString());
                                    objects[i].setCat(bo.get("cat").toString());
                                    objects[i].setLocation(bo.get("location").toString());
                                    objects[i].setLat(bo.get("lat").toString());
                                    objects[i].setLng(bo.get("lng").toString());
                                    objects[i].setSliderPic(bo.get("sliderpic").toString());
                                    objects[i].setSponserPic(bo.get("sponserpic").toString());

                                }
                                obj.onExists(objects);
                            } else
                                obj.onNotExists();
                        } else {
                            obj.onFailure();
                        }
                    }
                });
    }

    public void getObjectByPlace(String s, final GetObjects obj) {
        BacktoryQuery.getQuery("Products").whereMatches("permission", "1").orderByDescending("createdAt").whereContains("place", s).findInBackground(
                new BacktoryCallBack<List<BacktoryObject>>() {
                    @Override
                    public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                        if (backtoryResponse.isSuccessful()) {
                            if (backtoryResponse.body().size() > 0) {
                                BackendObject[] objects = new BackendObject[backtoryResponse.body().size()];
                                for (int i = 0; i < backtoryResponse.body().size(); i++) {
                                    BacktoryObject bo = backtoryResponse.body().get(i);
                                    objects[i] = new BackendObject();
                                    objects[i].setId(bo.getObjectId().toString());
                                    objects[i].setName(bo.get("name").toString());
                                    objects[i].setPics(bo.get("pics").toString());
                                    objects[i].setPrimaryPrice(bo.get("pp").toString());
                                    objects[i].setSecondaryPrice(bo.get("sp").toString());
                                    objects[i].setInfo(bo.get("info").toString());
                                    objects[i].setDetails(bo.get("details").toString());
                                    objects[i].setCat(bo.get("cat").toString());
                                    objects[i].setPlace(bo.get("place").toString());
                                    objects[i].setUser(bo.get("user").toString());
                                    objects[i].setPage(bo.get("page").toString());
                                    objects[i].setPermission(bo.get("permission").toString());
                                    objects[i].setLocation(bo.get("location").toString());
                                    objects[i].setNumber(bo.get("number").toString());

                                }
                                obj.onExists(objects);
                            } else
                                obj.onNotExists();
                        } else {
                            obj.onFailure();
                        }
                    }
                });
    }

    public void getObjectByCat(String s, final GetObjects obj) {
        BacktoryQuery.getQuery("Products").whereMatches("permission", "1").orderByDescending("createdAt").whereContains("cat", s).findInBackground(
                new BacktoryCallBack<List<BacktoryObject>>() {
                    @Override
                    public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                        if (backtoryResponse.isSuccessful()) {
                            if (backtoryResponse.body().size() > 0) {
                                BackendObject[] objects = new BackendObject[backtoryResponse.body().size()];
                                for (int i = 0; i < backtoryResponse.body().size(); i++) {
                                    BacktoryObject bo = backtoryResponse.body().get(i);
                                    objects[i] = new BackendObject();
                                    objects[i].setId(bo.getObjectId().toString());
                                    objects[i].setName(bo.get("name").toString());
                                    objects[i].setPics(bo.get("pics").toString());
                                    objects[i].setPrimaryPrice(bo.get("pp").toString());
                                    objects[i].setSecondaryPrice(bo.get("sp").toString());
                                    objects[i].setInfo(bo.get("info").toString());
                                    objects[i].setDetails(bo.get("details").toString());
                                    objects[i].setCat(bo.get("cat").toString());
                                    objects[i].setPlace(bo.get("place").toString());
                                    objects[i].setUser(bo.get("user").toString());
                                    objects[i].setPage(bo.get("page").toString());
                                    objects[i].setPermission(bo.get("permission").toString());
                                    objects[i].setLocation(bo.get("location").toString());
                                    objects[i].setNumber(bo.get("number").toString());

                                }
                                obj.onExists(objects);
                            } else
                                obj.onNotExists();
                        } else {
                            obj.onFailure();
                        }
                    }
                });
    }

    public void getPageByUser(String user, final GetUserPage response) {
        BacktoryQuery.getQuery("Pages").whereMatches("user", user).whereMatches("permission", "1").
                findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
                    @Override
                    public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                        if (backtoryResponse.isSuccessful()) {
                            if (backtoryResponse.body().isEmpty()) {
                                response.onNotExists();
                            } else {
                                BackendPage page = new BackendPage();
                                BacktoryObject obj = backtoryResponse.body().get(0);
                                page.setId(obj.getObjectId().toString());
                                page.setBrand(obj.get("brand").toString());
                                page.setLogo(obj.get("logo").toString());
                                page.setInfo(obj.get("info").toString());
                                page.setNumber(obj.get("number").toString());
                                page.setDetail(obj.get("detail").toString());
                                page.setUser(obj.get("user").toString());
                                page.setPermission(obj.get("permission").toString());
                                page.setPlace(obj.get("place").toString());
                                page.setCat(obj.get("cat").toString());
                                page.setLocation(obj.get("location").toString());
                                page.setLat(obj.get("lat").toString());
                                page.setLng(obj.get("lng").toString());
                                page.setSliderPic(obj.get("sliderpic").toString());
                                page.setSponserPic(obj.get("sponserpic").toString());

                                response.onExists(page);
                            }
                        } else {
                            response.onFailure();
                            core.toast(core.getString(R.string.connection_error));
                        }
                    }
                });
    }

    public void deletePage(final Runnable run) {
        BacktoryQuery.getQuery("Pages").whereMatches("user", BacktoryUser.getCurrentUser().getUserId()).
                findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
                    @Override
                    public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                        if (backtoryResponse.isSuccessful())
                            backtoryResponse.body().get(0).deleteInBackground(new BacktoryCallBack<Void>() {
                                @Override
                                public void onResponse(BacktoryResponse<Void> backtoryResponse) {
                                    core.toast("حذف شد");
                                    run.run();
                                }
                            });
                    }
                });

    }

    public void writeComment(String com,String itemId,final SimpleResponse response) {
        BacktoryObject object = new BacktoryObject("Comments");
        object.put("user", BacktoryUser.getCurrentUser().getUserId());
        object.put("comment", com);
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

    public void readComment(String id,final CommentResponse response){
        new BacktoryQuery("Comments").whereMatches("itemId", id).orderByDescending("createdAt").whereMatches("permission", "1")
                .findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
                    @Override
                    public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                        if (backtoryResponse.isSuccessful()) {

                            int count = backtoryResponse.body().size();
                            BackendComment[] bcmt = new BackendComment[count];

                            for (int i = 0; i < count; i++) {
                                bcmt[i] = new BackendComment();
                                BacktoryObject obj = backtoryResponse.body().get(i);
                                bcmt[i].create(obj.get("comment").toString(),
                                        obj.get("itemId").toString(),
                                        obj.get("username").toString(),
                                        obj.getObjectId().toString());
                            }

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

}