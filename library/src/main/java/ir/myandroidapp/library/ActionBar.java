package ir.myandroidapp.library;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by kam.amir on 4/7/17.
 */

public class ActionBar extends LinearLayout {

    Toolbar tool;
    Core core;
    FloatingActionButton btn;

    public ActionBar(Context ctx, Core cre, int lay) {
        super(ctx);
        core = cre;
        LayoutInflater.from(ctx).inflate(R.layout.action_bar, this);
        btn = (FloatingActionButton) findViewById(R.id.fab);
        LinearLayout layout = (LinearLayout) findViewById(R.id.action_content);
        LinearLayout content = (LinearLayout) LayoutInflater.from(ctx).inflate(lay,new LinearLayout(ctx));
        layout.addView(content);
        tool = (Toolbar) findViewById(R.id.toolbar);
    }

    public ActionBar(Context ctx, Core cre, LinearLayout lay){
        super(ctx);
        core = cre;
        LayoutInflater.from(ctx).inflate(R.layout.action_bar, this);
        btn = (FloatingActionButton) findViewById(R.id.fab);
        LinearLayout layout = (LinearLayout) findViewById(R.id.action_content);
        layout.addView(lay);
        tool = (Toolbar) findViewById(R.id.toolbar);
    }

    public void setTitle(String title) {
        tool.setTitle(core.spannableString(title));
        tool.setTitleTextColor(core.getColor(R.color.white));
    }

    public void AddPanel(int menu) {
        tool.inflateMenu(menu);
        int size = tool.getMenu().getItem(0).getSubMenu().size();
        for (int i = 0; i < size; i++) {
            tool.getMenu().getItem(0).getSubMenu().getItem(i).setTitle(core.spannableString(
                    tool.getMenu().getItem(0).getSubMenu().getItem(i).getTitle().toString()));
        }
    }

    public void setMenu(int menu){
        tool.inflateMenu(menu);
    }

    public void setOnItemClick(Toolbar.OnMenuItemClickListener listener) {
        tool.setOnMenuItemClickListener(listener);
    }

    // navIcons

    public void setNavIcon(int drawable, View.OnClickListener listener) {
        tool.setNavigationIcon(drawable);
        tool.setNavigationOnClickListener(listener);
    }

    public void setTickIcon(View.OnClickListener listner) {
        tool.setNavigationIcon(R.drawable.ic_tick_white);
        tool.setNavigationOnClickListener(listner);
    }

    public void setBackIcon(final Activity activity) {
        tool.setNavigationIcon(R.drawable.ic_back_white);
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    public void setCloseIcon(final Activity activity) {
        tool.setNavigationIcon(R.drawable.ic_close_white);
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    public void turnOnFloatingButton(boolean on) {
        if (on)
            btn.setVisibility(VISIBLE);
        else
            btn.setVisibility(GONE);
    }

    public FloatingActionButton getFloatingButton() {
        return btn;
    }


}
