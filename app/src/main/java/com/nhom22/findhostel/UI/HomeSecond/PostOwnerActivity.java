package com.nhom22.findhostel.UI.HomeSecond;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;
import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Detail_Furniture;
import com.nhom22.findhostel.Model.Detail_Utilities;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.Model.Type;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.Model.Utilities;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.AddressService;
import com.nhom22.findhostel.Service.CitiesService;
import com.nhom22.findhostel.Service.Detail_FurnitureService;
import com.nhom22.findhostel.Service.Detail_ImageService;
import com.nhom22.findhostel.Service.Detail_UtilitiesService;
import com.nhom22.findhostel.Service.DistrictsService;
import com.nhom22.findhostel.Service.FurnitureService;
import com.nhom22.findhostel.Service.ImagesService;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.Service.StreetsService;
import com.nhom22.findhostel.Service.SubDistrictsService;
import com.nhom22.findhostel.Service.TypeService;
import com.nhom22.findhostel.Service.UserAccountService;
import com.nhom22.findhostel.Service.UtilitiesService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
    private final UtilitiesService utilitiesService = new UtilitiesService();

    private final UserAccountService userAccountService = new UserAccountService();
    private final TypeService typeService = new TypeService();

    private final PostsService postsService = new PostsService();

    private final AddressService addressService = new AddressService();

    private final ImagesService imagesService = new ImagesService();
    private final Detail_ImageService detail_imageService = new Detail_ImageService();
    private final Detail_FurnitureService detail_furnitureService = new Detail_FurnitureService();
    private final Detail_UtilitiesService detail_utilitiesService = new Detail_UtilitiesService();

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
        EditText desPost = findViewById(R.id.descPost);


        FlexboxLayout boxFurni = findViewById(R.id.boxFurni);
        List<Furniture> furnitureList = furnitureService.getAllFurniture();


        List<Furniture> furnitureListChoose = new ArrayList<>();

        if (furnitureList != null && !furnitureList.isEmpty()) {
            for (Furniture furniture : furnitureList) {
                CheckBox newCheckBox = new CheckBox(this);
                newCheckBox.setText(furniture.getName());
                newCheckBox.setTextColor(ContextCompat.getColor(this, R.color.black));
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
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

        FlexboxLayout boxUti = findViewById(R.id.boxUti);
        List<Utilities> utilitiesList = utilitiesService.getAllUtilities();
        List<Detail_Utilities> detail_utilitiesList = new ArrayList<>();

        if (utilitiesList != null && !utilitiesList.isEmpty()) {
            for (Utilities utility : utilitiesList) {
                // Create a new Detail_Utilities object for each utility
                Detail_Utilities detail_utilities = new Detail_Utilities();
                LinearLayout utilityLayout = new LinearLayout(this);
                utilityLayout.setOrientation(LinearLayout.HORIZONTAL);

                // Create the CheckBox for the utility
                CheckBox newCheckBox = new CheckBox(this);
                newCheckBox.setText(utility.getName());
                newCheckBox.setTextColor(ContextCompat.getColor(this, R.color.black));

                // Create the EditText fields for price and unit
                EditText txtPriceUti = new EditText(this);
                EditText txtUnitUti = new EditText(this);
                txtPriceUti.setInputType(InputType.TYPE_CLASS_NUMBER); // Only allow integer input
                txtPriceUti.setTextColor(Color.BLACK);
                txtPriceUti.setHint("Nhập giá");

                txtUnitUti.setTextColor(Color.BLACK);
                txtUnitUti.setHint("Nhập đơn vị");

                // Initially, set the EditText fields to be invisible
                txtPriceUti.setVisibility(View.GONE);
                txtUnitUti.setVisibility(View.GONE);

                // Set layout parameters for each view
                LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                checkBoxParams.weight = 1; // The CheckBox will take 1/3 of the width

                LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                editTextParams.weight = 2; // The EditText fields will take 2/3 of the width

                newCheckBox.setLayoutParams(checkBoxParams);
                txtPriceUti.setLayoutParams(editTextParams);
                txtUnitUti.setLayoutParams(editTextParams);

                // Add the CheckBox and EditText fields to the utility layout
                utilityLayout.addView(newCheckBox);
                utilityLayout.addView(txtPriceUti);
                utilityLayout.addView(txtUnitUti);

                boxUti.addView(utilityLayout);

                // Set up the CheckBox and EditText listeners
                newCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        detail_utilities.setUtilities(utility);
                        txtPriceUti.setVisibility(View.VISIBLE);
                        txtUnitUti.setVisibility(View.VISIBLE);
                    } else {
                        detail_utilities.setUtilities(null);
                        txtPriceUti.setVisibility(View.GONE);
                        txtUnitUti.setVisibility(View.GONE);
                    }
                });

                txtPriceUti.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String price = txtPriceUti.getText().toString();
                        if (price.isEmpty()) {
                            txtPriceUti.setError("Chưa nhập giá của tiện ích");
                            return;
                        }

                        try {
                            float priceA = Float.parseFloat(price);
                            if (priceA <= 0) {
                                txtPriceUti.setError("Giá phải lớn hơn 0");
                                return;
                            }
                            detail_utilities.setPrice(Double.parseDouble(price));
                        } catch (NumberFormatException e) {
                            txtPriceUti.setError("Giá không hợp lệ");
                        }
                    }
                });

                txtUnitUti.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String unit = txtUnitUti.getText().toString();
                        if (unit.isEmpty()) {
                            txtUnitUti.setError("Chưa nhập đơn vị của tiện ích");
                        } else {
                            detail_utilities.setUnit(unit);
                        }
                    }
                });

                detail_utilitiesList.add(detail_utilities);
            }
        } else {
            Toast.makeText(this, "Không có tiện ích", Toast.LENGTH_SHORT).show();
        }


        Button test = (Button) findViewById(R.id.test3);

        test.setOnClickListener(v -> {
            for (Detail_Utilities detail_utilities : detail_utilitiesList) {
                Double price = detail_utilities.getPrice();
                String unit = detail_utilities.getUnit();
                Toast.makeText(this, unit + " - " + String.valueOf(price), Toast.LENGTH_SHORT).show();
            }

        });


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


        Button galleryButton = findViewById(R.id.galleryButton);
        Button cameraButton = findViewById(R.id.cameraButton);
        Button upImage = findViewById(R.id.btnUpImage);
        recyclerView = findViewById(R.id.recyclerView);
        selectedImages = new ArrayList<>();
        AtomicBoolean isUpdateImageButton = new AtomicBoolean(false);
        List<Integer> countImage = new ArrayList<>();
        upImage.setOnClickListener(v -> {
            for (Uri uri : selectedImages) {
                Images images = new Images();
                images.setIsActive(1);
                images.setName(null);
                try {
                    byte[] a = getBytesFromUri(uri);
                    images.setImage(a);
                    long result = imagesService.addAImages(images);
                    if (result > 0) {
                        countImage.add((int) result);
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        isUpdateImageButton.set(true);
                    }
                } catch (IOException e) {
                    Toast.makeText(this, "Error adding image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
            FlexboxLayout.LayoutParams flexLayoutParams = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.MATCH_PARENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
            );

            FlexboxLayout flexboxLayout = new FlexboxLayout(this);
            flexboxLayout.setPadding(0, 10, 30, 15);
            flexboxLayout.setFlexDirection(FlexDirection.ROW);
            flexboxLayout.setFlexWrap(FlexWrap.WRAP);
            flexboxLayout.setJustifyContent(JustifyContent.FLEX_START);
            flexboxLayout.setLayoutParams(flexLayoutParams);

            RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setLayoutParams(new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
            ));

            FlexboxLayout.LayoutParams radioButtonLayoutParams = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
            );
            radioButtonLayoutParams.setMargins(0, 0, 10, 10);

            for (Type type : typeList) {
                RadioButton newRadioButton = new RadioButton(this);
                newRadioButton.setText(type.getName());
                newRadioButton.setTextColor(ContextCompat.getColor(this, R.color.black));
                newRadioButton.setLayoutParams(radioButtonLayoutParams);

                newRadioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        Type selectedType = typeService.getTypeById(type.getId());
                        posts.setType(selectedType);
                    }
                });

                radioGroup.addView(newRadioButton);
            }

            flexboxLayout.addView(radioGroup);
            boxType.addView(flexboxLayout);
        } else {
            Button btn = new Button(this);
            btn.setText("Thêm loại phòng");
            boxType.addView(btn);
        }


        Button btnAddPost = findViewById(R.id.btnAddPost);
        btnAddPost.setOnClickListener(v -> {
            String namePost = namePostEditText.getText().toString().trim();
            String priceText = pricePost.getText().toString().trim();
            String desPostA = desPost.getText().toString().trim();

            // Validate namePost
            if (namePost.isEmpty()) {
                namePostEditText.setError("Chưa nhập tiêu đề bài viết");
                return;
            }

            // Validate priceText
            if (priceText.isEmpty()) {
                pricePost.setError("Chưa nhập giá");
                return;
            }

            // Validate desPostA
            if (desPostA.isEmpty()) {
                desPost.setError("Chưa nhập mô tả");
                return;
            }

            try {
                float price = Float.parseFloat(priceText);
                if (price <= 0) {
                    pricePost.setError("Giá phải lớn hơn 0");
                    return;
                }
            } catch (NumberFormatException e) {
                pricePost.setError("Giá không hợp lệ");
                return;
            }

            // Validate furniture selection
            boolean isFurnitureSelected = false;
            for (int i = 0; i < boxFurni.getChildCount(); i++) {
                View view = boxFurni.getChildAt(i);
                if (view instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) view;
                    if (checkBox.isChecked()) {
                        isFurnitureSelected = true;
                        break;
                    }
                }
            }

            if (!isFurnitureSelected) {
                Toast.makeText(this, "Chưa chọn nội thất", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate utilities selection
            boolean isUtilitiesSelected = false;
            for (int i = 0; i < boxUti.getChildCount(); i++) {
                View view = boxUti.getChildAt(i);
                if (view instanceof LinearLayout) {
                    LinearLayout utilityLayout = (LinearLayout) view;
                    CheckBox checkBox = (CheckBox) utilityLayout.getChildAt(0); // The first child is the CheckBox
                    if (checkBox.isChecked()) {
                        isUtilitiesSelected = true;
                        break;
                    }
                }
            }

            if (!isUtilitiesSelected) {
                Toast.makeText(this, "Chưa chọn tiện ích", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate image
            if (!isUpdateImageButton.get()) {
                Toast.makeText(this, "Bạn chưa thêm ảnh vào trong dữ liệu", Toast.LENGTH_SHORT).show();
                return;
            }

            // Set the address
            Address address = new Address();
            String city = autoCitiesField.getText().toString();
            String district = autoDistrictField.getText().toString();
            String subdistrict = autoSubDistrictField.getText().toString();
            String street = autoStreetField.getText().toString();
            String housenum = houseNumberEditText.getText().toString();

            if (city.isEmpty()) {
                autoCitiesField.setError("Chưa chọn thành phố");
                return;
            }

            if (district.isEmpty()) {
                autoDistrictField.setError("Chưa chọn quận");
                return;
            }

            if (subdistrict.isEmpty()) {
                autoSubDistrictField.setError("Chưa chọn phường");
                return;
            }

            if (street.isEmpty()) {
                autoStreetField.setError("Chưa chọn tên đường");
                return;
            }

            if (housenum.isEmpty()) {
                houseNumberEditText.setError("Chưa nhập số nhà");
                return;
            }
            runOnUiThread(() -> {

                String selectedCityName = autoCitiesField.getText().toString();
                if (!TextUtils.isEmpty(selectedCityName)) {
                    int cityID = getCitiesIdByName(selectedCityName);
                    Cities cityObj = citiesService.getCityById(cityID);
                    address.setCities(cityObj);
                }

                String selectedDistrictName = autoDistrictField.getText().toString();
                if (!TextUtils.isEmpty(selectedDistrictName)) {
                    int districtId = getDistrictByName(selectedDistrictName);
                    Districts districtObj = districtsService.getDistrictById(districtId);
                    address.setDistricts(districtObj);
                }

                String selectedSubDistrictName = autoSubDistrictField.getText().toString();
                if (!TextUtils.isEmpty(selectedSubDistrictName)) {
                    int subDistrictId = getSubDistrictIdByName(selectedSubDistrictName);
                    SubDistricts subDistrictObj = subDistrictsService.getSubDistrictById(subDistrictId);
                    address.setSubDistrics(subDistrictObj);
                }

                String selectedStreetName = autoStreetField.getText().toString();
                if (!TextUtils.isEmpty(selectedStreetName)) {
                    int streetId = getStreetIdByName(selectedStreetName);
                    Streets streetObj = streetsService.getStreetsById(streetId);
                    address.setStreets(streetObj);
                }

                address.setHouseNumber(housenum);
                address.setIsActive(1);
                long addressId = addressService.addAddress(address);
                if (addressId > 0) {
                    Toast.makeText(this, "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    posts.setAddress(address);
                } else {
                    Toast.makeText(this, "Thêm địa chỉ thất bại", Toast.LENGTH_SHORT).show();
                }

                // Set the post name
                posts.setPostName(namePost);

                // Set the price
                posts.setPrice(Float.parseFloat(priceText));

                // Set the description
                posts.setDescription(desPostA);


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

                try {
                    long postId = postsService.addAPost(posts);
                    if (postId > 0) {
                        Toast.makeText(this, "Thêm bài viết thành công", Toast.LENGTH_SHORT).show();
                        for (Integer c : countImage) {
                            try {
                                detail_imageService.addADetailImage(c, Integer.parseInt(String.valueOf(postId)));
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        // Add detail_furniture
                        Detail_Furniture detailFurniture = new Detail_Furniture();
                        for (Furniture furniture : furnitureListChoose) {
                            try {
                                detailFurniture.setPosts(postsService.getPostById(Integer.parseInt(String.valueOf(postId))));
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            detailFurniture.setQuantity(1);
                            detailFurniture.setFurniture(furniture);
                            try {
                                long check1 = detail_furnitureService.addADetailFurniture(detailFurniture);
                                if (check1 > 0) {
                                    Toast.makeText(this, "thêm nội thất thành công", Toast.LENGTH_SHORT).show();
                                }
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        // Add detail_utilities
                        for (Detail_Utilities detailUtilities : detail_utilitiesList) {
                            if (detailUtilities.getUtilities() != null) {
                                try {
                                    detailUtilities.setPosts(postsService.getPostById(Integer.parseInt(String.valueOf(postId))));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                long check2 = detail_utilitiesService.addADetailUtilities(detailUtilities);
                                if (check2 > 0) {
                                    Toast.makeText(this, "thêm tiện ích thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    } else {
                        Toast.makeText(this, "Thêm bài viết thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d("error", e.getMessage());
                }
            });
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
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

