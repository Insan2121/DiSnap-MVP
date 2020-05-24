package com.example.disnap.ui.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
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
        
        if (getPrefIntro()){
            goToMainMenu();
        }else {
            goToIntroApp();
        }

    }

    void goToIntroApp(){
        setPrefIntro();
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

    void setPrefIntro(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("intro",true);
        editor.apply();
    }

    boolean getPrefIntro(){
        SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        boolean status = preferences.getBoolean("intro",false );
        if (status){
            return true;
        }else {
            return false;
        }
    }


}
