package nf.co.thehoproject.tmdbmovies.web;

import nf.co.thehoproject.tmdbmovies.model.Movie;
import nf.co.thehoproject.tmdbmovies.model.MovieList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shivesh on 5/5/17.
 */

public interface MovieClientInterface {


    @GET("/3/movie/now_playing")
    Call<MovieList>  getMovieList(@Query("api_key") String apiKey,@Query("language") String language,@Query("page") String page);

    @GET("/3/movie/{id}")
    Call<Movie>  getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey );


        @GET("/3/search/movie")
        Call<MovieList>  searchMovies(@Query("api_key") String apiKey,@Query("query") String query);





}
