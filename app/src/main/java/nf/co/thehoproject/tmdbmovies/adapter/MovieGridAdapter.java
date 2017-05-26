package nf.co.thehoproject.tmdbmovies.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nf.co.thehoproject.tmdbmovies.R;
import nf.co.thehoproject.tmdbmovies.Utils;
import nf.co.thehoproject.tmdbmovies.model.Movie;
import okhttp3.internal.Util;


public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.CustomViewHolder> {
    public List<Movie> movieList;
    private Context ctx;

    public MovieGridAdapter(Context context, List<Movie> movieList) {
        this.movieList = movieList;
        this.ctx = context;
          WindowManager windowManager = (WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
          width = windowManager.getDefaultDisplay().getWidth();

    }

    public static int width;
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item,  null, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final Movie cat= movieList.get(i);
        final int id=i;
        customViewHolder.name.setText(Html.fromHtml(movieList.get(i).getTitle()));
        customViewHolder.year.setText(movieList.get(i).getReleaseDate());


         customViewHolder.name.setTypeface(Utils.getFace(Utils.AVENIR_MED,ctx));




        customViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                click(cat,id,customViewHolder.itempic);
            }
        });

      Picasso.with(ctx).load(Utils.getPosterUrl(cat.getPosterPath()))
               .placeholder(R.drawable.movie_plc)
                .into(customViewHolder.itempic);




    }

    @Override
    public int getItemCount() {
        return (null != movieList ? movieList.size() : 0);
    }

    public void click(Movie cat, int id, ImageView transitionStart)
    {

    }


public class CustomViewHolder extends RecyclerView.ViewHolder
{
    public TextView name;
    public TextView year;

    public ImageView itempic;
    public View view;



    public CustomViewHolder(View itemView) {
        super(itemView);
        view=itemView;
        name =(TextView)itemView.findViewById(R.id.movie_title);
        year =(TextView)itemView.findViewById(R.id.movie_year);




        itempic=(ImageView)itemView.findViewById(R.id.movie_poster);




    }
}




}
