package com.example.qaunewsalerts.datamodals;

public class NewsRequest {
    private String news_description;
    private  String news_title;
    private String uri;
    public String  newsCategory;
    private String userID;
    public String  userDepartment;

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    public String getNews_description() {
        return news_description;
    }

    public void setNews_description(String news_description) {
        this.news_description = news_description;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public NewsRequest(){}

    public NewsRequest(String news_description, String uri,String  newsCategory,String userID,String userDepartment) {
        this.news_description = news_description;
        this.uri = uri;
        this.newsCategory=newsCategory;
        this.userDepartment=userDepartment;
        this.userID=userID;
    }

    public String getNewsDescription() {
        return news_description;
    }

    public void setNewsDescription(String news_description) {
        this.news_description = news_description;
    }

    public String geturi() {
        return uri;
    }

    public void seturi(String uri) {
        this.uri = uri;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getnewsCategory() {
        return newsCategory;
    }

    public void setnewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
    }

}
