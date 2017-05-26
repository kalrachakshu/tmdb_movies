package nf.co.thehoproject.tmdbmovies;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import nf.co.thehoproject.tmdbmovies.model.Movie;
import nf.co.thehoproject.tmdbmovies.model.ProductionCompany;
import nf.co.thehoproject.tmdbmovies.web.MovieClient;
import nf.co.thehoproject.tmdbmovies.web.MovieClientInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    MovieClientInterface apiService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideNotificationBar(this);

        setContentView(R.layout.activity_detail);
        try {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


            setSupportActionBar(toolbar);
            setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        initUI();


        int id = getIntent().getIntExtra("id", 0);
        apiService = MovieClient.getRetrofitInstance().create(MovieClientInterface.class);
        if (apiService != null) {

            //CALL API TO GET TOP MOVIE LIST
            Call<Movie> call = apiService.getMovieDetails(id, Constants.API_KEY);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {

                    updateUI(response.body());

                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {

                     Logger.l(t.toString());
                }
            });


        }
    }

    public void updateUI(final Movie movie)
    {
        title.setText(movie.getTitle());
        Utils.setFace(Utils.AVENIR_MED,title);

        releaseDate.setText(movie.getReleaseDate());
        Utils.setFace(Utils.CAVIAR,releaseDate);


        Picasso.with(this).load(Utils.getPosterUrl(movie.getPosterPath())).placeholder(R.drawable.movie_plc).into(coverImage);

        webSite.setText(movie.getHomepage());
        webSite.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        Utils.setFace(Utils.CAVIAR,webSite);

        webSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String externUrl=movie.getHomepage();
                if(externUrl==null)
                    return;
                    Intent it=new Intent(Intent.ACTION_VIEW);
                    it.setData(Uri.parse(externUrl));
                    startActivity(it);

            }
        });



        String compa="";
        for(ProductionCompany comp:movie.getProductionCompanies()){

            compa+=comp.getName()+"\n";

        }
        companies.setText(compa);
        Utils.setFace(Utils.CAVIAR,companies);

        tags.setText(movie.getTagline());
        Utils.setFace(Utils.ROBOTO_THIN,tags);

        detailOverview.setText(movie.getOverview());
        Utils.setFace(Utils.ROBOTO_THIN,detailOverview);


        shareButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent();
                it.setType("text/plain");
                it.setAction(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_TEXT, movie.getHomepage());
                startActivity(Intent.createChooser(it, "Share via"));

            }
        });


        getSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String externUrl=getResources().getString(R.string.movie_schedule,URLEncoder.encode(movie.getTitle(),"UTF-8"));
                    Intent it=new Intent(Intent.ACTION_VIEW);
                    it.setData(Uri.parse(externUrl));
                    startActivity(it);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    private ImageView coverImage;
    private TextView title, releaseDate, webSite, companies, tags, detailOverview;

    private View shareButtom,getSchedule;

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    public void initUI()
    {

        title = (TextView) findViewById(R.id.title);
        releaseDate = (TextView) findViewById(R.id.releaseDate);
        coverImage = (ImageView) findViewById(R.id.coverImage);
        webSite = (TextView) findViewById(R.id.webSite);
        companies = (TextView) findViewById(R.id.companies);
        tags = (TextView) findViewById(R.id.tags);
        detailOverview = (TextView) findViewById(R.id.detailOverview);
        shareButtom = findViewById(R.id.shareButtom);
        getSchedule = findViewById(R.id.getSchedule);
    }
}
