package com.example.disnap.data.pojo;

public class Disease {
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
}
