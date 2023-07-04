package com.nhom22.findhostel.ui.Account;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nhom22.findhostel.R;
import com.nhom22.findhostel.data.DatabaseHelper;
import com.nhom22.findhostel.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {

    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentLoginBinding binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        databaseHelper = new DatabaseHelper(requireContext());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getActivity(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
                    if (cursor != null && cursor.moveToFirst()) {
                        int userIdIndex = cursor.getColumnIndex("id");
                        if (userIdIndex != -1) {
                            int userId = cursor.getInt(userIdIndex);
                            saveUserSession(userId);
                            replaceFragment(new AccountPageFragment());
                        } else {
                            Toast.makeText(getActivity(), "Invalid column name: user_id", Toast.LENGTH_SHORT).show();
                        }
                        cursor.close();
                    } else {
                        Toast.makeText(getActivity(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new RegistrationFragment());
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void saveUserSession(int userId) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putInt("userId", userId);
        editor.apply();

        // Proceed to the next screen or perform any other desired actions
    }
}