package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.CitiesDAO;
import com.nhom22.findhostel.Data.DistricsDAO;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.YourApplication;

import java.util.ArrayList;
import java.util.List;

public class DistrictsService {
    private final static DistricsDAO DISTRICS_DAO;

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        DISTRICS_DAO = new DistricsDAO(appContext);
    }

    public Districts getDistrictById(int districtsId) {
        if(districtsId >= 0){
            return DISTRICS_DAO.getDistrictById(districtsId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null districtsId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public List<Districts> getAllDistricts() {
        try {
            if (DISTRICS_DAO != null) {
                return DISTRICS_DAO.getAllDistricts();
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
