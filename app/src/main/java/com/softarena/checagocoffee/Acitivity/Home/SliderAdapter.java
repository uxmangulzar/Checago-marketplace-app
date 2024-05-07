package com.softarena.checagocoffee.Acitivity.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softarena.checagocoffee.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }
    int images[] = {R.drawable.coffeeshop, R.drawable.mugshopn, R.drawable.newsimage};
    int headings[] = {R.string.first_slide,R.string.second_slide,R.string.third_slide};
    int Description[] = {R.string.first_slide_desc,R.string.second_slide_desc,R.string.third_slide_desc};


    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_slider,container,false);
        ImageView imageView = view.findViewById(R.id.sliderImage);
        TextView textView = view.findViewById(R.id.sliderHeading);
        TextView textView1 = view.findViewById(R.id.sliderDesc);

        imageView.setImageResource(images[position]);
        textView.setText(headings[position]);
        textView1.setText(Description[position]);

            container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
