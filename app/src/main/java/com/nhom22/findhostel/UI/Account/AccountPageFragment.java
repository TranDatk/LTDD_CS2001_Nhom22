package com.nhom22.findhostel.UI.Account;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.UserAccountService;
import com.nhom22.findhostel.UI.HomeSecond.HomeSecondActivity;
import com.nhom22.findhostel.databinding.FragmentAccountPageBinding;


public class AccountPageFragment extends Fragment {

    UserAccountService userAccountService = new UserAccountService();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentAccountPageBinding binding = FragmentAccountPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        int userId = sharedPreferences.getInt("userId", -1);
        UserAccount userAccount = userAccountService.getUserAccountById(userId);

            if (isLoggedIn) {
                if (userAccount != null) {
                    String username = userAccount.getUsername();
                    byte[] image = userAccount.getImage();
                    binding.screenLogined.setVisibility(View.VISIBLE);
                    binding.btnLogin.setVisibility(View.INVISIBLE);
                    binding.userName.setText("Xin chào " + username);
                    try {
                        if (image != null && image.length > 0) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                            binding.userAvatar.setImageBitmap(bitmap);
                        } else {
                            Toast.makeText(requireContext(), "Ko cos anh", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }


                    binding.btnLogout.setVisibility(View.VISIBLE);

                    binding.screenNotLogin.setVisibility(View.GONE);
                }
            } else {
                binding.screenNotLogin.setVisibility(View.VISIBLE);
                binding.btnLogin.setVisibility(View.VISIBLE);
                binding.btnLogout.setVisibility(View.INVISIBLE);
                binding.screenLogined.setVisibility(View.GONE);
            }

        binding.changeMode.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), HomeSecondActivity.class);
            startActivity(intent);
            requireActivity().finish(); // Optional: Finish the current activity if needed
        });

        binding.btnLogin.setOnClickListener(v -> {
            // Replace the fragment with LoginFragment
            replaceFragment(new LoginFragment());
        });


        binding.btnLogout.setOnClickListener(view1 -> {
            clearUserSession();
           replaceFragment(new AccountPageFragment());
        });

        ImageView gifImageView = binding.imageView;
        Glide.with(this)
                .asBitmap()
                .load(R.drawable.login_art)
                .into(gifImageView);
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