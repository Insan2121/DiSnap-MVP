package com.example.disnap.ui.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.disnap.App;
import com.example.disnap.R;
import com.example.disnap.ui.MainActivity;
import com.example.disnap.ui.intro.IntroActivity;
import io.isfaaghyth.rak.Rak;

public class SplashScreenActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Rak.initialize(App.getContext());

        if (Rak.grab("intro") == null){
            goToIntroApp();
        }else {
            boolean a = Rak.grab("intro");
            Log.d("cek", "onCreate: "+a);
            goToMainMenu();
        }
    }

    void goToIntroApp(){
        Rak.entry("intro", true);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, IntroActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    void goToMainMenu(){
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }


}
