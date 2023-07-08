package com.nhom22.findhostel.UI.Account;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.databinding.FragmentRegistrationBinding;


public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;
    private ClipboardManager clipboardManager;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        clipboardManager = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        binding.nextButton.setOnClickListener(v -> {
            String email = binding.emailEditText.getText().toString();
            String username = binding.usernameEditText.getText().toString();
            String password = binding.passwordEditText.getText().toString();
            String repassword = binding.repasswordEditText.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(requireContext(), "Please enter email", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(username)) {
                Toast.makeText(requireContext(), "Please enter username", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Please enter password", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(repassword)) {
                Toast.makeText(requireContext(), "Please re-enter password", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(repassword)) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {

                String clipboardData = email + "|" + username + "|" + password;
                ClipData clipData = ClipData.newPlainText("UserInput", clipboardData);
                clipboardManager.setPrimaryClip(clipData);

                // Navigate to the second fragment
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SecondRegisterFragment secondFragment = new SecondRegisterFragment();
                fragmentTransaction.replace(R.id.frame_container, secondFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.loginRedirectText.setOnClickListener(view1 -> replaceFragment(new LoginFragment()));


        ImageView gifImageView = binding.gifImageView;
        Glide.with(this)
                .asGif()
                .load(R.drawable.login)
                .override(800, 600)
                .into(gifImageView);
        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}