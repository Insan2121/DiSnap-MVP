package com.example.disnap.data.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Disease implements Parcelable {
    /*@Expose
   @SerializedName("diseaseImage") */
    private String diseaseImage;
    /*@Expose
    @SerializedName("diseaseName") */
    private String diseaseName;
    /*@Expose
    @SerializedName("diseaseLatin")*/
    private String diseaseLatin;
    private String pesticide;
    private String indication;
    private String control;

    public Disease(String diseaseImage, String diseaseName, String diseaseLatin, String pesticide, String indication, String control) {
        this.diseaseImage = diseaseImage;
        this.diseaseName = diseaseName;
        this.diseaseLatin = diseaseLatin;
        this.pesticide = pesticide;
        this.indication = indication;
        this.control = control;
    }


    public String getDiseaseImage() {
        return diseaseImage;
    }

    public void setDiseaseImage(String diseaseImage) {
        this.diseaseImage = diseaseImage;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseLatin() {
        return diseaseLatin;
    }

    public void setDiseaseLatin(String diseaseLatin) {
        this.diseaseLatin = diseaseLatin;
    }

    public String getPesticide() {
        return pesticide;
    }

    public void setPesticide(String pesticide) {
        this.pesticide = pesticide;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    protected Disease(Parcel in) {
        diseaseImage = in.readString();
        diseaseName = in.readString();
        diseaseLatin = in.readString();
        pesticide = in.readString();
        indication = in.readString();
        control = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(diseaseImage);
        dest.writeString(diseaseName);
        dest.writeString(diseaseLatin);
        dest.writeString(pesticide);
        dest.writeString(indication);
        dest.writeString(control);
    }

    public static final Creator<Disease> CREATOR = new Creator<Disease>() {
        @Override
        public Disease createFromParcel(Parcel in) {
            return new Disease(in);
        }

        @Override
        public Disease[] newArray(int size) {
            return new Disease[size];
        }
    };


}
