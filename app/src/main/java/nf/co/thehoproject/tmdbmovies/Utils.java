package nf.co.thehoproject.tmdbmovies;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by shivesh on 5/5/17.
 */

public class Utils {


    public static String getPosterUrl(String posterPath)
    {
        String url="https://image.tmdb.org/t/p/w780";
        url+=posterPath;

        return url;


    }


    public static void hideNotificationBar(Activity ctx)
    {
        try {
            ctx.requestWindowFeature(Window.FEATURE_NO_TITLE);
            ctx.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static String AVENIR_MED="Avenir-Medium.ttf", CAVIAR="CaviarDreams.ttf",ROBOTO_THIN="Roboto-Thin.ttf";


    public static Typeface getFace(String font,Context ctx)
    {
         Typeface face = Typeface.createFromAsset(ctx.getAssets(),
                "fonts/"+font);

        if(face==null)
        {
            face=Typeface.createFromAsset(ctx.getAssets(),
                    "fonts/"+AVENIR_MED);
        }
         return face;

    }



    public static Typeface setFace(String font,TextView textView)
    {
        Context ctx=textView.getContext();
        Typeface face = Typeface.createFromAsset(ctx.getAssets(),
                "fonts/"+font);

        if(face==null)
        {
            face=Typeface.createFromAsset(ctx.getAssets(),
                    "fonts/"+AVENIR_MED);
        }
        textView.setTypeface(face);
        return face;

    }




}
