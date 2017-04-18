package ir.myandroidapp.library.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.backtory.java.internal.BacktoryFile;
import com.soundcloud.android.crop.Crop;

import java.io.File;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Dialogs.DecideView;
import ir.myandroidapp.library.Dialogs.DialogInput;
import ir.myandroidapp.library.Dialogs.ProgressView;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.backend.BackendData;
import ir.myandroidapp.library.backend.SimpleResponse;
import ir.myandroidapp.library.cards.DetailViewEditable;

/**
 * Created by kam.amir on 3/12/17.
 */
public class AddItem extends LinearLayout {

    Core core;
    Activity activity;

    TextView tvName;
    EditText etName;

    TextView tvPp;
    EditText etPp;

    TextView tvSp;
    EditText etSp;

    TextView tvInfo;
    EditText etInfo;

    int number = 0;
    TextView iv_title;
    ImageView[] iv = new ImageView[5];
    int[] ivOn = new int[5];
    String[] pics;

    LinearLayout detailsLayout;
    String details="";
    String nameIp="";
    String valueIp="";

    WindowManager windowManager;

    BackendData data;

    String[] paths = new String[5];

    public AddItem(Context context, Activity act, Core cre, WindowManager wm) {
        super(context);
        core = cre;
        activity = act;
        windowManager = wm;

        data = new BackendData(core,"Products");

        LayoutInflater.from(context).inflate(R.layout.add_item, this);

        tvName = (TextView) findViewById(R.id.ai_name_tv);
        etName = (EditText) findViewById(R.id.ai_name_et);
        tvName.setTypeface(core.setTypeFace());
        etName.setTypeface(core.setTypeFace());

        tvPp = (TextView) findViewById(R.id.ai_pp_tv);
        etPp = (EditText) findViewById(R.id.ai_pp_et);
        tvPp.setTypeface(core.setTypeFace());
        etPp.setTypeface(core.setTypeFace());

        tvSp = (TextView) findViewById(R.id.ai_sp_tv);
        etSp = (EditText) findViewById(R.id.ai_sp_et);
        tvSp.setTypeface(core.setTypeFace());
        etSp.setTypeface(core.setTypeFace());

        tvInfo = (TextView) findViewById(R.id.ai_info_tv);
        etInfo = (EditText) findViewById(R.id.ai_info_et);
        tvInfo.setTypeface(core.setTypeFace());
        etInfo.setTypeface(core.setTypeFace());


        iv_title = (TextView) findViewById(R.id.ai_pics_title);
        iv_title.setTypeface(core.setTypeFace());

        iv[0] = (ImageView) findViewById(R.id.ai_iv1);
        iv[1] = (ImageView) findViewById(R.id.ai_iv2);
        iv[2] = (ImageView) findViewById(R.id.ai_iv3);
        iv[3] = (ImageView) findViewById(R.id.ai_iv4);
        iv[4] = (ImageView) findViewById(R.id.ai_iv5);

        onClick(iv[0], 0);
        onClick(iv[1], 1);
        onClick(iv[2], 2);
        onClick(iv[3], 3);
        onClick(iv[4], 4);

        ivOn[0] = 0;
        ivOn[1] = 0;
        ivOn[2] = 0;
        ivOn[3] = 0;
        ivOn[4] = 0;

        detailsLayout = (LinearLayout) findViewById(R.id.ai_details);
        detailLayout();

    }

    public void result(int requestCode, int resultCode, Intent data) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == activity.RESULT_OK) {
            startCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    private void onClick(View view, final int count) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                number = count;

                if (ivOn[count] == 0)
                    Crop.pickImage(activity);
                else
                    new DecideView(core.context, core, "آیا مایل به جایگزین کردن تصویر جدید میباشید ؟",
                            new Runnable() {
                                @Override
                                public void run() {
                                    Crop.pickImage(activity);
                                }
                            });
            }
        });

        view.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                number = count;


                if(ivOn[count] == 1)
                    new DecideView(core.context, core, "آیا مایل به حذف تصویر میباشید ؟",
                            new Runnable() {
                                @Override
                                public void run() {
                                    clearImage();
                                }
                            });

                return true;
            }
        });

    }

    private void startCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(activity.getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(activity);

    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == activity.RESULT_OK) {
            paths[number]=Crop.getOutput(result).getPath().toString();
            setImage(result);
            core.toast(paths[number]);

        } else if (resultCode == Crop.RESULT_ERROR) {
            core.toast("خطا");
        }
    }

    private void setImage(Intent result) {
        iv[number].setImageResource(R.drawable.ic_add_photo_dark);
        iv[number].setImageURI(Crop.getOutput(result));
        ivOn[number] = 1;
    }

    private void clearImage(){
        iv[number].setImageResource(R.drawable.ic_add_photo_dark);
        ivOn[number] = 0;
    }

    private void detailLayout(){
        detailsLayout.removeAllViews();
        core.toast(details);
        if (details.length() > 0) {
            final String[] divided = core.divide(details, '|');

            int count = divided.length;

            for (int i = 0; i < count; i++) {

                final String name = core.divide(divided[i],':')[0];
                final String value = core.divide(divided[i],':')[1];
                detailsLayout.addView(dve(name,value,divided[i]));
            }
        }
    }

    private DetailViewEditable dve(final String name,final String value,final String divided){
        DetailViewEditable dv = new DetailViewEditable(core.context, core, windowManager, name +" : "+value);

        dv.delete().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new DecideView(core.context, core, "آیا مایل به حذف این بخش میباشید ؟", new Runnable() {
                    @Override
                    public void run() {
                        details=details.replace(divided + "|", "");
                        detailLayout();
                    }
                });
            }
        });

        dv.getLayout().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogInput(core.context, core, "", value, 15,
                        new DialogInput.Response() {
                            @Override
                            public void resp(String result) {
                                details=details.replace(value,result);
                            }
                        }).getEditText().setText(name);
            }
        });

        return dv;
    }

    public void setDetails(){
        new DialogInput(core.context, core, "عنوان", "برای مثال وزن", 15, new DialogInput.Response() {
            @Override
            public void resp(String result) {
                nameIp = result;

                new DialogInput(core.context, core, "مقدار", "برای مثال ۴ کیلوگرم", 15, new DialogInput.Response() {
                    @Override
                    public void resp(String result) {
                        valueIp = result;
                        details = details + nameIp + ":" + valueIp + ":|";
                        detailLayout();
                    }
                }).getEditText().setFilters(core.charAndNumOnly());
            }
        }).getEditText().setFilters(core.charAndNumOnly());
    }

    public void onDone(doneListener listener){
        listener.done(etName.getText().toString(),etPp.getText().toString(),etSp.getText().toString()
        ,etInfo.getText().toString(),details,pics);
    }

    public interface doneListener{
        void done(String name, String primaryPrice, String secondaryPrice, String info, String details,
                  String[] pics);
    }


}