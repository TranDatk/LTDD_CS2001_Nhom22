package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.CitiesDAO;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.YourApplication;

import java.util.ArrayList;
import java.util.List;

public class CitiesService {
    private final static CitiesDAO CITIES_DAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference citiesRef = database.getReference("cities");


    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        CITIES_DAO = new CitiesDAO(appContext);
    }

    public Cities getCityById(int citiesId) {
        if(citiesId >= 0){
            return CITIES_DAO.getCityById(citiesId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null citiesId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }


    public List<Cities> getAllCitiesList() {
        try {
            if (CITIES_DAO != null) {
                return CITIES_DAO.getAllCities();
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public long addCities(Cities city) {
        if(city != null){
            long result = CITIES_DAO.addCities(city);
            if(result < 1){
                Log.d("AddCities", "cities uploaded failed");
            }else{
                Cities citiesFirebase = new Cities();
                citiesFirebase.setId(Integer.parseInt(String.valueOf(result)));
                citiesFirebase.setName(city.getName());
                citiesFirebase.setIsActive(city.getIsActive());

                citiesRef.child(String.valueOf(citiesFirebase.getId())).setValue(citiesFirebase);
            }
            return result; // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null city", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }

    public void deleteAllCities() {
        CITIES_DAO.deleteAllCities();
    }

    public void resetCitiesAutoIncrement() {
        CITIES_DAO.resetCitiesAutoIncrement();
    }
}
