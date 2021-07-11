package com.retrofittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.retrofittest.model.Comment;
import com.retrofittest.model.Post;
import com.retrofittest.requests.JsonPlaceHolderApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    Button getPosts,getComments,createPost, updatePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bind xml button to java side button code
        getPosts = (Button) findViewById(R.id.getPosts);
        getComments = (Button) findViewById(R.id.getComments);
        createPost = (Button) findViewById(R.id.createPost);
        updatePost = (Button) findViewById(R.id.updatePost);

        textViewResult = findViewById(R.id.text_view_result);


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        getPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPosts();
            }
        });
        getComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getComments();
            }
        });
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost();
            }
        });
        updatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePost();
            }
        });



    }


    private void getPosts() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }

                List<Post> posts = response.body();

                for(Post post: posts) {
                    String content = "";
                    content += "ID: " +post.getId() + "\n";
                    content += "User ID: " +post.getUserId() + "\n";
                    content += "Title: " +post.getTitle() + "\n";
                    content += "Text: " +post.getText() + "\n\n";

                    textViewResult.append(content);

                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getComments() {

        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(1);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }

                List<Comment> comments = response.body();

                for (Comment comment : comments) {
                    String content = "";
                    content += "ID: " +comment.getId() + "\n";
                    content += "Post ID: " +comment.getPostId() + "\n";
                    content += "Name: " +comment.getName() + "\n";
                    content += "Email: " +comment.getEmail() + "\n";
                    content += "Text: " +comment.getText() + "\n\n";

                    textViewResult.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPost() {

        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New Title");



        Call<Post> call = jsonPlaceHolderApi.createPost(fields);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }

                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " +postResponse.getId() + "\n";
                content += "User ID: " +postResponse.getUserId() + "\n";
                content += "Title: " +postResponse.getTitle() + "\n";
                content += "Text: " +postResponse.getText() + "\n\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void updatePost() {

        Post post  = new Post(12, null, "New Text");

        Call<Post> call = jsonPlaceHolderApi.patchPost(5, post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {


                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }

                Post postResponse = response.body();
                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " +postResponse.getId() + "\n";
                content += "User ID: " +postResponse.getUserId() + "\n";
                content += "Title: " +postResponse.getTitle() + "\n";
                content += "Text: " +postResponse.getText() + "\n\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void deletePost() {
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
