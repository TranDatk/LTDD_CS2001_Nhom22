package com.nhom22.findhostel.UI.Extension;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.HostelCollection;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.AddressService;
import com.nhom22.findhostel.Service.CitiesService;
import com.nhom22.findhostel.Service.DistrictsService;
import com.nhom22.findhostel.Service.HotelCollectionService;
import com.nhom22.findhostel.Service.StreetsService;
import com.nhom22.findhostel.Service.SubDistrictsService;
import com.nhom22.findhostel.Service.UserAccountService;
import com.nhom22.findhostel.YourApplication;
import com.nhom22.findhostel.databinding.FragmentHostelCollectionPageAddBinding;
import com.nhom22.findhostel.databinding.FragmentPostDecorPageAddBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class HostelCollectionPageAddFragment extends Fragment {

    private static final String[] CAMERA_PERMISSION = {Manifest.permission.CAMERA};

    private ImageView imageView;
    private ActivityResultLauncher<String[]> cameraPermissionLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    private EditText houseNumberEditText;
    private AutoCompleteTextView autoCitiesField;
    private AutoCompleteTextView autoDistrictField;
    private AutoCompleteTextView autoSubDistrictField;
    private AutoCompleteTextView autoStreetField;

    private ClipboardManager clipboardManager;

    private final CitiesService citiesService = new CitiesService();
    private final DistrictsService districtsService = new DistrictsService();
    private final SubDistrictsService subDistrictsService = new SubDistrictsService();
    private final StreetsService streetsService = new StreetsService();
    private final AddressService addressService = new AddressService();
    private final HotelCollectionService hotelCollectionService = new HotelCollectionService();

    private final UserAccountService userAccountService = new UserAccountService();
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
        // Inflate the layout for this fragment
        FragmentHostelCollectionPageAddBinding binding = FragmentHostelCollectionPageAddBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        imageView = binding.imgHotelCollection;
        ImageButton ibtnCamera = binding.imgButtonCamera;
        ImageButton ibtnGallery = binding.imgButtonFolder;
        EditText edtTitle = binding.edtHotelCollectionTitle;
        Button btnAdd = binding.btnAddImgHotelCollection;
        Button btnCancel = binding.btnCancelHotelCollection;


        clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        houseNumberEditText = binding.houseNumberEditTextHotelCollection;
        autoCitiesField = binding.autoCitiesFieldHotelCollection;
        autoDistrictField = binding.autoDistrictFieldHotelCollection;
        autoSubDistrictField = binding.autoSubDistrictFieldHotelCollection;
        autoStreetField = binding.autoStreetFieldHotelCollection;

        addCities();

        autoCitiesField.setEnabled(true);
        autoDistrictField.setEnabled(false);
        autoSubDistrictField.setEnabled(false);
        autoStreetField.setEnabled(false);

        autoCitiesField.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedCityName = (String) parent.getItemAtPosition(position);
            int selectedCityId = getCitiesIdByName(selectedCityName);
            autoDistrictField.setEnabled(true);
            addDistrict(selectedCityId);
            autoSubDistrictField.setEnabled(false);
            autoStreetField.setEnabled(false);
        });

        autoDistrictField.setOnItemClickListener((parent, view12, position, id) -> {
            String selectedDistrictName = (String) parent.getItemAtPosition(position);
            int selectedDistrictId = getDistrictByName(selectedDistrictName);
            autoSubDistrictField.setEnabled(true);
            addSubDistrict(selectedDistrictId);
            autoStreetField.setEnabled(false);
        });

        autoSubDistrictField.setOnItemClickListener((parent, view13, position, id) -> {
            String selectedDistrictName = (String) parent.getItemAtPosition(position);
            int selectedSubDistrictId = getSubDistrictIdByName(selectedDistrictName);
            autoStreetField.setEnabled(true);
            addStreet(selectedSubDistrictId);
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] hinhAnh = byteArrayOutputStream.toByteArray();


                String selectedCityName = autoCitiesField.getText().toString();
                String selectedDistrictName = autoDistrictField.getText().toString();
                String selectedSubDistrictName = autoSubDistrictField.getText().toString();
                String selectedStreetName = autoStreetField.getText().toString();

                int cityID = getCitiesIdByName(selectedCityName);
                int districtId = getDistrictByName(selectedDistrictName);
                int subDistrictId = getSubDistrictIdByName(selectedSubDistrictName);
                int streetId = getStreetIdByName(selectedStreetName);

                Cities city = citiesService.getCityById(cityID);
                Districts districts = districtsService.getDistrictById(districtId);
                SubDistricts subDistricts = subDistrictsService.getSubDistrictById(subDistrictId);
                Streets streets = streetsService.getStreetsById(streetId);



                Address address = new Address();
                address.setHouseNumber(houseNumberEditText.getText().toString().trim());
                address.setCities(city);
                address.setDistricts(districts);
                address.setSubDistrics(subDistricts);
                address.setStreets(streets);
                addressService.addAddress(address);


                Address newAddress = addressService.getAddressByNameStreetAndHouseNumber(streets.getName(),houseNumberEditText.getText().toString().trim());


                HostelCollection hostelCollection = new HostelCollection();
                hostelCollection.setTitle(edtTitle.getText().toString().trim());
                hostelCollection.setImage(hinhAnh);
                hostelCollection.setAddress(newAddress.getId());




                long addHostelCollection = hotelCollectionService.addHostelCollection(hostelCollection);
                if (addHostelCollection != -1) {
                    Toast.makeText(getContext(), "Tạo tin thành công!!!", Toast.LENGTH_LONG).show();
                    List<UserAccount> userAccountList = userAccountService.getAllUserAccountByDistrictId(newAddress.getDistricts().getId());
                    for (UserAccount user : userAccountList) {
                        sendNotification(user.getUsername());
                    }
                } else {
                    Toast.makeText(getContext(), "Tạo tin không thành công!!!", Toast.LENGTH_LONG).show();
                }
            }
        });

        ibtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    // Yêu cầu quyền truy cập CAMERA
                    cameraPermissionLauncher.launch(new String[]{Manifest.permission.CAMERA});
                }
            }
        });

        ibtnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnCancel.setOnClickListener(v->{replaceFragment(new HostelCollectionPageFragment());});

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

    private void replaceFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void addCities() {
        List<Cities> cityList = citiesService.getAllCitiesList();

        List<String> cityNameList = new ArrayList<>();
        for (Cities city : cityList) {
            cityNameList.add(city.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, cityNameList);
        autoCitiesField.setAdapter(adapter);
    }

    private void addDistrict(int citiesId) {
        List<Districts> districtsList = districtsService.getAllDistrictsByCitiesId(citiesId);

        List<String> districtNameList = new ArrayList<>();
        for (Districts district : districtsList) {
            districtNameList.add(district.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, districtNameList);
        autoDistrictField.setAdapter(adapter);
    }

    private void addSubDistrict(int districtId) {
        List<SubDistricts> subDistrictsList = subDistrictsService.getAllSubDistrictsByDistrictId(districtId);

        List<String> subDistrictNameList = new ArrayList<>();
        for (SubDistricts subDistrict : subDistrictsList) {
            subDistrictNameList.add(subDistrict.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, subDistrictNameList);
        autoSubDistrictField.setAdapter(adapter);
    }

    private void addStreet(int subDistrictId) {
        List<Streets> streetsList = streetsService.getAllStreetsBySubDistrictId(subDistrictId);

        List<String> streetNameList = new ArrayList<>();
        for (Streets street : streetsList) {
            streetNameList.add(street.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, streetNameList);
        autoStreetField.setAdapter(adapter);
    }

    private int getCitiesIdByName(String cityName) {
        List<Cities> cityList = citiesService.getAllCitiesList();
        for (Cities city : cityList) {
            if (city.getName().equals(cityName)) {
                return city.getId();
            }
        }
        return -1;
    }

    private int getDistrictByName(String districtName) {
        List<Districts> districtList = districtsService.getAllDistricts();

        for (Districts district : districtList) {
            if (district.getName().equals(districtName)) {
                return district.getId();
            }
        }
        return -1;
    }

    private int getSubDistrictIdByName(String districtName) {
        List<SubDistricts> subdistrictList = subDistrictsService.getAllSubDistricts();

        for (SubDistricts sdistrict : subdistrictList) {
            if (sdistrict.getName().equals(districtName)) {
                return sdistrict.getId();
            }
        }
        return -1;
    }

    private int getStreetIdByName(String districtName) {
        List<Streets> streetsList = streetsService.getAllStreets();

        for (Streets streets : streetsList) {
            if (streets.getName().equals(districtName)) {
                return streets.getId();
            }
        }
        return -1;
    }



    private void sendNotification(String content){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(getContext(), YourApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_search)
                .setContentTitle("Thông báo phòng trọ mới")
                .setContentText("Hiện đang có 1 phòng trọ mới xung quanh khu vực " + content + " của bạn")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build();

        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(getContext().NOTIFICATION_SERVICE);
        if(notificationManager != null)
            notificationManager.notify(getNotificationId(), notification);
    }

    private int getNotificationId(){
        return (int) new Date().getTime();
    }

}