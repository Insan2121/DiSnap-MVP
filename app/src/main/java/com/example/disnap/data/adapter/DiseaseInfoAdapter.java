package com.example.disnap.data.adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
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
import com.example.disnap.ui.home.HomeFragment;


import java.util.ArrayList;

public class DiseaseInfoAdapter extends RecyclerView.Adapter<DiseaseInfoAdapter.ViewHolder> {

    private Context context;
    private View view;
    private ArrayList<Disease> diseaseInfoModelArrayList;
    private AssetManager assetManager;

    public DiseaseInfoAdapter(Context context, ArrayList<Disease> diseaseInfoModelArrayList) {
        this.context = context;
        this.diseaseInfoModelArrayList = diseaseInfoModelArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_disease_info, viewGroup, false);

        return new DiseaseInfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Disease diseaseInfoModel = diseaseInfoModelArrayList.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .asBitmap()
                .load(diseaseInfoModel.getDiseaseImage())
                .into(viewHolder.img);

        Log.d("cekgambar", diseaseInfoModel.getDiseaseImage());
        viewHolder.name.setText(diseaseInfoModel.getDiseaseName());
        viewHolder.latin.setText(diseaseInfoModel.getDiseaseLatin());


    }

    @Override
    public int getItemCount() {
        return diseaseInfoModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView latin;
        CardView cardView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.disease_image);
            name = itemView.findViewById(R.id.disease_name);
            latin = itemView.findViewById(R.id.disease_latin);
            cardView = itemView.findViewById(R.id.parent_layout);

            /*img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Disease diseaseInfoModel = diseaseInfoModelArrayList.get(getAdapterPosition());

                    Intent intent = new Intent(context, DetailDiseaseInfoActivity.class);
                    intent.putExtra("name", diseaseInfoModel.getDiseaseName());
                    intent.putExtra("latin", diseaseInfoModel.getDiseaseLatin());
                    intent.putExtra("image", diseaseInfoModel.getDiseaseImage());
                    intent.putExtra("pesticide", diseaseInfoModel.getPesticide());
                    intent.putExtra("indication", diseaseInfoModel.getIndication());
                    intent.putExtra("control", diseaseInfoModel.getControl());
                    context.startActivity(intent);

                    Log.d("cucu", diseaseInfoModel.getDiseaseName());
                }
            });*/

        }
    }
}
