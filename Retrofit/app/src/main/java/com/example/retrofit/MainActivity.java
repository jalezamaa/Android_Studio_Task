package com.example.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.retrofit.Interface.JsonPlaceHolderApi;
import com.example.retrofit.Model.Post;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity   {

    private static String BaseURL = "https://jsonplaceholder.typicode.com/";
    private TextView mJsonTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJsonTxtView = findViewById(R.id.jsontext);
        getPosts();
    }

    private void getPosts()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = jsonPlaceHolderApi.getAllPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful())
                {
                    mJsonTxtView.setText("Codigo" + response.code());
                    return;
                }

                List<Post> postsList = response.body();

                for(Post post:postsList)
                {
                    String content = "";
                    content += "userId:"+ post.getUserId() + "\n";
                    content += "id:"+ post.getId() + "\n";
                    content += "title:"+ post.getTitle() + "\n";
                    content += "body:"+ post.getBody() + "\n\n";

                    mJsonTxtView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                mJsonTxtView.setText(t.getMessage());
            }
        });
    }
}
