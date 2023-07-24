package com.nhom22.findhostel.UI.HomeSecond;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.Model.Type;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.CitiesService;
import com.nhom22.findhostel.Service.DistrictsService;
import com.nhom22.findhostel.Service.FurnitureService;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.Service.StreetsService;
import com.nhom22.findhostel.Service.SubDistrictsService;
import com.nhom22.findhostel.Service.TypeService;
import com.nhom22.findhostel.Service.UserAccountService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        EditText houseNumberEditText = findViewById(R.id.houseNumberEditText);
        autoCitiesField = findViewById(R.id.autoCitiesField);
        autoDistrictField = findViewById(R.id.autoDistrictField);
        autoSubDistrictField =findViewById(R.id.autoSubDistrictField);
        autoStreetField = findViewById(R.id.autoStreetField);
        EditText namePostEditText = findViewById(R.id.txtNamePost);
        EditText pricePost = findViewById(R.id.txtPrice);
        EditText desPost = findViewById(R.id.editTextLanguage);

        FlexboxLayout boxFurni = findViewById(R.id.boxFurni);
        List<Furniture> furnitureList = furnitureService.getAllFurniture();

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

                boxFurni.addView(newCheckBox);
            }
        } else {
            Toast.makeText(this, "Không có tiện ích", Toast.LENGTH_SHORT).show();
        }



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


        Spinner spinnerLanguage = findViewById(R.id.spinnerLanguage);

        String[] languages = {"Tiếng anh","Tiếng việt"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages);
        spinnerLanguage.setAdapter(adapter);

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = languages[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Address address = new Address();
        String selectedCityName = autoCitiesField.getText().toString();
        if (!TextUtils.isEmpty(selectedCityName)) {
            int cityID = getCitiesIdByName(selectedCityName);
            Cities city = citiesService.getCityById(cityID);
            address.setCities(city);
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
        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, 1);
        Date futureDate = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("en"));
        String formattedCurrentDate = sdf.format(currentDate);
        String formattedFutureDate = sdf.format(futureDate);

        Posts posts = new Posts();
        posts.setPostName(namePostEditText.getText().toString());
        posts.setAddress(address);
        String priceText = pricePost.getText().toString();
        if (!priceText.isEmpty()) {
            posts.setPrice(Float.parseFloat(priceText));
        } else { }
        posts.setDescription(desPost.getText().toString());

        try {
            Date parsedCurrentDate = sdf.parse(formattedCurrentDate);
            Date parsedFutureDate = sdf.parse(formattedFutureDate);
            posts.setTimeFrom(parsedCurrentDate);
            posts.setTimeTo(parsedFutureDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        posts.setActivePost(1);
        posts.setUserAccount(user);



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

                newRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            Type selectedType = typeService.getTypeById(type.getId());
                            posts.setType(selectedType);
                        }
                    }
                });

                radioGroup.addView(newRadioButton);
            }

            boxType.addView(radioGroup);
        } else {
            Button btn = new Button(this);
            btn.setText("Thêm loại phòng");
            boxType.addView(btn);
        }


        Button btnAddPost = findViewById(R.id.btnAddPost);
        btnAddPost.setOnClickListener(v -> {
            long checkAdd = postsService.addAPost(posts);
            if (checkAdd > 0) {
                Toast.makeText(this, "Thêm bài viết thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Thêm bài viết thất bại", Toast.LENGTH_SHORT).show();
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

}