package com.nhom22.findhostel;


import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.Firebase.AddressFirebase;
import com.nhom22.findhostel.Firebase.CitiesFirebase;
import com.nhom22.findhostel.Firebase.DistrictsFirebase;
import com.nhom22.findhostel.Firebase.FirebaseCallbackHandler;
import com.nhom22.findhostel.Firebase.ImageUser;
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
import java.io.ByteArrayOutputStream;


import nl.joery.animatedbottombar.AnimatedBottomBar;

import java.util.List;



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


//        long cities = databaseHelper.addCity("Hồ chí minh", 1);
//        long cities1 = databaseHelper.addCity("Hà nội", 1);
//        long cities2 = databaseHelper.addCity("Kon tum", 1);
//
//        long districtId1 = databaseHelper.addDistrict("Quận 1", 1, 1);
//        long districtId2 = databaseHelper.addDistrict("Quận 2", 1, 1);
//        long districtId3 = databaseHelper.addDistrict("Quận 3", 1, 1);

//        long subDistrictsId1 =  databaseHelper.addSubDistricts("Phường 1 Quận 1", 1, 1);
//        long subDistrictsId2 =  databaseHelper.addSubDistricts("Phường 2 Quận 1", 1, 1);
//        long subDistrictsId3 =  databaseHelper.addSubDistricts("Phường 1 Quận 2", 1, 2);
//        long subDistrictsId4 =  databaseHelper.addSubDistricts("Phường 2  Quận 2", 1, 2);
//        long subDistrictsId5 =  databaseHelper.addSubDistricts("Phường 1 Quận 3", 1, 3);
//        long subDistrictsId6 =  databaseHelper.addSubDistricts("Phường 2  Quận 3", 1, 3);
//
//        long streetId1 =  databaseHelper.addStreet("Đường 1 phường 1 quận 1", 1, 1);
//        long streetId2 =  databaseHelper.addStreet("Đường 1 phuờng 2 quận 1", 1, 2);
//        long streetId3 =  databaseHelper.addStreet("Đường 1 phường 1 quận 2", 1, 3);
//        long streetId4 =  databaseHelper.addStreet("Đường 1 phuờng 2 quận 2", 1, 4);


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

    public static byte[] convertImageToByteArray(Context context, int resourceId) {
        byte[] imageBytes = null;

        try {
            Resources resources = context.getResources();
            Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);
            Log.e("bitmapImage", bitmap.toString());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageBytes = stream.toByteArray();

            stream.close();
        } catch (Exception e) {
            Log.e("ImageUtil", "Error converting image to byte array: " + e.getMessage());
        }

        return imageBytes;
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
        }
    }

    public void onImageUserLoaded(List<ImageUser> imageUserList) {
        UserAccountService userAccountService = new UserAccountService();
        for (ImageUser imageUser : imageUserList) {
            userAccountService.insertImageUserAccount(imageUser.getUserId(),
                    Base64.decode(imageUser.getBase64Image(), Base64.DEFAULT));
        }
    }
}



