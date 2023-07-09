package com.nhom22.findhostel.UI.Account;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.CitiesService;
import com.nhom22.findhostel.Service.DistrictsService;
import com.nhom22.findhostel.Service.StreetsService;
import com.nhom22.findhostel.Service.SubDistrictsService;
import com.nhom22.findhostel.databinding.FragmentSecondRegisterFragmentBinding;

import java.util.ArrayList;
import java.util.List;


public class SecondRegisterFragment extends Fragment {


    private FragmentSecondRegisterFragmentBinding binding;
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



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecondRegisterFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        houseNumberEditText = binding.houseNumberEditText;
        Button nextButton = binding.nextButton;
        autoCitiesField = binding.autoCitiesField;
        autoDistrictField = binding.autoDistrictField;
        autoSubDistrictField = binding.autoSubDistrictField;
        autoStreetField = binding.autoStreetField;

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

        nextButton.setOnClickListener(v -> {
            ThirdRegisterFragment thirdRegisterFragment = new ThirdRegisterFragment();
            Bundle dataBundle = new Bundle();

            ClipData clipData = clipboardManager.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0) {
                ClipData.Item item = clipData.getItemAt(0);
                String clipboardData = item.getText().toString();
                String[] userData = clipboardData.split("\\|");

                String selectedCityName = autoCitiesField.getText().toString();
                if (!TextUtils.isEmpty(selectedCityName)) {
                    int cityID = getCitiesIdByName(selectedCityName);
                    dataBundle.putInt("citiesId", cityID);
                }

                String selectedDistrictName = autoDistrictField.getText().toString();
                if (!TextUtils.isEmpty(selectedDistrictName)) {
                    int districtId = getDistrictByName(selectedDistrictName);
                    dataBundle.putInt("districtId", districtId);
                    dataBundle.putString("selectedDistrictName", selectedDistrictName);
                }

                String selectedSubDistrictName = autoSubDistrictField.getText().toString();
                if (!TextUtils.isEmpty(selectedSubDistrictName)) {
                    int subDistrictId = getSubDistrictIdByName(selectedSubDistrictName);
                    dataBundle.putInt("subDistrictId", subDistrictId);
                    dataBundle.putString("selectedSubDistrictName", selectedSubDistrictName);
                }

                String selectedStreetName = autoStreetField.getText().toString();
                if (!TextUtils.isEmpty(selectedStreetName)) {
                    int streetId = getStreetIdByName(selectedStreetName);
                    dataBundle.putInt("streetId", streetId);
                    dataBundle.putString("selectedStreetName", selectedStreetName);
                }

                String email = userData[0];
                String username = userData[1];
                String password = userData[2];
                String houseNumber = houseNumberEditText.getText().toString();

                dataBundle.putString("email", email);
                dataBundle.putString("username", username);
                dataBundle.putString("password", password);
                dataBundle.putString("houseNumber", houseNumber);

                thirdRegisterFragment.setArguments(dataBundle);

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, thirdRegisterFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}