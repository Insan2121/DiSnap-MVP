package com.example.disnap.ui.analyze;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.disnap.MainActivity;
import com.example.disnap.R;
import com.example.disnap.ui.base.BaseActivity;
import com.example.disnap.ui.bottomsheetdialog.ActionBottomDialogFragment;
import com.example.disnap.util.Constants;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AnalyzeActivity extends BaseActivity {
    private ImageView btnBack;
    private ImageView btnCamera;
    private Button btnAnalyze;
    private ImageView imageUser;
    private static ActionBottomDialogFragment fragment = new ActionBottomDialogFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);
        findViews();
        initViews();
        initListeners();

        Intent intent = getIntent();
        if (intent != null){
            String img = intent.getStringExtra("imageUri");
            Log.d("epe100", "onCreate: "+img);
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(img)
                    .into(imageUser);
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void findViews() {
        btnBack = findViewById(R.id.btn_back_analyze);
        btnCamera = findViewById(R.id.btn_new_image);
        btnAnalyze = findViewById(R.id.button_analyze);
        imageUser = findViewById(R.id.img_user);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnalyzeActivity.this, MainActivity.class));
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetFragment();
            }
        });

        btnAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void showBottomSheetFragment(){
        fragment.show(getSupportFragmentManager(), fragment.getTag());
        fragment.setCancelable(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("epe0", "onActivityResult: "+resultCode);
        Log.d("epe01", "onActivityResult: "+resultCode);
        if (resultCode == RESULT_OK){
            Log.d("epe1", resultCode +"");
            if (requestCode == Constants.GALERY_REQUEST){
                Log.d("epe2",requestCode+"");
                Uri a = data.getData();
                Log.d("epe3",a.toString());
                startCrop(a);
            }else if (requestCode == Constants.CAMERA_REQUEST){
                Log.d("epe4",requestCode+"");
                Uri a = data.getData();
                Log.d("epe5",a.toString());
                startCrop(a);
            } else if (requestCode == Constants.CROP_REQUEST) {
                Log.d("epe6",requestCode+"");
                Uri c = UCrop.getOutput(data);
                String imgUri = getRealPathFromUri(c);
                Log.d("epe7",c.toString());
                Intent intent = new Intent(this, AnalyzeActivity.class);
                intent.putExtra("imageUri", imgUri);
                startActivity(intent);
                this.finish();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Ada yang salah", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromUri(Uri uri){
        String result;
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void startCrop(@NonNull Uri uri){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getApplicationContext().getCacheDir(), imageFileName)));
        uCrop.withAspectRatio(1,1);
        uCrop.withMaxResultSize(450, 450);
        uCrop.start(AnalyzeActivity.this, Constants.CROP_REQUEST);
        Log.d("apa?", "crop ok");
    }
}
