package com.example.qaunewsalerts;

import com.example.qaunewsalerts.datamodals.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsInterface {

    @GET("qauApi.php")
    Call<List<News>> getAllNews();
}
