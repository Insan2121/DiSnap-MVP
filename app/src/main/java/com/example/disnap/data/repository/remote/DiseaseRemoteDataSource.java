package com.example.disnap.data.repository.remote;
import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.disnap.data.pojo.Disease;
import com.example.disnap.data.repository.DiseaseDataSource;
import com.example.disnap.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.isfaaghyth.rak.Rak;

import static com.yalantis.ucrop.UCropFragment.TAG;

public class DiseaseRemoteDataSource implements DiseaseDataSource {
    private static DiseaseRemoteDataSource instance;

    public static DiseaseRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new DiseaseRemoteDataSource();
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
    public void analyzeImage(final LoadAnalyzeCallback callback, String img) {
        callback.onShowLoading();
        String indexRandom = randomNumberString(getRandomNumber());
        AndroidNetworking.upload(Constants.uimgurAPI)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization",Constants.authImgur)
                .addHeaders( "Content-Type", "x-www-form-urlencoded")
                .addMultipartParameter("name","image"+indexRandom)
                .addMultipartFile("image", new File(img))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String data = response.getJSONObject("data").getString("link");
                            String url = removeUnusedChar(data);
                            Log.i("=====", "onResponse: "+url);
                            predictImage(callback, url);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("prosessresponse", response.toString());
                    }

                    @Override
                    public void onError(ANError anError) {
                        callback.onError();
                    }
                });
    }

    @Override
    public void getDiseaseAnalysisResultFromDB(LoadDiseaseCallback callback) {

    }

    @Override
    public void getDiseaseFromJSONFile(LoadDiseaseFromJSONFileCallback callback) {

    }

    private void predictImage(final LoadAnalyzeCallback callback, final String url) throws JSONException {
        callback.onShowLoading();
        AndroidNetworking.post(Constants.clarifaiAPI)
                .setPriority(Priority.IMMEDIATE)
                .addHeaders("Authorization", Constants.authClarifai)
                .addHeaders("Content-Type", "application/json")
                .addJSONObjectBody(this.getBody(url))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onHideLoading();
                        try {
                            Date date = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                            String formattedDate = df.format(date);

                            JSONArray jsonArray = response.getJSONArray("outputs");
                            Log.d("mamang", jsonArray.toString());
                            JSONObject a = jsonArray.getJSONObject(0);
                            Log.d("mamang2", a.toString());
                            JSONObject b = a.getJSONObject("data");
                            Log.d("mamang3", b.toString());
                            JSONArray c = b.getJSONArray("concepts");
                            Log.d("mamang4", c.toString());
                            String name = c.getJSONObject(0).getString("name");
                            double value = c.getJSONObject(0).getDouble("value");

                            Log.i(TAG, "onResponse: "+name);
                            Log.i(TAG, "onResponse: "+value+"");
                            Log.i(TAG, "onResponse: "+getPersentageResult(value));

                            ArrayList<Disease> diseaseArrayList;
                            Disease disease = new Disease();
                            disease.setDiseaseName(name);
                            if (Rak.grab("ListDiseaseTemp") != null){
                                diseaseArrayList = Rak.grab("ListDiseaseTemp");
                                Log.d(TAG, "onResponse12: "+diseaseArrayList.size());
                                for (int i = 0; i < diseaseArrayList.size() ; i++) {
                                    if (name.equalsIgnoreCase(diseaseArrayList.get(i).getDiseaseName())){
                                        disease.setDiseaseLatin(diseaseArrayList.get(i).getDiseaseLatin());
                                        disease.setAccuration(value);
                                        disease.setResultImage(diseaseArrayList.get(i).getUserImage());
                                        disease.setUserImage(url);
                                        disease.setIndication(diseaseArrayList.get(i).getIndication());
                                        disease.setControling(diseaseArrayList.get(i).getControling());
                                        disease.setPesticide(diseaseArrayList.get(i).getPesticide());
                                        disease.setDate(formattedDate);

                                        Log.d(TAG, "onResponse0: "+disease.getDiseaseName());
                                        Log.d(TAG, "onResponse1: "+disease.getDiseaseLatin());
                                        Log.d(TAG, "onResponse2: "+disease.getAccuration());
                                        Log.d(TAG, "onResponse3: "+disease.getResultImage());
                                        Log.d(TAG, "onResponse4: "+disease.getUserImage());
                                        Log.d(TAG, "onResponse5: "+disease.getIndication());
                                        Log.d(TAG, "onResponse6: "+disease.getControling());
                                        Log.d(TAG, "onResponse7: "+disease.getPesticide());
                                        Log.d(TAG, "onResponse8: "+disease.getDate());
                                        callback.onAnalyzeSuccess(disease);
                                    }
                                }
                            }
                            Log.d(TAG, "onResponse: "+"Rak == null");
                        } catch (JSONException e) {
                            callback.onHideLoading();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        callback.onHideLoading();
                        callback.onError();
                    }
                });

    }

    private double getRandomNumber() {
        return Math.random();
    }

    private String randomNumberString(double a) {
        return String.valueOf(a);
    }

    private String removeUnusedChar(String a) {
        return a.replaceAll("'\'", "");
    }

    private JSONObject getBody(String url) throws JSONException {
        JSONArray inputs = new JSONArray();
        inputs.put(
                new JSONObject().put(
                        "data",
                        new JSONObject().put("image", new JSONObject().put(
                                "url",
                                url
                        ))
                )
        );
        JSONObject model = new JSONObject().put(
                "output_info",
                new JSONObject().put(
                        "output_config",
                        new JSONObject().put(
                                "max_concepts",
                                3
                        )
                )
        );
        return new JSONObject().put("inputs", inputs).put("model", model);
    }

    private String getPersentageResult(double value) {
        double result = value * 100;
        return result + " %";
    }
}
