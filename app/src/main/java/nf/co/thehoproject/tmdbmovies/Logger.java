package nf.co.thehoproject.tmdbmovies;

import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by shivesh on 5/5/17.
 */

public class Logger {


    private static String TAG="TMDB";
    public static void init(String TAG)
    {
        Logger.TAG=TAG;
    }


    public static void l(@Nullable String TAG, String text)
    {

        if(TAG==null)
        {
            TAG= Logger.TAG;
        }

        Log.d(TAG,""+text);



    }



    public static void l( String text)
    {



        Log.d(TAG,""+text);



    }





}
