package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.DistricsDAO;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.YourApplication;

import java.util.ArrayList;
import java.util.List;

public class DistrictsService {
    private final static DistricsDAO DISTRICS_DAO;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference districtsRef = database.getReference("districts");


    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        DISTRICS_DAO = new DistricsDAO(appContext);
    }

    public Districts getDistrictById(int districtsId) {
        if (districtsId >= 0) {
            return DISTRICS_DAO.getDistrictById(districtsId); // -1 Unsuccessful, >0 Successful
        } else {
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

    public List<Districts> getAllDistrictsByCitiesId(int citiesId) {
        try {
            if (DISTRICS_DAO != null) {
                return DISTRICS_DAO.getAllDistrictsByCitiesId(citiesId);
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public long addDistricts(Districts district) {
        CitiesService citiesService = new CitiesService();
        if (district == null) {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null districts", Toast.LENGTH_SHORT).show();
            return -1; // Trả về -1 để chỉ ra thao tác không thành công
        } else if (citiesService.getCityById(district.getCities().getId()) == null) {
            Toast.makeText(YourApplication.getInstance().getApplicationContext(),
                    "Func addDistricts in DistrictsService: input district.getCities.getId Null", Toast.LENGTH_SHORT).show();
            return -1;
        }

        long result = DISTRICS_DAO.addDistricts(district);

        if(result < 1){
            Log.e("addDistricts", "districts uploaded failed");
        }else{
            Districts districtsFirebase = new Districts();
            districtsFirebase.setId(Integer.parseInt(String.valueOf(result)));
            districtsFirebase.setName(district.getName());
            districtsFirebase.setIsActive(district.getIsActive());
            districtsFirebase.setCities(district.getCities());

            districtsRef.child(String.valueOf(districtsFirebase.getId())).setValue(districtsFirebase);
        }
        return result; // Trả về kết quả từ phương thức addDistricts() trong DAO
    }

    public void deleteAllDistricts() {
        DISTRICS_DAO.deleteAllDistricts();
    }

    public void resetDistrictsAutoIncrement() {
        DISTRICS_DAO.resetDistrictsAutoIncrement();
    }
}
