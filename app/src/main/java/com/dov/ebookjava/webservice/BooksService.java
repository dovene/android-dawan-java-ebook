package com.dov.ebookjava.webservice;


import com.dov.ebookjava.model.BooksResponse;
import com.dov.ebookjava.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BooksService {
    //https://www.googleapis.com/books/v1/volumes?q=' + titleBook + '+inauthor:'+ auteur
    @GET("volumes")
    Call<Response.Root> getBooks(@Query("q") String sort);
}