package com.example.disnap.ui.home;

import android.content.Context;
import android.util.Log;

import com.example.disnap.data.pojo.Disease;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import men.ngopi.zain.jsonloaderlibrary.JSONLoader;
import men.ngopi.zain.jsonloaderlibrary.JSONObjectLoaderListener;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mView;
    private Context context;

    HomePresenter(HomeContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
    }


    @Override
    public void start() {

    }

    @Override
    public void populateDiseaseInfo() {
        final ArrayList<Disease> diseases = new ArrayList<>();
        mView.showProgress();
        JSONLoader.with(this.context)
                .fileName("disnap_data.json")
                .getAsJSONObject(new JSONObjectLoaderListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mView.hideProgress();
                        try {
                            diseases.addAll(insertData(response, "hama"));
                            diseases.addAll(insertData(response, "penyakit"));
                            mView.setDiseaseInfo(diseases);
                        } catch (JSONException e) {
                            mView.hideProgress();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Exception error) {
                        mView.hideProgress();
                    }
                });
    }

    @Override
    public void openDetailDiseaseInfoActivity(Disease disease) {
        mView.showDetailDiseaseInfoActivity(disease);
    }

    private ArrayList<Disease> insertData(JSONObject jsonObject, String jenis) throws JSONException {
        ArrayList<Disease> arrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(jenis);
            for (int i = 0; i < jsonArray.length(); i++) {
                String img = jsonArray.getJSONObject(i).getString("img");
                String name = jsonArray.getJSONObject(i).getString("nama");
                String latin = jsonArray.getJSONObject(i).getString("ilmiah");
                String pesticide = jsonArray.getJSONObject(i).getString("pestisida");
                //String[] pesticide = jsonArray.getJSONObject(i).("pestisida");
                String indication = jsonArray.getJSONObject(i).getString("gejala");
                String control = jsonArray.getJSONObject(i).getString("pengendalian");
                arrayList.add(new Disease(img, name, latin, pesticide, indication, control));

            }
        } catch (JSONException e) {
            Log.d("cekstatus", "gagalcatch");
            e.printStackTrace();
        }
        return arrayList;
    }
}
