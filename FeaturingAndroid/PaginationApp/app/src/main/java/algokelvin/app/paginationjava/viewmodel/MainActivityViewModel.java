package algokelvin.app.paginationjava.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import algokelvin.app.paginationjava.model.Movie;
import algokelvin.app.paginationjava.model.MovieRepository;

public class MainActivityViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public LiveData<List<Movie>> getAllMovies(){
        return movieRepository.getMutableLiveData();
    }

}
