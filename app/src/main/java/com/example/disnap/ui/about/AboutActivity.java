package com.example.disnap.ui.about;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.disnap.R;
import com.example.disnap.ui.base.BaseActivity;

public class AboutActivity extends BaseActivity {
    private Button btnBack;
    private TextView link;



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
        link = findViewById(R.id.linkBPTP);
    }

    @Override
    public void initViews() {
    }

    @Override
    public void initListeners() {

        link.setMovementMethod(LinkMovementMethod.getInstance());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
