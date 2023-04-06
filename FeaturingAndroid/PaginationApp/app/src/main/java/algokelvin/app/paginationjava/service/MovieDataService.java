package algokelvin.app.paginationjava.service;

import com.androidtutz.anushka.tmdbclient.model.MovieDBResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by K. A. ANUSHKA MADUSANKA on 7/9/2018.
 */
public interface MovieDataService {


    @GET("movie/popular")
    Call<MovieDBResponse> getPopularMovies(@Query("api_key") String apiKey);


}
