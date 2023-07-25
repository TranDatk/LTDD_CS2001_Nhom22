package com.nhom22.findhostel.UI.HomeSecond;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.nhom22.findhostel.R;

import java.util.Objects;

public class UpdatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle ActionBar item clicks here
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // Handle the back button click here
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}