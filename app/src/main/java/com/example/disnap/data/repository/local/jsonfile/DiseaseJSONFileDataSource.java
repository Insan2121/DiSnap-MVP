package com.example.disnap.data.repository.local.jsonfile;

import android.util.Log;

import com.example.disnap.App;
import com.example.disnap.data.pojo.Disease;
import com.example.disnap.data.repository.DiseaseDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import men.ngopi.zain.jsonloaderlibrary.JSONLoader;
import men.ngopi.zain.jsonloaderlibrary.JSONObjectLoaderListener;

import static com.yalantis.ucrop.UCropFragment.TAG;

public class DiseaseJSONFileDataSource implements DiseaseDataSource {
    private static DiseaseJSONFileDataSource instance;

    public static DiseaseJSONFileDataSource getInstance() {
        if (instance == null) {
            instance = new DiseaseJSONFileDataSource();
        }
        return instance;
    }

    @Override
    public void insertAnalysisResultToDB(InsertAnalysisResultCallback callback, Disease disease) {

    }

    @Override
    public void removeHistoryFromDB(RemoveHistoryCallback callback, Disease disease) {

    }

    @Override
    public void analyzeImage(LoadAnalyzeCallback callback, String img) {

    }

    @Override
    public void getDiseaseAnalysisResultFromDB(LoadDiseaseCallback callback) {

    }

    @Override
    public void getDiseaseFromJSONFile(final LoadDiseaseFromJSONFileCallback callback) {
        final ArrayList<Disease> diseases = new ArrayList<>();
        callback.onShowLoading();
        JSONLoader.with(App.getContext())
                .fileName("disnap_data.json")
                .getAsJSONObject(new JSONObjectLoaderListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onHideLoading();
                        try {
                            diseases.addAll(insertData(response, "hama"));
                            diseases.addAll(insertData(response, "penyakit"));
                            callback.onDiseaseLoaded(diseases);
                            Log.d(TAG, "onResponse11: " + diseases.size());
                        } catch (JSONException e) {
                            callback.onHideLoading();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Exception error) {
                        callback.onHideLoading();
                    }
                });
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
                arrayList.add(new Disease(name, latin, img, indication, control, pesticide));
            }
        } catch (JSONException e) {
            Log.d("cekstatus", "gagalcatch");
            e.printStackTrace();
        }
        return arrayList;
    }
}
