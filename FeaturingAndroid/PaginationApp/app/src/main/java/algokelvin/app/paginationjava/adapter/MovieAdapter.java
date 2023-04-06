package algokelvin.app.paginationjava.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algokelvin.app.paginationjava.R;
import algokelvin.app.paginationjava.databinding.MovieListItemBinding;
import algokelvin.app.paginationjava.model.Movie;
import algokelvin.app.paginationjava.view.MovieActivity;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private Context context;
    private ArrayList<Movie> movieArrayList;

    public MovieAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         MovieListItemBinding movieListItemBinding= DataBindingUtil.inflate(
                 LayoutInflater.from(parent.getContext()),
                 R.layout.movie_list_item,parent,
                 false
         );
        return new MovieViewHolder(movieListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie=movieArrayList.get(position);
        String imagePath="https://image.tmdb.org/t/p/w500"+movie.getPosterPath();
        movie.setPosterPath(imagePath);
        holder.movieListItemBinding.setMovie(movie);
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private MovieListItemBinding movieListItemBinding;

        public MovieViewHolder(@NonNull MovieListItemBinding movieListItemBinding) {
            super(movieListItemBinding.getRoot());
            this.movieListItemBinding=movieListItemBinding;

            movieListItemBinding.getRoot().setOnClickListener(view -> {
                int position=getAdapterPosition();

                if(position != RecyclerView.NO_POSITION) {
                    Movie selctedMovie = movieArrayList.get(position);
                    Intent intent=new Intent(context, MovieActivity.class);
                    intent.putExtra("movie",selctedMovie);
                    context.startActivity(intent);
                }
            });

        }
    }
}
