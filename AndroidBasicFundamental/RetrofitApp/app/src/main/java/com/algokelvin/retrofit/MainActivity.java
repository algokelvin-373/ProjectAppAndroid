package com.algokelvin.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.algokelvin.retrofit.request.GetResponse;
import com.algokelvin.retrofit.request.JsonInterfaceApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewResult;
    private GetResponse getResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getPosts = (Button) findViewById(R.id.getPosts);
        Button getComments = (Button) findViewById(R.id.getComments);
        Button createPost = (Button) findViewById(R.id.createPost);
        Button updatePost = (Button) findViewById(R.id.updatePost);

        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        JsonInterfaceApi jsonPlaceHolderApi = retrofit.create(JsonInterfaceApi.class);
        getResponse = new GetResponse(jsonPlaceHolderApi);

        getPosts.setOnClickListener(this);
        getComments.setOnClickListener(this);
        createPost.setOnClickListener(this);
        updatePost.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.getPosts)
            getResponse.getPosts(textViewResult);
        else if (id == R.id.getComments)
            getResponse.getComments(textViewResult);
        else if (id == R.id.createPost)
            getResponse.createPost(textViewResult);
        else if (id == R.id.updatePost)
            getResponse.updatePost(textViewResult);
    }
}