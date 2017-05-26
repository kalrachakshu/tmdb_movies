package nf.co.thehoproject.tmdbmovies;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nf.co.thehoproject.tmdbmovies.adapter.GridSpacingItemDecoration;
import nf.co.thehoproject.tmdbmovies.adapter.MovieGridAdapter;
import nf.co.thehoproject.tmdbmovies.model.Movie;
import nf.co.thehoproject.tmdbmovies.model.MovieList;
import nf.co.thehoproject.tmdbmovies.web.MovieClient;
import nf.co.thehoproject.tmdbmovies.web.MovieClientInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Context ctx;

    ProgressBar progressBar;

     TextView log_text;

     RecyclerView recyclerView;

    MovieClientInterface apiService;
    public void bindLayout()
    {
        progressBar=(ProgressBar)findViewById(R.id.loading);
        log_text=(TextView)findViewById(R.id.log);
        recyclerView=(RecyclerView)findViewById(R.id.rec);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideNotificationBar(this);

        setContentView(R.layout.activity_main);
        ctx=this;
        Logger.init("MainActivity");
        bindLayout();



        progressBar.setVisibility(View.VISIBLE);
        //GET RETROFIT INSTANCE AND CREATE INTERFACE
         apiService = MovieClient.getRetrofitInstance().create(MovieClientInterface.class);
        if(apiService!=null)
        {

            //CALL API TO GET TOP MOVIE LIST
            Call<MovieList> call = apiService.getMovieList(Constants.API_KEY, "en-US", "undefined");
            call.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList>call, Response<MovieList> response) {
                    progressBar.setVisibility(View.GONE);
                    List<Movie> movies = response.body().getResults();
                    Logger.l( "List Size : " + movies.size());
                    setUpGrid(movies);
                }

                @Override
                public void onFailure(Call<MovieList>call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Logger.l( t.toString());
                }
            });



        }




    }



    public void recycle()
    {
        recyclerView.removeItemDecoration(decor);
        recyclerView.removeAllViews();
        recyclerView.getLayoutManager().removeAllViews();
        recyclerView.removeAllViewsInLayout();
    }


    public void setUpGrid(final List<Movie> movies)
    {


        MovieGridAdapter adapter=new MovieGridAdapter(ctx,movies)
        {
            @Override
            public void click(Movie cat, int id, ImageView imageView) {
                super.click(cat, id,imageView);
                Logger.l(cat.getTitle());

                Intent intent=new Intent(ctx,DetailActivity.class);
                intent.putExtra("id",cat.getId());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //Adding Expansion transition POSTER-to-POSTER
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(MainActivity.this, imageView, getString(R.string.activity_image_trans));
                    startActivity(intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }



            }
        };
        GridLayoutManager gridLayoutManager=new GridLayoutManager(ctx,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(decor);


    }

    GridSpacingItemDecoration decor=new GridSpacingItemDecoration(2,5,true,0);


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.menu, menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                recycle();
               // https://api.themoviedb.org/3/search/movie?api_key=<<api_key>>&language=en-US&query=fff&page=1&include_adult=false

                progressBar.setVisibility(View.VISIBLE);

                //CALL API TO GET TOP MOVIE LIST
                Call<MovieList> call = apiService.searchMovies(Constants.API_KEY, query);
                call.enqueue(new Callback<MovieList>() {
                    @Override
                    public void onResponse(Call<MovieList>call, Response<MovieList> response) {
                        progressBar.setVisibility(View.GONE);
                        List<Movie> movies = response.body().getResults();
                        Logger.l( "List Size : " + movies.size());
                        setUpGrid(movies);
                    }

                    @Override
                    public void onFailure(Call<MovieList>call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Logger.l( t.toString());
                    }
                });


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });




        return true;
    }








}
