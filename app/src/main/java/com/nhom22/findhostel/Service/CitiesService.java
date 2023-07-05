package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.CitiesDAO;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.YourApplication;

public class CitiesService {
    private final static CitiesDAO CITIES_DAO;

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
}