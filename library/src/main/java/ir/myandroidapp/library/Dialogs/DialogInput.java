package ir.myandroidapp.library.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.Primary;
import ir.myandroidapp.library.R;

/**
 * Created by kam.amir on 2/14/17.
 */
public class DialogInput extends Dialog {

    EditText text;

    public DialogInput(Context context, final Core core, String title, String hint, int limit, final Response response) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_input);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView ttl = (TextView) findViewById(R.id.di_title);
        text = (EditText) findViewById(R.id.di_text);
        Button button = (Button) findViewById(R.id.di_button);

        ttl.setTypeface(core.setTypeFace());
        text.setTypeface(core.setTypeFace());
        button.setTypeface(core.setTypeFace());

        ttl.setText(title);
        text.setHint(hint);

        button.setTextColor(core.getColor(new Primary().getPrimary()));

        if (limit > 0)
            text.setFilters(core.textLimit(limit));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text.getText().toString() == "") {
                    core.toast(core.getString(R.string.empty_error));
                } else {
                    response.resp(text.getText().toString());
                    cancel();
                }
            }
        });

        setCanceledOnTouchOutside(true);
        setCancelable(true);
        show();
    }

    public interface Response {
        void resp(String result);
    }

    public EditText getEditText() {
        return text;
    }
}
