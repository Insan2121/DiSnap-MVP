package com.example.disnap.data.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.disnap.R;
import com.squareup.picasso.Picasso;

public class ImageViewPagerAdapter extends PagerAdapter {
    private Context context;
    private String[][] imgAndDesc;

    public ImageViewPagerAdapter(Context context, String[][] imgAndDesc) {
        this.context = context;
        this.imgAndDesc = imgAndDesc;
    }

    @Override
    public int getCount() {
        return imgAndDesc.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_result_analyze_image, null);

        ImageView imageView = view.findViewById(R.id.img_view_res);
        TextView textView = view.findViewById(R.id.tv_image_belong);

        textView.setText(imgAndDesc[position][1]);

        Picasso.get()
                .load(imgAndDesc[position][0])
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePopup imagePopup = new ImagePopup(context);

                imagePopup.setWindowHeight(800);
                imagePopup.setWindowWidth(800);
                imagePopup.setBackgroundColor(Color.BLACK);
                imagePopup.setFullScreen(true);
                imagePopup.setHideCloseIcon(true);
                imagePopup.setImageOnClickClose(true);

                imagePopup.initiatePopupWithGlide(imgAndDesc[position][0]);
                imagePopup.viewPopup();
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
