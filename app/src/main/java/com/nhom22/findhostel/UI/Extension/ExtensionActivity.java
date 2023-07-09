package com.nhom22.findhostel.UI.Extension;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom22.findhostel.Data.UserAccountDAO;
import com.nhom22.findhostel.MainActivity;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.Data.PostDecorDAO;
import com.nhom22.findhostel.Model.PostDecor;
import com.nhom22.findhostel.UI.Home.HomePageFragment;
import com.nhom22.findhostel.UI.Save.SavePageFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.sql.DriverManager;

public class ExtensionActivity extends AppCompatActivity {
    Button btnAdd, btnCancel;
    EditText edtContent;
    ImageButton ibtnCamera, ibtnFolder;
    ImageView img;
//    private PostDecorDAO postDecorDAO;

    TextView name;
    private DatabaseHelper databaseHelper;
    public static PostDecorDAO dao;

    public static UserAccountDAO accountDAO;

    private ActivityResultLauncher<Intent> activityResultLauncherC = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        img.setImageBitmap(bitmap);
                    }
                }
            });

    private ActivityResultLauncher<Intent> activityResultLauncherF = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = (Bitmap) BitmapFactory.decodeStream(inputStream);
                            img.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension);
        databaseHelper = new DatabaseHelper(this);
        dao = new PostDecorDAO(this);


//        postDecorDAO = new PostDecorDAO(this, "/data/data/com.nhom22.findhostel/databases/findhostel.sqlite", null, 2);
//        Button btnBack = findViewById(R.id.btnA);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        AnhXa();


        edtContent.setFilters(new InputFilter[]{new MaxLengthInputFilter(100, edtContent)});

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chuyển data imageview -> byte[]
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] hinhAnh = byteArrayOutputStream.toByteArray();

                Date currentDate = Calendar.getInstance().getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = format.format(currentDate);

                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                dao.addPostDecor(new PostDecor(1000,
                        edtContent.getText().toString().trim(),
                        hinhAnh,
                        formattedDate,
                        1,
                        1
                ));

                Intent intent = new Intent(ExtensionActivity.this , ListDecorPost.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ibtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultLauncherC.launch(intent);
            }
        });

        ibtnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncherF.launch(intent);
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack(); // Quay lại Fragment trước đó
    }

    private void AnhXa(){
        btnAdd = (Button) findViewById(R.id.btnAddImg_postDecor);
        btnCancel = (Button) findViewById(R.id.btnCancel_postDecor);
        edtContent = (EditText) findViewById(R.id.edt_postDecor_content);
        img = (ImageView) findViewById(R.id.img_postDecor);
        ibtnCamera = (ImageButton)  findViewById(R.id.imageButtonCamera);
        ibtnFolder = (ImageButton) findViewById(R.id.imageButtonFolder);
    }
}