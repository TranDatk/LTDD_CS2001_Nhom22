package com.nhom22.findhostel.UI.Account;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhom22.findhostel.R;
import com.nhom22.findhostel.databinding.FragmentAccountPageBinding;


public class AccountPageFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentAccountPageBinding binding = FragmentAccountPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);


        if (isLoggedIn) {
            binding.btnLogin.setVisibility(View.INVISIBLE);
            binding.btnRegister.setVisibility(View.INVISIBLE);
            binding.a.setText("Xin chào người dùng mới");
            binding.btnLogout.setVisibility(View.VISIBLE);
        } else {
            binding.btnLogin.setVisibility(View.VISIBLE);
            binding.btnRegister.setVisibility(View.VISIBLE);
            binding.a.setText("Vui lòng đăng nhập or đăng ký");
            binding.btnLogout.setVisibility(View.INVISIBLE);
        }

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the fragment with LoginFragment
                replaceFragment(new LoginFragment());
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the fragment with RegistrationFragment
                replaceFragment(new RegistrationFragment());
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearUserSession();
               replaceFragment(new LoginFragment());
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void clearUserSession() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}