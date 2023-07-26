package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.StreetsDAO;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.YourApplication;

import java.util.ArrayList;
import java.util.List;

public class StreetsService {
    private final static StreetsDAO STREETS_DAO;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    private final DatabaseReference streetsRef = database.getReference("streets");

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        STREETS_DAO = new StreetsDAO(appContext);
    }

    public Streets getStreetsById(Integer streetsId) {
        if(streetsId >= 0){
            return STREETS_DAO.getStreetsById(streetsId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null streetsId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public List<Streets> getAllStreets() {
        try {
            if (STREETS_DAO != null) {
                return STREETS_DAO.getAllStreets();
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Streets> getAllStreetsBySubDistrictId(int subDistrictId) {
        try {
            if (STREETS_DAO != null) {
                return STREETS_DAO.getAllStreetsBySubDistrictId(subDistrictId);
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public long addStreets(Streets streets) {
        if(streets != null){
            long result = STREETS_DAO.addStreets(streets);
            if (result > 0){
                streets.setId(Integer.parseInt(String.valueOf(result)));
                streetsRef.child(String.valueOf(streets.getId())).setValue(streets);
            }else{
                Log.e("addStreets", String.valueOf(result));
            }
            return result;
        }else{
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null streets", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }

    public void deleteAllStreets() {
        STREETS_DAO.deleteAllStreets();
    }

    public void resetStreetsAutoIncrement() {
        STREETS_DAO.resetStreetsAutoIncrement();
    }
}
