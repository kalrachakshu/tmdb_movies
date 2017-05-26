package nf.co.thehoproject.tmdbmovies;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    ImageView icon;
    TextView app,dev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideNotificationBar(this);

        setContentView(R.layout.activity_splash);
        icon=(ImageView)findViewById(R.id.icon);
        app=(TextView)findViewById(R.id.app);
        dev=(TextView)findViewById(R.id.dev);

        Utils.setFace(Utils.AVENIR_MED,app);
        Utils.setFace(Utils.CAVIAR,dev);

        animate();
        findViewById(R.id.activity_splash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.postDelayed(runnable,100);
            }
        });

    }
    Handler handler;
    Runnable runnable;
    public void animate()
    {

            handler=new Handler();
            runnable=new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(Splash.this,MainActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //Adding Expansion transition ICON-to-ACTIVITY
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(Splash.this, icon, getString(R.string.activity_splash_trans));
                    startActivity(intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }
            }
        };
        Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.zoom);
        hyperspaceJump.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                app.setVisibility(View.VISIBLE);
                dev.setVisibility(View.VISIBLE);

                handler.postDelayed(runnable,1500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        icon.startAnimation(hyperspaceJump);
    }
}
