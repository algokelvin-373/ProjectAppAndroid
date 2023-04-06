package algokelvin.app.paginationjava.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by K. A. ANUSHKA MADUSANKA on 7/9/2018.
 */
public class RetrofitInstance {

    private static Retrofit retrofit = null;
    private static String BASE_URL="https://api.themoviedb.org/3/";

    public static MovieDataService getService(){


        if(retrofit==null){

               retrofit=new Retrofit
                       .Builder()
                       .baseUrl(BASE_URL)
                       .addConverterFactory(GsonConverterFactory.create())
                       .build();

        }

        return retrofit.create(MovieDataService.class);

    }

}
