package ir.myandroidapp.library.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 1/3/17.
 */
public class Login extends LinearLayout {

    Core core;

    EditText name, user, pass, mail, number, address;
    Button main, secondary, guest;
    Boolean isSignIn = false;

    LinearLayout thisLayout;

    public Login(final Context context, final onSignIn onSignIn) {
        super(context);
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        core = new Core(context);

        LayoutInflater.from(context).inflate(R.layout.register, this);
        thisLayout=this;

        name = (EditText) findViewById(R.id.layout_sign_up_name);
        user = (EditText) findViewById(R.id.layout_sign_up_user);
        pass = (EditText) findViewById(R.id.layout_sign_up_pass);
        mail = (EditText) findViewById(R.id.layout_sign_up_mail);
        number = (EditText) findViewById(R.id.layout_sign_up_phone);
        address = (EditText) findViewById(R.id.layout_sign_up_address);

        main = (Button) findViewById(R.id.layout_sign_up_main_btn);
        secondary = (Button) findViewById(R.id.layout_sign_up_secondary_btn);
        guest = (Button) findViewById(R.id.layout_sign_up_guest_btn);

        name.setTypeface(core.setTypeFace());
        user.setTypeface(core.setTypeFace());
        pass.setTypeface(core.setTypeFace());
        mail.setTypeface(core.setTypeFace());
        number.setTypeface(core.setTypeFace());
        address.setTypeface(core.setTypeFace());

        main.setTypeface(core.setTypeFace());
        secondary.setTypeface(core.setTypeFace());
        guest.setTypeface(core.setTypeFace());

        main.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String sName ,sUser, sPass, sMail, sNumber, sAddress;
                sName = name.getText().toString(); sUser=user.getText().toString();
                sPass = pass.getText().toString(); sMail=mail.getText().toString();
                sNumber = number.getText().toString(); sAddress = address.getText().toString();

                if (isSignIn) {
                    onSignIn.onLogin(sUser,sPass);
                } else {
                    onSignIn.onRegister(sName,sUser,sMail,sPass,sNumber,sAddress);
                }
            }
        });

        secondary.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSignIn) {
                    layoutSignUp();
                } else {
                    layoutSignIn();
                }
            }
        });

        guest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignIn.onGuestLogin();
            }
        });

        layoutSignIn();

    }


    private void layoutSignUp() {
        name.setVisibility(VISIBLE);
        mail.setVisibility(VISIBLE);
        number.setVisibility(VISIBLE);
        address.setVisibility(VISIBLE);
        guest.setVisibility(GONE);

        secondary.setText("ورود");
        main.setText("ثبت نام");

        isSignIn = false;
    }

    private void layoutSignIn() {
        name.setVisibility(GONE);
        mail.setVisibility(GONE);
        number.setVisibility(GONE);
        address.setVisibility(GONE);
        guest.setVisibility(VISIBLE);

        secondary.setText("ثبت نام");
        main.setText("ورود");

        isSignIn = true;
    }

    public interface onSignIn{
        void onLogin(String username, String password);
        void onRegister(String firstname, String username, String email, String password, String number, String address);
        void onGuestLogin();
    }

}