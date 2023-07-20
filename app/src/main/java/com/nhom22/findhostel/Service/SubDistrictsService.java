package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.SubDistrictsDAO;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.YourApplication;

import java.util.ArrayList;
import java.util.List;

public class SubDistrictsService {
    private final static SubDistrictsDAO SUB_DISTRICTS_DAO;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference subDistrictsRef = database.getReference("sub_districts");

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

    public List<SubDistricts> getAllSubDistricts() {
        try {
            if (SUB_DISTRICTS_DAO != null) {
                return SUB_DISTRICTS_DAO.getAllSubDistricts();
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<SubDistricts> getAllSubDistrictsByDistrictId(int districtId) {
        try {
            if (SUB_DISTRICTS_DAO != null) {
                return SUB_DISTRICTS_DAO.getAllSubDistrictsByDistrictsId(districtId);
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public long addSubDistricts(SubDistricts subDistricts) {
        DistrictsService districtsService = new DistrictsService();
        if (subDistricts == null) {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null subdistricts", Toast.LENGTH_SHORT).show();
            return -1; // Trả về -1 để chỉ ra thao tác không thành công
        } else if (districtsService.getDistrictById(subDistricts.getDistricts().getId()) == null) {
            Toast.makeText(YourApplication.getInstance().getApplicationContext(),
                    "Func addSubDistricts in SubDistrictsService: input subDistrict.getSubDistricts.getId Null",
                    Toast.LENGTH_SHORT).show();
            return -1;
        }

        long result = SUB_DISTRICTS_DAO.addSubDistricts(subDistricts);

        if(result < 1){
            Log.e("addSubDistricts", "subDistricts uploaded failed");
        }else{
            SubDistricts subDistrictsFirebase = new SubDistricts();
            subDistrictsFirebase.setId(Integer.parseInt(String.valueOf(result)));
            subDistrictsFirebase.setName(subDistricts.getName());
            subDistrictsFirebase.setIsActive(subDistricts.getIsActive());
            subDistrictsFirebase.setDistricts(subDistricts.getDistricts());

            subDistrictsRef.child(String.valueOf(subDistrictsFirebase.getId())).setValue(subDistrictsFirebase);
        }
        return result; // Trả về kết quả từ phương thức addDistricts() trong DAO
    }

    public void deleteAllSubDistricts() {
        SUB_DISTRICTS_DAO.deleteAllSubDistricts();
    }

    public void resetSubDistrictsAutoIncrement() {
        SUB_DISTRICTS_DAO.resetSubDistrictsAutoIncrement();
    }
}
