package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.UtilitiesDAO;
import com.nhom22.findhostel.Model.Utilities;
import com.nhom22.findhostel.YourApplication;

import java.util.List;

public class UtilitiesService {
    private final static UtilitiesDAO UTILITIES_DAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference utilitiesRef = database.getReference("utilities");

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        UTILITIES_DAO = new UtilitiesDAO(appContext);
    }

    public Utilities getUtilitiesById(int utilitiesId) {
        if(utilitiesId >= 0){
            return UTILITIES_DAO.getUtilitiesById(utilitiesId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null utilitiesId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public List<Utilities> getAllUtilities() {
        return UTILITIES_DAO.getAllUtilities();
    }

    public long addAUtilities(Utilities utilities) {
        if(utilities != null){
            long result = UTILITIES_DAO.addAUtilities(utilities);
            if(result < 1){
                Log.d("AddUtilities", "utilities uploaded failed");
            }else{
                Utilities utilitiesFirebase = new Utilities();
                utilitiesFirebase.setId(Integer.parseInt(String.valueOf(result)));
                utilitiesFirebase.setName(utilities.getName());
                utilitiesFirebase.setIsActive(utilities.getIsActive());

                utilitiesRef.child(String.valueOf(utilitiesFirebase.getId())).setValue(utilitiesFirebase);
            }
            return result;
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null utilities", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }

    public void deleteAllUtilities() {
        UTILITIES_DAO.deleteAllUtilities();
    }

    public void resetUtilitiesAutoIncrement() {
        UTILITIES_DAO.resetUtilitiesAutoIncrement();
    }
}
