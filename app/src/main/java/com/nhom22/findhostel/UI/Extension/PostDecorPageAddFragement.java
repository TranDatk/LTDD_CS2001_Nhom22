package com.nhom22.findhostel.UI.Extension;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.nhom22.findhostel.Data.PostDecorDAO;
import com.nhom22.findhostel.Model.PostDecor;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.PostDecorService;
import com.nhom22.findhostel.Service.UserAccountService;
import com.nhom22.findhostel.databinding.FragmentPostDecorPageAddBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class PostDecorPageAddFragement extends Fragment {

    public static PostDecorService postDecorService;
    private static UserAccountService userAccountService;

    private static final String[] CAMERA_PERMISSION = {Manifest.permission.CAMERA};

    private ImageView imageView;
    private ActivityResultLauncher<String[]> cameraPermissionLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), this::handleCameraPermissionResult);
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::handleCameraResult);
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::handleGalleryResult);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPostDecorPageAddBinding binding = FragmentPostDecorPageAddBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        int userId = sharedPreferences.getInt("userId", -1);

//        UserAccount userAccount = userAccountService.getUserAccountById(userId);

        postDecorService = new PostDecorService();

        imageView = binding.imgPostDecor;
        ImageButton ibtnCamera = binding.imageButtonCamera;
        ImageButton ibtnGallery = binding.imageButtonFolder;
        EditText edtContent = binding.edtPostDecorContent;
        Button btnAdd = binding.btnAddImgPostDecor;
        Button btnCancel = binding.btnCancelPostDecor;



        edtContent.setFilters(new InputFilter[]{new MaxLengthInputFilter(100, edtContent)});

        ibtnCamera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Request camera permission
                cameraPermissionLauncher.launch(CAMERA_PERMISSION);
            } else {
                // Camera permission is already granted
                openCamera();
            }
        });

        ibtnGallery.setOnClickListener(v -> openGallery());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!binding.edtPostDecorContent.getText().toString().trim().isEmpty() && binding.edtPostDecorContent.getText().toString().trim() != null){

                    if (imageView.getDrawable() != null) {
                        // chuyển data imageview -> byte[]
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] hinhAnh = byteArrayOutputStream.toByteArray();

                        Date currentDate = Calendar.getInstance().getTime();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String formattedDate = format.format(currentDate);

                        PostDecor postDecor = new PostDecor(1000, edtContent.getText().toString().trim(), hinhAnh, formattedDate, userId, 1);

                        if(postDecor != null){
                            postDecorService.addPostDecor(postDecor);
                            replaceFragment(new ListDecorPostFragement());
                        }else {
                            Toast.makeText(getContext(), "Thêm bài viết mới thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(getContext(), "Bạn phải thêm ảnh vào bài viết", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Bạn phải nhập nội dung bài viết", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ListDecorPostFragement());
            }
        });


        return view;
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }

    private void handleCameraPermissionResult(Map<String, Boolean> result) {
        if (result.containsKey(Manifest.permission.CAMERA) && Boolean.TRUE.equals(result.get(Manifest.permission.CAMERA))) {
            openCamera();
        } else {
            Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCameraResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            assert result.getData() != null;
            Bundle extras = result.getData().getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    private void handleGalleryResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            Uri imageUri = result.getData().getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                imageView.setImageBitmap(imageBitmap);
                imageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}