package com.example.disnap.ui.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Window;
import android.view.WindowManager;

import com.example.disnap.R;
import com.example.disnap.ui.MainActivity;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_intro);

        SliderPage home = new SliderPage();
        home.setTitle("Disease Information");
        home.setDescription("Dapatkan semua informasi mengenai berbagai penyakit pada tanaman cabai");
        home.setImageDrawable(R.drawable.intro_home);
        home.setBgColor(R.color.colorRed);
        addSlide(AppIntroFragment.newInstance(home));

        SliderPage analyze = new SliderPage();
        analyze.setTitle("Analyze Image");
        analyze.setDescription("Analisis penyakit melalui gambar dari kamera atau galeri anda");
        analyze.setImageDrawable(R.drawable.intro_analyze);
        analyze.setBgColor(R.color.colorGreen);
        addSlide(AppIntroFragment.newInstance(analyze));

        SliderPage history = new SliderPage();
        history.setTitle("History");
        history.setDescription("Cek kembali aktivitas kegiatan analisis anda");
        history.setImageDrawable(R.drawable.intro_history);
        history.setBgColor(R.color.colorWhite);
        addSlide(AppIntroFragment.newInstance(history));

        showSkipButton(true);
        setProgressButtonEnabled(true);

        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        gotToMainMenu();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        gotToMainMenu();
    }

    void gotToMainMenu(){
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
        finish();
    }
}
