package com.nhom22.findhostel.UI.Account;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.AddressService;
import com.nhom22.findhostel.Service.CitiesService;
import com.nhom22.findhostel.Service.DistrictsService;
import com.nhom22.findhostel.Service.StreetsService;
import com.nhom22.findhostel.Service.SubDistrictsService;
import com.nhom22.findhostel.Service.UserAccountService;
import com.nhom22.findhostel.databinding.FragmentThirdRegisterBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class ThirdRegisterFragment extends Fragment {

    private static final String[] CAMERA_PERMISSION = {Manifest.permission.CAMERA};

    private ActivityResultLauncher<String[]> cameraPermissionLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    private ImageView imageView;

    private final CitiesService citiesService = new CitiesService();
    private final DistrictsService districtsService = new DistrictsService();
    private final SubDistrictsService subDistrictsService = new SubDistrictsService();
    private final StreetsService streetsService = new StreetsService();

    private final AddressService addressService = new AddressService();

    private final UserAccountService userAccountService = new UserAccountService();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the Activity Result Launchers
        cameraPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), this::handleCameraPermissionResult);
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::handleCameraResult);
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::handleGalleryResult);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.nhom22.findhostel.databinding.FragmentThirdRegisterBinding binding = FragmentThirdRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Button btnCamera = binding.btnCamera;
        Button btnGallery = binding.btnGallery;
        imageView = binding.imageView;
        Button btnRegister = binding.registerButton;

        btnCamera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Request camera permission
                cameraPermissionLauncher.launch(CAMERA_PERMISSION);
            } else {
                // Camera permission is already granted
                openCamera();
            }
        });

        btnGallery.setOnClickListener(v -> openGallery());


        btnRegister.setOnClickListener(v -> {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] hinhAnh = byteArrayOutputStream.toByteArray();

            Bundle dataBundle = getArguments();
            if (dataBundle != null) {
                String email = dataBundle.getString("email");
                String username = dataBundle.getString("username");
                String password = dataBundle.getString("password");
                String phone  = dataBundle.getString("phone");
                String houseNumber = dataBundle.getString("houseNumber");
                int streetId = dataBundle.getInt("streetId");
                int cityId = dataBundle.getInt("citiesId");
                int districtId = dataBundle.getInt("districtId");
                int subDistrictId = dataBundle.getInt("subDistrictId");

                Cities city = citiesService.getCityById(cityId);
                Districts districts = districtsService.getDistrictById(districtId);
                SubDistricts subDistricts = subDistrictsService.getSubDistrictById(subDistrictId);
                Streets streets = streetsService.getStreetsById(streetId);



                Address address = new Address();
                address.setHouseNumber(houseNumber);
                address.setCities(city);
                address.setDistricts(districts);
                address.setSubDistrics(subDistricts);
                address.setStreets(streets);
                addressService.addAddress(address);

                Address newAddress = addressService.getAddressByNameStreetAndHouseNumber(streets.getName(),houseNumber);

                UserAccount userAccount = new UserAccount();
                userAccount.setAddress(newAddress);
                userAccount.setEmail(email);
                userAccount.setPhone(phone);
                userAccount.setUsername(username);
                userAccount.setPassword(password);
                userAccount.setImage(hinhAnh);

                long addUser = userAccountService.addUserAccount(userAccount);

                if (addUser != -1) {
                    Toast.makeText(getContext(), "Đăng ký thành công!!!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Đăng ký không thành công!!!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "Thông tin đăng ký đã bị thiếu", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView gifImageView = binding.imageView;
        Glide.with(this)
                .asGif()
                .load(R.drawable.profile)
                .override(400, 400)
                .into(gifImageView);
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
        }
    }

    private void handleGalleryResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            Uri imageUri = result.getData().getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                imageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}



