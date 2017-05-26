package nf.co.thehoproject.tmdbmovies.web;

import nf.co.thehoproject.tmdbmovies.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shivesh on 5/5/17.
 */

public class MovieClient {

    public static final String BASE_URL = Constants.API_BASE;
    private static Retrofit retrofit = null;


    public static Retrofit getRetrofitInstance() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
