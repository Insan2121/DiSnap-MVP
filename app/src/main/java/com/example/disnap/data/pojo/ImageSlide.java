package com.example.disnap.data.pojo;

import androidx.recyclerview.widget.RecyclerView;

public class ImageSlide {
    String img;
    String description;


    public ImageSlide(String img, String description) {
        this.img = img;
        this.description = description;
    }

    public ImageSlide() {

    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
