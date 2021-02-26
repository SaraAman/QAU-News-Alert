package com.example.qaunewsalerts.adaptor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.qaunewsalerts.R;

public class SliderAdapter extends PagerAdapter {
    private Context slidercontext;
    private int[] sliderimagesID=new int[]{R.drawable.slider1,R.drawable.one,R.drawable.two,R.drawable.three};
    public SliderAdapter(Context context)
    {
        slidercontext=context;

    }

    @Override
    public int getCount() {
        return sliderimagesID.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView=new ImageView(slidercontext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(sliderimagesID[position]);
        container.addView(imageView,0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((ImageView)object);
    }
}
