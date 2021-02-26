package com.example.qaunewsalerts.datamodals;

public class AllDepartment_news {
    private String NewsDescription;
    private String userID;
    public String  userDepartment;
    private  String news_title;
    private String uri;
    public String  newsCategory;

    public AllDepartment_news(){}

    public AllDepartment_news(String NewsDescription, String uri,String  newsCategory,String  userDepartment,String userID) {
        this.NewsDescription = NewsDescription;
        this.uri = uri;
        this.newsCategory=newsCategory;
        this.userID=userID;
       this.userDepartment=userDepartment;
    }

    public String getNewsDescription() {
        return NewsDescription;
    }

    public void setNewsDescription(String news_description) {
        this.NewsDescription = news_description;
    }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }
}

