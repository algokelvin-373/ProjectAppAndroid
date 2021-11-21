package com.algokelvin.retrofit.request;

import android.widget.TextView;

import com.algokelvin.retrofit.model.Comment;
import com.algokelvin.retrofit.model.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetResponse {
    private final JsonInterfaceApi jsonInterfaceApi;

    public GetResponse(JsonInterfaceApi jsonInterfaceApi) {
        this.jsonInterfaceApi = jsonInterfaceApi;
    }

    public void getPosts(TextView textViewResult) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        Call<List<Post>> call = jsonInterfaceApi.getPosts(parameters);

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

    public void getComments(TextView textViewResult) {
        Call<List<Comment>> call = jsonInterfaceApi.getComments(1);

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

    public void createPost(TextView textViewResult) {
        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New Title");

        Call<Post> call = jsonInterfaceApi.createPost(fields);

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

    public void updatePost(TextView textViewResult) {
        Post post  = new Post(12, null, "New Text");

        Call<Post> call = jsonInterfaceApi.patchPost(5, post);

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
}
