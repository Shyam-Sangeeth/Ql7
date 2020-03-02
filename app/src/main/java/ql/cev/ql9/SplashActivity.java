package ql.cev.ql9;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        VideoView simpleVideoView = findViewById(R.id.videoView1);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.logo_anim;
        simpleVideoView.setVideoURI(Uri.parse(path));
        simpleVideoView.start();
        int SPLASH_DISPLAY_LENGTH = 4000;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}