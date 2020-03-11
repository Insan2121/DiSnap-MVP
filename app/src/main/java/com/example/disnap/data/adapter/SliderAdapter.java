package com.example.disnap.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.disnap.R;
import com.example.disnap.data.pojo.ImageSlide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {
    private Context context;
    private List<ImageSlide> mSliderItems = new ArrayList<>();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<ImageSlide> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(ImageSlide sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_result, parent, false);
        return new SliderAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        ImageSlide imageSlide = mSliderItems.get(position);

        viewHolder.desc.setText(imageSlide.getDescription());

        Glide.with(context)
                .asBitmap()
                .load(imageSlide.getImg())
                .into(viewHolder.img);
    }


    @Override
    public int getCount() {
        return mSliderItems.size();
    }


    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        ImageView img;
        TextView desc;
        public SliderAdapterVH(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageSlider);
            desc = itemView.findViewById(R.id.textImageSlider);
        }
    }
}
