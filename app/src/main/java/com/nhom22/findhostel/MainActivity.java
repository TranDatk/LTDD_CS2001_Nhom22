package com.nhom22.findhostel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.UserManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.nhom22.findhostel.data.UserAccountDAO;
import com.nhom22.findhostel.databinding.ActivityMainBinding;
import com.nhom22.findhostel.model.Address;
import com.nhom22.findhostel.model.UserAccount;
import com.nhom22.findhostel.ui.Account.AccountPageFragment;
import com.nhom22.findhostel.ui.Extension.ExtensionPageFragment;
import com.nhom22.findhostel.ui.Home.HomePageFragment;
import com.nhom22.findhostel.ui.Save.SavePageFragment;
import com.nhom22.findhostel.ui.Search.SearchPageFragment;
import com.nhom22.findhostel.data.DatabaseHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.navigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.navigation_home) {
                selectedFragment = new HomePageFragment();
            } else if (item.getItemId() == R.id.navigation_exten) {
                selectedFragment = new ExtensionPageFragment();
            } else if (item.getItemId() == R.id.navigation_search) {
                selectedFragment = new SearchPageFragment();
            } else if (item.getItemId() == R.id.navigation_info) {
                selectedFragment = new SavePageFragment();
            } else if (item.getItemId() == R.id.navigation_account) {
                selectedFragment = new AccountPageFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, selectedFragment)
                        .commit();
                return true;
            }

            return false;
        });

        // Set the initial selected fragment
        binding.navigation.setSelectedItemId(R.id.navigation_search);
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Address address = new Address(1, "ABC", 1 , null , null , null, null);
        UserAccount userAccount = new UserAccount(2, "trandatk2", "hgoos@gmail.com",
                "123", "092020", 2000, 1, null,1,address);


        UserAccountDAO dao = new UserAccountDAO(this);
        dao.addUserAccount(userAccount);
    }

}