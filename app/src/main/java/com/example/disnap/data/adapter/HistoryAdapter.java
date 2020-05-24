package com.example.disnap.data.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.disnap.R;
import com.example.disnap.data.pojo.Disease;

import java.util.ArrayList;

import static com.yalantis.ucrop.UCropFragment.TAG;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;

    public interface OnItemClickListener {
        void clickItem(Disease disease);

        void clickRemoveButton(Disease disease);
    }

    private ArrayList<Disease> historyArrayList;
    private OnItemClickListener itemClickListener;

    public HistoryAdapter(OnItemClickListener itemClickListener, Context context) {
        this.context = context;
        historyArrayList = new ArrayList<>();
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.mItem = historyArrayList.get(position);

        holder.diseaseName.setText(holder.mItem.getDiseaseName());
        holder.latinName.setText(holder.mItem.getDiseaseLatin());
        holder.date.setText(holder.mItem.getDate());

        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(holder.mItem.getUserImage())
                .into(holder.imageHistory);

        holder.imageHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + historyArrayList.get(position).getDiseaseName());
                itemClickListener.clickItem(holder.mItem);
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick01: " + "true");
                showAlertDialog(position, holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyArrayList.size();
    }

    public void setValues(ArrayList<Disease> diseases) {
        historyArrayList = diseases;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View nView;
        private ImageView imageHistory;
        private TextView diseaseName;
        private TextView latinName;
        private TextView date;
        private Button btnRemove;
        Disease mItem;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nView = itemView;
            imageHistory = itemView.findViewById(R.id.img_history_disease);
            diseaseName = itemView.findViewById(R.id.tv_history_disease_name);
            latinName = itemView.findViewById(R.id.tv_history_disease_latin);
            date = itemView.findViewById(R.id.tv_history_date);
            btnRemove = itemView.findViewById(R.id.btn_remove_history);
        }
    }

    private void removeHistoryItem(int position) {
        historyArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, historyArrayList.size());
    }

    private void showAlertDialog(final int position, final Disease disease) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Remove");
        builder.setMessage("Apakah anda yakin ingin menghapus riwayat ini?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemClickListener.clickRemoveButton(disease);
                removeHistoryItem(position);
            }
        })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
