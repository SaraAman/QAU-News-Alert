package com.example.qaunewsalerts.datamodals;

import com.google.gson.annotations.SerializedName;

public class FAVItem {
    private String key_id;
    @SerializedName("NewsTitle")
    private String NewsTitle;
    @SerializedName("NewsDescription")
    private String NewsDescription;
    @SerializedName("NewsImage")
    private String NewsImage;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public FAVItem() {
    }

    public FAVItem(String key_id, String newsTitle, String newsDescription, String newsImage, String userId) {
        this.key_id = key_id;
        NewsTitle = newsTitle;
        NewsDescription = newsDescription;
        NewsImage = newsImage;
        this.userId = userId;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public void setNewsDescription(String newsDescription) {
        NewsDescription = newsDescription;
    }

    public void setNewsImage(String newsImage) {
        NewsImage = newsImage;
    }

    public String getKey_id() {
        return key_id;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public String getNewsDescription() {
        return NewsDescription;
    }

    public String getNewsImage() {
        return NewsImage;
    }
}
