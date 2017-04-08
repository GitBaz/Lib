package ir.myandroidapp.library.backend;

import com.backtory.java.HttpStatusCode;
import com.backtory.java.internal.BacktoryCallBack;
import com.backtory.java.internal.BacktoryClient;
import com.backtory.java.internal.BacktoryFile;
import com.backtory.java.internal.BacktoryResponse;
import com.backtory.java.internal.BacktoryUser;
import com.backtory.java.internal.Config;
import com.backtory.java.internal.GuestRegistrationParam;
import com.backtory.java.internal.LoginResponse;

import java.io.File;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Remember;

/**
 * Created by kam.amir on 4/7/17.
 */

public class BackendUser {

    Core core;

    public BackendUser(Core cre) {
        core = cre;
    }

    public void init(String authId, String authkey, String storage) {
        BacktoryClient.Android.init(Config.newBuilder().
                initAuth(authId, authkey).
                initObjectStorage(storage).
                build(), core.context);
    }

    public void guestLogin(final Response resp) {
        BacktoryUser.loginAsGuestInBackground(new BacktoryCallBack<LoginResponse>() {
            @Override
            public void onResponse(BacktoryResponse<LoginResponse> response) {
                if (response.isSuccessful()) {
                    resp.onSuccess();
                } else {
                    resp.onFailure();
                    core.toast(core.getString(R.string.connection_error));
                }
            }
        });
    }

    public void login(final String username, final String password, final Response resp) {
        BacktoryUser.loginInBackground(username, password,
                new BacktoryCallBack<LoginResponse>() {
                    @Override
                    public void onResponse(BacktoryResponse<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            resp.onSuccess();
                        } else {
                            resp.onFailure();
                            core.toast(core.getString(R.string.connection_error));
                        }
                    }
                });
    }

    public void register(final String firstname, final String username, final String email, final String password,
                         final String number, final String address, final Response resp) {
        BacktoryUser newUser = new BacktoryUser.
                Builder().
                setFirstName(firstname).
                setLastName(address).
                setUsername(username).
                setEmail(email).
                setPassword(password).
                setPhoneNumber(number)
                .build();

        newUser.registerInBackground(new BacktoryCallBack<BacktoryUser>() {
            @Override
            public void onResponse(BacktoryResponse<BacktoryUser> response) {
                if (response.isSuccessful()) {
                    resp.onSuccess();
                } else if (response.code() == HttpStatusCode.Conflict.code()) {
                    resp.onFailure();
                    core.toast(core.getString(R.string.user_exists));
                } else {
                    resp.onFailure();
                    core.toast(core.getString(R.string.connection_error));
                }
            }
        });
    }

    public void compeleteRegistration(String name, String address, String email, final String username,
                                      final String number, final String password, final Response resp) {
        if (BacktoryUser.getCurrentUser().isGuest()) {
            GuestRegistrationParam params = new GuestRegistrationParam
                    .Builder()
                    .setFirstName(name)
                    .setLastName(address)
                    .setEmail(email)
                    .setNewUsername(username)
                    .setNewPassword(password)
                    .setPhoneNumber(number)
                    .build();

            BacktoryUser.getCurrentUser().completeRegistrationInBackground(params,
                    new BacktoryCallBack<BacktoryUser>() {
                        @Override
                        public void onResponse(BacktoryResponse<BacktoryUser> response) {
                            if (response.isSuccessful()) {
                                resp.onSuccess();
                            } else if (response.code() == HttpStatusCode.Conflict.code()) {
                                core.toast(core.getString(R.string.user_exists));
                                resp.onFailure();
                            } else {
                                core.toast(core.getString(R.string.connection_error));
                                resp.onFailure();
                            }
                        }
                    });
        } else {
            core.toast(core.getString(R.string.not_guest));
        }
    }

    public void logout() {
        BacktoryUser.logoutInBackground();
        Remember.clear();
    }

    public void changePassword(String oldPass, final String newPass) {
        String oldPassword = oldPass;
        String newPassword = newPass;

        BacktoryUser.getCurrentUser().changePasswordInBackground(oldPassword, newPassword,
                new BacktoryCallBack<Void>() {
                    @Override
                    public void onResponse(BacktoryResponse<Void> response) {
                        if (response.isSuccessful()) {
                            Remember.putString("PASSWORD", newPass);
                            core.toast(core.getString(R.string.password_changed));
                        } else if (response.code() == HttpStatusCode.Forbidden.code()) {
                            core.toast(core.getString(R.string.wrong_password));
                        } else {
                            core.toast(core.getString(R.string.connection_error));
                        }
                    }
                });
    }

    public void changeAddress(String address, final Response resp) {
        BacktoryUser user = BacktoryUser.getCurrentUser();
        user.setLastName(address);

        user.updateInBackground(new BacktoryCallBack<BacktoryUser>() {
            @Override
            public void onResponse(BacktoryResponse<BacktoryUser> backtoryResponse) {
                if (backtoryResponse.isSuccessful()) {
                    resp.onSuccess();
                    core.toast(core.getString(R.string.address_changed));
                } else {
                    resp.onFailure();
                    core.toast(core.getString(R.string.connection_error));
                }
            }
        });
    }

    public void changePhoneNumber(String number, final Response resp) {
        BacktoryUser user = BacktoryUser.getCurrentUser();
        user.setPhoneNumber(number);
        user.updateInBackground(new BacktoryCallBack<BacktoryUser>() {
            @Override
            public void onResponse(BacktoryResponse<BacktoryUser> backtoryResponse) {
                if (backtoryResponse.isSuccessful()) {
                    resp.onSuccess();
                    core.toast(core.getString(R.string.phone_changed));
                } else {
                    resp.onFailure();
                    core.toast(core.getString(R.string.connection_error));
                }
            }
        });
    }

    public interface Response {
        void onSuccess();

        void onFailure();
    }


}
