package ir.myandroidapp.library;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Core {

    public Context context;

    public Core(Context ctx) {
        context = ctx;
    }

    //Font Changing
    @SuppressLint("ParcelCreator")
    public class typeFace extends TypefaceSpan {
        Typeface typeface;

        public typeFace(String family, Typeface typeface) {
            super(family);
            this.typeface = typeface;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setTypeface(typeface);
        }

        @Override
        public void updateMeasureState(TextPaint ds) {
            ds.setTypeface(typeface);
        }
    }

    public SpannableString spannableString(String string) {
        SpannableString span = new SpannableString(string);
        span.setSpan(new typeFace("", Typeface.createFromAsset(context.getAssets(),
                "fonts/iran_sans.ttf")), 0, span.length(), span.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span;
    }

    public Typeface setTypeFace() {
        return Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans.ttf");
    }

    //Actions
    public void intentActivity(Class Class) {
        Intent intent = new Intent(context, Class);
        context.startActivity(intent);
    }

    public void intentActivityPutExtra(Class Class, String name, String extra) {
        Intent intent = new Intent(context, Class);
        intent.putExtra(name, extra);
        context.startActivity(intent);

    }

    public void intentActivityPutExtra(Class Class, String name, int extra) {
        Intent intent = new Intent(context, Class);
        intent.putExtra(name, extra);
        context.startActivity(intent);
    }

    public void toast(String text) {
        Toast.makeText(context, spannableString(text), Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void forceRTLIfSupported(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            window.getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public Bitmap watermark(Bitmap src, String watermark, int color) {

        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor((context).getResources().getColor(color));
        paint.setTextSize(50);
        paint.setTypeface(setTypeFace());
        paint.setAntiAlias(true);
        paint.setUnderlineText(false);

        canvas.drawText(watermark, 20, h - 30, paint);

        return result;
    }

    public File storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {

            return pictureFile;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        return pictureFile;
    }

    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public InputFilter[] textLimit(int length) {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(length);
        return fArray;
    }

    public InputFilter[] charAndNumOnly() {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i)) && !Character.isSpaceChar(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        return new InputFilter[]{filter};
    }

    public void drawRedLine(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public int getColor(int color) {
        return (context).getResources().getColor(color);
    }

    public String getString(int string) {
        return (context).getResources().getString(string);
    }

    public String priceString(String price) {
        int count = price.length();
        StringBuilder reversed = new StringBuilder(price).reverse();

        for (int i = 3; i < count; i += 4) {
            reversed.insert(i, ",");
            count++;
        }
        return reversed.reverse().toString();
    }

    public String[] divide(String in, char divider) {
        String[] result;
        int count = 0;
        int start = 0;
        int resultCount = 0;

        for (int i = 0; i < in.length(); i++)
            if (in.charAt(i) == divider)
                count++;

        result = new String[count];

        for (int i = 0; i < in.length(); i++)
            if (in.charAt(i) == divider) {
                result[resultCount] = in.substring(start, i);
                start = i + 1;
                resultCount++;
            }

        return result;
    }

    public String combine(String[] in, char divider) {
        String combined = "";
        for (String cmb : in) {
            combined = combined + cmb + divider;
        }
        return combined;
    }

    public String combineNoNull(String[] in, char divider) {
        String combined = "";

        for (String cmb : in) {
            if (!cmb.equals(""))
                combined = combined + cmb + divider;
        }
        return combined;
    }

    public void For(int count, ForAction action) {
        for (int i = 0; i < count; i++)
            action.act(i);
    }

    public interface ForAction {
        void act(int i);
    }

    public int stringNotNullCounter(String[] in) {
        int count = 0;

        for (String cmb : in) {
            if (!cmb.equals(""))
                count++;
        }
        return count;
    }

    public String[] removeNullStrings(String[] in) {
        String[] result = new String[stringNotNullCounter(in)];
        int step = 0;
        for (String s : in) {
            if (!s.equals("")) {
                result[step] = s;
                step++;
            }
        }
        return result;
    }

    public String stringLimit(String s, int i) {
        String result = s;
        if (result.length() > i)
            result = result.substring(0, i) + " ...";
        return result;
    }

    public void fav(String id) {
        String f = Remember.getString("fav", "");
        if (f.contains(id)) {
            f=f.replace(id + "|", "");
            toast("حذف شد");
            Remember.putString("fav", f);
        } else {
            f += id + "|";
            toast("اضافه شد");
            Remember.putString("fav", f);
        }
    }

    //Checkers
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean fileMoreThan(String path, int size) {
        return new File(path).length() / 1024 > size;
    }

    public LinearLayout space(int height, WindowManager wm){
        LinearLayout sp = new LinearLayout(context);
        sp.setLayoutParams(new LinearLayout.LayoutParams(new Size(context,wm).getdp(height)
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        return sp;
    }

}