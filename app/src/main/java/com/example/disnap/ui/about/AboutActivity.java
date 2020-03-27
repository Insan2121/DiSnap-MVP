package com.example.disnap.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disnap.R;
import com.example.disnap.ui.base.BaseActivity;
import com.example.disnap.util.Constants;

public class AboutActivity extends BaseActivity {
    private Button btnBack;
    private Button btnGo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findViews();
        initViews();
        initListeners();
    }

    @Override
    public void findViews() {
        btnBack = findViewById(R.id.btnBack);
        btnGo = findViewById(R.id.btnGo);
    }

    @Override
    public void initViews() {
    }

    @Override
    public void initListeners() {

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                goToBPTPWeb(Constants.LINK_BPTP);
            }
        });



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    void goToBPTPWeb(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
