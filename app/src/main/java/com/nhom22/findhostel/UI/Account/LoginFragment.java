package com.nhom22.findhostel.UI.Account;

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

import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.Service.UserAccountService;
import com.nhom22.findhostel.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {

    UserAccountService userAccountService = new UserAccountService();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentLoginBinding binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.loginButton.setOnClickListener(v -> {
            String email = binding.loginEmail.getText().toString();
            String password = binding.loginPassword.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getActivity(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
            } else {
                UserAccount user = userAccountService.checkLoginUser(email, password);
                if (user != null) {
                    int userId = user.getId();

                    saveUserSession(userId);
                    replaceFragment(new AccountPageFragment());
                } else {
                    Toast.makeText(getActivity(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.signupRedirectText.setOnClickListener(view1 -> replaceFragment(new RegistrationFragment()));

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