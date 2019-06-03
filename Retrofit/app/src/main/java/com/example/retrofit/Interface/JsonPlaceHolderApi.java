package com.example.retrofit.Interface;

import com.example.retrofit.Model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<Post>> getAllPosts();
}
