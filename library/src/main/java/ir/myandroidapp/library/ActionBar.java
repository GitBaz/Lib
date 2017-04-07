package ir.myandroidapp.library;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by kam.amir on 4/7/17.
 */

public class ActionBar {

    Toolbar tool;
    Core core;

    public void actionBarInit(Toolbar toolbar) {
        tool = toolbar;
    }

    public void actionBarTitle(String title) {
        tool.setTitle(core.spannableString(title));
        tool.setTitleTextColor(core.getColor(R.color.white));
    }

    public void actionBarAddPanel(int menu) {
        tool.inflateMenu(menu);
        int size = tool.getMenu().getItem(0).getSubMenu().size();
        for (int i = 0; i < size; i++) {
            tool.getMenu().getItem(0).getSubMenu().getItem(i).setTitle(core.spannableString(
                    tool.getMenu().getItem(0).getSubMenu().getItem(i).getTitle().toString()));
        }
    }

    public void actionBarItemClick(Toolbar.OnMenuItemClickListener listener) {
        tool.setOnMenuItemClickListener(listener);
    }

    // navIcons

    public void actionBarNavIcon(int drawable, View.OnClickListener listener) {
        tool.setNavigationIcon(drawable);
        tool.setNavigationOnClickListener(listener);
    }

    public void actionbarTickIcon(View.OnClickListener listner) {
        tool.setNavigationIcon(R.drawable.ic_tick_white);
        tool.setNavigationOnClickListener(listner);
    }

    public void actionBarBackIcon(final Activity activity) {
        tool.setNavigationIcon(R.drawable.ic_back_white);
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    public void actionBarCloseIcon(final Activity activity) {
        tool.setNavigationIcon(R.drawable.ic_close_white);
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }


}
