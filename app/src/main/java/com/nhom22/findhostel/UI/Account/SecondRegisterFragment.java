package com.nhom22.findhostel.UI.Account;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.nhom22.findhostel.Data.CitiesDAO;
import com.nhom22.findhostel.Data.DatabaseHelper;

import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.Service.CitiesService;
import com.nhom22.findhostel.Service.DistrictsService;
import com.nhom22.findhostel.Service.StreetsService;
import com.nhom22.findhostel.Service.SubDistrictsService;
import com.nhom22.findhostel.Service.UserAccountService;
import com.nhom22.findhostel.databinding.FragmentSecondRegisterFragmentBinding;

import java.util.ArrayList;
import java.util.List;


public class SecondRegisterFragment extends Fragment {

    private FragmentSecondRegisterFragmentBinding binding;
    private EditText houseNumberEditText;
    private Button registerButton;
    private AutoCompleteTextView autoCitiesField;
    private AutoCompleteTextView autoDistrictField;
    private AutoCompleteTextView autoSubDistrictField;
    private AutoCompleteTextView autoStreetField;

    private ClipboardManager clipboardManager;
    private DatabaseHelper databaseHelper;

    private CitiesService citiesService = new CitiesService();
    private DistrictsService districtsService = new DistrictsService();
    private SubDistrictsService subDistrictsService = new SubDistrictsService();
    private StreetsService streetsService = new StreetsService();

    private UserAccountService userAccountService = new UserAccountService();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecondRegisterFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        databaseHelper = new DatabaseHelper(getActivity());

        houseNumberEditText = binding.houseNumberEditText;
        registerButton = binding.registerButton;
        autoCitiesField = binding.autoCitiesField;
        autoDistrictField = binding.autoDistrictField;
        autoSubDistrictField = binding.autoSubDistrictField;
        autoStreetField = binding.autoStreetField;

        addCities();
        addDistrict();
        addSubDistrict();
        addStreet();

        autoCitiesField.setEnabled(true);
        autoDistrictField.setEnabled(false);
        autoSubDistrictField.setEnabled(false);
        autoStreetField.setEnabled(false);

        autoCitiesField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Enable the district field
                autoDistrictField.setEnabled(true);
                // Disable the sub-district and street fields
                autoSubDistrictField.setEnabled(false);
                autoStreetField.setEnabled(false);
            }
        });

        autoDistrictField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Enable the sub-district field
                autoSubDistrictField.setEnabled(true);
                // Disable the street field
                autoStreetField.setEnabled(false);
            }
        });

        autoSubDistrictField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Enable the street field
                autoStreetField.setEnabled(true);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the user input from the clipboard
                ClipData clipData = clipboardManager.getPrimaryClip();
                if (clipData != null && clipData.getItemCount() > 0) {
                    ClipData.Item item = clipData.getItemAt(0);
                    String clipboardData = item.getText().toString();
                    String[] userData = clipboardData.split("\\|");

                    // Get the user data from the clipboard
                    String email = userData[0];
                    String username = userData[1];
                    String password = userData[2];


                    autoCitiesField = binding.autoCitiesField;

                    int cityID = -1;
                    Cities selectedCity = null;
                    int selectedItemPosition = autoCitiesField.getListSelection();
                    if (selectedItemPosition != AdapterView.INVALID_POSITION) {
                        selectedCity = (Cities) autoCitiesField.getAdapter().getItem(selectedItemPosition);
                    }


                    int districtsId = -1;
                    Districts selectedDistrict = null;
                    int selectedDistrictPosition = autoDistrictField.getListSelection();
                    if (selectedDistrictPosition != AdapterView.INVALID_POSITION) {
                        // Retrieve the item object from the adapter
                        selectedDistrict = (Districts) autoDistrictField.getAdapter().getItem(selectedDistrictPosition);

                    }

                    int subDistrictId = -1;
                    SubDistricts selectedSubDistrict = null;
                    int selectedSubDistrictPosition = autoSubDistrictField.getListSelection();
                    if (selectedSubDistrictPosition != AdapterView.INVALID_POSITION) {
                        selectedSubDistrict = (SubDistricts) autoSubDistrictField.getAdapter().getItem(selectedSubDistrictPosition);

                    }

                    int streetId = -1;
                    Streets selectedStreet = null;
                    int selectedStreetPosition = autoStreetField.getListSelection();
                    if (selectedStreetPosition != AdapterView.INVALID_POSITION) {
                        selectedStreet = (Streets) autoStreetField.getAdapter().getItem(selectedStreetPosition);

                    }

                    String numHouse = houseNumberEditText.getText().toString();


                    Address address = new Address(1,numHouse,1,selectedCity,selectedDistrict,selectedSubDistrict,selectedStreet);

                    // Perform the registration operation with all the new user data
                    UserAccount user = new UserAccount();
                    user.setAddress(address);
                    user.setEmail(email);
                    user.setUsername(username);
                    user.setPassword(password);
                    long userId = userAccountService.addUserAccount(user);
                    if (userId != -1) {
                        Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    private void addCities() {
        List<Cities> cityList = citiesService.getAllCitiesList();

        List<String> cityNameList = new ArrayList<>();
        for (Cities city : cityList) {
            cityNameList.add(city.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, cityNameList);
        autoCitiesField.setAdapter(adapter);
    }

    private void addDistrict() {
        List<Districts> districtsList = districtsService.getAllDistricts();

        List<String> districtNameList = new ArrayList<>();
        for (Districts district : districtsList) {
            districtNameList.add(district.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, districtNameList);
        autoDistrictField.setAdapter(adapter);
    }

    private void addSubDistrict() {
        List<SubDistricts> subDistrictsList = subDistrictsService.getAllSubDistricts();

        List<String> subDistrictNameList = new ArrayList<>();
        for (SubDistricts subDistrict : subDistrictsList) {
            subDistrictNameList.add(subDistrict.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, subDistrictNameList);
        autoSubDistrictField.setAdapter(adapter);
    }

    private void addStreet() {
        List<Streets> streetsList = streetsService.getAllStreets();

        List<String> streetNameList = new ArrayList<>();
        for (Streets street : streetsList) {
            streetNameList.add(street.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, streetNameList);
        autoStreetField.setAdapter(adapter);
    }


    private long insertUser(String email, String username, String password, String city, String district,String subDistrict, String street, String houseNumber) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

       return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}