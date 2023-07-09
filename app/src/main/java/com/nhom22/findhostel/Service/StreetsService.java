package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.StreetsDAO;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.YourApplication;

import java.util.ArrayList;
import java.util.List;

public class StreetsService {
    private final static StreetsDAO STREETS_DAO;

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
}
