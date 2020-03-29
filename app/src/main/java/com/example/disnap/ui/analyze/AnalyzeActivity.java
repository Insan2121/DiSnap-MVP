package com.example.disnap.ui.analyze;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.disnap.ui.MainActivity;
import com.example.disnap.R;
import com.example.disnap.data.pojo.Disease;
import com.example.disnap.data.repository.DataManager;
import com.example.disnap.data.repository.DiseaseRepository;
import com.example.disnap.ui.base.BaseActivity;
import com.example.disnap.ui.bottomsheetdialog.ActionBottomDialogFragment;
import com.example.disnap.ui.result.ResultActivity;
import com.example.disnap.util.Connectivity;
import com.example.disnap.util.Constants;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import io.isfaaghyth.rak.Rak;

public class AnalyzeActivity extends BaseActivity implements AnalyzeView {
    private ImageView btnBack;
    private ImageView btnCamera;
    private Button btnAnalyze;
    private ImageView imageUser;
    private AnalyzePresenter analyzePresenter;
    private static ActionBottomDialogFragment fragment = new ActionBottomDialogFragment();
    private int status = 0;
    private Handler handler = new Handler();
    private FrameLayout fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        Rak.initialize(getApplicationContext());

        findViews();
        initViews();
        initListeners();
    }

    @Override
    public void findViews() {
        fl = findViewById(R.id.container);
        btnBack = findViewById(R.id.btn_back_analyze);
        btnCamera = findViewById(R.id.btn_new_image);
        btnAnalyze = findViewById(R.id.button_analyze);
        imageUser = findViewById(R.id.img_user);
    }

    @Override
    public void initViews() {
        DiseaseRepository diseaseRepository = DataManager.getInstance().analyzeImageRepositoryRemote();
        analyzePresenter = new AnalyzePresenter(this, diseaseRepository);
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

        Intent intent = getIntent();
        if (intent != null) {
            final String img = intent.getStringExtra("imageUri");
            Log.d("epe100", "onCreate: " + img);
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(img)
                    .into(imageUser);

            btnAnalyze.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Connectivity.isNetworkAvailable(AnalyzeActivity.this)){
                        analyzePresenter.AnalyzeImageFromRemote(img);
                    }
                    else {
                        Toast.makeText(AnalyzeActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    public void showBottomSheetFragment() {
        fragment.show(getSupportFragmentManager(), fragment.getTag());
        fragment.setCancelable(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.GALERY_REQUEST) {
                Uri a = data.getData();
                startCrop(a);
            } else if (requestCode == Constants.CAMERA_REQUEST) {
                Uri a = data.getData();
                startCrop(a);
            } else if (requestCode == Constants.CROP_REQUEST) {
                Uri c = UCrop.getOutput(Objects.requireNonNull(data));
                String imgUri = getRealPathFromUri(c);
                Intent intent = new Intent(this, AnalyzeActivity.class);
                intent.putExtra("imageUri", imgUri);
                startActivity(intent);
                this.finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), "There is problem", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromUri(Uri uri) {
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

    private void startCrop(@NonNull Uri uri) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getApplicationContext().getCacheDir(), imageFileName)));
        uCrop.withAspectRatio(1, 1);
        uCrop.withMaxResultSize(450, 450);
        uCrop.start(AnalyzeActivity.this, Constants.CROP_REQUEST);
    }

    @Override
    public void showResultAnalyze(Disease disease) {
        Intent intent = new Intent(AnalyzeActivity.this, ResultActivity.class);
        intent.putExtra("Result", (Parcelable) disease);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoading() {
        showDialog(AnalyzeActivity.this);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorMessage() {
    }

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity, R.style.Widget_AppCompat_ProgressBar_Horizontal);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_bar_dialog);
        fl.setBackgroundColor(Color.BLACK);

        final ProgressBar text = (ProgressBar) dialog.findViewById(R.id.progress_horizontal);
        final TextView text2 = dialog.findViewById(R.id.value123);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (status < 100) {
                    status += 1;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            text.setProgress(status);
                            text2.setText(String.valueOf(status));

                            if (status == 100) {
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }
        }).start();

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}


