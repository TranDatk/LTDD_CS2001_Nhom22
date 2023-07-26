package com.nhom22.findhostel.UI.HomeSecond;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Detail_Furniture;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.Model.Type;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.AddressService;
import com.nhom22.findhostel.Service.CitiesService;
import com.nhom22.findhostel.Service.Detail_FurnitureService;
import com.nhom22.findhostel.Service.Detail_ImageService;
import com.nhom22.findhostel.Service.DistrictsService;
import com.nhom22.findhostel.Service.FurnitureService;
import com.nhom22.findhostel.Service.ImagesService;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.Service.StreetsService;
import com.nhom22.findhostel.Service.SubDistrictsService;
import com.nhom22.findhostel.Service.TypeService;
import com.nhom22.findhostel.Service.UserAccountService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PostOwnerActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCitiesField;
    private AutoCompleteTextView autoDistrictField;
    private AutoCompleteTextView autoSubDistrictField;
    private AutoCompleteTextView autoStreetField;

    private final CitiesService citiesService = new CitiesService();
    private final DistrictsService districtsService = new DistrictsService();
    private final SubDistrictsService subDistrictsService = new SubDistrictsService();
    private final StreetsService streetsService = new StreetsService();
    private final FurnitureService furnitureService = new FurnitureService();

    private final UserAccountService userAccountService = new UserAccountService();
    private final TypeService typeService = new TypeService();

    private final PostsService postsService = new PostsService();

    private final AddressService addressService = new AddressService();

    private final ImagesService imagesService = new ImagesService();
    private final Detail_ImageService detail_imageService = new Detail_ImageService();
    private final Detail_FurnitureService detail_furnitureService = new Detail_FurnitureService();

    private static final int REQUEST_PERMISSIONS = 1;
    private static final int PICK_IMAGE_MULTIPLE = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 3;

    private RecyclerView recyclerView;
    private ArrayList<Uri> selectedImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_owner);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        int userId = sharedPreferences.getInt("userId", -1);
        UserAccount user = userAccountService.getUserAccountById(userId);

        Posts posts = new Posts();

        EditText houseNumberEditText = findViewById(R.id.houseNumberEditText);
        autoCitiesField = findViewById(R.id.autoCitiesField);
        autoDistrictField = findViewById(R.id.autoDistrictField);
        autoSubDistrictField = findViewById(R.id.autoSubDistrictField);
        autoStreetField = findViewById(R.id.autoStreetField);
        EditText namePostEditText = findViewById(R.id.txtNamePost);
        EditText pricePost = findViewById(R.id.txtPrice);
        EditText desPost = findViewById(R.id.editTextLanguage);



        FlexboxLayout boxFurni = findViewById(R.id.boxFurni);
        List<Furniture> furnitureList = furnitureService.getAllFurniture();


        List<Furniture> furnitureListChoose = new ArrayList<>();

        if (furnitureList != null && !furnitureList.isEmpty()) {
            for (Furniture furniture : furnitureList) {
                CheckBox newCheckBox = new CheckBox(this);
                newCheckBox.setText(furniture.getName());
                newCheckBox.setTextColor(ContextCompat.getColor(this, R.color.black));
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                        0,
                        FlexboxLayout.LayoutParams.WRAP_CONTENT
                );
                params.setFlexBasisPercent(0.33f);
                newCheckBox.setLayoutParams(params);
                newCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            furnitureListChoose.add(furniture);
                        } else {
                            furnitureListChoose.remove(furniture);
                        }
                    }
                });

                boxFurni.addView(newCheckBox);
            }
        } else {
            Toast.makeText(this, "Không có tiện ích", Toast.LENGTH_SHORT).show();
        }

        addCities();

        autoCitiesField.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedCityName = (String) parent.getItemAtPosition(position);
            int selectedCityId = getCitiesIdByName(selectedCityName);
            addDistrict(selectedCityId);
        });

        autoDistrictField.setOnItemClickListener((parent, view12, position, id) -> {
            String selectedDistrictName = (String) parent.getItemAtPosition(position);
            int selectedDistrictId = getDistrictByName(selectedDistrictName);
            addSubDistrict(selectedDistrictId);
        });

        autoSubDistrictField.setOnItemClickListener((parent, view13, position, id) -> {
            String selectedDistrictName = (String) parent.getItemAtPosition(position);
            int selectedSubDistrictId = getSubDistrictIdByName(selectedDistrictName);
            addStreet(selectedSubDistrictId);
        });

        Spinner spinnerLanguage = findViewById(R.id.spinnerLanguage);

        String[] languages = {"Tiếng Anh", "Tiếng Việt"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages);
        spinnerLanguage.setAdapter(adapter);

        Button galleryButton = findViewById(R.id.galleryButton);
        Button cameraButton = findViewById(R.id.cameraButton);
        Button upImage = findViewById(R.id.btnUpImage);
        recyclerView = findViewById(R.id.recyclerView);
        selectedImages = new ArrayList<>();

        upImage.setOnClickListener(v -> {
            String name = namePostEditText.getText().toString();
            for (Uri uri : selectedImages) {
                Images images = new Images();
                images.setIsActive(1);
                images.setName(name);
                try {
                    byte[] a = getBytesFromUri(uri);
                    images.setImage(a);
                    long result = imagesService.addAImages(images);
                    if (result > 0) {
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        galleryButton.setOnClickListener(v -> openGallery());

        cameraButton.setOnClickListener(v -> openCamera());

        // Request necessary permissions
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[0]), REQUEST_PERMISSIONS);
        }


        FlexboxLayout boxType = findViewById(R.id.boxType);
        List<Type> typeList = typeService.getAllType();

        if (typeList != null && !typeList.isEmpty()) {
            RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setOrientation(RadioGroup.HORIZONTAL);

            for (Type type : typeList) {
                RadioButton newRadioButton = new RadioButton(this);
                newRadioButton.setText(type.getName());
                newRadioButton.setTextColor(ContextCompat.getColor(this, R.color.black));
                newRadioButton.setLayoutParams(new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                ));

                newRadioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        Type selectedType = typeService.getTypeById(type.getId());
                        posts.setType(selectedType);
                    }
                });

                radioGroup.addView(newRadioButton);
            }

            boxType.addView(radioGroup);
        } else {
            Button btn = new Button(this);
            btn.setText("Thêm loại phòng");
            boxType.addView(btn);
        }

        Button btnAddPost = findViewById(R.id.btnAddPost);
        btnAddPost.setOnClickListener(v -> {
            // Create a new Posts object


            // Set the post name
            String name = namePostEditText.getText().toString();
            posts.setPostName(name);

            // Set the address
            Address address = new Address();
            String city = autoCitiesField.getText().toString();
            String district = autoDistrictField.getText().toString();
            String subdistrict = autoSubDistrictField.getText().toString();
            String street = autoStreetField.getText().toString();
            String housenum = houseNumberEditText.getText().toString();

            boolean isDataValid = true;
            if (TextUtils.isEmpty(city)) {
                isDataValid = false;
                autoCitiesField.setError("Chưa chọn thành phố");
            }

            if (TextUtils.isEmpty(district)) {
                isDataValid = false;
                autoDistrictField.setError("Chưa chọn quận");
            }

            if (TextUtils.isEmpty(subdistrict)) {
                isDataValid = false;
                autoSubDistrictField.setError("Chưa chọn phường");
            }

            if (TextUtils.isEmpty(street)) {
                isDataValid = false;
                autoStreetField.setError("Chưa chọn tên đường");
            }

            if (TextUtils.isEmpty(housenum)) {
                isDataValid = false;
                houseNumberEditText.setError("Chưa nhập số nhà");
            }

            if (isDataValid) {
                String selectedCityName = autoCitiesField.getText().toString();
                if (!TextUtils.isEmpty(selectedCityName)) {
                    int cityID = getCitiesIdByName(selectedCityName);
                    Cities citys = citiesService.getCityById(cityID);
                    address.setCities(citys);
                }

                String selectedDistrictName = autoDistrictField.getText().toString();
                if (!TextUtils.isEmpty(selectedDistrictName)) {
                    int districtId = getDistrictByName(selectedDistrictName);
                    Districts districts = districtsService.getDistrictById(districtId);
                    address.setDistricts(districts);
                }

                String selectedSubDistrictName = autoSubDistrictField.getText().toString();
                if (!TextUtils.isEmpty(selectedSubDistrictName)) {
                    int subDistrictId = getSubDistrictIdByName(selectedSubDistrictName);
                    SubDistricts subDistricts = subDistrictsService.getSubDistrictById(subDistrictId);
                    address.setSubDistrics(subDistricts);
                }

                String selectedStreetName = autoStreetField.getText().toString();
                if (!TextUtils.isEmpty(selectedStreetName)) {
                    int streetId = getStreetIdByName(selectedStreetName);
                    Streets streets = streetsService.getStreetsById(streetId);
                    address.setStreets(streets);
                }

                address.setHouseNumber(houseNumberEditText.getText().toString());
                address.setIsActive(0);
                long a = addressService.addAddress(address);
                if (a > 0) {
                    Toast.makeText(this, "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    posts.setAddress(address);
                }
            }

            // Set the price
            String priceText = pricePost.getText().toString();
            if (!priceText.isEmpty()) {
                posts.setPrice(Float.parseFloat(priceText));
            }

            // Set the description
            posts.setDescription(desPost.getText().toString());

            // Set the time
            long currentTimeMillis = System.currentTimeMillis();
            Date currentDate = new Date(currentTimeMillis);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.MONTH, 1);
            Date futureDate = calendar.getTime();

            posts.setTimeFrom(currentDate);
            posts.setTimeTo(futureDate);

            // Set the active post
            posts.setActivePost(1);
            posts.setUserAccount(user);

            // Add the post
            long idCurrentPost = postsService.addAPost(posts);
            if (idCurrentPost > 0) {
                Toast.makeText(this, "Thêm bài viết thành công", Toast.LENGTH_SHORT).show();

                // add detail_image
                List<Images> imagesList = imagesService.getAllImagesByName(name);
                for (Images images : imagesList) {
                    try {
                        detail_imageService.addADetailImage(images.getId(),Integer.parseInt(String.valueOf(idCurrentPost)));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }

                // add detail_furniture
                Detail_Furniture detailFurniture = new Detail_Furniture();

                for (Furniture furniture : furnitureListChoose) {
                    try {
                        detailFurniture.setPosts(postsService.getPostById(Integer.parseInt(String.valueOf(idCurrentPost))));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    detailFurniture.setQuantity(1);
                    detailFurniture.setFurniture(furniture);
                    try {
                        detail_furnitureService.addADetailFurniture(detailFurniture);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }

            } else {
                Toast.makeText(this, "Thêm bài viết thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle ActionBar item clicks here
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // Handle the back button click here
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addCities() {
        List<Cities> cityList = citiesService.getAllCitiesList();

        List<String> cityNameList = new ArrayList<>();
        for (Cities city : cityList) {
            cityNameList.add(city.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_dropdown_item_1line, cityNameList);
        autoCitiesField.setAdapter(adapter);
    }

    private void addDistrict(int citiesId) {
        List<Districts> districtsList = districtsService.getAllDistrictsByCitiesId(citiesId);

        List<String> districtNameList = new ArrayList<>();
        for (Districts district : districtsList) {
            districtNameList.add(district.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_dropdown_item_1line, districtNameList);
        autoDistrictField.setAdapter(adapter);
    }

    private void addSubDistrict(int districtId) {
        List<SubDistricts> subDistrictsList = subDistrictsService.getAllSubDistrictsByDistrictId(districtId);

        List<String> subDistrictNameList = new ArrayList<>();
        for (SubDistricts subDistrict : subDistrictsList) {
            subDistrictNameList.add(subDistrict.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_dropdown_item_1line, subDistrictNameList);
        autoSubDistrictField.setAdapter(adapter);
    }

    private void addStreet(int subDistrictId) {
        List<Streets> streetsList = streetsService.getAllStreetsBySubDistrictId(subDistrictId);

        List<String> streetNameList = new ArrayList<>();
        for (Streets street : streetsList) {
            streetNameList.add(street.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_dropdown_item_1line, streetNameList);
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

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void updateRecyclerView(List<Uri> selectedImages) {
        ImageAdapter imageAdapter = new ImageAdapter(selectedImages);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(imageAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    selectedImages.add(imageUri);
                }
                Toast.makeText(this, "Selected " + count + " images", Toast.LENGTH_SHORT).show();
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                selectedImages.add(imageUri);

                Toast.makeText(this, "Selected 1 image", Toast.LENGTH_SHORT).show();
            }
            updateRecyclerView(selectedImages);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Uri imageUri = getImageUriFromBitmap(imageBitmap);
            selectedImages.add(imageUri);
            updateRecyclerView(selectedImages);
        }
    }

    private Uri getImageUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private byte[] getBytesFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        return outputStream.toByteArray();
    }

    private int getSelectedTypeId(FlexboxLayout boxType) {
        for (int i = 0; i < boxType.getChildCount(); i++) {
            View view = boxType.getChildAt(i);
            if (view instanceof RadioGroup) {
                RadioGroup radioGroup = (RadioGroup) view;
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = radioGroup.findViewById(radioButtonId);
                if (radioButton != null) {
                    String selectedTypeName = radioButton.getText().toString();
                    return getTypeByName(selectedTypeName);
                }
            }
        }
        return -1;
    }

    private int getTypeByName(String typeName) {
        List<Type> typeList = typeService.getAllType();

        for (Type type : typeList) {
            if (type.getName().equals(typeName)) {
                return type.getId();
            }
        }
        return -1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission denied: " + permissions[i], Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

