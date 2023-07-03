package com.nhom22.findhostel.ui.Extension;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nhom22.findhostel.R;
import com.nhom22.findhostel.ui.Home.HomePageFragment;
import com.nhom22.findhostel.ui.Save.SavePageFragment;

public class ExtensionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension);

        Button btnBack = findViewById(R.id.btnA);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack(); // Quay lại Fragment trước đó
    }
}