package tricksfair.someshagra01.tricksfair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


public class splash_screen extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = this.getSharedPreferences("tricksfair.someshagra01.tricksfair",Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                    Intent mainIntent = new Intent(splash_screen.this,MainActivity.class);
                    splash_screen.this.startActivity(mainIntent);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    splash_screen.this.finish();
            }
        }, 2000);
    }
}
