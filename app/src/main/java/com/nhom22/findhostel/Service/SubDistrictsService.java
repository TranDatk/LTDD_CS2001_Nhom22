package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.SubDistrictsDAO;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.YourApplication;

public class SubDistrictsService {
    private final static SubDistrictsDAO SUB_DISTRICTS_DAO;

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        SUB_DISTRICTS_DAO = new SubDistrictsDAO(appContext);
    }

    public SubDistricts getSubDistrictById(int subDistrictsId) {
        if(subDistrictsId >= 0){
            return SUB_DISTRICTS_DAO.getSubDistrictById(subDistrictsId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null subDistrictsId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }
}