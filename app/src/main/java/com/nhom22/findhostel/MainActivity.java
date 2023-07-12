package com.nhom22.findhostel;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.Firebase.AddressFirebase;
import com.nhom22.findhostel.Firebase.CitiesFirebase;
import com.nhom22.findhostel.Firebase.DistrictsFirebase;
import com.nhom22.findhostel.Firebase.FirebaseCallbackHandler;
import com.nhom22.findhostel.Firebase.ImageUserAccountFirebase;
import com.nhom22.findhostel.Firebase.StreetsFirebase;
import com.nhom22.findhostel.Firebase.SubDistrictsFirebase;
import com.nhom22.findhostel.Firebase.UserAccountFirebase;
import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.Service.AddressService;
import com.nhom22.findhostel.Service.CitiesService;
import com.nhom22.findhostel.Service.DistrictsService;
import com.nhom22.findhostel.Service.StreetsService;
import com.nhom22.findhostel.Service.SubDistrictsService;
import com.nhom22.findhostel.Service.UserAccountService;
import com.nhom22.findhostel.UI.Account.AccountPageFragment;
import com.nhom22.findhostel.UI.Extension.ExtensionPageFragment;
import com.nhom22.findhostel.UI.Notification.NotificationPageFragment;
import com.nhom22.findhostel.UI.Save.SavePageFragment;
import com.nhom22.findhostel.UI.Search.SearchPageFragment;
import com.nhom22.findhostel.databinding.ActivityMainBinding;

import java.util.List;

import nl.joery.animatedbottombar.AnimatedBottomBar;



public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseCallbackHandler firebaseCallbackHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Context context = this;

        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        binding.navigation.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {
                Fragment selectedFragment = null;
                if (newTab.getId() == R.id.navigation_notification) {
                    selectedFragment = new NotificationPageFragment();
                } else if (newTab.getId() == R.id.navigation_exten) {
                    selectedFragment = new ExtensionPageFragment();
                } else if (newTab.getId() == R.id.navigation_search) {
                    selectedFragment = new SearchPageFragment();
                } else if (newTab.getId() == R.id.navigation_info) {
                    selectedFragment = new SavePageFragment();
                } else if (newTab.getId() == R.id.navigation_account) {

                    selectedFragment = new AccountPageFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, selectedFragment)
                            .commit();
                }
            }

            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {
                // Handle tab reselection if needed
            }
        });

        Fragment defaultFragment = new SearchPageFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, defaultFragment)
                .commit();




    SQLiteDatabase db = databaseHelper.getWritableDatabase();


        createFirebase();
    }

    private void createFirebase(){
        firebaseCallbackHandler = new FirebaseCallbackHandler(this);

        CitiesFirebase citiesFirebase = new CitiesFirebase();
        citiesFirebase.getCities(firebaseCallbackHandler);

        DistrictsFirebase districtsFireBase = new DistrictsFirebase();
        districtsFireBase.getDistricts(firebaseCallbackHandler);

        SubDistrictsFirebase subDistrictsFireBase = new SubDistrictsFirebase();
        subDistrictsFireBase.getSubDistricts(firebaseCallbackHandler);

        StreetsFirebase streetsFirebase = new StreetsFirebase();
        streetsFirebase.getStreets(firebaseCallbackHandler);

        AddressFirebase addressFirebase = new AddressFirebase();
        addressFirebase.getAddress(firebaseCallbackHandler);

        UserAccountFirebase userAccountFirebase = new UserAccountFirebase();
        userAccountFirebase.getUserAccount(firebaseCallbackHandler);
        
    }

    public void onDistrictsLoaded(List<Districts> districts) {
        DistrictsService districtsService = new DistrictsService();
        districtsService.deleteAllDistricts();
        districtsService.resetDistrictsAutoIncrement();
        for (Districts district : districts) {
            districtsService.addDistricts(district);
        }

    }

    public void onError(String errorMessage) {
        Toast.makeText(MainActivity.this,"Thực hiện truy vấn firebase thất bại",Toast.LENGTH_LONG).show();
        Log.d("FirebaseLog",errorMessage);
    }


    public void onCityLoaded(List<Cities> cities) {
        CitiesService citiesService = new CitiesService();
        citiesService.deleteAllCities();
        citiesService.resetCitiesAutoIncrement();
        for (Cities city : cities) {
            citiesService.addCities(city);
            // Thực hiện các xử lý khác với citiesService
        }
    }

    public void onSubDistrictsLoaded(List<SubDistricts> subDistrictsList) {
        SubDistrictsService subDistrictsService = new SubDistrictsService();
        subDistrictsService.deleteAllSubDistricts();
        subDistrictsService.resetSubDistrictsAutoIncrement();
        for (SubDistricts subDistricts : subDistrictsList) {
            subDistrictsService.addSubDistricts(subDistricts);
            // Thực hiện các xử lý khác với citiesService
        }
    }

    public void onStreetsLoaded(List<Streets> streetsList) {
        StreetsService streetsService = new StreetsService();
        streetsService.deleteAllStreets();
        streetsService.resetStreetsAutoIncrement();
        for (Streets streets : streetsList) {
            streetsService.addStreets(streets);
        }
    }

    public void onAddressLoaded(List<Address> addressList) {
        AddressService addressService = new AddressService();
        addressService.deleteAllAddress();
        addressService.resetAddressAutoIncrement();
        for (Address address : addressList) {
            addressService.addAddress(address);
        }
    }

    public void onUserAccountLoaded(List<UserAccount> userAccountList) {
        UserAccountService userAccountService = new UserAccountService();
        userAccountService.deleteAllUserAccount();
        userAccountService.resetUserAccountAutoIncrement();

        for (UserAccount userAccount : userAccountList) {
            userAccountService.addUserAccount(userAccount);

            final UserAccount finalUserAccount = userAccount; // Tạo biến cuối cùng (final) bằng biến userAccount

            ImageUserAccountFirebase.getImageUserAccount(String.valueOf(userAccount.getId()) + ".png",
                    new ImageUserAccountFirebase.ImageDownloadListener() {
                        @Override
                        public void onImageDownloaded(byte[] imageData) {
                            userAccountService.insertImageUserAccount(finalUserAccount.getId(), imageData);
                        }

                        @Override
                        public void onImageDownloadFailed(String errorMessage) {
                            // Xử lý khi có lỗi tải xuống ảnh
                            Log.e("ErrorDowloadImage", "Failed to download image: " + errorMessage);
                        }
                    });
        }
    }
}



