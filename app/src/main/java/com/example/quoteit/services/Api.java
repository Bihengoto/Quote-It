package com.example.quoteit.services;

import com.example.quoteit.models.Quote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("/popular.json")
    Call<List<Quote>> getQuote();
}
