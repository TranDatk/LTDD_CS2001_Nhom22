package com.nhom22.findhostel.UI.HomeSecond;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.UserAccountService;
import java.util.Objects;


public class CreditPageAcitivy extends AppCompatActivity {


    private final UserAccountService userAccountService = new UserAccountService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_page_acitivy);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        int userId = sharedPreferences.getInt("userId", -1);
        UserAccount user = userAccountService.getUserAccountById(userId);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        LinearLayout btnNap = (LinearLayout) findViewById(R.id.btnNap);

        TextView txtCredit =  (TextView)  findViewById(R.id.creditCount);
        TextView txtCreditCanWithdraw = (TextView) findViewById(R.id.creditCountWithdraw);




        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationTop);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_giaodich) {
                    loadFragment(new GiaoDichFragment());
                    return true;
                } else if (item.getItemId() == R.id.action_dadung) {
                    loadFragment(new DaDungFragment());
                    return true;
                } else if (item.getItemId() == R.id.action_dangdung) {
                    loadFragment(new DangDungFragment());
                    return true;
                }
                return false;
            }
        });
        loadFragment(new GiaoDichFragment());




        txtCredit.setText(String.valueOf(user.getDigital_money()));
        txtCreditCanWithdraw.setText("Có thể rút: " + String.valueOf(user.getDigital_money()));

        btnNap.setOnClickListener(v-> {
            Intent intent = new Intent(this, PayScreen.class);
            startActivity(intent);
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }


}