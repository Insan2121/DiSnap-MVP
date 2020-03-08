package com.example.disnap.data.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.disnap.R;
import com.example.disnap.data.pojo.Disease;
import com.example.disnap.ui.home.HomeContract;

import java.util.ArrayList;

import static com.yalantis.ucrop.UCropFragment.TAG;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.ViewHolder> {

    private ArrayList<Disease> mDisease;
    private HomeContract.OnItemClickListener onItemClickListener;

    public DiseaseAdapter(HomeContract.OnItemClickListener onItemClickListener) {
        mDisease = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_disease_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.mItem = mDisease.get(position);

        holder.name.setText(mDisease.get(position).getDiseaseName());
        holder.latin.setText(mDisease.get(position).getDiseaseLatin());
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(mDisease.get(position).getUserImage())
                .into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + mDisease.get(position).getDiseaseName());
                onItemClickListener.clickItem(holder.mItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDisease.size();
    }

    public void setValues(ArrayList<Disease> diseases) {
        mDisease = diseases;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View nView;
        ImageView img;
        TextView name;
        TextView latin;
        CardView cardView;
        Disease mItem;

        ViewHolder(View mView) {
            super(mView);
            nView = mView;
            img = mView.findViewById(R.id.disease_image);
            name = mView.findViewById(R.id.disease_name);
            latin = mView.findViewById(R.id.disease_latin);
            cardView = mView.findViewById(R.id.parent_layout);
        }
    }
}
