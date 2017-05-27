package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.backtory.java.internal.BacktoryUser;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.DecideView;
import ir.myandroidapp.library.Dialogs.DialogInput;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.backend.BackendUser;

/**
 * Created by kam.amir on 2/14/17.
 */
public class Profile extends LinearLayout {

    TextView username;
    TextView name;
    TextView password;
    TextView email;
    TextView number;
    TextView address;
    TextView exit;
    Core core;

    BackendUser backend;

    public Profile(final Context context, Core cre, final Activity activity, final Class exitActivity) {
        super(context);
        core = cre;
        backend = new BackendUser(cre);

        LayoutInflater.from(context).inflate(R.layout.profile, this);

        username = (TextView) findViewById(R.id.profile_username);
        name = (TextView) findViewById(R.id.profile_name);
        password = (TextView) findViewById(R.id.profile_password);
        email = (TextView) findViewById(R.id.profile_email);
        number = (TextView) findViewById(R.id.profile_number);
        address = (TextView) findViewById(R.id.profile_address);
        exit = (TextView) findViewById(R.id.profile_exit);

        username.setTypeface(core.setTypeFace());
        name.setTypeface(core.setTypeFace());
        password.setTypeface(core.setTypeFace());
        email.setTypeface(core.setTypeFace());
        number.setTypeface(core.setTypeFace());
        address.setTypeface(core.setTypeFace());
        exit.setTypeface(core.setTypeFace());

        setText();

        number.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogInput(core.context, core, core.getString(R.string.phone_change),
                        core.getString(R.string.enter_your_phone), 0, new DialogInput.Response() {
                    @Override
                    public void resp(final String result) {
                        new DecideView(context, core, core.getString(R.string.are_you_sure), new Runnable() {
                            @Override
                            public void run() {
                                backend.changePhoneNumber(result, new BackendUser.Response() {
                                    @Override
                                    public void onSuccess() {
                                        core.toast(core.getString(R.string.phone_changed));
                                        setText();
                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });

                            }
                        });
                    }
                });
            }
        });

        address.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogInput(core.context, core, core.getString(R.string.address_change),
                        core.getString(R.string.enter_your_address), 0, new DialogInput.Response() {
                    @Override
                    public void resp(final String result) {
                        new DecideView(context, core, core.getString(R.string.are_you_sure), new Runnable() {
                            @Override
                            public void run() {
                                backend.changeAddress(result, new BackendUser.Response() {
                                    @Override
                                    public void onSuccess() {
                                        core.toast(core.getString(R.string.address_changed));
                                        setText();
                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });

                            }
                        });
                    }
                });
            }
        });

        password.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogInput(core.context, core, core.getString(R.string.password_change),
                        core.getString(R.string.enter_your_password), 0, new DialogInput.Response() {
                    @Override
                    public void resp(String result) {
                        final String oldPassword = result;

                        new DialogInput(core.context, core, core.getString(R.string.password_change),
                                core.getString(R.string.enter_your_new_password), 0, new DialogInput.Response() {
                            @Override
                            public void resp(String result) {
                                final String firstPass = result;

                                new DialogInput(core.context, core, core.getString(R.string.password_change),
                                        core.getString(R.string.password_repeat), 0, new DialogInput.Response() {
                                    @Override
                                    public void resp(String result) {
                                        final String secondPass = result;

                                        if (firstPass.equals(secondPass)) {
                                            backend.changePassword(oldPassword, result);
                                        } else {
                                            core.toast(core.getString(R.string.passwords_doesnt_match));
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                new DecideView(context, core, core.getString(R.string.exit_sure), new Runnable() {
                    @Override
                    public void run() {
                        backend.logout();
                        core.intentActivity(exitActivity);
                        activity.finishAffinity();
                    }
                });
            }
        });


    }

    void setText() {
        username.setText(BacktoryUser.getCurrentUser().getUsername());
        name.setText(BacktoryUser.getCurrentUser().getFirstName());
        password.setText(core.getString(R.string.password_change));
        email.setText(BacktoryUser.getCurrentUser().getEmail());
        number.setText(BacktoryUser.getCurrentUser().getPhoneNumber());
        address.setText(BacktoryUser.getCurrentUser().getLastName());
    }

}
