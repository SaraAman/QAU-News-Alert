package com.example.qaunewsalerts.datamodals;

import com.example.qaunewsalerts.activities.AddNews;
import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.google.gson.annotations.SerializedName;

public class News {

    @SerializedName("NewsTitle")
    private String NewsTitle;
    @SerializedName("NewsDescription")
    private String NewsDescription;
    @SerializedName("NewsImage")
    private String NewsImage;
    @SerializedName("Id")
    private String key_id;
    private String favStatus;

    public Boolean getRead_status() {
        return read_status;
    }

    public void setRead_status(Boolean read_status) {
        this.read_status = read_status;
    }

    private  Boolean read_status;
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    private String userID;
    public News(){}


    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }

    public News( String NewsTitle,String NewsDescription, String NewsImage){}

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getNewsDescription() {
        return NewsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        NewsDescription = newsDescription;
    }

    public String getNewsImage() {
        return NewsImage;
    }

    public void setNewsImage(String newsImage) {
        NewsImage = newsImage;
    }
}
