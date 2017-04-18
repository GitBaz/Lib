package ir.myandroidapp.library.cards;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ir.myandroidapp.library.Core;
import ir.myandroidapp.library.R;
import ir.myandroidapp.library.Size;

/**
 * Created by kam.amir on 2/14/17.
 */
public class ViewPager extends android.support.v4.view.ViewPager {

    Core core;

    public ViewPager(Context context, Core cre, WindowManager wm, final int count, final Set set) {
        super(context);
        Size size = new Size(context, wm);
        core = cre;

        this.setLayoutParams(new ViewGroup.LayoutParams(size.getW(), size.getW() / 2));
        this.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return count;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return (view == object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                return set.setImages(container, position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }

    public interface Set {
        Object setImages(ViewGroup container, int position);
    }

    public ImageView pager(ViewGroup container, String image) {
        final ImageView imageView = new ImageView(core.context);
        imageView.setBackgroundColor(core.getColor(R.color.colorCard));
        Picasso.with(core.context).load(image).into(imageView);
        container.addView(imageView);
        return imageView;
    }
}