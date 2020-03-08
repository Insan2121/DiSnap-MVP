package com.example.disnap.data.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tHistory")
public class Disease implements Parcelable, Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "disease_name")
    private String diseaseName;

    @ColumnInfo(name = "disease_latin")
    private String diseaseLatin;

    @ColumnInfo(name = "accuration")
    private double accuration;

    @ColumnInfo(name = "result_image")
    private String resultImage;

    @ColumnInfo(name = "user_image")
    private String userImage;

    @ColumnInfo(name = "indication")
    private String indication;

    @ColumnInfo(name = "control")
    private String controling;

    @ColumnInfo(name = "pesticide")
    private String pesticide;

    @ColumnInfo(name = "date")
    private String date;

    public Disease() {
    }

    public Disease(int id, String diseaseName, String diseaseLatin, double accuration, String resultImage, String userImage, String indication, String controling, String pesticide, String date) {
        this.id = id;
        this.diseaseName = diseaseName;
        this.diseaseLatin = diseaseLatin;
        this.accuration = accuration;
        this.resultImage = resultImage;
        this.userImage = userImage;
        this.indication = indication;
        this.controling = controling;
        this.pesticide = pesticide;
        this.date = date;
    }

    public Disease(String diseaseName, String diseaseLatin, String userImage, String indication, String controling, String pesticide) {
        this.diseaseName = diseaseName;
        this.diseaseLatin = diseaseLatin;
        this.userImage = userImage;
        this.indication = indication;
        this.controling = controling;
        this.pesticide = pesticide;
    }

    public Disease(String diseaseName, String diseaseLatin, double accuration, String resultImage, String userImage, String indication, String controling, String pesticide, String date) {
        this.diseaseName = diseaseName;
        this.diseaseLatin = diseaseLatin;
        this.accuration = accuration;
        this.resultImage = resultImage;
        this.userImage = userImage;
        this.indication = indication;
        this.controling = controling;
        this.pesticide = pesticide;
        this.date = date;
    }

    protected Disease(Parcel in) {
        id = in.readInt();
        diseaseName = in.readString();
        diseaseLatin = in.readString();
        accuration = in.readDouble();
        resultImage = in.readString();
        userImage = in.readString();
        indication = in.readString();
        controling = in.readString();
        pesticide = in.readString();
        date = in.readString();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getAccuration() {
        return accuration;
    }

    public void setAccuration(double accuration) {
        this.accuration = accuration;
    }

    public String getResultImage() {
        return resultImage;
    }

    public void setResultImage(String resultImage) {
        this.resultImage = resultImage;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getControling() {
        return controling;
    }

    public void setControling(String controling) {
        this.controling = controling;
    }

    public String getPesticide() {
        return pesticide;
    }

    public void setPesticide(String pesticide) {
        this.pesticide = pesticide;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(diseaseName);
        dest.writeString(diseaseLatin);
        dest.writeDouble(accuration);
        dest.writeString(resultImage);
        dest.writeString(userImage);
        dest.writeString(indication);
        dest.writeString(controling);
        dest.writeString(pesticide);
        dest.writeString(date);
    }
}
